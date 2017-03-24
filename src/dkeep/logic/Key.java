package dkeep.logic;

import java.io.Serializable;

public class Key  implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -561019109671844773L;
private Point pos;
/**
 * default contructor for key
 * @param pos key position in board
 */
	public Key(Point pos){
		this.pos=pos;
	};
	/**
	 * key placer on board
	 * @param board 
	 */
	public void placeKey(Board board){
		board.placeSymbol(pos.getX(), pos.getY(), 'k');
	}
	/**
	 * getter for key position
	 * @return
	 */
	public Point getKeyPosition(){
		return pos;
	}
	
}
