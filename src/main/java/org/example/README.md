🟦 1. HEADER UTAMA – LibraryApp
📌 Fungsi

Bagian utama aplikasi yang:

1. Menyimpan konstanta

2. Menyimpan data global

3. Menjalankan aplikasi pertama kali

🧭 Alur Penggunaan

- Program dijalankan melalui main()
- Data buku dimuat dari file buku.txt
- Aplikasi menampilkan Splash Screen

📖 Petunjuk untuk pengguna:

Saat aplikasi dijalankan, sistem akan memuat seluruh data buku yang tersimpan sebelumnya, kemudian menampilkan layar pembuka sebelum masuk ke proses login.

🟦 2. MODEL DATA – class Buku
📌 Fungsi

Mewakili 1 data buku dalam sistem.

📋 Data yang Disimpan

- ID Buku
- Judul
- Penulis
- Tahun terbit
- Status peminjaman
- Nama peminjam
- Tanggal pengembalian

📖 Petunjuk untuk pengguna:

Setiap buku yang ada di perpustakaan disimpan sebagai satu objek buku yang memuat informasi lengkap, termasuk apakah buku sedang dipinjam atau tersedia.

🟦 3. SPLASH SCREEN – class Splash
📌 Fungsi

Menampilkan layar pembuka selama 2 detik.

🧭 Alur Penggunaan

- Splash muncul saat aplikasi dijalankan
- Menampilkan judul aplikasi
- Otomatis berpindah ke halaman login

📖 Petunjuk untuk pengguna:

Splash screen berfungsi sebagai tampilan pembuka aplikasi sebelum pengguna masuk ke sistem, memberikan kesan profesional dan informasi awal tentang aplikasi.

🟦 4. LOGIN ROLE – class LoginRole
📌 Fungsi

Memilih peran pengguna:

- Admin
- Siswa

🧭 Langkah Penggunaan

- Pilih tombol Admin atau Siswa
- Sistem menyimpan jenis role
- Pengguna diarahkan ke halaman password

📖 Petunjuk untuk pengguna:

Pada halaman ini, pengguna menentukan perannya. Hak akses aplikasi akan disesuaikan berdasarkan peran yang dipilih.

🟦 5. LOGIN PASSWORD – class LoginPassword
📌 Fungsi

Melakukan autentikasi password sesuai role.

🧭 Langkah Penggunaan

- Masukkan password
- Klik tombol Masuk
- Jika benar → masuk Dashboard
- Jika salah → muncul peringatan

📖 Petunjuk untuk pengguna:

Sistem akan memverifikasi password berdasarkan peran yang dipilih sebelumnya. Jika password benar, pengguna dapat melanjutkan ke menu utama.

🟦 6. DASHBOARD – class Dashboard
📌 Fungsi

Sebagai menu utama aplikasi.

- Menu yang Tersedia
- Data Buku
- Laporan (Admin saja)
- Keluar

📖 Petunjuk untuk pengguna:

Dashboard merupakan pusat navigasi aplikasi. Dari sini pengguna dapat mengakses data buku, melihat laporan, atau keluar dari aplikasi.

🟦 7. DATA BUKU – class FrameBuku
📌 Fungsi

Menampilkan dan mengelola seluruh data buku.

🧭 Langkah Penggunaan

1. Buku ditampilkan dalam tabel
2. Gunakan kolom pencarian untuk mencari buku

Pilih buku untuk:

1. Tambah / Edit / Hapus (Admin)
2. Pinjam / Kembalikan (Siswa)

📖 Petunjuk untuk pengguna:

Halaman ini menampilkan seluruh koleksi buku perpustakaan. Pengguna dapat mencari buku dengan cepat dan melakukan aksi sesuai hak akses.

🟦 8. FORM BUKU – class FormBuku
📌 Fungsi

Digunakan Admin untuk:

- Menambah buku
- Mengedit data buku

🧭 Langkah Penggunaan

1. Isi data buku
2. Klik Simpan
3. Data otomatis masuk ke sistem

📖 Petunjuk untuk pengguna:

Form ini digunakan oleh Admin untuk mengelola data buku. Setiap perubahan akan langsung tersimpan ke sistem.

🟦 9. LAPORAN – class FrameLaporan
📌 Fungsi

Menampilkan laporan buku yang sedang dipinjam.

🧭 Informasi yang Ditampilkan

- Judul buku
- Nama peminjam
- Tanggal pengembalian

📖 Narasi untuk pengguna:

Halaman laporan memberikan informasi buku yang sedang dipinjam, membantu Admin memantau aktivitas perpustakaan.

🟦 10. FILE HANDLER – muatData() & simpanData()
📌 Fungsi

Mengelola penyimpanan data.

🧭 Cara Kerja

- muatData() → membaca buku.txt
- simpanData() → menyimpan data ke file

📖 Petunjuk untuk pengguna:

Sistem secara otomatis menyimpan dan memuat data buku dari file sehingga data tidak hilang meskipun aplikasi ditutup.

🟦 11. UTILITAS – btn() & GradientPanel
📌 Fungsi

- Membuat tombol seragam
- Memberi tampilan header dengan gradasi warna

📖 Petunjuk untuk pengguna:

Komponen ini dibuat untuk menjaga konsistensi tampilan dan meningkatkan kenyamanan visual pengguna.

✅ KESIMPULAN UNTUK PENGGUNA

1. Jalankan aplikasi
2. Login sebagai Admin atau Siswa
3. Gunakan Dashboard untuk navigasi
4. Kelola atau pinjam buku sesuai hak akses
5. Data otomatis tersimpan