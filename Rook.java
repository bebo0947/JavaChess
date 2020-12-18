package Chess;

import java.util.ArrayList;

public class Rook extends Piece{
	
	public Rook(String colour, int i) {
		super(colour, i, "Rook");
	}
	
	public ArrayList<String> initPosss(String colour) {
		ArrayList<String> temp = new ArrayList<String>();
		if (colour.equals("black")) {
			temp.add("A8"); temp.add("A1");
		} else if (colour.equals("white")) {
			temp.add("H8"); temp.add("H1");
		} return temp;
	}


	@Override
	public ArrayList<String> getPossMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
