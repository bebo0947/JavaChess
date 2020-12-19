package Chess.JavaChess;

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
		this.possLocs = new ArrayList<>();
		int[] numbers = {1, 2, 3, 4 , 5, 6, 7};
		int[] curr = l2C(this.position);
		for (int row: delta) {
			for (int column: delta) {
				for (int num: numbers) {
					int[] newLoc = {curr[0] + num*row, curr[1] + num* column};
					Piece atLoc = board.board[newLoc[0]][newLoc[1]];
					if (atLoc.equals(null)) {
						// Either there is nothing there or whats there can be eaten
						this.possLocs.add(c2L(newLoc));
					} else if (! atLoc.colour.equals(this.colour)) {
						// Either there is nothing there or whats there can be eaten
						this.possLocs.add(c2L(newLoc));
						break;
					} else if (atLoc.colour.equals(this.colour)) {
						// Either there is nothing there or whats there can be eaten
						break;
					}
				}
			}
		}
		
		return this.possLocs;
	}
	
}
