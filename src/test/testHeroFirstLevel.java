package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.GameEngine;
import dkeep.logic.Point;

public class testHeroFirstLevel {

	final char[][] b1 =
		{
			{ 'X', 'X', 'X', 'X', 'X' },
			{ 'X', 'H', ' ', 'G', 'X' },
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'I', 'k', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X' }
		};
	final char[][] b2 =
		{
			{ 'X', 'X', 'X', 'X', 'X' },
			{ 'I', 'H', ' ', 'k', 'X' },
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'X', 'O', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X' }
		};

	@Test
	public void testMoveHeroIntoAFreeCell() {
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		assertEquals(new Point(1,1),gameEngine.getHeroPosition());
		gameEngine.moveHero(board, Direction.DOWN);
		assertEquals(new Point(1,2),gameEngine.getHeroPosition());
	}
	@Test
	public void testMoveHeroIntoWall() {
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		assertEquals(new Point(1,1),gameEngine.getHeroPosition());
		gameEngine.moveHero(board, Direction.UP);
		assertEquals(new Point(1,1),gameEngine.getHeroPosition());
	}
	
	@Test
	public void testHeroIsCapturedByGuard(){
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board); 
		assertEquals(true, GameEngine.getGameOver());
		gameEngine.moveHero(board, Direction.RIGHT);
		assertTrue(GameEngine.getGameOver());
		assertFalse(GameEngine.getPlayerWon()); 
	}
	
	@Test
	public void testHeroMovesToClosedDoorsAndFailsToLeave(){
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.moveHero(board, Direction.DOWN);
		gameEngine.moveHero(board, Direction.LEFT);
		assertEquals(new Point(1,2),gameEngine.getHeroPosition());
		assertEquals(gameEngine.getHeroIcon(), gameEngine.getSymbolAt(board,1, 2));//
	
	}
	
	@Test
	public void heroMovesToKeyAndOpensDoor(){
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		assertNotEquals(gameEngine.getSymbolAt(board,0,2),'S');
		assertNotEquals(gameEngine.getSymbolAt(board,0,3),'S');
		gameEngine.moveHero(board, Direction.DOWN);
		gameEngine.moveHero(board, Direction.DOWN);
		assertEquals(new Point(1,3),gameEngine.getHeroPosition());
		assertEquals(new Point(1,3),gameEngine.getKeyPosition());
		assertEquals(gameEngine.getSymbolAt(board,0,2),'S');
		assertEquals(gameEngine.getSymbolAt(board,0,3),'S');
		board.draw(); 
	}
	
	@Test
	public void heroMovesToDoorAndGoesToNextLevel(){
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.moveHero(board, Direction.DOWN);
		gameEngine.moveHero(board, Direction.DOWN);
		gameEngine.moveHero(board, Direction.LEFT);
		assertEquals(gameEngine.getBoardLevel(board),2);
		assertNotEquals(gameEngine.getHeroPosition(),new Point(0,3));
	}



}
