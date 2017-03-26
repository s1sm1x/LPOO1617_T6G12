package test;


import org.junit.Test;

import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.GameEngine;
import dkeep.logic.Point;

import static org.junit.Assert.*;


public class TestDungeonGameLogic {
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

	@Test
	public void heroMovesNearOgreAndGameEnds(){

		Board board= new Board(b2);
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		assertEquals(2,gameEngine.getBoardLevel(board));
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
		Board board= new Board(b2,2);
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
	@Test(timeout=1000)
	public void testOgreAndMassiveClubRandomMovement(){
		Board board= new Board(b2,2);//indicar que é o do segundo nível
		GameEngine gameEngine= new GameEngine(board);
		gameEngine.setBoardLevel(board, 2);
		gameEngine.setOgrePosition(board, new Point(1,3), gameEngine.getHeroIcon(), gameEngine.getKeyPosition());
		gameEngine.moveOgres(board,gameEngine.getKey());
		boolean ogreGoesUp =false;
		boolean ogreGoesDown= false;
		boolean ogreGoesLeft=false;
		boolean ogreGoesRight=false;
		while(!ogreGoesUp || !ogreGoesDown || !ogreGoesLeft || !ogreGoesRight )
		{
			gameEngine.setOgrePosition(board, new Point(2,2), gameEngine.getHeroIcon(), gameEngine.getKeyPosition());
			gameEngine.moveOgres(board,gameEngine.getKey());
			if (gameEngine.getOgrePosition().equals(new Point(1,2))){
				ogreGoesLeft=true;
			}
			else if (gameEngine.getOgrePosition().equals(new Point(2,1))){
				ogreGoesUp=true;
			}
			else if (gameEngine.getOgrePosition().equals(new Point(3,2))){
				ogreGoesRight=true;
			}
			else if (gameEngine.getOgrePosition().equals(new Point(2,3))){
				ogreGoesDown=true;
			}
			else
				fail("unexpected movement");
		}
	
		boolean massiveGoesRight=false; 
		boolean massiveGoesLeft=false;
		boolean massiveGoesUp= false;
		boolean massiveGoesDown= false;
		while(!massiveGoesRight || !massiveGoesLeft || !massiveGoesUp || !massiveGoesDown )
		{
			gameEngine.setOgrePosition(board, new Point(2,2), gameEngine.getHeroIcon(), gameEngine.getKeyPosition());
		
			
			if (gameEngine.getMassiveClubPosition().equals(new Point(1,2))){
				massiveGoesLeft=true;
			}
			else if (gameEngine.getMassiveClubPosition().equals(new Point(2,1))){
				massiveGoesUp=true;
			}
			else if (gameEngine.getMassiveClubPosition().equals(new Point(3,2))){
				massiveGoesRight=true;
			}
			else if (gameEngine.getMassiveClubPosition().equals(new Point(2,3))){
				massiveGoesDown=true;
			}
			else
				fail("unexpected movement");
		}
		
		
	}
	@Test 
	public void testOgreChangesIconWhenHeroGetsNearby(){
		
			Board board= new Board(b2,2);
			GameEngine gameEngine= new GameEngine(board);
			gameEngine.setBoardLevel(board, 2);
			assertEquals('O', gameEngine.getOgreIcon());
			gameEngine.moveHero(board, Direction.DOWN);
			gameEngine.attackNearbyOgres(board,gameEngine.getHeroIcon());
			assertEquals('8', gameEngine.getOgreIcon());
	}
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
