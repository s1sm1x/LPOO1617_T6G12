package dkeep.logic;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4604242787779813790L;
	private int level = 1;
	private boolean board2keyCaptured= false;
	private Point heroPositionLevel1 ;
	private Point heroPositionLevel2;
	private Point keyPositionLevel1;
	private Point keyPositionLevel2;
	private ArrayList<Point> ogresPositions = new ArrayList<>();
	private Point guardPosition;
	
	
	private  char[][] board= {
			{'X','X','X','X','X','X','X','X','X','X'},
			{'X',' ',' ',' ','I',' ','X', ' ',' ','X'},
			{'X','X','X', ' ','X','X','X',' ',' ','X'},
			{'X',' ','I',' ','I',' ','X',' ',' ','X'},
			{'X','X','X',' ','X','X','X',' ',' ','X'},
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'I',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X','X','X',' ','X','X','X','X',' ','X'},
			{'X',' ','I',' ','I',' ','X',' ',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'},
	};
	private  char[][] board2= {
			{'X','X','X','X','X','X','X','X','X','X'},
			{'I',' ',' ',' ',' ',' ',' ', ' ',' ','X'},
			{'X',' ',' ', ' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X',' ',' ',' ',' ',' ',' ',' ',' ','X'},
			{'X','X','X','X','X','X','X','X','X','X'},
	};
	/**
	 * Board constructor ( used to specify the board )
	 * @param testBoard desired board 
	 */
	public Board ( char[][] testBoard){
		this.board=testBoard;
	}
	/**
	 * sets the board disposal
	 * @param board changing board
	 * @param level changing level
	 */
	public void setMatrix(char[][] board, int level)
	{
		switch(level){
		case 1:
			this.board=board;
		case 2:
			this.board2=board;
		}

	}
	/**
	 * default Board constructor when no particular disposals are needed
	 */
	public Board(){	
	}
/**
 * Contructor for Board
 * @param board desired board disposal
 * @param level 
 */
	public Board(char[][] board , int level) {
		switch(level){
		case 1:
			this.board =board ;
			break;
		case 2:
			this.board2=board ;
			break;
		}
		this.level=level;
		
	}
/**
 * Contrutor to set both board's height and width
 * @param x width
 * @param y height
 */
	public Board(int x, int y) {
		board = new char[x][y];
		board2 = new char[x][y];
	}
/**
 * gets a two-dimensional matrix representing the board
 * @return
 */
	protected char[][] getBoard(){
		
		switch (level){
		case 1: 
			return board;
		case 2:
			return board2;
		}
		return null;
	}
/**
 * Changes the board level
 * @param level
 */
	public void setLevel(int level){
		this.level= level;
	}
	/**
	 * getter for board level
	 * @return board level
	 */
	public int getLevel(){
		return level;
	}
/**
 * board drawer on console
 */
	public void draw(){
		switch (level){
		case 1: 
			for( int o =0; o<board.length; o++){ 
				for( int p = 0 ; p < board[0].length ; p++){
					System.out.print(board[o][p]);
				}
				System.out.println();
			}
			break;
		case 2:
			for(int o =0; o<board2.length; o++){
				
				for(int p = 0 ; p < board2[0].length; p++){
					
					System.out.print(board2[o][p]);
					
				}
				
				System.out.println();
			}
			break;
		}
	}
	/**
	 * getter of symbol on a specific coordinate
	 * @param x xaxis coordinate
	 * @param y yaxis coordinate
	 * @return char representing the symbol
	 */
	public final char symbolAt(int x, int y){
		switch (level)
		{
		case 1:
			if (x >= 0 && x < board.length && y >= 0 && y < board[0].length)
			{
				return board[y][x];
			}
			break;
		case 2:
			if (x >= 0 && x < board2.length && y >= 0 && y < board2[0].length)
			{
				return board2[y][x];
			}
			break;
		}
		return 'n';
	}
/**
 *  replaces the symbol at x and y with the given symbol
 * @param x xAxis coordinate
 * @param y yAxis coordinate
 * @param s symbol to place
 * @return return true if it places the symbol, otherwise false
 */
	public boolean placeSymbol(int x, int y, char s){
		
		switch (level)
		{
		case 1:
			if (x >= 0 && x < board.length && y >= 0 && y < board[0].length)
			{
				board[y][x] = s;
				return true;
			}
			break;
		case 2:
			if (x >= 0 && x < board2.length && y >= 0 && y < board2[0].length)
			{
				board2[y][x] = s; 
				return true;
			}
			break;
		}
		return false;
	}
/**
 * getter for board height
 * @return board height
 */
	public final int getHeight(){
		
		switch (level)
		{
		case 1:
			return board.length;

		case 2:
			return board2.length;
		}
		return  0;
	}
	/**
	 * getter for board width
	 * @return board width
	 */
	public final int getWidth(){
		switch (level){
		case 1:
			return board[0].length;

		case 2:
			return board2[0].length;
		}
		return  0;
	}
	/**
	 * checks if position is a corner is a board corner
	 * @param x xAxis coordinate
	 * @param y yAxis coordinate
	 * @return  true if given position is a board corner, 'false' otherwise
	 */
	public final boolean isCorner(int x, int y)
	{
		return (x == 0 && y == 0) || (x == 0 && y == board[0].length - 1) || (x == board.length - 1 && y == 0) || (x == board.length - 1 && y == board[0].length - 1);
	}
	public Point getHeroPositionLevel1() {
		return heroPositionLevel1;
	}
	public void setHeroPositionLevel1(Point heroPositionLevel1) {
		this.heroPositionLevel1 = heroPositionLevel1;
		System.out.println("adiciono level 1 hero");
	}
	public Point getHeroPositionLevel2() {
		return heroPositionLevel2;
	}
	public void setHeroPositionLevel2(Point heroPositionLevel2) {
		this.heroPositionLevel2 = heroPositionLevel2;
		System.out.println("adiciono level 2 hero");
	}
	public Point getKeyPositionLevel1() {
		return keyPositionLevel1;
	}
	public void setKeyPositionLevel1(Point keyPositionLevel1) {
		this.keyPositionLevel1 = keyPositionLevel1;
		System.out.println("adiciono level 1 key");
	}
	public Point getKeyPositionLevel2() {
		return keyPositionLevel2;
	}
	public void setKeyPositionLevel2(Point keyPositionLevel2) {
		this.keyPositionLevel2 = keyPositionLevel2;
		System.out.println("adiciono level 2 key");
	}
	public Point getGuardPosition() {
		return guardPosition;
	}
	public void setGuardPosition(Point guardPosition) {
		this.guardPosition = guardPosition;
		System.out.println("adiciono guarda");
	}
	public ArrayList<Point> getOgresPositions() {
		return ogresPositions;
	}
	public void addOgrePosition ( Point ogrePosition ) {
		this.ogresPositions.add(ogrePosition);
		System.out.println("adiciono ogre");
	}
	public void removeOgrePosition ( Point ogrePosition ) {
		this.ogresPositions.remove(ogrePosition);
		System.out.println("removi ogre de: "+ ogrePosition);
	}
	
	public void fillEmptySpaces() {		
		
		for( int o =0; o<board.length; o++){ 
			for( int p = 0 ; p < board[0].length ; p++){
				if((board[o][p] != 'X') && (board[o][p] != 'k') && (board[o][p] != 'G')&& (board[o][p] != 'H')&& (board[o][p] != 'I'))
					board[o][p]=' ';
			}
		}
		for( int o =0; o<board2.length; o++){ 
			for( int p = 0 ; p < board2[0].length ; p++){
				if((board2[o][p] != 'X') && (board2[o][p] != 'k') && (board2[o][p] != 'O')&& (board2[o][p] != 'H')&& (board2[o][p] != 'I'))
					board2[o][p]=' ';
			}
		}
	}

}