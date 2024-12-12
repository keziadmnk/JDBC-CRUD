import java.sql.*;

public class Barang {
    protected String kodeBarang;
    protected String namaBarang;
    protected double hargaBarang;

    // Konstruktor untuk barang
    public Barang(String kodeBarang, String namaBarang, double hargaBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }

    // Menyimpan barang ke database
    public void saveToDatabase() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "INSERT INTO barang (kodeBarang, namaBarang, hargaBarang) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, this.kodeBarang);
                stmt.setString(2, this.namaBarang);
                stmt.setDouble(3, this.hargaBarang);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mendapatkan barang berdasarkan kodeBarang
    public static Barang getByKodeBarang(String kodeBarang) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "SELECT * FROM barang WHERE kodeBarang = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, kodeBarang);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return new Barang(
                        rs.getString("kodeBarang"),
                        rs.getString("namaBarang"),
                        rs.getDouble("hargaBarang")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update harga barang di database
    public void updateHarga(double hargaBaru) {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "UPDATE barang SET hargaBarang = ? WHERE kodeBarang = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDouble(1, hargaBaru);
                stmt.setString(2, this.kodeBarang);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Menghapus barang dari database
    public void delete() {
        try (Connection conn = DatabaseUtil.getConnection()) {
            String sql = "DELETE FROM barang WHERE kodeBarang = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, this.kodeBarang);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
