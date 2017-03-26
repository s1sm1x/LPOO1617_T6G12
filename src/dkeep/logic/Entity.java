package dkeep.logic;

import java.io.Serializable;

public abstract class Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3157799648533988887L;
	protected Point pos;
	/**
	 * default constructor for entity
	 * @param pos desired position
	 */
	protected Entity(Point pos){
		this.pos = pos;
	}
	/**
	 * getter for entity position
	 * @return X coordinate
	 */
	public final int getX(){
		return pos.getX();
	}

	/**
	 * getter for Y position
	 * @return Y coordinate
	 */
	public final int getY(){
		return pos.getY();
	}
	/**
	 * getter for Entity position
	 * @return Point at moment position
	 */
	public final Point getPosition(){
		return pos;
	}
	/**
	 * setter for entity position
	 * @param position to set
	 */
	public final void setPosition(Point pos){
		this.pos=pos;
	}
	/**
	 * Converter between given direction and relative point
	 * @param guardMoves
	 * @return
	 */
	public Point directionToRelativePoint(Direction move){
		switch (move) {
		case UP: 
			return new Point( 0,-1);

		case DOWN:
			return new Point(0,1);

		case RIGHT:
			return new Point(1,0);

		case LEFT:
			return new Point(-1,0);

		default:
			return new Point(0,0);

		}
	}
	/**
	 * abstract method for checking desired board position in each entity extendable
	 * @param board point to verify
	 * @param absolutePoint board absolute point
	 * @return
	 */
	protected abstract boolean checkPositionToMove(Board board, Point absolutePoint);
}
