- Refleksi 1:\
Dalam kode saya, saya telah mengimplementasikan fitur-fitur seperti create, edit, dan delete. Kemudian, saya mengimplementasikan hal-hal berikut:
1. Meaningful Name: contohnya editProductPage, createProductPage, dan findById.
2. Small Function: tiap metode dan service dalam projek saya dibuat untuk satu tugas spesifik
3. Layered Architecture: projek ini saya memisahkan logika antara Controller, Service, dan Repository.
4. Secure ID Generation: penggunaan UUID untuk identitas produk bersifat lebih aman dibanding dengan string biasa
5. Hidden Fields: Pada halaman edit, ID produk tetap hidden sehingga data tetap terjaga.

    Kekurangan:
   1. Direct Repository Access: awalnya, kode pada Controller milik saya mengakses Repository secara langsung. Kemudian, saya perbaiki dengan menggunakan lapisan Service supaya konsistensi tetap terjaga.
   2. Input validation: pada beberapa bagian, seperti input kuantitas belum saya batasi supaya > 0 dan untuk memperbaikinya perlu ditambahkan notasi @min(0)


- Refleksi 2: 
1. Setelah membuat unit test, saya merasa pentingnya unittest dalam pembuatan, supaya kekurangan fitur dapat diketahui lebih awal. Menurut saya, jumlah test dalam suatu kelas harus mencakup semua kemungkinan yang bisa terjadi, dan setidak-tidaknya harus ada satu untuk skenario positif dan satu untuk negatif. 100% code average tidak menjamin kode yang saya buat bebas sepenuhnya dari bug, karena yang dites adalah semua fungsi dan logika yang dijalankan sudah sesuai, tapi jika ada jenis input yang tidak sesuai tidak termasuk di dalamnya.
2. Kualitas kodenya menjadi tidak terlalu baik, hal ini dikarenakan terjadinya pengulangan kode yang membuatnya sulit untuk di-_maintenance_. Jika nantinya ada perubahan link, saya harus memperbaiki banyak bagian pada kode saya. Kemudian, bagian yang bisa di-_improve_ adalah dengan membuat base _class_ yang cukup general sehingga mencakup hal yang banyak muncul di test-test lainnya dan kelas lain cukup lakukan _inheritance_.   


- Refleksi Modul 2:
1. Saat saya melakukan CI dengan SonarCloud, ditemukan security hotspot dengan rule kotlin:S6474 yang menyatakan bahwa dependency tidak diverifikasi karena file verification-metadata.xml tidak ada. Masalah ini berpotensi menimbulkan risiko keamanan berupa supply chain attack, karena Gradle akan mengunduh artifact dari remote repository tanpa melakukan verifikasi integritas (checksum). Jika artifact tersebut dimodifikasi oleh pihak tidak bertanggung jawab, aplikasi dapat menjalankan kode berbahaya tanpa terdeteksi. Cara saya mengatasi masalah ini adalah dengan mengaktifkan fitur dependency verification pada Gradle melalui perintah:
```gradle --write-verification-metadata sha256 help``` Perintah tersebut membuat file verification-metadata.xml yang berisi checksum SHA-256 dari setiap dependency yang digunakan. Dengan adanya file ini, Gradle akan memverifikasi integritas artifact sebelum digunakan dalam proses build. Setelah file tersebut ditambahkan dan di-push ke branch utama, SonarCloud tidak lagi mendeteksi masalah tersebut. Strategi yang saya gunakan adalah meningkatkan kepercayaan (trust) terhadap dependency eksternal dengan memastikan integritas dan keaslian artifact yang digunakan dalam proses build. Beberapa masalah lain adalah "
   Remove the declaration of thrown exception 'java.lang.Exception', as it cannot be thrown from method's body.", "Remove this unused import 'java.util.List'.
   ", "Rename this method name to match the regular expression '^[a-z][a-zA-Z0-9]*$'.", dan "Group dependencies by their destination". Cara saya menyelesaikan masalah-masalah tersebut adalah menghapus throw exception di bagian unittest, menghapus import yang tidak terpakai, menamai ulang method supaya sesuai Java naming convention, dan mengurutkan depedencies sesuai destinasinya.
2. Dari script CI pada .github/workflows, dan settings _Automatic Deploys_ pada Heroku dapat disimpulkan bahwa implementasi CI-CD di kode ini sudah memenuhinya. Selain itu, CI yang melakukan _unit-testing_ pada kode  dan _code scanning_ dengan beberapa tools, seperti OSSF Scorecard bawaan Github, dan tools dari luar, yaitu SonarCloud. Kemudian, setelah CI selesai dan berhasil melewati _code scanning_, Heroku akan melakukan auto deployment, ataupun re-deployment, pada branch Master/Main yang memenuhi CI tersebut.


- Refleksi Modul 3:

1. Nomor 1: \
  a) SRP diimplementasikan lewat pemisahan CarController dengan ProductController yang dipisahkan menjadi 2 file.\
  b) OCP diimplementasikan dengan membuat Interface general untuk ProductRepository dan CarRepository, yaitu BaseRepository, hal tersebut karena keduanya memiliki kode dan method yang mirip jadi bisa dibuat BaseReponya. Selain itu, saat ingin menambahkan kode baru tidak perlu memodifikasi file yang sudah ada.\
  c) LSP diimplementasikan dengan membuat CarController tidak lagi meng-_extends_ ProductController, karen keduanya berurusan dengan model yang berbeda, dan memiliki mapping yang berbeda.\
  d) ISP diimplementasikan melalui Service yang dipisahkan menjadi ReadService dan WriteService, hal tersebut dilakukan supaya class-classnya menjadi lebih spesifik sesuai fungsinya.\
  e) DIP diimplementasikan lewat CarController dan ProductController bergantung pada CarService dan ProductService yang mana keduanya bergantung pada interface atau abstraksi, bukan pada class konkrit.

2. Dengan mengimplementasikan SOLID, kode yang saya buat lebih mudah untuk di-_maintain_ dan dikembangkan dikemudan hari. Selain itu, penambahan kode baru tidak akan merusak kode yang sudah ada sebelumnya, dan fungsi yang sudah ada tidak akan membingungkan. Contohnya: implementasi CarProduct dan ProductController yang memiliki tanggung jawab masing-masing sehingga lebih mudah dibaca, di-test, dan di-_maintain_.
3. Kerugian jika tidak mengimplementasikan SOLID, yaitu kode menjadi sulit di-_maintain_ dan membingungkan, contohnya ProductController yang tadinya mengurus 2 hal. Selain itu, konsistensi kode yang membuat kode seperti tidak ada standar, contohnya sebelum diimplementasikan SOLID, kedua memiliki CarRepository dan ProductRepository yang berbeda. 
