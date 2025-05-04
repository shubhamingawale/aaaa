import java.rmi.Naming;
import java.util.Scanner;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Calculator stub = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter first number: ");
            double a = scanner.nextDouble();

            System.out.print("Enter second number: ");
            double b = scanner.nextDouble();

            System.out.println("\nSelect operation:");
            System.out.println("1. Add\n2. Subtract\n3. Multiply\n4. Divide");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            double result = 0;

            switch (choice) {
                case 1:
                    result = stub.add(a, b);
                    System.out.println("Result: " + result);
                    break;
                case 2:
                    result = stub.subtract(a, b);
                    System.out.println("Result: " + result);
                    break;
                case 3:
                    result = stub.multiply(a, b);
                    System.out.println("Result: " + result);
                    break;
                case 4:
                    result = stub.divide(a, b);
                    System.out.println("Result: " + result);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            scanner.close();
        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}
