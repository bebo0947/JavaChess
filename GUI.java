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
	public Button selected = null;
	
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
				temp.setPrefSize(60, 60);
				
				if ((i + j) % 2 == 1) {	temp.setStyle("-fx-background-color: #000000");;}
				else {temp.setStyle("-fx-background-color: #FFFFFF");}
				
				EventHandler<ActionEvent> update = new EventHandler<ActionEvent>() {
					public void handle(ActionEvent e) {
						Button b = (Button) e.getSource();
						
						Piece p = butts.get(b);

						System.out.println("" + p);
						
						if (p.colour.equals(board.turn)) {
							selected = b;
							Update(stage, board);
						}	
					}
				};
				
				boardDisplay.add(temp, i, j);
				
				if (!(piece == null)) {
					temp.setText("" + piece);
					temp.setOnAction(update);
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
		System.out.println("thing");
		
		// Looks like im gonna have to reconstruct the gridpane, scene and stage every time :) yay
		
		if (this.selected == null) {
			
		} else {
			Piece p = this.butts.get(this.selected);
			assert p.colour == board.turn;
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					Piece piece = board.board[i][j];
					int[] loc = {i, j};
					String locs = Piece.c2L(loc);
					

					Button temp = new Button();
					if (this.Pieces.containsKey(piece)) {
						temp = Pieces.get(piece);
					}
					
					temp.setPrefSize(60, 60);
					
					if ((i + j) % 2 == 1) {	temp.setStyle("-fx-background-color: #000000");;}
					else {temp.setStyle("-fx-background-color: #FFFFFF");}

					if ( butts.get(selected).getPossMoves(board).contains(locs)) {
						temp.setStyle("-fx-background-color: #FFFF00"); 
						System.out.println("In Danger");
					}
					
				}
			}
		}
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
		System.out.println("hello");
	}

	public void handle(ActionEvent e) {
		Button b = (Button) e.getSource();
		
		Piece p = map.get(b);

		System.out.println("" + p);
		
		if (p.colour.equals(board.turn)) {
			this.selected = b;
		}		
		
	}
}



class MovePiece implements EventHandler<ActionEvent> {

	private HashMap<Button, Piece> map;
	private Stage stage;
	private Board board;
	private Button selected;

	public MovePiece(HashMap<Button, Piece> map, Stage stage, Board board, Button selected) {
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
			System.out.println("" + p);
			
		}		
		
	}
}








