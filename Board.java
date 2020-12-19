package Chess.JavaChess;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	
	public static void println(Object o) {
		System.out.println(o);
	}
	public static void print(Object o) {
		System.out.print(o);
	}
	
	public HashMap<String, ArrayList<Piece>> discriminated;
	public ArrayList<Piece> pieces;
	public Piece[][] board;
	public String turn;
	
	private String[] colours = {"black", "white"};
	
	public Board() {
		this.loadPieces();
		this.board = new Piece[8][8];
		this.placePiece();
		this.turn = "white";
	}
	
	private void placePiece() {
		for (Piece piece: this.pieces) {
			int[] pos = Piece.l2C(piece.position);
			this.board[pos[0]][pos[1]] = piece;
		}
		
	}
	
	public void move(Piece piece, String loc) {
		assert this.pieces.contains(piece);
		assert piece.getPossMoves(this).contains(loc);
		Piece thing = this.getAtLoc(loc);
		if (thing.equals(null)) {
			
		}
	}
	
	private Piece getAtLoc(int[] loc) {
		return this.board[loc[0]][loc[1]];
	}
	
	private Piece getAtLoc(String loc) {
		int[] loc2 = Piece.l2C(loc);
		return this.getAtLoc(loc2);
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
			} for (int i = 0; i < 8; i++) {
				fin.get(colour).add(new Pawn(colour, i));
			}
		}
		this.pieces = fin.get("black");
		for (Piece piece: fin.get("white")) { this.pieces.add(piece); }
		this.discriminated = fin;
	}
	
	public static void main(String[] args) {
		Board board = new Board();
	}
	
}
