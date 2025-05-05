class Node {
    private String name;
    private double clockTime;

    public Node(String name, double clockTime) {
        this.name = name;
        this.clockTime = clockTime;
    }

    public double getTime() {
        return clockTime;
    }

    public void adjustTime(double offset) {
        this.clockTime += offset;
    }

    public String getName() {
        return name;
    }
}

class BerkeleyAlgorithm {
    private Node[] nodes;
    private Node master;

    public BerkeleyAlgorithm(Node[] nodes) {
        this.nodes = nodes;
        this.master = nodes[0]; // Assume the first node is the master
    }

    public void synchronize() {
        double totalOffset = 0;
        int count = 0;

        double masterTime = master.getTime();

        // Calculate offset from master for each node
        for (Node node : nodes) {
            if (node != master) {
                double offset = node.getTime() - masterTime;
                totalOffset += offset;
                count++;
            }
        }

        // Calculate average offset
        double averageOffset = totalOffset / (count + 1); // +1 includes the master itself

        // Adjust each node's time
        for (Node node : nodes) {
            double offset = averageOffset - (node.getTime() - masterTime);
            node.adjustTime(offset);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Node[] nodes = {
            new Node("Master", 100.0),
            new Node("Node2", 98.5),
            new Node("Node3", 102.3),
            new Node("Node4", 97.0)
        };

        BerkeleyAlgorithm berkeley = new BerkeleyAlgorithm(nodes);
        berkeley.synchronize();

        for (Node node : nodes) {
            System.out.printf("%s time: %.2f\n", node.getName(), node.getTime());
        }
    }
}