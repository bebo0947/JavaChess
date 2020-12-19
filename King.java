package Chess.JavaChess;

import java.util.ArrayList;

public class King extends Piece {
	
	public final int[] delta = {-1, 0, 1};
	
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
		int[] pos = c2L(this.position);
		this.possLocs = new ArrayList<>();
		for (int row: delta) {
			for (int column: delta) {
				int[] newLoc = {pos[0] + row, pos[1] + column};
				Piece atLoc = board.board[newLoc[0]][newLoc[1]];
				if (atLoc.equals(null) || ! atLoc.colour.equals(this.colour)) {
					// Either there is nothing there or whats there can be eaten
					possLocs.add(l2C(newLoc));
				}
			}
		}
		
		return this.possLocs;
		
	}
	
	
	
}
