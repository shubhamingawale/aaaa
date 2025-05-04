import mpi.MPI;

public class Asign2 {
    static final int N = 1000; // Size of the array

    public static void main(String[] args) {
        // Initialize MPI
        MPI.Init(args);

        // Get the rank of the process and the total number of processes
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int[] arr = new int[N];
        int localSum = 0;
        int[] globalSum = new int[1]; // To hold the result of the reduction
        int elementsPerProcess = N / size;

        // Initialize the array with sample values
        if (rank == 0) {
            for (int i = 0; i < N; i++) {
                arr[i] = 1; // Assign 1 to each element for simplicity
            }
        }

        // Scatter the array to all processes
        int[] subArray = new int[elementsPerProcess];
        MPI.COMM_WORLD.Scatter(arr, 0, elementsPerProcess, MPI.INT, subArray, 0, elementsPerProcess, MPI.INT, 0);

        // Calculate the local sum for each process
        for (int i = 0; i < elementsPerProcess; i++) {
            localSum += subArray[i];
        }

        // Print the intermediate local sum at the current processor
        System.out.println("Process " + rank + ": Local sum = " + localSum);

        // Reduce the local sums to get the global sum at process 0
        MPI.COMM_WORLD.Reduce(new int[] { localSum }, 0, globalSum, 0, 1, MPI.INT, MPI.SUM, 0);

        // Process 0 will print the final global sum
        if (rank == 0) {
            System.out.println("Global sum = " + globalSum[0]);
        }

        // Finalize MPI
        MPI.Finalize();
    }
}



commands for eclipse
export MPJ_HOME="/c/Users/ingaw/Downloads/mpj-v0_44"
export PATH="$MPJ_HOME/bin:$PATH"
 javac -cp ".:$MPJ_HOME/lib/mpj.jar" mpiclass.java
  mpjrun.sh -np 4 mpiclass


commands for window
$env:MPJ_HOME = "C:\Users\Vikas\Downloads\mpj-v0_44"
$env:PATH = "$env:MPJ_HOME\bin;$env:PATH"
 javac -cp ".;$env:MPJ_HOME\lib\mpj.jar" Asign2.java
 mpjrun.bat -np 4 mpiclass

