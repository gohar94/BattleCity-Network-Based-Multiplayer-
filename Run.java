import javafx.application.*;
import javafx.stage.*;
import javafx.util.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Run extends Application {

	Scene myScene;
	Group root;

	public void start(Stage stage) {
		root = new Group();
		myScene = new Scene(root, 500, 500);
		Button multi = new Button("Multiplayer");
		multi.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Multiplayer!");
                 
            }
        });
		root.getChildren().add(multi);

		stage.setScene(myScene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}