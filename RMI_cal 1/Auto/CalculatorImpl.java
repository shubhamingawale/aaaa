import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    public CalculatorImpl() throws RemoteException {
        super();
    }
    
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws RemoteException {
        if (b == 0) throw new RemoteException("Division by zero");
        return a / b;
    }

    public static void main(String[] args) {
        try {
            CalculatorImpl obj = new CalculatorImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CalculatorService", obj);
            System.out.println("Calculator RMI Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
