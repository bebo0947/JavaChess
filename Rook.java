package Chess.JavaChess;

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
		this.possLocs = new ArrayList<>();
		int[] numbers = {1, 2, 3, 4 , 5, 6, 7};
		int[] curr = l2C(this.position);
		int[] delta = {-1, 1};
		for (int row: delta) {
			for (int num: numbers) {
				int[] newLoc = {curr[0] + num * row, curr[1]};
				if (!(0 <= newLoc[0] && newLoc[0] <= 7 && 0 <= newLoc[1] && newLoc[1] <= 7)) {
					break;
				} Piece atLoc = board.board[newLoc[0]][newLoc[1]];
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
		
		for (int column: delta) {
			for (int num: numbers) {
				int[] newLoc = {curr[0], curr[1] + num * column};
				if (!(0 <= newLoc[0] && newLoc[0] <= 7 && 0 <= newLoc[1] && newLoc[1] <= 7)) {
					break;
				} Piece atLoc = board.board[newLoc[0]][newLoc[1]];
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
		
		return this.possLocs;
	}

}
