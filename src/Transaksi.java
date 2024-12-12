import java.sql.*;

public class Transaksi extends Barang {
    private int jumlahBeli;
    private double total;
    private String namaKasir;
    private String noFaktur;


    public Transaksi(String kodeBarang, String namaBarang, double hargaBarang, int jumlahBeli, String namaKasir, String noFaktur) {
        super(kodeBarang, namaBarang, hargaBarang);
        this.jumlahBeli = jumlahBeli;
        this.namaKasir = namaKasir;
        this.noFaktur = noFaktur;
        this.total = hargaBarang * jumlahBeli;
    }

    //untuk memvalidasi apakah kodeBarang ada di tabel barang
    private boolean iskodeBarangValid(String kodeBarang) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM barang WHERE kodeBarang = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, kodeBarang);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Jika hasil query lebih besar dari 0, berarti kodeBarang ada di tabel barang
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Jika terjadi error atau kodeBarang tidak ditemukan
    }


    // Menyimpan transaksi ke database
    public void saveToDatabase() {
          // Validasi kode barang sebelum menyimpan
          if (!iskodeBarangValid(this.kodeBarang)) {
            System.out.println("Error: Kode barang '" + this.kodeBarang + "' tidak ditemukan di tabel barang.");
            return;
        }
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO transaksi (noFaktur, kodeBarang, jumlahBeli, total, namaKasir, waktuTransaksi) VALUES (?, ?, ?, ?, ?, NOW())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, this.noFaktur);
                stmt.setString(2, this.kodeBarang);
                stmt.setInt(3, this.jumlahBeli);
                stmt.setDouble(4, this.total);
                stmt.setString(5, this.namaKasir);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menampilkan transaksi
    public void displayTransaksi() {
        System.out.println("Kode Barang: " + this.kodeBarang);
        System.out.println("Nama Barang: " + this.namaBarang);
        System.out.println("Harga Barang: " + this.hargaBarang);
        System.out.println("Jumlah Beli: " + this.jumlahBeli);
        System.out.println("Total: " + this.total);
    }

    // Mendapatkan transaksi berdasarkan No Faktur
    public static Transaksi getByNoFaktur(String noFaktur) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM transaksi WHERE noFaktur = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, noFaktur);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Transaksi(
                        rs.getString("kodeBarang"),
                        rs.getString("namaBarang"),
                        rs.getDouble("hargaBarang"),
                        rs.getInt("jumlahBeli"),
                        rs.getString("namaKasir"),
                        rs.getString("noFaktur")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Menghapus transaksi dari database
    public void delete() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "DELETE FROM transaksi WHERE noFaktur = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, this.noFaktur);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
