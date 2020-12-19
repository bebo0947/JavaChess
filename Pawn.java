package Chess.JavaChess;

import java.util.ArrayList;

public class Pawn extends Piece{
	
	public Pawn(String colour, int i) {
		super(colour, i, "Pawn");
	}
	
	public ArrayList<String> initPosss(String colour) {
		ArrayList<String> temp = new ArrayList<String>();
		if (colour.equals("black")) {
			for (int i = 1; i <= 8; i++) {
				temp.add("B" + i);
			}
		} else if (colour.equals("white")) {
			for (int i = 1; i <= 8; i++) {
				temp.add("G" + i);
			}
		} return temp;
	}


	@Override
	public ArrayList<String> getPossMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
