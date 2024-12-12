import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loginBerhasil = false;

        while (!loginBerhasil) {
            System.out.println("+-----------------------------------------------------+");
            System.out.println("Log in");
            System.out.println("+-----------------------------------------------------+");

            // Input username
            System.out.print("Username: ");
            String username = scanner.nextLine();

            // Input password
            System.out.print("Password: ");
            String password = scanner.nextLine();

            // Input captcha
            System.out.print("Captcha (masukkan 'abc123'): ");
            String captcha = scanner.nextLine();

            // Validasi username
            if (!username.equals("Kezia")) {
                System.out.println("Username salah. Silakan coba lagi.");
                continue; // Kembali ke awal loop
            }

            // Validasi password
            if (!password.equals("akucintapbo")) {
                System.out.println("Password salah. Silakan coba lagi.");
                continue;
            }

            // Validasi captcha
            if (!captcha.equalsIgnoreCase("abc123")) {
                System.out.println("Captcha salah. Silakan coba lagi.");
                continue;
            }

            // Jika login berhasil
            loginBerhasil = true;
            System.out.println("Login berhasil!");
        }

        // Catat waktu login
        Date waktuLogin = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println("Login berhasil pada " + sdf.format(waktuLogin));
        System.out.println("+-----------------------------------------------------+");

        boolean transaksiBerhasil = false;

        while (!transaksiBerhasil) {
            try {
                System.out.println("Selamat Datang di Supermarket SUPER!");
                System.out.println("+-----------------------------------------------------+");

                // Input detail transaksi
                System.out.print("Masukkan No Faktur (diawali dengan 'TRX'): ");
                String noFaktur = scanner.nextLine();

                // Validasi No Faktur
                if (!noFaktur.startsWith("TRX")) {
                    System.out.println("No Faktur harus diawali dengan 'TRX'. Silakan coba lagi.");
                    continue;
                }

                System.out.print("Masukkan Kode Barang: ");
                String kodeBarang = scanner.nextLine();

                System.out.print("Masukkan Nama Barang: ");
                String namaBarang = scanner.nextLine();

                System.out.print("Masukkan Harga Barang: ");
                double hargaBarang = scanner.nextDouble();
                if (hargaBarang <= 0) {
                    System.out.println("Harga barang harus lebih dari 0. Silakan coba lagi.");
                    continue;
                }

                System.out.print("Masukkan Jumlah Beli: ");
                int jumlahBeli = scanner.nextInt();
                if (jumlahBeli <= 0) {
                    System.out.println("Jumlah beli harus lebih dari 0. Silakan coba lagi.");
                    continue;
                }

                scanner.nextLine(); // Bersihkan buffer

                System.out.print("Masukkan Nama Kasir: ");
                String namaKasir = scanner.nextLine().toLowerCase();

                // Membuat objek transaksi dengan menambahkan noFaktur
                Transaksi transaksi = new Transaksi(kodeBarang, namaBarang, hargaBarang, jumlahBeli, namaKasir, noFaktur);

                // Catat waktu selesai transaksi
                Date waktuSelesai = new Date();

                // Menampilkan data transaksi
                System.out.println("\n+-----------------------------------------------------+");
                System.out.println("No Faktur: " + noFaktur);
                transaksi.displayTransaksi();
                System.out.println("Kasir: " + namaKasir);
                System.out.println("Tanggal dan Waktu Selesai: " + sdf.format(waktuSelesai));
                System.out.println("+-----------------------------------------------------+");

                // Hitung durasi proses
                long durasi = waktuSelesai.getTime() - waktuLogin.getTime();
                long detik = durasi / 1000 % 60;
                long menit = durasi / (1000 * 60) % 60;
                System.out.println("Durasi Proses: " + menit + " menit " + detik + " detik");
                System.out.println("+-----------------------------------------------------+");
                
                transaksi.saveToDatabase();
                transaksiBerhasil = true; // Keluar dari loop transaksi

            } catch (Exception e) {
                System.out.println("Terjadi kesalahan input. Silakan coba lagi.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
