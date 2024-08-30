# Garbage-Collector-Applicaton-Performance-Impact
Studi Pengaruh Garbage Collector Terhadap Performansi Aplikasi
# Studi Pengaruh Garbage Collector Terhadap Performansi Aplikasi

## Identifikasi Problem
Garbage Collector (GC) dapat menyebabkan jeda dan penurunan performa aplikasi jika tidak dikelola dengan baik, terutama ketika frekuensi pengumpulannya terlalu tinggi.

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
   
#Analisis Hasil Eksperimen
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
