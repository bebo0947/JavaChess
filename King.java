package Chess;

import java.util.ArrayList;

public class King extends Piece {
	
	public King(String colour) { 
		super(colour, 0, "King");
	}
	
	public ArrayList<String> initPosss(String colour) {
		ArrayList<String> temp = new ArrayList<String>();
		if (colour.equals("black")) {
			temp.add("A4");
		} else if (colour.equals("white")) {
			temp.add("H4");
		} return temp;
	}

	@Override
	public ArrayList<String> getPossMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
