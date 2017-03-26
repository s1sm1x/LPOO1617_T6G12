/*
 * 
 */
package dkeep.logic;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import dkeep.cli.*;
import dkeep.guiGuided.InterfaceGUI;


/**
 * The Class GameEngine.
 */
public class GameEngine  implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5338709714077742867L;

	/** The game over. */
	private static  boolean gameOver=false;

	/** The player won. */
	private static  boolean playerWon=false;

	/** The keep first game. */
	private static boolean keepFirstGame=true;

	/** The keep second game. */
	private static  boolean keepSecondGame=true;

	/** The cli. */
	private  DungeonKeepCLI cli;

	/** The board. */
	private  Board board;

	/** The hero. */
	private  Hero hero;

	/** The key. */
	private  Key key;

	/** The guard. */
	private  Guard guard;

	/** The ogres. */
	private  ArrayList<Ogre> ogres= new ArrayList<Ogre>();

	/** The test ogre. */
	private  Ogre testOgre;

	/** The guard position. */
	private  Point guardPosition = new Point(8,1);

	/** The level 1 hero position. */
	private  Point level1HeroPosition=new Point(1,1);

	/** The level 2 hero position. */
	private  Point level2HeroPosition= new Point(1,6);

	/** The level 2 key position. */
	private  Point level2KeyPosition= new Point( 8,1 );

	/** The level 1 key position. */
	private  Point level1KeyPosition=new Point(7,8);

	/**
	 * default constructor for gameEngine.
	 */
	public GameEngine(){

		board= new Board();

		initializeEntitiesAndKey(level1HeroPosition, guardPosition,level1KeyPosition , false);
	}
	/**
	 * Constructor specifying the desired board. Just for test purposes
	 * @param board desired board
	 */
	public GameEngine(Board board){  

		cli = new DungeonKeepCLI();
		this.board = board;
		initializeEntitiesAndKey(new Point(1,1), new Point(3,1), new Point(1,3), false);

	}


	/**
	 * Game engine initializer, placing entities and key .
	 *
	 * @param heroPosition  X and Y coordinate
	 * @param guardOrOgrePosition  X and Y coordinate
	 * @param keyPosition  X and Y coordinate
	 * @param testingOgre boolean used for test purposes, just to place ogre on second level
	 */
	private void initializeEntitiesAndKey(Point heroPosition, Point guardOrOgrePosition, Point keyPosition, boolean testingOgre){
		hero = new Hero(heroPosition);//new Point(1,1)
		if(testingOgre){
			testOgre = new Ogre(guardOrOgrePosition);
			ogres.add(testOgre);
			board.placeSymbol(testOgre.getX(),testOgre.getY(),testOgre.getOgreIcon() );
		}
		else{
			guard = new Guard(guardOrOgrePosition);//new Point(3,1)
			board.placeSymbol(guard.getX(),guard.getY(),guard.getGuardIcon() );
		}
		key = new Key(keyPosition);// new Point(1,3)
		key.placeKey(board);
		board.placeSymbol(hero.getX(),hero.getY(),hero.getHeroIcon() );

	}

	/**
	 * Getter of ogres' arraylist.
	 *
	 * @return ogres' array
	 */
	public ArrayList<Ogre> getOgresList(){
		return ogres;
	}

	/**
	 * moving the ogres saved on the ogres' Array.
	 *
	 * @param board board to move
	 * @param key Key
	 */
	public void moveOgres(Board board, Key key){
		ogres.forEach((ogre) -> ((Ogre) ogre).move(board, new String(), key.getKeyPosition(), hero.getHeroIcon()));
	}

	/**
	 * getter for game ending (hero caught or reached the exit).
	 *
	 * @return true if game as ended
	 */
	public static  boolean getGameOver(){
		return gameOver;
	} 

	/**
	 * getter for winning player.
	 *
	 * @return true if player reached the exit
	 */
	public static  boolean getPlayerWon(){
		return playerWon;
	}

	/**
	 * getter for continuing game on second level.
	 *
	 * @return true if game continues
	 */
	public boolean getKeepSecondGame(){
		return keepSecondGame;
	}

	/**
	 * setter for continuing game on second level.
	 *
	 * @param keepSecondGame desired state
	 */
	public static void setKeepSecondGame(boolean keepSecondGame){
		GameEngine.keepSecondGame=keepSecondGame;
	}

	/**
	 * setter for continuing game on first level.
	 *
	 * @param keepFirstGame desired state
	 */
	public void setKeepFirstGame(boolean keepFirstGame){
		this.keepFirstGame=keepFirstGame;
	}

	/**
	 * setter for game state.
	 *
	 * @param gameOver desired state
	 */
	public static void setGameOver(boolean gameOver){
		GameEngine.gameOver=gameOver;
	}

	/**
	 * setter for player won state.
	 *
	 * @param playerWon desired state
	 */
	public static void setPlayerWon(boolean playerWon){
		GameEngine.playerWon= playerWon;
	}

	/**
	 * stuns the ogres that are ortogonal to the hero.
	 *
	 * @param board playing board
	 * @param heroIcon hero icon at the moment
	 */
	public void attackNearbyOgres(Board board, char heroIcon){
		for (Ogre ogreIt : ogres) {
			if(ogreIt.isOgreNearby(board, heroIcon)){				
				board.placeSymbol(ogreIt.getX(), ogreIt.getY(), '8'); 
				ogreIt.toggleStunOgre();
			}
		}
	}

	/**
	 * adds the desired number of ogres to the ogres array.
	 *
	 * @param ogresNumber the ogres number
	 */
	public void createOgresArray(int ogresNumber){
		for (int p=1 ; p<ogresNumber+1; p++){
			ogres.add( new Ogre(new Point(p,1)) );
		}
	}

	/**
	 * getter for the heroPosition.
	 *
	 * @return Point with hero position
	 */

	public Point getHeroPosition() {
		return hero.getPosition();
	}

	/**
	 * moves hero and checks if there is any guard, ogre or massive club near.
	 *
	 * @param board current borad
	 * @param direction direction to move
	 */
	public void moveHero(Board board, Direction direction) {
		hero.move(board, direction);
		if(!hero.checkSecurityDistance(board)){
			gameOver=true;
			playerWon=false;
			keepSecondGame= false;
			keepFirstGame=false;
		}
	}

	/**
	 * getter for key position.
	 *
	 * @return Point with key position
	 */
	public Point getKeyPosition() {
		return key.getKeyPosition();
	}

	/**
	 * getter for acessing the board symbolAt.
	 *
	 * @param board the board
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return char symbol
	 */
	public  char getSymbolAt(Board board, int x, int y) {
		return board.symbolAt(x, y);
	}

	/**
	 * getter for board Level.
	 *
	 * @param board the board
	 * @return level
	 */
	public int getBoardLevel(Board board) {
		return board.getLevel();
	}

	/**
	 * setter for board level.
	 *
	 * @param board the board
	 * @param level the level
	 */
	public void setBoardLevel(Board board, int level) {
		board.setLevel(2);
		initializeEntitiesAndKey(new Point(1,1), new Point(1,3), new Point(3,1), true);

	}

	/**
	 * getter for ogre position ( just for ogre purposes) .
	 *
	 * @return Point with ogre position
	 */
	public Point getOgrePosition() {
		return testOgre.getPosition();
	}

	/**
	 * getter for at moment hero icon.
	 *
	 * @return char with heroIcon
	 */
	public char getHeroIcon() {
		return hero.getHeroIcon();
	}

	/**
	 * board drawer on console.
	 */
	public void draw() {
		board.draw();
	}

	/**
	 * getter for key.
	 *
	 * @return key
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * getter for ogres' massive club ( just for test purposes).
	 *
	 * @return the massive club position
	 */
	public Point getMassiveClubPosition() {
		return ogres.get(0).getMassiveClub().getMassiveClubPosition();
	}

	/**
	 * getter for at moment ogre icon ( just for test purposes).
	 *
	 * @return char with ogre icon
	 */
	public char getOgreIcon() {
		return ogres.get(0).getOgreIcon();
	}

	/**
	 * move guard accordingly to his behaviour .
	 *
	 * @param board the board
	 * @param unused the unused
	 * @throws Throwable the throwable
	 */
	public void moveGuard(Board board, String unused) throws Throwable {
		guard.move(board, unused);
	}

	/**
	 * setter for guard moves.
	 *
	 * @param guardMoves the new guard moves
	 */
	public void setguardMoves(Direction[] guardMoves) {
		guard.setguardMoves(guardMoves);
	}

	/**
	 * setter for guard type.
	 *
	 * @param type the new guard type
	 */
	public void setGuardType(int type) {
		guard.setGuardType(type);
	}

	/**
	 * getter for guardtype.
	 *
	 * @return String with guard type
	 */
	public  String getGuardType() {
		return guard.getGuardType();
	}

	/**
	 * getter for guard position.
	 *
	 * @return Point with guard position
	 */
	public Point getGuardPosition() {
		return  guard.getPosition();
	}

	/**
	 * setter for patrolling direction.
	 *
	 * @param direction ( forward or backwards)
	 */
	public void setPatrolDirection(char direction) {
		guard.setPatrolDirection(direction);
	}

	/**
	 * setter for guard state.
	 *
	 * @param state the new guard state
	 */
	public void setGuardState(String state) {
		guard.setstate(state);
	}

	/**
	 * guard move direction reverser.
	 *
	 * @param direction the direction
	 * @return Direction reversed
	 */
	public Direction convertToReverseMove(Direction direction) {
		return guard.convertToReverseLetter(direction);	
	}

	/**
	 * getter for guard sleeping state.
	 *
	 * @return true if sleeping, ,false otherwise
	 */
	public boolean isGuardSleeping() {
		return guard.isSleeping();
	}

	/**
	 * setter for ogre position just for test purposes).
	 *
	 * @param board the board
	 * @param point desired position
	 */
	public void setOgrePosition(Board board ,Point point, char heroIcon, Point keyPosition) {
		ogres.get(0).setOgrePosition(board, point, heroIcon, keyPosition);
	}

	/**
	 * getter for board.
	 *
	 * @return board at moment
	 */
	public  Board getBoard() {
		return board;
	}

	/**
	 * getter for hero.
	 *
	 * @return hero
	 */
	public Hero getHero() {

		return hero;
	}

	/**
	 * getter for guard.
	 *
	 * @return guard
	 */
	public Guard getGuard() {

		return guard;
	}

	/**
	 * setter for key.
	 *
	 * @param key the new key
	 */
	public void setKey(Key key) {

		this.key= key;
	}

	/**
	 * key placer on board.
	 */
	public void placeKey() {

		key.placeKey(board);
	}

	/**
	 * symbol placer for board.
	 *
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param ogreIcon char with at moment ogreIcon
	 */
	public void placeSymbol(int x, int y, char ogreIcon) {

		board.placeSymbol(x, y, ogreIcon);
	}

	/**
	 * Getter for first game ongoing.
	 *
	 * @return true if user is on first level, false, otherwise
	 */
	public boolean getKeepFirstGame() {

		return keepFirstGame;
	}

	/**
	 * reinitializer for restarting the game when called.
	 */
	public void reinitialize() {
		gameOver=false;
		playerWon=false;
		keepFirstGame=true;
		keepSecondGame=true;

	}

	/**
	 * Handle board level 2.
	 */
	private void handleBoardLevel2(){
		hero.setHeroPosition(level2HeroPosition);
		hero.setHeroIcon('A');
		key= new Key(level2KeyPosition);
		key.placeKey(board);
		board.placeSymbol(hero.getX(),hero.getY(),hero.getHeroIcon() );
		for (Ogre ogreIt : ogres) {
			board.placeSymbol(ogreIt.getX(),ogreIt.getY(), ogreIt.getOgreIcon());
		}
		
	}

	/**
	 * Sets the game states.
	 */
	private void setGameStates(){
		gameOver=true;
		playerWon=false;
		keepSecondGame=false;
		keepFirstGame=false;
	}

	/**
	 * Game Logic caller to handle user entered movement  .
	 *
	 * @param move desired direction
	 * @return String to display some feedback about game state to the user
	 */
	public String handleSelectedHeroMovement(Direction move){
		String messageTodisplay="";
		if(keepFirstGame && board.getLevel()==1) 
		{	hero.move(board, move);
		messageTodisplay="you must escape from the Dungeon Keep";
		guard.move(board, "");
		if(!hero.checkSecurityDistance(board)){
			setGameStates();}
		if(board.getLevel()==2) 
		{handleBoardLevel2();
		messageTodisplay="you must escape from the Dungeon Keep ";}}
		else if(keepSecondGame){
			{guard.destroyGuard();
			hero.move(board, move);
			moveOgres(board,key);
			attackNearbyOgres(board, hero.getHeroIcon());
			if(!hero.checkSecurityDistance(board)){
				gameOver=true;
				playerWon=false;
				keepSecondGame=false;
			}}}
		return messageTodisplay;}

	/**
	 * writes game state to an output stream.
	 *
	 * @param oout the oout
	 * @throws IOException if stream invalid, insufficient permissions or file not found
	 */
	public static void write(ObjectOutputStream oout) throws IOException
	{

	}

	/**
	 * reads the game state from an input stream.
	 *
	 * @param aInputStream objectInputStream to read from
	 * @throws ClassNotFoundException if the application tries to load a class that doesn't exist
	 * @throws IOException if stream invalid, insufficient permissions or file not found
	 */
	public  void read(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException
	{
		board= (Board) aInputStream.readObject();
		hero = (Hero) aInputStream.readObject();
		key = (Key) aInputStream.readObject();
		guard= (Guard) aInputStream.readObject();
		testOgre = (Ogre) aInputStream.readObject();
		keepFirstGame =  aInputStream.readBoolean();
		keepSecondGame= aInputStream.readBoolean();
		gameOver = aInputStream.readBoolean();
		playerWon = aInputStream.readBoolean();
		cli = (DungeonKeepCLI) aInputStream.readObject();

		for (final Ogre ogre : ogres)
		{
			aInputStream.readObject();
		}

	}

	/**
	 * Reinitialize custom.
	 *
	 * @param tempBoard the temp board
	 */
	public void reinitializeCustom(Board tempBoard) {
		reinitialize();
		this.board= tempBoard;
		level1HeroPosition=tempBoard.getHeroPositionLevel1();
		level2HeroPosition=tempBoard.getHeroPositionLevel2();
		level1KeyPosition=tempBoard.getKeyPositionLevel1();
		level2KeyPosition= tempBoard.getHeroPositionLevel2();
		guardPosition= tempBoard.getGuardPosition();
		ArrayList<Point> tempOgresPositons= tempBoard.getOgresPositions();
		for (int i = 0 ; i <tempOgresPositons.size(); i++)
		{
			ogres.add(new Ogre(tempOgresPositons.get(i)));
		}
		InterfaceGUI.setOgresNumber(tempOgresPositons.size());
		board.setLevel(1);
		initializeEntitiesAndKey(level1HeroPosition, guardPosition,level1KeyPosition , false);
		this.board.draw();
		board.setLevel(2);
		this.board.draw();
		board.setLevel(1);
	}
}









