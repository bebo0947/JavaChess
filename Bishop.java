package Chess.JavaChess;

import java.util.ArrayList;

public class Bishop extends Piece{
	
	public Bishop(String colour, int i) {
		super(colour, i, "Bishop");
	}
	
	public ArrayList<String> initPosss(String colour) {
		ArrayList<String> temp = new ArrayList<String>();
		if (colour.equals("black")) {
			temp.add("A6"); temp.add("A3");
		} else if (colour.equals("white")) {
			temp.add("H6"); temp.add("H3");
		} return temp;
	}


	@Override
	public ArrayList<String> getPossMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}

}
