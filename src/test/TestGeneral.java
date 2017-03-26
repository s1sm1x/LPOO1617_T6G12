package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.GameEngine;
import dkeep.logic.Point;

public class TestGeneral {
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
	
	public void testPoint0ToString(){
		Point O= new Point();
		assertEquals("Point [x=0, y=0]",O+"");
		O.setX(2);
		O.setY(2);
		assertEquals("Point [x=2, y=2]",O+"");
	}
	
	@Test
	public void testRookiemoves() throws Throwable{
		Direction[] guardMoves= {Direction.DOWN,Direction.DOWN,Direction.UP,Direction.UP};
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setguardMoves(guardMoves);
		gameEngine.setGuardType(1);
		assertEquals("Rookie", gameEngine.getGuardType());
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,2),gameEngine.getGuardPosition());
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,3),gameEngine.getGuardPosition());
		
	}
	
	@Test
	public void testDrunkenmoves() throws Throwable{
		Direction[] guardMoves= {Direction.DOWN,Direction.DOWN,Direction.UP,Direction.UP};
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setguardMoves(guardMoves);
		gameEngine.setGuardType(2);
		assertEquals("Drunken", gameEngine.getGuardType());
		gameEngine.setPatrolDirection('B');
		gameEngine.setGuardState("G");
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,2),gameEngine.getGuardPosition());
		gameEngine.setGuardState("g");
		gameEngine.moveGuard(board,"");
		assertTrue(gameEngine.isGuardSleeping());
		assertEquals(new Point(3,2),gameEngine.getGuardPosition());
		gameEngine.setGuardState("G");
		gameEngine.moveGuard(board,"");
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,2),gameEngine.getGuardPosition());
		gameEngine.setPatrolDirection('F');
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,3),gameEngine.getGuardPosition());
	}
	@Test
	public void testSupiciousmoves() throws Throwable{
		Direction[] guardMoves= {Direction.DOWN,Direction.DOWN,Direction.UP,Direction.UP};
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setguardMoves(guardMoves);
		gameEngine.setGuardType(3);
		assertEquals("Suspicious", gameEngine.getGuardType());
		gameEngine.setPatrolDirection('F');
		gameEngine.moveGuard(board,"");
		gameEngine.moveGuard(board,"");
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,2),gameEngine.getGuardPosition());
		gameEngine.setPatrolDirection('B');
		gameEngine.moveGuard(board,"");
		assertEquals(new Point(3,3),gameEngine.getGuardPosition());
	}
	@Test
	public void testConvertToReverseMove() {
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		assertEquals(gameEngine.convertToReverseMove(Direction.UP),Direction.DOWN);
		assertEquals(gameEngine.convertToReverseMove(Direction.LEFT),Direction.RIGHT);
		assertEquals(gameEngine.convertToReverseMove(Direction.DOWN),Direction.UP);
		assertEquals(gameEngine.convertToReverseMove(Direction.RIGHT),Direction.LEFT);
		 
	}
	
	@Test
	public void testOutBoardBorders(){
		Board board= new Board(b1);
		GameEngine gameEngine= new GameEngine(board);
		assertEquals('X', gameEngine.getSymbolAt(board, 0, 0));
		assertEquals('n',gameEngine.getSymbolAt(board, -1, 0));
		assertFalse(board.placeSymbol( -1, 0, 'G'));
		gameEngine.setBoardLevel(board, 2);
		assertEquals('X', gameEngine.getSymbolAt(board, 0, 0));
		assertEquals('n',gameEngine.getSymbolAt(board, -1, 0));
		assertFalse(board.placeSymbol( -1, 0, 'G'));
	}
	

}
