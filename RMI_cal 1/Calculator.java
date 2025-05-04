import java.rmi.*;

public interface Calculator extends Remote {
    int add(int a, int b) throws RemoteException;
}
