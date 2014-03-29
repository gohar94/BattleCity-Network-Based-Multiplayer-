import javafx.scene.image.*;
import java.util.ArrayList;
import javafx.scene.shape.*;
import javafx.scene.paint.ImagePattern;
import java.io.Serializable;
 
public class BasicBlock {
 
    public static int score;
    protected static BasicBlock[][] map;
    public static ArrayList<Rectangle> mapRectangles;
    public static ArrayList<BasicBlock> tanks;
    public static boolean end;

    public static Location[] locTanks;
    public static int[] dirTanks;
    public static Location[] locMissiles;
    public static Location[] locBricks;
    public static Location[] locEagles;
    public static Location[] locPower;
    public static Location[] locEnemy;

    public class BlockType {
        static final int BRICK = 1;
        static final int MISSILE = 2;
        static final int SPACE = 3;
        static final int EAGLE = 4;
        static final int TANK = 5;
        static final int STEEL = 6;
        static final int POWERUP = 7;
    }

    public static void updLocTanks() {
        int count = 0;
        for (BasicBlock x : tanks) {
            if (x.type == 5) {
                if (!x.rect.isVisible()) {
                    locTanks[count] = null;
                    continue;
                }
                if (x.isEnemy) {
                    locEnemy[count] = x.location;
                    continue;
                }
                locTanks[count] = x.location;
                if (x.orientation == 0) {
                    System.out.println("BROKENNNN");
                }
                dirTanks[count] = x.orientation;
            } else {
                locMissiles[count] = x.location;
            }
            count++;
        }
        int temp = 0;
        count = 0;
        int countEagle = 0;
        for (int x = 0; x < 13; x++) {
            for (int y = 0; y < 13; y++) {
                if (map[x][y] == null) {
                    continue;
                }
                if (map[x][y].type == 4) {
                    locEagles[countEagle] = map[x][y].location;
                    countEagle++;
                    continue;
                }
                locBricks[count] = map[x][y].location;
                count++;
                temp++;
            }
        }
        score = temp;
    }
    
    boolean frozen;
    Location location;
    Image image;
    int type;
    Rectangle rect;
    Missile missile;
    boolean isEnemy;
    boolean isPlayer;
    boolean helmet;
    int helmetCount;
    int frozenTimer;
    int orientation;
 
    BasicBlock(Location l, Image im){
        location = l;   
        image = im;
        rect = new Rectangle(location.getRow(), location.getCol(), 40, 40);
        rect.setFill(new ImagePattern(im));
        mapRectangles.add(rect);
        end = false;
        frozen = false;
        frozenTimer = 0;
        score = 0;
    }
 
    BasicBlock(Location l, Image im, int _type){
        score = 0;
        location = l;
        image = im;
        type = _type;
        if (_type == BasicBlock.BlockType.TANK) {
            rect = new Rectangle(location.getRow(), location.getCol(), 33, 33);
            rect.setFill(new ImagePattern(im));
            mapRectangles.add(rect);
        } else if (_type == BasicBlock.BlockType.MISSILE) {
            rect = new Rectangle(location.getRow(), location.getCol(), 10, 10);
            rect.setFill(new ImagePattern(im));
            mapRectangles.add(rect);
        } else if (_type == BasicBlock.BlockType.POWERUP) {
            rect = new Rectangle(location.getRow(), location.getCol(), 30, 30);
            rect.setFill(new ImagePattern(im));
        } else {
            rect = new Rectangle(location.getRow(), location.getCol(), 40, 40);
            rect.setFill(new ImagePattern(im));
            mapRectangles.add(rect);
        }
        frozen = false;
        frozenTimer = 0;
    }
 
    public Image getImage() {
        return image;
    }
 
    /* Template method. Each frame before rendering will invoke this method for 
     * all objects contained on the canvas. They are to update their location, image
     * and perhaps even blockType (if blockType change is required).
     * All subclasses should override this method unless they are not expected to have
     * any change over their lifetime
     */
    public void doAction(){
         
         
    } 
     
    /* If another object (eg missile) moves into the space of this object (during doAction), 
     * the missile will invoke method impact of this object passing itself. Using pre-defined
     * rules you are to determine the outcome
     */
    public void impact(BasicBlock b){
 
    }
 
 
    protected boolean movePossible(Location oldL, Location newL) {
        //evaluate move
        Rectangle newLocRect = new Rectangle(newL.getRow(), newL.getCol(), 30, 30);
         
        for (Rectangle x : mapRectangles) {
            if (x != this.rect && x != this.missile.rect) {
                if (newLocRect.getBoundsInParent().intersects(x.getBoundsInParent())) {
                    return false;
                }
            }
        }
        if (newLocRect.getX() < 0 || newLocRect.getX() > 500 || newLocRect.getY() < 0 || newLocRect.getY() > 480) {
            return false;
        }
        return true;
    }

    public void freeze() {
        frozen = true;
    }
 
    protected void makeMove(Location oldL, Location newL) {
        //make the move and make necessary updates
    }
 
}