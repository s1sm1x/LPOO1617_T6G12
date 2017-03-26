package test;

import static org.junit.Assert.*;

import org.junit.Test;

import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.GameEngine;
import dkeep.logic.Point;

public class TestOgreMovement {
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
		// test to massive Club
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
			//board.draw();
			assertEquals('O', gameEngine.getOgreIcon());
		
			gameEngine.moveHero(board, Direction.DOWN);
			gameEngine.attackNearbyOgres(board,gameEngine.getHeroIcon());
			assertEquals('8', gameEngine.getOgreIcon());
	}
	
	

}
