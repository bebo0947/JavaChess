package Chess;

import java.util.ArrayList;

public abstract class Piece {
	
	public final String colour;
	public String position;
	public String type;
	
	private String init_pos;
	private ArrayList<String> poss_locs;
	
	public Piece(String colour, int i, String type) {
		this.type = type;
		this.colour = colour;
		ArrayList<String> init_locs = this.initPosss(colour);
		this.init_pos = init_locs.get(i);
		this.position = this.init_pos;
		this.poss_locs = new ArrayList<String>();
	}
	
	public abstract ArrayList<String> getPossMoves(Board board);
	
	public abstract ArrayList<String> initPosss(String colour);
	
	public String getStart() {
		return this.init_pos;
	}
	
	public static String l2C(int[] pos) {
		char letter = (char) (pos[0] + 65);
		String num = "" + (9 - pos[1]);
		return letter + num;
	}
	
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
