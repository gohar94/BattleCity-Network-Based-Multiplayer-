import java.rmi.*;

/**All RMI objects must implement an interface that extends from Remote.
 * It allows for the stub of the object to be created and sent over the network
 */
interface Chat extends Remote{

	String sendMessage(String text) throws RemoteException;
}
