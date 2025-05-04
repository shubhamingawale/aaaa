import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {
    public CalculatorImpl() throws RemoteException {
        super();
    }

    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

    public static void main(String[] args) {
        try {
            CalculatorImpl obj = new CalculatorImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("CalcService", obj);
            System.out.println("Server ready");
        } catch (Exception e) {
            System.out.println("Server exception: " + e);
        }
    }
}
