import java.net.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;

import javafx.scene.input.KeyEvent;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.application.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import java.util.ArrayList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.*;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

public class Client extends Application {

	Group root;
	Scene scene;
	Timeline myTimeline;
	int button;
	Key k;

	public Client() {
		button = -1;
		k = new Key();
	}
	
	@SuppressWarnings("unchecked")
	public void start(Stage stage) throws RemoteException {

		final Registry registry = LocateRegistry.getRegistry();
		root = new Group();
		scene = new Scene(root, 540, 520, Color.BLACK);
		stage.setScene(scene);
		stage.show();
		Image p1u = new Image("Player1_Up.png");
		Image p1l = new Image("Player1_Left.png");
		Image p1r = new Image("Player1_Right.png");
		Image p1d = new Image("Player1_Down.png");
		Image br = new Image("Bricks.png");
		Image eg = new Image("Eagle.png");
		Image sw = new Image("SteelWall.png");
		Image e1u = new Image("Enemy1_Up.png");
		Image e1d = new Image("Enemy1_Down.png");
		Image e1l = new Image("Enemy1_Left.png");
		Image e1r = new Image("Enemy1_Right.png");
		Image p2u = new Image("Player2_Up.png");
		Image p2l = new Image("Player2_Left.png");
		Image p2r = new Image("Player2_Right.png");
		Image p2d = new Image("Player2_Down.png");
		
		final EventHandler<ActionEvent> events = new EventHandler<ActionEvent>() {
			

 			ArrayList<Rectangle> tanks1 = new ArrayList<Rectangle>();

            public void handle(ActionEvent event) {
            	
            	
            	root.getChildren().setAll(tanks1);

				try {
					Multi fake=(Multi)registry.lookup("stub");
					Location[] tanks = fake.getScene().tanks;
					Location[] bricks = fake.getScene().bricks;
					Location[] missiles = fake.getScene().missiles;
					Location[] eagles = fake.getScene().eagles;
					int[] dirTanks  = fake.getScene().dirTanks;
					Location[] power = fake.getScene().power;
					Location[] enemy = fake.getScene().enemy;

					// Rectangle nn = new Rectangle(power[0].getRow(), power[0].getCol(), 33, 33);
					// nn.setFill(new ImagePattern(p1u));
					// root.getChildren().add(nn);

					int count = 0;
					for (Location x : tanks) {
						if (count == 0) {
							if (x != null) {
								Rectangle n = new Rectangle(x.getRow(), x.getCol(), 33, 33);
								if (dirTanks[count] == 1) {
									n.setFill(new ImagePattern(p1u));
								} else if (dirTanks[count] == 2) {
									n.setFill(new ImagePattern(p1d));
								} else if (dirTanks[count] == 3) {
									n.setFill(new ImagePattern(p1l));
								} else {
									n.setFill(new ImagePattern(p1r));
								}
								root.getChildren().add(n);
							} else {
								
							}
						} else {
							if (x != null) {
								Rectangle n = new Rectangle(x.getRow(), x.getCol(), 33, 33);
								if (dirTanks[count] == 1) {
									n.setFill(new ImagePattern(p2u));
								} else if (dirTanks[count] == 2) {
									n.setFill(new ImagePattern(p2d));
								} else if (dirTanks[count] == 3) {
									n.setFill(new ImagePattern(p2l));
								} else {
									n.setFill(new ImagePattern(p2r));
								}
								root.getChildren().add(n);
							} else {
								
							}
						}
						count++;
					}

					count = 0;
					for (Location x : enemy) {
						if (x != null) {
							Rectangle n = new Rectangle(x.getRow(), x.getCol(), 33, 33);
							if (dirTanks[count] == 1) {
								n.setFill(new ImagePattern(e1u));
							} else if (dirTanks[count] == 2) {
								n.setFill(new ImagePattern(e1d));
							} else if (dirTanks[count] == 3) {
								n.setFill(new ImagePattern(e1l));
							} else {
								n.setFill(new ImagePattern(e1r));
							}
							root.getChildren().add(n);
						} else {
							
						}
						count++;
					}

					for (Location x : bricks) {
						if (x != null) {
							if (x.getRow() == 240 && x.getCol() == 120) {
								Rectangle n = new Rectangle(x.getRow(), x.getCol(), 40, 40);
								n.setFill(new ImagePattern(sw));
								root.getChildren().add(n);	
								continue;
							}
							Rectangle n = new Rectangle(x.getRow(), x.getCol(), 40, 40);
							n.setFill(new ImagePattern(br));
							root.getChildren().add(n);
						} else {
			
						}
					}

					for (Location x : missiles) {
						if (x != null) {
							Rectangle n = new Rectangle(x.getRow(), x.getCol(), 10, 10);
							n.setFill(new ImagePattern(br));
							root.getChildren().add(n);
						} else {
			
						}
					}

					for (Location x : eagles) {
						if (x != null) {
							Rectangle n = new Rectangle(x.getRow(), x.getCol(), 40, 40);
							n.setFill(new ImagePattern(eg));
							root.getChildren().add(n);
						}
					}
				} catch(Exception e) {
					System.out.println("Server not reachable");
				}


			}
		};

		KeyFrame myFrame = new KeyFrame(Duration.millis(10), events);
 
        myTimeline = new Timeline(myFrame);
        myTimeline.setCycleCount(Animation.INDEFINITE);
        myTimeline.setAutoReverse(true);
        myTimeline.play();
        stage.getScene().setOnKeyPressed(k);
		
	}

	public static void main(String[] args) throws Exception {
		Application.launch(args);
    }    
}