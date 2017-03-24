package dkeep.logic;

import java.io.Serializable;

public class Point  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8608482595289896814L;
	private int x;
	private int y;
	/**
	 * default contructor for point 
	 */
	public Point(){
		this(0, 0);
	}
	/**
	 * consctructor for point specifying coordinates
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * setter for X coordinate point
	 * @param x
	 */
	public void setX(int x){
		this.x=x;
	}
	/**
	 * setter for y coordinate point
	 * @param y
	 */
	
	public void setY(int y){
		this.y=y;
	}
	/**
	 * getter for X coordinate point
	 * @return X coordinate
	 */
	public final int getX(){
		return x;
	}
	/**
	 * getter for Y coordinate point
	 * @return Y coordinate
	 */
	public final int getY(){
		return y;
	}
	/**
	 * point printer
	 */
	public void printPoint(){
		System.out.println(this.x + " " + this.y);
	}
	/**
	 * converter to string 
	 */
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + "]";
	}
/**
 * point equals override
 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
