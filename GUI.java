package Chess.JavaChess;

import javafx.scene.control.Button;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application{


	@Override
	public void start(Stage stage) throws Exception {
		Board board = new Board();
		
		this.setUp(stage, board);
	}
	
	private void setUp(Stage stage, Board board) {
		
		HashMap<Button, Piece> butts = new HashMap<>();
		
		for (Piece[] row: board.board) {
			for (Piece piece: row) {
				Button temp = new Button("" + piece);
				butts.put(temp, piece);
			}
		}
		
		VBox container = new VBox();
		GridPane boardDisplay = new GridPane();
		
		
		
		Scene scene = new Scene(container);
		stage.setTitle("The Most Beautiful Chess Game");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
