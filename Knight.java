package Chess.JavaChess;

import java.util.ArrayList;

public class Knight extends Piece{
	
	public Knight(String colour, int i) {
		super(colour, i, "Knight");
	}
	
	public ArrayList<String> initPosss(String colour) {
		ArrayList<String> temp = new ArrayList<String>();
		if (colour.equals("black")) {
			temp.add("A7"); temp.add("A2");
		} else if (colour.equals("white")) {
			temp.add("H7"); temp.add("H2");
		} return temp;
	}


	@Override
	public ArrayList<String> getPossMoves(Board board) {
		this.po
	}

}
