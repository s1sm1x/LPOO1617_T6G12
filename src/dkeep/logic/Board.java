package dkeep.logic;

import java.io.Serializable;

public class Board implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4604242787779813790L;
	private int level = 1;
	private boolean board2keyCaptured= false;
	
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

}