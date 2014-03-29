import java.rmi.*;

public interface MultiServer extends Remote {
	int getButton() throws RemoteException;
}