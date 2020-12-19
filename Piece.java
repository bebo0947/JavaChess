package Chess.JavaChess;

import java.util.ArrayList;

public abstract class Piece {
	
	public final String colour;
	public String position;
	public String type;
	public ArrayList<String> possLocs;
	
	private String init_pos;
	
	public Piece(String colour, int i, String type) {
		this.type = type;
		this.colour = colour;
		ArrayList<String> init_locs = this.initPosss(colour);
		this.init_pos = init_locs.get(i);
		this.position = this.init_pos;
		this.possLocs = new ArrayList<String>();
	}
	
	/**
	 * Compose, and return the possible positions this piece can go to
	 * @param board the current state of the board.
	 * @return possible positions
	 */
	public abstract ArrayList<String> getPossMoves(Board board);
	
	/**
	 * Return the potential initial position for a piece of this type and color
	 * @param colour color of this piece. 
	 * @return initial positions
	 */
	public abstract ArrayList<String> initPosss(String colour);
	
	public String getStart() {
		return this.init_pos;
	}
	
	/**
	 * Convert from coordinate to Chess position
	 * @param pos coordinate
	 * @return chess position in form of letter number
	 */
	public static String l2C(int[] pos) {
		char letter = (char) (pos[0] + 65);
		String num = "" + (9 - pos[1]);
		return letter + num;
	}
	
	/**
	 * Convert from chess position to coordinate
	 * @param pos chess position in form of letter number
	 * @return coordinate
	 */
	public static int[] c2L(String pos) {
		int letter = ((int) pos.charAt(0)) - 65;
		int num = 9 - (int) pos.charAt(1);
		int[] thing = {letter, num};
		return thing;
	}
	
	@Override
	public String toString() {
		String fin = "";
		fin = "" + this.colour + " " + this.type + " at position " + this.position;
		return fin;		
	}
	
	public static void main(String[] args) {
		int[] pos = {5, 4};
		System.out.println(Piece.l2C(pos));
		System.out.print(c2L("A1"));
	}
}