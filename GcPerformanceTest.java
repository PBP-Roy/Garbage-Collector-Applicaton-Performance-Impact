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
