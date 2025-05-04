import java.util.*;

public class LeaderElection {
    static final int NUM_PROCESSES = 5; // Number of processes
    static List<Process> processes = new ArrayList<>();
    static int leaderId = -1; // Initially, no leader

    public static void main(String[] args) throws InterruptedException {
        // Create and start processes
        for (int i = 0; i < NUM_PROCESSES; i++) {
            Process p = new Process(i + 1); // Process ID from 1 to NUM_PROCESSES
            processes.add(p);
        }

        System.out.println("\nRunning Bully Algorithm:");
        runBullyAlgorithm();

        System.out.println("\nRunning Ring Algorithm:");
        runRingAlgorithm();
    }

    // Process class (simplified with threading and communication)
    static class Process extends Thread {
        int id;
        boolean isInitiator = false; // Is this process the initiator of the election?

        Process(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            // Just for demonstration; actual election logic happens in these methods.
            if (isInitiator) {
                // Simulate the election process
                System.out.println("Process " + id + " is participating in the election.");
            }
        }

        void startElection() {
            start();
        }

        // Method to simulate communication with another process
        synchronized void sendMessage(Process p) {
            System.out.println("Process " + this.id + " sends message to Process " + p.id);
        }
    }

    // Bully Algorithm
    static void runBullyAlgorithm() {
        int initiator = processes.get(0).id; // Let's assume process with ID 1 starts the election
        System.out.println("Process " + initiator + " is initiating Bully Election...");
        int maxId = -1;
        
        // Start the election by the initiator
        Process initiatorProcess = processes.get(0);
        initiatorProcess.isInitiator = true;
        initiatorProcess.startElection();
        
        // Simulate sending messages to higher numbered processes
        for (Process p : processes) {
            if (p.id > initiator && p.id > maxId) {
                maxId = p.id;
                p.sendMessage(initiatorProcess); // Initiator sends messages to other processes
            }
        }

        // Elected leader
        leaderId = maxId;
        System.out.println("Bully Algorithm elected Process " + leaderId + " as the leader.");
    }

    // Ring Algorithm
    static void runRingAlgorithm() {
        System.out.println("Process " + processes.get(0).id + " is initiating Ring Election...");
        int maxId = -1;
        
        // Simulate the election passing around in a ring
        for (Process p : processes) {
            if (p.id > maxId) {
                maxId = p.id;
                p.sendMessage(p); // Simulate message passing around the ring
            }
        }
        
        // Elected leader
        leaderId = maxId;
        System.out.println("Ring Algorithm elected Process " + leaderId + " as the leader.");
    }
}
