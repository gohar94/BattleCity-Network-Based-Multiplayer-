import javafx.scene.image.*;
import javafx.event.*;

public class Player extends Tank implements EventHandler{
	public Player(Location l,Image im){
		super(l,im);
	}
	
	/*
	 * You need to register the appropriate event handling at the root node level.
	 * eg, if Group is your root node, then addEventHandler(KeyEvent.KEY_TYPED, player)
	 */
	void handle(Event e){
		//logic for player. Remember that the player is not allowed to make more
		//than one move per 'tick' or frame.
	}
}
