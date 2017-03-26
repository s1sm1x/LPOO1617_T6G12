package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.GameEngine;
import dkeep.logic.Point;

public class TestHeroSecondLevel {
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
	public void heroMovesNearOgreAndGameEnds(){

		Board board= new Board(b2);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		assertEquals(2,gameEngine.getBoardLevel(board));
		/*board.placeSymbol(hero.getX(),hero.getY(),hero.getHeroIcon() );
		board.placeSymbol(testOgre.getX(),testOgre.getY(),testOgre.getOgreIcon() );*/
		assertEquals(2,gameEngine.getBoardLevel(board));
		assertEquals(new Point(1,1),gameEngine.getHeroPosition());
		assertEquals(new Point(1,3),gameEngine.getOgrePosition());
		gameEngine.moveHero(board, Direction.DOWN);
		assertTrue(GameEngine.getGameOver());
		assertFalse(GameEngine.getPlayerWon());
	}
	@Test
	public void heroMovesToExitDoorAndNearOgreAndGameEnds(){
		Board board= new Board(b2);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		gameEngine.moveHero(board, Direction.RIGHT);
		gameEngine.moveHero(board, Direction.RIGHT);
		assertEquals('K',gameEngine.getHeroIcon());
	}

	@Test
	public void heroMovesToExitDoorWithoutKeyAndFails(){
		Board board= new Board(b2);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		gameEngine.moveHero(board, Direction.LEFT);
		assertEquals(new Point(1,1),gameEngine.getHeroPosition());
		assertNotEquals(gameEngine.getSymbolAt(board,0,1),'S');
	}

	/*
	 * 
	 * final char[][] b2 =
		{
			{ 'X', 'X', 'X', 'X', 'X' },
			{ 'I', 'H', ' ', 'k', 'X' },
			{ 'I', ' ', ' ', ' ', 'X' },
			{ 'X', 'O', ' ', ' ', 'X' },
			{ 'X', 'X', 'X', 'X', 'X' }
		};
	 * 
	 */
	@Test
	public void heroMovesWithKeyAndDoorOpensUponReachingIt(){
		Board board= new Board(b2);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		gameEngine.moveHero(board, Direction.RIGHT);
		gameEngine.moveHero(board, Direction.RIGHT);
		gameEngine.moveHero(board, Direction.LEFT);
		gameEngine.moveHero(board, Direction.LEFT);
		assertEquals(gameEngine.getSymbolAt(board,0,1),'I');
		gameEngine.moveHero(board, Direction.LEFT);
		assertEquals(gameEngine.getSymbolAt(board,0,1),'S'); 
	}
	@Test
	public void heroMovesWithKeyDoorOpensAndWinsTheGame(){
		Board board= new Board(b2,2);//indicar que é o do segundo nível
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		
		gameEngine.moveHero(board, Direction.RIGHT);
		gameEngine.moveHero(board, Direction.RIGHT);
		
		gameEngine.moveHero(board, Direction.LEFT);
		gameEngine.moveHero(board, Direction.LEFT);
		gameEngine.moveHero(board, Direction.LEFT);
		gameEngine.moveHero(board, Direction.LEFT);
		assertTrue(GameEngine.getGameOver());
		assertTrue(GameEngine.getPlayerWon());
		//board.draw();
	}

	

}
