import java.net.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.lang.Math;

import javafx.application.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.animation.Timeline;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.ArrayList;
import java.lang.Math;
import javafx.scene.image.WritableImage;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

public class TankWar extends Application implements Multi {
    // This is the class to execute the game
    // protected static BasicBlock[][] map;
     
    // --- IMAGES ---

    static TankWar tWar; 

    public static TankWar getInstance(){
        if (tWar == null){
            tWar = new TankWar();
        }
        return tWar;
    }

    public TankWar() {
        super();
    }

    static Image playerImage = new Image("Player1_Left.png");
    static Image enemyImage = new Image("Enemy1_Left.png");
    static Image brickImage = new Image("Bricks.png");
    static Image steelWall = new Image("SteelWall.png");
    static Image eagle = new Image("Eagle.png");
    static Image helmet = new Image("Helmet.png");
    static Image grenade = new Image("Grenade.png");
    static Image timer = new Image("Timer.png");

    // --- IMAGES ---

    int counter = 0;
    int powerLife = 0;
    boolean powerOn = false;
    int powerrr = -1;
    int winner = 0;

    Scene myScene;
    Group root;

    public void changeCounter() {
        counter++;
    }
 
    public void resetCounter() {
        counter = 0;
    }

    public PowerUp powerUpChange(PowerUp power, int type) {
        power = new PowerUp((new Location(400, 470)), eagle, type);
        power.rect.setX(400);
        power.rect.setY(470);
        return power;
    }
    
