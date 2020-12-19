package Chess.JavaChess;

import java.util.ArrayList;

public class Pawn extends Piece{
	
	private boolean hasMoved;
	
	public Pawn(String colour, int i) {
		super(colour, i, "Pawn");
		this.hasMoved = false;
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
		int[] curr = l2C(this.position);
		this.possLocs = new ArrayList<>();
		if (0 == curr[1] || curr[1] == 8) {
			return this.possLocs;
		} Board.println(hasMoved);
		if (this.colour.equals("black")) {
			int[] first = {curr[0] + 1, curr[1]};
			this.possLocs.add(c2L(first));
			if (! this.hasMoved) {
				int[] second = {curr[0] + 2, curr[1]};
				this.possLocs.add(c2L(second));
			}
		} else if (this.colour.equals("white")) {
			int[] first = {curr[0] - 1, curr[1]};
			this.possLocs.add(c2L(first));
			if (! this.hasMoved) {
				int[] second = {curr[0] - 2, curr[1]};
				this.possLocs.add(c2L(second));
			}
		} return this.possLocs;
	}
}
