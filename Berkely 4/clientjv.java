import java.io.*;
import java.net.*;
import java.time.*;
import java.util.concurrent.*;

public class ClockClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            System.out.println("Connected to clock server.");

            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.submit(() -> sendTime(socket));
            executor.submit(() -> receiveTime(socket));
        } catch (IOException e) {
            System.out.println("Could not connect to server.");
        }
    }

    private static void sendTime(Socket socket) {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (true) {
                LocalDateTime now = LocalDateTime.now();
                out.println(now.toString());
                System.out.println("Sent time: " + now);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            System.out.println("Sending thread terminated.");
        }
    }

    private static void receiveTime(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                LocalDateTime syncTime = LocalDateTime.parse(line);
                System.out.println("Synchronized time received: " + syncTime);
            }
        } catch (Exception e) {
            System.out.println("Receiving thread terminated.");
        }
    }
}
