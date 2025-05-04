public class DistributedSum {
    public static void main(String[] args) {
        int N = 10;
        int n = 5;

        int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = i + 1;
        }

        int chunkSize = N / n;
        SumTask[] tasks = new SumTask[n];

        for (int i = 0; i < n; i++) {
            int start = i * chunkSize;
            int end = (i == n - 1) ? N : (i + 1) * chunkSize;
            tasks[i] = new SumTask(array, start, end);
        }

        for (int i = 0; i < n; i++) {
            tasks[i].start();
        }

        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            try {
                tasks[i].join();
                totalSum += tasks[i].getPartialSum();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e);
            }
        }

        System.out.println("Final total sum: " + totalSum);
    }
}
