import java.rmi.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.util.*;

public interface Multi extends Remote {
	Fake getScene() throws RemoteException;
}