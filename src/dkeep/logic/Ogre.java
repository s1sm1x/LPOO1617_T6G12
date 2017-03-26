package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Ogre extends Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7096410761853288544L;
	private MassiveClub massiveClub= new MassiveClub(new Point(2,1));
	private char ogreIcon= 'O';
	private int roundsStunnedCounter = 0;
	private static final Random r = new Random();
	/**
	 * default constructor for Ogre
	 * @param pos desired ogre position
	 */
	public Ogre(Point pos) {
		super(pos);
	}
/**
 * getter for ogre's massive club
 * @return this ogre massive club
 */
	protected MassiveClub getMassiveClub(){
		return massiveClub;
	}
/**
 * setter for ogre icon
 * @param s desired icon
 */
	protected void setOgreIcon( char s){
		ogreIcon = s;
	}
/**
 * getter for ogreIcon
 * @return char with at moment ogre icon
 */
	public char getOgreIcon(){
		return ogreIcon;
	}
/**
 * stun de ogre by changing its symbol
 */
	void toggleStunOgre(){
		roundsStunnedCounter=2;
		ogreIcon = '8';
	}
/**
 * random direction availability calculator
 * @param board
 * @return
 */
	private Direction randomMovement(Board board){
		boolean canMove = false;
		Direction move = Direction.NONE;
		Direction validMove= Direction.NONE;
		ArrayList<Direction> allowedDirections= new ArrayList<>(Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));	
		while(canMove == false){ 
			if(allowedDirections.size()==0)
				return Direction.NONE;
			move= allowedDirections.get(r.nextInt(allowedDirections.size()));
			if (move== Direction.UP){
				canMove= checkPositionToMove(board, new Point(0,-1));
				validMove= Direction.UP;
				allowedDirections.remove(Direction.UP);
			}else if (move==Direction.LEFT){
				canMove= checkPositionToMove(board, new Point(-1, 0));
				validMove= Direction.LEFT;
				allowedDirections.remove(Direction.LEFT);
			}else if (move==Direction.DOWN){
				canMove= checkPositionToMove(board, new Point(0,1)); 
				validMove= Direction.DOWN;
				allowedDirections.remove(Direction.DOWN);
			}else if (move==Direction.RIGHT){
				canMove= checkPositionToMove(board, new Point(1,0)); 
				validMove= Direction.RIGHT;
				allowedDirections.remove(Direction.RIGHT);
				}	
			}
		return validMove;
	}
/**
 * ogre's massive club mover
 * @param board
 */
	private void moveMassiveClub( Board board){
		Direction validClubMove = randomMovement(board);
		Point relativepoint =directionToRelativePoint(validClubMove);
		Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());
		if(!(massiveClub.getX() == pos.getX() && massiveClub.getY() == pos.getY()) )
			board.placeSymbol(massiveClub.getX(), massiveClub.getY(), ' '); 
		board.placeSymbol(absolutePoint.getX(), absolutePoint.getY(), '*');
		massiveClub.pos.setX(absolutePoint.getX());
		massiveClub.pos.setY(absolutePoint.getY());
	}
/**
 * ogre mover
 * @param board
 * @param direction desired direction
 * @param keyPosition key current position
 */
	public void move(Board board, String direction, Point keyPosition){
		if( roundsStunnedCounter==0){
			Direction validMove = randomMovement(board);
			Point relativepoint =directionToRelativePoint(validMove);
			Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());
			if(absolutePoint.getX() == keyPosition.getX() && absolutePoint.getY() == keyPosition.getY()){
				ogreIcon='$';
			}

			if(pos.getX()==keyPosition.getX() && pos.getY() == keyPosition.getY()){
				board.placeSymbol(pos.getX(), pos.getY(), 'k');
				ogreIcon='O';
			}

			else {
				board.placeSymbol(pos.getX(), pos.getY(), ' ');
			}
			board.placeSymbol(absolutePoint.getX(), absolutePoint.getY(), ogreIcon);
			pos.setX(absolutePoint.getX());
			pos.setY(absolutePoint.getY());
		}
		else{
			board.placeSymbol(pos.getX(), pos.getY(), ogreIcon);
			roundsStunnedCounter--;
			if (roundsStunnedCounter == 0)
				ogreIcon='O';
		}
		moveMassiveClub(board);
	}
/**
 * ogre further position to move checker
 * @return true if move is possible, otherwise false
 */
	@Override
	protected boolean checkPositionToMove(Board board, Point absolutePoint) {		
		return board.symbolAt(pos.getX()+absolutePoint.getX(), pos.getY()+absolutePoint.getY()) != 'S'  
				&& board.symbolAt(pos.getX()+absolutePoint.getX(), pos.getY()+absolutePoint.getY()) != 'X' 
				&& board.symbolAt(pos.getX()+absolutePoint.getX(), pos.getY()+absolutePoint.getY()) != 'I' 
				&& board.symbolAt(pos.getX()+absolutePoint.getX(), pos.getY()+absolutePoint.getY()) != '*' ;
		}
/**
 * checker if there is any ogre nearby the Icon
 * @param board
 * @param  Icon
 * @return true if there is ogre, false other wise
 */
	protected boolean isOgreNearby(Board board, char  Icon){
		return ((board.symbolAt(pos.getX()-1, pos.getY())== Icon) ||(board.symbolAt(pos.getX()+1, pos.getY())== Icon)
				||(board.symbolAt(pos.getX(), pos.getY()-1)== Icon)||(board.symbolAt(pos.getX(), pos.getY()+1)== Icon));
	}
/**
 * setter for ogre position
 * @param board
 * @param point desired point to move
 */
	public void setOgrePosition(Board board,Point point) {
		board.placeSymbol(pos.getX(), pos.getY(),' ');
		pos.setX(point.getX());
		pos.setY(point.getY());
		board.placeSymbol(pos.getX(), pos.getY(),ogreIcon);
		moveMassiveClub(board);
	}
}
