import java.net.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

import javafx.event.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.Serializable;

public class Key implements EventHandler, MultiServer {
	int button;

	public Key() {
		System.out.println("Made");
		button = -1;
        try {
            MultiServer stub = (MultiServer)UnicastRemoteObject.exportObject(this, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("stubClient", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void handle(Event _event) {

		KeyEvent event = (KeyEvent)_event;
        if (event.getCode() == KeyCode.UP) {
            button = 1;
        } else if (event.getCode() == KeyCode.DOWN) {
            button = 2;
        } else if (event.getCode() == KeyCode.LEFT) {
            button = 3;
        } else if (event.getCode() == KeyCode.RIGHT) {
            button = 4;
        } else if (event.getCode() == KeyCode.SPACE) {
            button = 5;
        } else if (event.getCode() == KeyCode.SHIFT) {
            button = 6;
        } else {
            button = -1; 
        }
        _event.consume();
    }

    public int getButton() throws RemoteException {
        return button;
    }
}