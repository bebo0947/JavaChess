package Chess.JavaChess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	
	public static void print(Object o) {
		System.out.println(o);
	}
	
	public HashMap<String, ArrayList<Piece>> discriminated;
	public ArrayList<Piece> pieces;
	public Piece[][] board;
	
	private String[] colours = {"black", "white"};
	
	public Board() {
		this.loadPieces();
		this.board = new Piece[8][8];
		this.placePiece();
	}
	
	private void placePiece() {
		for (Piece piece: this.pieces) {
			print(piece.position);
			int[] pos = Piece.l2C(piece.position);
			print(pos);
			this.board[pos[0] - 1][pos[1] - 1] = piece;
		}
		
	}

	private void loadPieces() {
		HashMap<String, ArrayList<Piece>> fin = new HashMap<>();
		fin.put("black", new ArrayList<Piece>());
		fin.put("white", new ArrayList<Piece>());
		for (String colour: colours) {
			fin.get(colour).add(new King(colour));
			fin.get(colour).add(new Queen(colour));
			for (int i = 0; i < 2; i++) {
				fin.get(colour).add(new Bishop(colour, i));
				fin.get(colour).add(new Knight(colour, i));
				fin.get(colour).add(new Rook(colour, i));
			} for (int i = 0; i < 4; i++) {
				fin.get(colour).add(new Pawn(colour, i));
			}
		}
		this.pieces = fin.get("black");
		for (Piece piece: fin.get("white")) { this.pieces.add(piece); }
		this.discriminated = fin;		
		
		for (Piece piece: this.pieces) {
			print(piece);
		}
	}
	
	public static void main(String[] args) {
		Board board = new Board();
	}
	
}
