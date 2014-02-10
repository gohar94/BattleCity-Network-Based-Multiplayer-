import javafx.scene.image.*;

public class BasicBlock {
	
	Location location;
	Image image;

	BasicBlock(Location l, Image im){
		location = l;
		image = im;
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


	protected boolean movePossible(Location oldL, Location newL){
                //evaluate move
                return false;
        }

        protected void makeMove(Location oldL, Location newL){
                //make the move and make necessary updates
        }

}
