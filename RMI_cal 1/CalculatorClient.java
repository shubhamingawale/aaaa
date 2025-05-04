import java.rmi.*;

public class CalculatorClient {
    public static void main(String[] args) {
        try {
            Calculator stub = (Calculator) Naming.lookup("rmi://localhost/CalcService");
            int result = stub.add(10, 20);
            System.out.println("Addition Result: " + result);
        } catch (Exception e) {
            System.out.println("Client exception: " + e);
        }
    }
}
