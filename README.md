- Refleksi 1:
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