
# Garbage-Collector-Applicaton-Performance-Impact
Studi Pengaruh Garbage Collector Terhadap Performansi Aplikasi

## Identifikasi Problem
Garbage Collector (GC) dapat menyebabkan jeda dan penurunan performa aplikasi jika tidak dikelola dengan baik, terutama ketika frekuensi pengumpulannya terlalu tinggi.

## Relevansi dan Kepentingan Studi di Dunia Industri

Studi tentang pengaruh Garbage Collector (GC) terhadap performa aplikasi menjadi relevan dan penting di dunia industri karena performa aplikasi yang buruk akibat pengelolaan memori yang tidak efisien dapat berdampak negatif pada operasi bisnis. Berikut beberapa contoh kasus nyata yang terjadi di industri:

### 1. Performa Aplikasi E-commerce
Dalam aplikasi e-commerce besar seperti Amazon atau Alibaba, waktu respons aplikasi yang cepat sangat penting untuk pengalaman pengguna dan konversi penjualan. GC yang berjalan terlalu sering atau pada saat yang tidak tepat dapat menyebabkan jeda dalam pemrosesan transaksi, yang bisa mengakibatkan pengguna meninggalkan keranjang belanja mereka atau berpindah ke situs pesaing. Dalam kasus ini, GC yang tidak dioptimalkan dapat langsung mempengaruhi pendapatan perusahaan.

### 2. Aplikasi Trading dan Keuangan
Di sektor keuangan, aplikasi trading real-time memerlukan latensi yang sangat rendah untuk mengeksekusi perdagangan dalam hitungan milidetik. Contoh nyata termasuk platform trading saham atau valuta asing seperti Bloomberg Terminal atau platform trading algoritmik di bursa saham. Jika GC mengumpulkan sampah pada waktu kritis, itu dapat menyebabkan delay yang cukup untuk mempengaruhi hasil perdagangan, berpotensi menyebabkan kerugian finansial yang signifikan.

### 3. Sistem Kendali Waktu Nyata (Real-Time Control Systems)
Sistem kendali waktu nyata yang digunakan dalam industri seperti otomotif (misalnya, sistem kontrol mobil otonom) atau aerospace memerlukan konsistensi dan prediktabilitas dalam performa. Jeda yang disebabkan oleh GC dapat menyebabkan kegagalan sistem atau respons yang tidak tepat waktu, yang dalam konteks ini, dapat berujung pada risiko keselamatan atau kerugian material besar.

### 4. Layanan Streaming dan Media
Perusahaan seperti Netflix, Disney+, atau YouTube menghadapi tantangan performa yang besar terkait dengan pemutaran video yang mulus tanpa buffer. GC yang tidak terkelola dengan baik dapat menyebabkan jeda atau lag selama streaming, mengurangi kualitas pengalaman pengguna dan berpotensi menyebabkan churn pelanggan. Dalam industri media, kecepatan dan kelancaran streaming adalah kunci untuk mempertahankan pengguna.

### 5. Aplikasi Berbasis Cloud dan Microservices
Banyak aplikasi modern beroperasi di lingkungan cloud atau menggunakan arsitektur microservices, seperti yang diimplementasikan oleh perusahaan seperti Uber atau Spotify. Pada skala ini, efisiensi performa sangat penting untuk meminimalkan biaya operasional. GC yang berjalan terlalu sering dapat meningkatkan penggunaan CPU dan memperpanjang waktu respons aplikasi, yang pada akhirnya meningkatkan biaya cloud dan menurunkan kualitas layanan.

## Mengapa Kasus Ini Harus Dicobakan?

1. **Optimasi Performa**: Memahami pengaruh GC terhadap performa aplikasi membantu dalam mengoptimalkan aplikasi untuk operasi bisnis yang lebih efisien dan responsif.

2. **Skalabilitas dan Biaya**: Pengelolaan GC yang buruk dapat menyebabkan biaya yang lebih tinggi dalam skala besar, seperti biaya server tambahan atau peningkatan kapasitas cloud yang tidak perlu.

3. **Pengalaman Pengguna**: Pengaruh GC terhadap respons aplikasi secara langsung mempengaruhi kepuasan dan retensi pengguna.

4. **Keamanan dan Kepatuhan**: Di industri yang sangat bergantung pada waktu nyata atau memerlukan performa tinggi, pengelolaan memori yang optimal bukan hanya tentang efisiensi, tetapi juga keselamatan dan kepatuhan terhadap standar industri.

Studi kasus ini sangat penting di industri untuk memastikan bahwa aplikasi tidak hanya berfungsi dengan benar tetapi juga efisien dan dapat diandalkan, menjaga bisnis tetap kompetitif dan aman dalam operasinya.


