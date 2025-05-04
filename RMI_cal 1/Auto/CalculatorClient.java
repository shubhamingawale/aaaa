import java.rmi.Naming;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Calculator stub = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");

            double a = 10, b = 5;
            System.out.println("Addition: " + stub.add(a, b));
            System.out.println("Subtraction: " + stub.subtract(a, b));
            System.out.println("Multiplication: " + stub.multiply(a, b));
            System.out.println("Division: " + stub.divide(a, b));
        } catch (Exception e) {
            System.out.println("Client error: " + e);
        }
    }
}
