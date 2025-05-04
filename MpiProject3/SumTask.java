public class SumTask extends Thread {
    private int[] array;
    private int start, end;
    private int partialSum;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.partialSum = 0;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
        System.out.println("Partial sum from processor (thread): " + partialSum);
    }

    public int getPartialSum() {
        return partialSum;
    }
}
