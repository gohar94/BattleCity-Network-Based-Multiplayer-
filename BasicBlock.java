import javafx.scene.image.*;

public class BasicBlock {
	
	Location location;
	Image image;
	BlockType blockType;

	BasicBlock(Location l, Image im, BlockType b){
		location = l;
		image = im;
		blockType = b;
	}

	public enum BlockType{
		TANK,
		EAGLE,
		MISSILE,
		BRICK,
		STEEL,
		SPACE
	};
}