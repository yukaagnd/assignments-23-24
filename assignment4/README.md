# Tugas Pemrograman 4: GUI & Event-driven programming
## Dasar-Dasar Pemrograman 2 @ Fakultas Ilmu Komputer Universitas Indonesia, Semester Genap 2023/2024

Tugas Pemrograman ini mencakup materi berikut:
- GUI (JavaFX, FXML)

### [Daftar Isi](#daftar-isi)
1. [Dokumen Soal](#dokumen-soal)
2. [Persiapan](#persiapan)
3. [Menjalankan dan Menguji Program](#menjalankan-dan-menguji-program)
4. [Pengumpulan](#pengumpulan)
5. [Daftar Pekerjaan](#daftar-pekerjaan)
    - [Daftar Pekerjaan Wajib](#daftar-pekerjaan-wajib)
6. [Penilaian](#penilaian)

### Dokumen Soal
Dokumen soal dapat diakses melalui [link ini](https://docs.google.com/document/d/1fpBJnxU4sEgwMw4PCfzUIceCycoobYlAeJFYO6PTaBg/edit?usp=sharing).

### Persiapan
Silakan ikuti persiapan yang ada pada berkas `README.md` di root repositori ini.

Template pengerjaan tugas ini disediakan di path `src\main\java\assignments\assignment4`

### Menjalankan dan Menguji Program
**Catatan:**
- Ubah `gradlew.bat` dengan `./gradlew` dan `\` dengan `/` jika kamu menggunakan Linux/Mac.
- Tanda `>` pada awal command tidak perlu ditulis. Digunakan sebagai pembeda antara command dan output-nya.

**Untuk menjalankan program:**
```bash
gradlew.bat :assignment4:run --console plain
```
**Alternatif tanpa Gradle:**
Jika kamu tidak ingin menggunakan Gradle, kamu juga bisa melakukan kompilasi dan menjalankan program dengan `javac` dan `java` seperti biasa. Masuk ke direktori `src\main\java`, compile semua berkas `.java` kamu, dan jalankan `MainMenu` class-nya.
```bash
cd src\main\java
javac assignments\assignment3\*.java
java assignments.assignment3.MainMenu
```
### Pengumpulan
Simpan pekerjaan kamu, lalu lakukan `git add`, `git commit`, dan `git push` sebagaimana dijelaskan pada dokumen *Getting Started with a Java Project* (Latihan Tugas Pemrograman). Push solusi kamu sebelum **1 Mei 2024 pukul 23.55**. Disarankan untuk push sebelum pukul 22.00 untuk mengantisipasi antrean pada GitHub Pipelines.

**Catatan:** Jangan lupa untuk menambahkan asdos kamu dan dekdepe sebagai member di repositori kamu.

### Daftar Pekerjaan
#### Daftar Pekerjaan Wajib
- [ ] Buat sekurang-kurangnya 2 commit yang mengandung progres kamu dalam mengerjakan Tugas Pemrograman 3.
- [ ] Push commit kamu ke repositori Git online yang ada pada proyek GitHub kamu.
- [ ] Implementasikan package `component` dengan benar.
- [ ] Implementasikan package `page` dengan benar.
- [ ] Demonstrasikan program kamu ke asdos kamu.

### Penilaian
- 40% Fungsionalitas program
- 10% Validasi input & tampilan error message
- 5% Informasi prompt program
- 5% Kerapian GUI dan output program
- 5% Dokumentasi (kejelasan kode)
- 5% Standar penulisan kode* (kerapian kode)
- 30% Demo program dengan asisten dosen
- 10% Bonus: Kreativitas GUI

