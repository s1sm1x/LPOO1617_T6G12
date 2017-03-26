package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Hero extends Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5736560334550967845L;
	private char heroIcon='H';
	private boolean isArmed;
	private boolean hasKey;
	/**
	 * Hero default constructor
	 * @param pos hero initial position
	 */
	public Hero(Point pos) {
		super(pos);
		isArmed=false;
		hasKey=false;
	}
	/**
	 * getter for hero icon
	 * @return char with hero Icon 
	 */
	public char getHeroIcon()
	{
		return heroIcon;
	}
	/**
	 * setter for hero position
	 * @param point desired position
	 */
	public void setHeroPosition(Point point)
	{
		pos.setX(point.getX());
		pos.setY(point.getY());

	}
	/**
	 * hero mover
	 * @param board 
	 * @param direction direction to move
	 */
	public void move(Board board, Direction direction)
	{
		Point relativepoint =directionToRelativePoint(direction);
		Point absolutePoint= new Point(pos.getX()+relativepoint.getX(), pos.getY()+relativepoint.getY());

		if ( checkPositionToMove(board, absolutePoint))
		{
			board.placeSymbol(pos.getX(), pos.getY(), ' ');
			board.placeSymbol(absolutePoint.getX(), absolutePoint.getY(), heroIcon);
			pos.setX(absolutePoint.getX());
			pos.setY(absolutePoint.getY());
		}
	}
	/**
	 * setter for hero icon
	 * @param s desired icon
	 */
	public void setHeroIcon( char s )
	{
		heroIcon= s;
	}
	/**
	 * getter for hero having key
	 * @return true if hero has key, false otherwise
	 */
	protected boolean getHasKey()
	{
		return hasKey;
	}
	/**
	 * hero checker for further position availability
	 */
	@Override
	protected boolean checkPositionToMove(Board board, Point absolutePoint) {

		if((board.symbolAt (absolutePoint.getX(),absolutePoint.getY())=='S') && (board.getLevel()==1))	{
			board.setLevel(2);
			isArmed=true;
			return false;
		}	else if((board.symbolAt(absolutePoint.getX(),absolutePoint.getY())==' ' || board.symbolAt(absolutePoint.getX(),absolutePoint.getY())=='k') && board.getLevel()==1 ){
			if( board.symbolAt(absolutePoint.getX(),absolutePoint.getY())=='k')	{
				heroIcon='K';
				changeEscapeSymbol( board );
			}
			return true;
		}else if ((board.symbolAt(absolutePoint.getX(), absolutePoint.getY())==' ' || board.symbolAt (absolutePoint.getX(),absolutePoint.getY())=='k') && board.getLevel()==2 ){
			if(board.symbolAt(absolutePoint.getX(),absolutePoint.getY())=='k' ){
				heroIcon='K';
				hasKey=true;
			}
			return true;
		}else if(board.symbolAt(absolutePoint.getX(),absolutePoint.getY())=='I' && heroIcon=='K' && board.getLevel()==2){
			changeEscapeSymbol( board );
			return false;
		}else if(board.symbolAt(absolutePoint.getX(),absolutePoint.getY())=='S' && board.getLevel()==2){
			GameEngine.setGameOver(true);
			GameEngine.setPlayerWon(true);
			GameEngine.setKeepSecondGame(false);
			return true;
		}
		return false;
	}

	/**
	 * checker for guard, ogres and massive clubs near the ogre
	 * @param board
	 * @return true if there isn't any ending entity in his ortogonal positions
	 */
	public boolean checkSecurityDistance(Board board)
	{
		ArrayList<Character> enemiesToSearch = new ArrayList<Character>();
		if(board.symbolAt(pos.getX(), pos.getY())== '*')
		{
			return false;
		}
		if(!isArmed)
			enemiesToSearch = new ArrayList<Character>(Arrays.asList('G','O','*','$'));
		else
			enemiesToSearch = new ArrayList<Character>(Arrays.asList('*'));

		for (char enemy : enemiesToSearch)
		{
			if ((board.symbolAt(pos.getX()-1, pos.getY()) == enemy) ||(board.symbolAt(pos.getX()+1, pos.getY()) == enemy)
					||(board.symbolAt(pos.getX(), pos.getY()-1) == enemy)||(board.symbolAt(pos.getX(), pos.getY()+1) == enemy)|| (board.symbolAt(pos.getX(), pos.getY()) == enemy))
				//heroIcon='d';
				return false;
		}
		return true; 
	}
	/**
	 * changer the board exit symbol when hero reaches the key
	 * @param board
	 */
	private void changeEscapeSymbol(Board board ){
		for( int l=0; l<board.getHeight(); l++){
			if(board.symbolAt(0, l)=='I') 
				board.placeSymbol(0,l,'S');}


		for( int m=0; m<board.getHeight(); m++)	{ 
			if(board.symbolAt(board.getHeight()-1, m)=='I') 
				board.placeSymbol(board.getHeight()-1,m,'S');}



		for( int n=0; n<board.getWidth(); n++)
		{if(board.symbolAt(n,0)=='I') 
			board.placeSymbol(n,0,'S');}


		for( int o=0; o<board.getWidth(); o++)
		{if(board.symbolAt(o, board.getWidth()-1)=='I') 
			board.placeSymbol(o,board.getWidth()-1,'S');}
	}



}

