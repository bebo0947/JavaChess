package Chess;

import java.util.ArrayList;

public class Queen extends Piece{
	
	public Queen(String colour) { 
		super(colour, 0, "Queen");
	}
	
	public ArrayList<String> initPosss(String colour) {
		ArrayList<String> temp = new ArrayList<String>();
		if (colour.equals("black")) {
			temp.add("A5");
		} else if (colour.equals("white")) {
			temp.add("H5");
		} return temp;
	}

	@Override
	public ArrayList<String> getPossMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