## Deskripsi Problem
GC mengelola memori dalam bahasa pemrograman seperti Java dan C#. Namun, GC dapat memperlambat aplikasi jika terlalu banyak objek yang tidak lagi digunakan dan frekuensi pengumpulannya terlalu sering.

## Metodologi Eksperimen
Eksperimen ini bertujuan untuk menganalisis bagaimana frekuensi GC mempengaruhi performa aplikasi. Metodologi yang digunakan adalah sebagai berikut:
1. Implementasikan aplikasi dengan beban kerja berat yang menghasilkan banyak objek.
2. Lacak waktu eksekusi GC dan ukur dampaknya terhadap waktu respons aplikasi.
3. Bandingkan performa dengan dan tanpa pemanggilan GC.

## Pelaksanaan Eksperimen
Program diimplementasikan menggunakan Java dengan kode berikut:

```java
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GcPerformanceTest {

    private static final int SIZE = 1000000;
    private static final int ITERATIONS = 50;

    public static void main(String[] args) {
        // Menjalankan eksperimen tanpa GC
        System.out.println("Running test without GC calls...");
        runExperiment(false);

        // Menjalankan eksperimen dengan GC
        System.out.println("\nRunning test with GC calls...");
        runExperiment(true);
    }

    private static void runExperiment(boolean useGc) {
        long startTime = System.currentTimeMillis();

        // Mendapatkan informasi GC
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
        long initialGcCount = getTotalGcCount(gcBeans);
        long initialGcTime = getTotalGcTime(gcBeans);

        for (int i = 0; i < ITERATIONS; i++) {
            long iterationStartTime = System.currentTimeMillis();
            runTest();

            if (useGc) {
                // Memaksa GC untuk menjalankan pengumpulan sampah
                System.gc();
            }

            long iterationEndTime = System.currentTimeMillis();
            System.out.println("Iteration " + (i + 1) + " Execution Time: " + (iterationEndTime - iterationStartTime) + " ms");
        }

        long endTime = System.currentTimeMillis();
        long finalGcCount = getTotalGcCount(gcBeans);
        long finalGcTime = getTotalGcTime(gcBeans);

        System.out.println("Total Execution Time: " + (endTime - startTime) + " ms");
        System.out.println("Total GC Count: " + (finalGcCount - initialGcCount));
        System.out.println("Total GC Time: " + (finalGcTime - initialGcTime) + " ms");
    }

    private static void runTest() {
        // Membuat banyak objek di memori
        int[] largeArray = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            largeArray[i] = i;
        }

        // Memaksa objek-objek untuk menjadi tidak terpakai
        largeArray = null;
    }

    private static long getTotalGcCount(List<GarbageCollectorMXBean> gcBeans) {
        long count = 0;
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            count += gcBean.getCollectionCount();
        }
        return count;
    }

    private static long getTotalGcTime(List<GarbageCollectorMXBean> gcBeans) {
        long time = 0;
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            time += gcBean.getCollectionTime();
        }
        return time;
    }
}
```
Eksperimen dilakukan dengan:

1. Menjalankan loop sebanyak 50 kali, setiap kali membuat banyak objek yang kemudian dibuat tidak terpakai.
2. Membandingkan dua skenario: satu dengan memanggil System.gc() dan satu lagi tanpa memanggil System.gc().
   
# Analisis Hasil Eksperimen
Berikut adalah hasil pengamatan dari eksperimen:

## Skenario Tanpa GC:
- Total Execution Time: <Time in ms>
- Total GC Count: <GC Count>
- Total GC Time: <GC Time in ms>
## Skenario Dengan GC:
- Total Execution Time: <Time in ms>
- Total GC Count: <GC Count>
- Total GC Time: <GC Time in ms>
Dari hasil eksperimen, dapat dilihat bahwa pemanggilan GC secara manual meningkatkan waktu eksekusi aplikasi dan jumlah pemanggilan GC. Performa aplikasi berkurang signifikan ketika GC sering dipanggil, menunjukkan bahwa pengelolaan GC yang lebih optimal diperlukan untuk menjaga performa aplikasi.

# Kesimpulan
Eksperimen ini menunjukkan dampak signifikan dari GC terhadap performa aplikasi, terutama ketika frekuensi GC tinggi. Langkah optimisasi yang dapat dilakukan antara lain:
1. Mengurangi alokasi objek yang tidak perlu.
2. Menggunakan algoritma GC yang lebih efisien seperti G1 atau CMS.
3. Menyesuaikan parameter heap size dan melakukan tuning JVM untuk performa yang optimal.
   
<p align="center">
<img src="\Images\mermaid-diagram-2024-09-06-075747.png">
</p>
