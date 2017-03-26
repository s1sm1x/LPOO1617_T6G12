package dkeep.cli;

import java.util.Scanner;

import dkeep.logic.Direction;
import dkeep.logic.GameEngine;

public class DungeonKeepCLI {
	final static Scanner reader = new Scanner(System.in);
	private static GameEngine gameEngine= new GameEngine();
	private static String objective="";
	public static void main(String[] args) {
		selectGuard();
		selectNumberOgres();
		gameEngine.draw();
		while(!GameEngine.getGameOver()){
			objective= gameEngine.handleSelectedHeroMovement(readString()) ;
			System.out.flush();
			gameEngine.draw();
			System.out.println(objective);
		}
		displayEnding(); 
	}

	public static int readInteger(int i, int j)
	{
		final Scanner input = new Scanner(System.in);
		int number=0;

		do
		{  
			System.out.print("Please enter a number between "+ i +" and " + j );


			number = input.nextInt();


		} while (number < i || number > j);
		
		return number;
	}

	public static Direction readString()
	{
		boolean validEntry=false;
		while(!validEntry){
			System.out.println("Enter direction: ");
			String nextLine=reader.nextLine(); 
			switch (nextLine){
			case "W": case "w":validEntry=true;	return Direction.UP;

			case "A": case "a":validEntry=true;return Direction.LEFT;

			case "D": case "d":validEntry=true;return Direction.RIGHT;

			case "S": case "s":validEntry=true;return Direction.DOWN;
			}
		}
		return Direction.NONE;
	}
	public static void selectGuard()
	{

		System.out.println("");System.out.println("+ - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - +");
		System.out.println("/                  D U N G E O N   K E E P                         \\");
		System.out.println("\\                                                                   /");
		System.out.println("|     Select Guard difficulty:                                     |");
		System.out.println("/                                                                   \\");
		System.out.println("-     1. Rookie ( Fixed Path, never sleeps )                           -");
		System.out.println("\\     2. Drunken (random, sleeps randomly )                           /");
		System.out.println("|     3. Suspicious (random, never sleeps)                           |");
		System.out.println("-                                                                    -");
		System.out.println("+ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - +");	System.out.println("");


		gameEngine.setGuardType(readInteger(1, 3));

	}
	public static void selectNumberOgres()
	{
		System.out.println("");
		System.out.println("+ - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - \\ | / - +");
		System.out.println("|                                                                   |");
		System.out.println("/                  D U N G E O N   K E E P                         \\");
		System.out.println("-                                                                    -");
		System.out.println("\\                                                                   /");
		System.out.println("|     Select Ogre Number :                                            |");
		System.out.println("/                                                                   \\");
		System.out.println("-                                                                  -");
		System.out.println("+ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - / | \\ - +");
		System.out.println("");

		gameEngine.createOgresArray(readInteger(0, 5));

	}

	public static void displayEnding()
	{

		if (GameEngine.getGameOver()){

			if (GameEngine.getPlayerWon()){

				System.out.println("");
				System.out.println("********************************");
				System.out.println("* " + "Congratulations, you have reached the exit of the Dungeon  :)" + " *");
				System.out.println("********************************");

			}else{

				System.out.println("");
				System.out.println("********************************");
				System.out.println("* " + "You have been caught :(" + " *");
				System.out.println("********************************");

			}}}


}
