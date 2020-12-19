package Chess.JavaChess;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.lang.reflect.Method;
import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GUI extends Application{

	public HashMap<Button, Piece> butts = new HashMap<>();
	public HashMap<Piece, Button> Pieces = new HashMap<>();
	public Button selected;
	
	@Override
	public void start(Stage stage) throws Exception {
		Board board = new Board();
		
		this.setUp(stage, board);
	}
	
	private void setUp(Stage stage, Board board) {
		
		GridPane boardDisplay = new GridPane();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = board.board[i][j];
				
				Button temp = new Button();
				temp.setPrefSize(40, 40);
				
				if ((i + j) % 2 == 1) {	temp.setStyle("-fx-background-color: #000000");;}
				else {temp.setStyle("-fx-background-color: #FFFFFF");}
				

				boardDisplay.add(temp, j, i);
				
				if (!(piece == null)) {
					temp.setText("" + piece);
					temp.setOnAction(new SelectButton(butts, stage, board, selected));
					this.butts.put(temp, piece);
					this.Pieces.put(piece, temp);
				}
			}
		}
		
		VBox container = new VBox();
		container.getChildren().add(boardDisplay);
		
		Scene scene = new Scene(container);
		stage.setTitle("The Most Beautiful Chess Game");
		stage.setScene(scene);
		stage.show();
	}
	
	public void Update(Stage stage, Board board) {
		
	}
	
	
	public static void main(String[] args) {
		launch(args);

	}

}


class SelectButton implements EventHandler<ActionEvent> {

	private HashMap<Button, Piece> map;
	private Stage stage;
	private Board board;
	private Button selected;

	public SelectButton(HashMap<Button, Piece> map, Stage stage, Board board, Button selected) {
		this.map = map;
		this.stage = stage;
		this.board = board;
		this.selected = selected;
	}

	public void handle(ActionEvent e) {
		
		Button b = (Button) e.getSource();
		
		Piece p = map.get(b);
		
		if (p.colour.equals(board.turn)) {
			selected = b;
			
		}		
		
	}
}






