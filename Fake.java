import javafx.scene.*;
import java.util.ArrayList;
import java.io.Serializable;
import javafx.scene.image.WritableImage;

public class Fake implements Serializable {
	Location[] tanks;
	String name;
	Location[] bricks;
	Location[] missiles;
	WritableImage wi;
	Location[] eagles;
	int[] dirTanks;
	Location[] power;
	Location[] enemy;

	public Fake(Location[] _tanks, Location[] _bricks, Location[] _missiles, Location[] _eagles, int[] _dirTanks, Location[] _power, Location[] _enemy) {
		name = "Gohar";
		tanks = _tanks;
		bricks = _bricks;
		missiles = _missiles;
		eagles = _eagles;
		dirTanks = _dirTanks;
		power = _power;
		enemy = _enemy;
	}
}