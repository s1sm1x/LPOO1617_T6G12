package dkeep.logic;

import java.io.Serializable;

public class MassiveClub  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2674269278295911166L;
	protected Point pos;
	/**
	 * default contructor fo massive club
	 * @param pos massive club position in board
	 */
	protected MassiveClub(Point pos) {
		this.pos=pos;

	}
	/**
	 * Default constructor massive club position
	 */
	protected MassiveClub() {
		this.pos=new Point();

	}
	/**
	 * getter for massive club position
	 * @return Point with massive club at moment position
	 */
	protected Point getMassiveClubPosition(){
		return pos;
	}
	/**
	 * getter for X coordinate of massiveClub
	 * @return X coordinate
	 */
	public int getX(){
		return pos.getX();
	}
	/**
	 * getter for Y coordinate massiveClub
	 * @return Y coordinate
	 */
	public int getY(){
		return pos.getY();
	}
	/**
	 * setter for X coordinate position
	 * @param x
	 */
	public void setX(int x){
		this.pos.setX(x);
	}
	/**
	 * setter for Y coordinate position
	 * @param y
	 */
	public void setY(int y){
		this.pos.setY(y);
	}


	/**

	 * @param board
	 * @param pointToPlace
	 * @param ownerOgrePosition
	 */
	/*protected void move(Board board, Point pointToPlace, Point ownerOgrePosition) {
		// TODO Auto-generated method stub
		// esta deve ser uma posição relativa ao ogre, Dizer direcção nao pode porque nao se move relativo à sua, mas à do ogre
		
	}*/

}
