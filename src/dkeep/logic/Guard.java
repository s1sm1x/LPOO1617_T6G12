package dkeep.logic;

import java.awt.Component;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Guard extends Entity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4777925650715934271L;
	private int guardMovementIndex=0;
	private static  Direction[] guardMoves= {Direction.LEFT, Direction.DOWN,Direction.DOWN,Direction.DOWN,Direction.DOWN,Direction.LEFT,Direction.LEFT,Direction.LEFT,Direction.LEFT,Direction.LEFT,Direction.LEFT,Direction.DOWN,Direction.RIGHT,Direction.RIGHT,Direction.RIGHT,Direction.RIGHT,Direction.RIGHT,Direction.RIGHT,Direction.RIGHT,Direction.UP,Direction.UP,Direction.UP,Direction.UP,Direction.UP};
	private  char lastGuardIcon;
	private char guardIcon='G';
	private  char direction='F';
	private String directionPatrol = "FFB";
	private int counterForTurn=0;
	private Direction dir = null;
	private char lastdirection=' ';
	private boolean sleeping;
	private String guardType= "Suspicious";
	private String patrol = "GGg";
	private static final Random a = new Random();
	/**
	 * Guard constructor
	 * @param pos position to place
	 */
	public Guard(Point pos) {
		super(pos);
		sleeping = false;
	}
	/**
	 * getter for guard icon
	 * @return at moment guard icon
	 */
	public char getGuardIcon(){
		return guardIcon;
	}
	/**
	 * getter for guard sleeping state
	 * @return true if sleeping false otherwise
	 */
	public final boolean isSleeping(){
		return sleeping;
	}
	/**
	 * takes guard out of the board
	 */
	public void destroyGuard() {
		guardIcon=' ';
	}
	/**
	 * setter or guard type
	 * @param type desired type
	 */
	protected void setGuardType(int type){
		switch (type) {
		case 1:  
			guardType="Rookie";
			break;

		case 2:
			guardType="Drunken";
			break;

		case 3:
			guardType="Suspicious";
			break;
		}
	}
	/**
	 * getter for guard type
	 * 
	 * @return guard at moment type
	 */
	protected String getGuardType(){
		return guardType;
	}
	/**
	 * Guard movement directon reverser
	 * @param direction
	 * @return reversed direction
	 */
	protected Direction convertToReverseLetter(Direction direction){
		switch (direction) {
		case UP:  
			dir=Direction.DOWN;
			break;

		case DOWN:
			dir=Direction.UP;
			break;

		case LEFT:
			dir=Direction.RIGHT;
			break;

		case RIGHT:
			dir=Direction.LEFT;
			break;
		}
		return dir;
	}
	/**
	 * guard mover forward direction
	 * @param board
	 */
	public void moveForward(Board board){
		Point relativepoint =directionToRelativePoint(guardMoves[guardMovementIndex]);
		Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());
		board.placeSymbol(pos.getX(), pos.getY(), ' ');
		board.placeSymbol(absolutePoint.getX(), absolutePoint.getY(), guardIcon);
		pos.setX(absolutePoint.getX());
		pos.setY(absolutePoint.getY());
		guardMovementIndex++;
		if( guardMovementIndex==guardMoves.length)
			guardMovementIndex=0;
	}
	/**
	 * guard mover backwards direction
	 * @param board
	 */
	public void moveBackward(Board board){
		if( guardMovementIndex==0) 
			guardMovementIndex=guardMoves.length;
		Point relativepoint =directionToRelativePoint(convertToReverseLetter(guardMoves[guardMovementIndex-1]));
		Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());
		board.placeSymbol(pos.getX(), pos.getY(), ' ');
		board.placeSymbol(absolutePoint.getX(), absolutePoint.getY(), guardIcon);
		pos.setX(absolutePoint.getX());
		pos.setY(absolutePoint.getY());
		guardMovementIndex--;
		if( guardMovementIndex<0)
			guardMovementIndex=guardMoves.length;
	}
	/**
	 * guard mover 
	 * @param board
	 * @param unused
	 * @throws Throwable
	 */
	public void move(Board board, String unused) {
		if( board.getLevel() ==1 ){
			switch (guardType) {
			case "Rookie":  
				moveForward(board);
				break;
			case "Drunken":
				lastGuardIcon=guardIcon;

				guardIcon=patrol.charAt(a.nextInt(patrol.length()));
				if (guardIcon!='g')
					sleeping=false;

				if(guardIcon=='G' && guardIcon== lastGuardIcon){
					if(direction=='B'){
						moveBackward(board);
					}
					else if(direction=='F'){
						moveForward(board);
					}
				}
				else if(guardIcon=='G' && guardIcon!=lastGuardIcon){
					direction=directionPatrol.charAt(a.nextInt(directionPatrol.length()));
					if(direction=='B'){
						moveBackward(board);
					}
					else if(direction=='F'){
						moveForward(board);
					}
				}
				else if(guardIcon=='g'){
					board.placeSymbol(pos.getX(), pos.getY(), ' ');
					board.placeSymbol(pos.getX(), pos.getY(), guardIcon);
					sleeping=true;
				}
				break;
			case "Suspicious":
				lastdirection=direction;
				if(counterForTurn>3){
					direction=directionPatrol.charAt(a.nextInt(directionPatrol.length()));
				}
				if(lastdirection!=direction)
					counterForTurn=0;
				counterForTurn++;
				if(direction=='B'){ 
					moveBackward(board);	
				}
				else if(direction=='F'){
					moveForward(board);
				}
				break;
			}
		}
	}
	/**guard extended check position, not needed due to guard's fixed moves
	 * 
	 */
	@Override
	protected boolean checkPositionToMove(Board board, Point absolutePoint) {
		return false;
	}
	/**
	 * guard moves setter
	 * @param moves
	 */
	public void setguardMoves(Direction[] moves){
		guardMoves=moves;
	}
	/**
	 * guard moves getter
	 ** @return guard moves array
	 */
	
	public Direction[]  getguardMoves(){
		return guardMoves;
	}
	/**
	 * setter for patrol direction
	 * @param directions direction desired
	 */
	public void setPatrolDirection(char directions){
		direction =directions;
	}
	/**
	 * guard sleeping state setter
	 * @param state desired state
	 */
	public void setstate(String state){
		patrol = state;
	}
	/*public ArrayList<Point> checkGuardFreePath(Board board) {
		ArrayList<Point> wrongPoints = new ArrayList<Point>();
		for( int i=0 ; i<guardMoves.length; i++ )
		{
			Point relativepoint =directionToRelativePoint(guardMoves[i]);
			Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());
			if(board.symbolAt(absolutePoint.getX(), absolutePoint.getY())!='n' && board.symbolAt(absolutePoint.getX(), absolutePoint.getY()) == ' '){
				wrongPoints.add(new Point(absolutePoint.getX(),absolutePoint.getY()));
				pos.setX(absolutePoint.getX());
				pos.setY(absolutePoint.getY());
				System.out.println(i);
			}
		}
		return wrongPoints;
	}

	public ArrayList<Point> checkGuardFreePath(Board board) {
		System.out.println("aqui");
		int currentLevel= board.getLevel();
		System.out.println("aqui2");
		board.setLevel(1);
		System.out.println("aqui3");
		ArrayList<Point> wrongPoints = new ArrayList<Point>();
		System.out.println("aqui4");
		for( int i=0 ; i<guardMoves.length; i++ )
		{
			System.out.println("aqui5");
			System.out.println("iteração numero: "+i);
			Point relativepoint =directionToRelativePoint(guardMoves[i]);
			Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());
			pos.setX(absolutePoint.getX());
			pos.setY(absolutePoint.getY());
			if( board.symbolAt(pos.getX(), pos.getY())!=' ')
				{
				System.out.println("aqui6");
				wrongPoints.add(pos);
				System.out.println("Encontro em: "+pos);
				}
		}
		board.setLevel(currentLevel);

		return wrongPoints;
	}*/
}

