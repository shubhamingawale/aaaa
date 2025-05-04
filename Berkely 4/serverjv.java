import java.io.*;
import java.net.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.*;

public class ClockServer {
    private static final Map<String, ClientInfo> clientData = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Clock server started on port 8080");

        // Accept clients
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    String address = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                    System.out.println(address + " connected.");
                    new Thread(() -> receiveClockTime(clientSocket, address)).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Synchronize time
        new Thread(ClockServer::synchronizeClocks).start();
    }

    private static void receiveClockTime(Socket socket, String address) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                String receivedTimeStr = in.readLine();
                if (receivedTimeStr == null) continue;

                LocalDateTime clientTime = LocalDateTime.parse(receivedTimeStr);
                Duration diff = Duration.between(clientTime, LocalDateTime.now());

                clientData.put(address, new ClientInfo(clientTime, diff, socket));
                System.out.println("Updated client data for " + address);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            System.out.println("Connection lost with " + address);
            clientData.remove(address);
        }
    }

    private static void synchronizeClocks() {
        while (true) {
            try {
                if (!clientData.isEmpty()) {
                    System.out.println("Starting synchronization cycle...");
                    Duration avgDiff = clientData.values().stream()
                            .map(client -> client.timeDiff)
                            .reduce(Duration.ZERO, Duration::plus)
                            .dividedBy(clientData.size());

                    LocalDateTime syncTime = LocalDateTime.now().plus(avgDiff);

                    for (Map.Entry<String, ClientInfo> entry : clientData.entrySet()) {
                        try {
                            PrintWriter out = new PrintWriter(entry.getValue().socket.getOutputStream(), true);
                            out.println(syncTime.toString());
                        } catch (IOException e) {
                            System.out.println("Error sending to " + entry.getKey());
                        }
                    }
                } else {
                    System.out.println("No clients to synchronize.");
                }
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private static class ClientInfo {
        LocalDateTime clientTime;
        Duration timeDiff;
        Socket socket;

        ClientInfo(LocalDateTime clientTime, Duration timeDiff, Socket socket) {
            this.clientTime = clientTime;
            this.timeDiff = timeDiff;
            this.socket = socket;
        }
    }
}