    Timeline myTimeline;
    Boolean multiplayer;
    AudioClip music;
    Text scorebox;
    Rectangle scorerect;
    @SuppressWarnings("unchecked")
    public void start(Stage stage){
        
        String source = new File("music.mp3").toURI().toString();
        Media media = null;
        media = new Media(source);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        
        scorerect = new Rectangle(0, 520, 540, 20);
        scorerect.setFill(Color.WHITE);
        scorebox = new Text(210, 540, "Score");
        scorebox.setFont(new Font(20));

        multiplayer = false;

        try {
 
        root = new Group();
 
        root.getChildren().addAll(BasicBlock.mapRectangles);
        root.getChildren().add(scorerect);
    
        final Player playerOne = new Player((new Location(1, 1)), playerImage);

        final Player playerTwo = new Player((new Location(50, 1)), playerImage);
 
        final Enemy enemyOne = new Enemy((new Location(-455, -1)), enemyImage);

        final Enemy enemyTwo = new Enemy((new Location(100, 470)), enemyImage);
 
        for (BasicBlock x : BasicBlock.tanks) {
            root.getChildren().add(x.rect);
        }
 
        myScene = new Scene(root, 540, 540, Color.BLACK);
        stage.setScene(myScene);
        stage.setTitle("Battle City");
        stage.show();

        try {
            Multi multi = new TankWar();
            Multi stub = (Multi)UnicastRemoteObject.exportObject(multi, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("stub", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final PowerUp powerUp = new PowerUp((new Location(-100, -100)), eagle, 3);
        root.getChildren().add(powerUp.rect);

        Button multi = new Button("Multiplayer");
        multi.setLayoutX(250);
                multi.setOnAction(new EventHandler<ActionEvent>() {
         
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Multiplayer!");
                        multiplayer = true;
                    }
                });

                root.getChildren().add(multi);
                root.getChildren().add(scorebox);
        final EventHandler<ActionEvent> events = new EventHandler<ActionEvent>() {
            
            public void handle(ActionEvent event) {
                Integer xxxxx = new Integer(48 - BasicBlock.score);
                scorebox.setText(xxxxx.toString());
                // System.out.println(BasicBlock.score);
                
                if (!multiplayer) {
                    return;
                }

                root.getChildren().remove(multi);

                try {
                    Registry registry = LocateRegistry.getRegistry();
                    final MultiServer keyy=(MultiServer)registry.lookup("stubClient");
                    playerTwo.button = keyy.getButton();
                    playerTwo.press();
                } catch (Exception e) {
                    // e.printStackTrace();
                }

                BrickBlock.updLocTanks();
                

                
                if (winner > 2000) {
                    System.out.println("WIN");
                    myScene.setRoot(new Group());
                    myScene.setFill(new ImagePattern(new Image("Winner.png")));
                    stopGame(myTimeline);
                }
                winner++;
                // myScene.setOnKeyPressed(playerOne);
                int spawned = 0;

                // POWER UPS
                double rand = Math.random()*200;
                if (rand < 2 && powerrr == -1) {
                    if (rand >= 0 && rand < 0.7) {
                        powerUp.setPower(1);
                        powerUp.rect.setFill(new ImagePattern(helmet));
                        powerUp.rect.setVisible(true);
                        powerUp.rect.setX(400);
                        powerUp.rect.setY(470);
                    } else if (rand >= 0.7 && rand < 1.4 ) {
                        powerUp.setPower(2);
                        powerUp.rect.setFill(new ImagePattern(grenade));
                        powerUp.rect.setVisible(true);
                        powerUp.rect.setX(240);
                        powerUp.rect.setY(200);
                    } else {
                        powerUp.setPower(3);
                        powerUp.rect.setFill(new ImagePattern(timer));
                        powerUp.rect.setVisible(true);
                        powerUp.rect.setX(40);
                        powerUp.rect.setY(470);
                    }
                    
                    powerrr++;
                }
                if (powerrr > -1) {
                    powerrr++;
                }
                if (powerrr > 300) {
                    powerUp.rect.setVisible(false);
                    powerUp.rect.setX(-100);
                    powerUp.rect.setY(-100);
                    powerrr = -1;
                }
                // -- POWER UPS OVER

                BasicBlock.locPower[0] = powerUp.location;

                if (BasicBlock.end || playerOne.deleter > 6) {
                    System.out.println("LOSE");
                    myScene.setRoot(new Group());
                    myScene.setFill(new ImagePattern(new Image("GameOver.png")));
                    stopGame(myTimeline);
                }
 
                // ArrayList<BasicBlock> copy = new ArrayList<BasicBlock>(BasicBlock.tanks);

                // DO ACTION ----
                try {
                for (BasicBlock x : BasicBlock.tanks) {
                    x.doAction();
                    if (x.rect.getBoundsInParent().intersects(powerUp.rect.getBoundsInParent())) {
                        powerUp.impact(x);
                    }
                    
                    spawned++;
                }
                } catch (Exception e) {
                    System.out.println("Handled");
                }
                // System.out.println("Sp " + spawned);
                changeCounter();
 
                // AUTO SPAWN ENEMIES
                if (counter > 400) {
                 boolean spawn = true;
                 if (spawned > 7) {
                     spawn = false;
                 }
                     
                 System.out.println(counter);
                 Location newLoc = new Location(500, 470);
                for (BasicBlock x : BasicBlock.tanks) {
                    if (x.location.equals(newLoc)) {
                        spawn = false;
                        break;
                    }
                }
                if (spawn) {
                    Enemy en = new Enemy((new Location(500, 470)), enemyImage);
                    root.getChildren().add(en.rect);
                    root.getChildren().add(en.missile.rect);
                }
                resetCounter();
                }
            }
        };
 
        KeyFrame myFrame = new KeyFrame(Duration.millis(10), events);
 
        myTimeline = new Timeline(myFrame);
        myTimeline.setCycleCount(Animation.INDEFINITE);
        myTimeline.setAutoReverse(true);
        myTimeline.play();
        stage.getScene().setOnKeyPressed(playerOne);

    } catch (Exception e) {
        System.out.println("Hi");
    }
    }

    public Fake getScene() {
        BasicBlock.updLocTanks();
        Fake f = new Fake(BasicBlock.locTanks, BasicBlock.locBricks, BasicBlock.locMissiles, BasicBlock.locEagles, BasicBlock.dirTanks, BasicBlock.locPower, BasicBlock.locEnemy);
        return f;
    }

    public void stopGame(Timeline myTimeline) {
        myTimeline.stop();
    }

    public static void main(String[] args) throws Exception {
        
        try {
        BasicBlock.locTanks = new Location[20];
        BasicBlock.locEnemy = new Location[20];
        BasicBlock.dirTanks = new int[20];
        BasicBlock.locMissiles = new Location[20];
        BasicBlock.locBricks = new Location[13*13];
        BasicBlock.locEagles = new Location[2];
        BasicBlock.map = new BasicBlock[13][13];
        BasicBlock.locPower = new Location[1];
        BasicBlock.mapRectangles = new ArrayList<Rectangle>();
        BasicBlock.tanks = new ArrayList<BasicBlock>();
        // images
         
        // fill the map array with bricks
        int[] nummm = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        int[] nummm2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};        
        
        for (int i = 0; i < 26; i++) {
            double dd = Math.random()*13;
            double ee = Math.random()*13;
            int fireNum = (int)dd;
            int fireNum2 = (int)ee;
            if (nummm[fireNum]*40 != 470 && nummm2[fireNum2]*40 != 470 && nummm[fireNum]*40 != 40 && nummm2[fireNum2]*40 != 0) {
                BasicBlock.map[fireNum][fireNum2] = new BrickBlock((new Location(fireNum*40, fireNum2*40)), brickImage);    
            }
        }
        
        // for (int i = 1; i < 5; i++) {
        //     BasicBlock.map[1][i] = new BrickBlock((new Location(1*40, i*40)), brickImage);
        //     BasicBlock.map[3][i] = new BrickBlock((new Location(3*40, i*40)), brickImage);
        //     BasicBlock.map[9][i] = new BrickBlock((new Location(9*40, i*40)), brickImage);
        //     BasicBlock.map[11][i] = new BrickBlock((new Location(11*40, i*40)), brickImage);
        // }
 
        // for (int i = 1; i < 4; i++) {
        //     BasicBlock.map[5][i] = new BrickBlock((new Location(5*40, i*40)), brickImage);
        //     BasicBlock.map[7][i] = new BrickBlock((new Location(7*40, i*40)), brickImage);
        // }
 
        // for (int i = 7; i < 10; i++) {
        //     BasicBlock.map[5][i] = new BrickBlock((new Location(5*40, i*40)), brickImage);
        //     BasicBlock.map[7][i] = new BrickBlock((new Location(7*40, i*40)), brickImage);  
        // }       
 
        // for (int i = 8; i < 11; i++) {
        //     BasicBlock.map[1][i] = new BrickBlock((new Location(1*40, i*40)), brickImage);
        //     BasicBlock.map[3][i] = new BrickBlock((new Location(3*40, i*40)), brickImage);
        //     BasicBlock.map[9][i] = new BrickBlock((new Location(9*40, i*40)), brickImage);
        //     BasicBlock.map[11][i] = new BrickBlock((new Location(11*40, i*40)), brickImage);
        // }

        BasicBlock.map[6][3] = new SteelBlock((new Location(6*40, 3*40)), steelWall);
        BasicBlock.map[2][6] = new BrickBlock((new Location(2*40, 6*40)), brickImage);
        BasicBlock.map[10][6] = new BrickBlock((new Location(10*40, 6*40)), brickImage);

        // blocks surrounding eagle
        BasicBlock.map[6][12] = new Eagle((new Location(6*40, 12*40)), eagle);
        BasicBlock.map[6][1] = new Eagle((new Location(6*40, 1*40)), eagle);

        BrickBlock a = new BrickBlock((new Location(5*40+20, 12*40+20)), brickImage);
        BrickBlock b = new BrickBlock((new Location(7*40, 12*40)), brickImage);
        BrickBlock c = new BrickBlock((new Location(5*40+20, 11*40+20)), brickImage);
        BrickBlock d = new BrickBlock((new Location(7*40, 11*40+20)), brickImage);
        BrickBlock e = new BrickBlock((new Location(6*40, 11*40+20)), brickImage);
        a.rect.setWidth(20);
        b.rect.setWidth(20);
        c.rect.setWidth(20);
        d.rect.setWidth(20);
        d.rect.setHeight(20);
        e.rect.setHeight(20);
        BasicBlock.map[5][12] = a;
        BasicBlock.map[7][12] = b;
        BasicBlock.map[5][11] = c;
        BasicBlock.map[7][11] = d;
        BasicBlock.map[6][11] = e;
        
        TankWar.getInstance();
        Application.launch(args); //to launch the startMethod
        } catch (Exception e) {
            System.out.println("hi");
            e.printStackTrace();
        }
    }

}