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
		this.possLocs = new ArrayList<>();
		int[] curr = l2C(this.position);
		int[] dub = {2, -2};
		int[] sin = {1, -1};
		for (int horizontal: dub) {
			for (int vertical: sin) {
				int[] newLoc = {curr[0] + horizontal, curr[1] + vertical};
				if ((0 <= newLoc[0] && newLoc[0] <= 7 && 0 <= newLoc[1] && newLoc[1] <= 7)) {
					Piece atLoc = board.board[newLoc[0]][newLoc[1]];
					if (atLoc == null || ! atLoc.colour.equals(this.colour)) {
						// Either there is nothing there or whats there can be eaten
						possLocs.add(c2L(newLoc));
					}
				} 
			}
		} for (int horizontal: sin) {
			for (int vertical: dub) {
				int[] newLoc = {curr[0] + horizontal, curr[1] + vertical};
				if ((0 <= newLoc[0] && newLoc[0] <= 7 && 0 <= newLoc[1] && newLoc[1] <= 7)) {
					Piece atLoc = board.board[newLoc[0]][newLoc[1]];
					if (atLoc == null || ! atLoc.colour.equals(this.colour)) {
						// Either there is nothing there or whats there can be eaten
						possLocs.add(c2L(newLoc));
					}
				} 
			}
		}
		
		return this.possLocs;
	}

}
