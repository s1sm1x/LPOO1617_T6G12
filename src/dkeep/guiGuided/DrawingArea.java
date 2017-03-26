package dkeep.guiGuided;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;
import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.Guard;

public class DrawingArea extends JPanel
{
	private static final long serialVersionUID = 1652644943686379111L;

	private BufferedImage heroIcon;
	private BufferedImage heroArmed;
	private BufferedImage guardIcon;
	private BufferedImage guardSleeping;
	private BufferedImage ogreIcon;
	private BufferedImage massiveClub;
	private BufferedImage multipleOgres;
	private BufferedImage StunnedOgre;
	private BufferedImage key;
	private BufferedImage doorOpened;
	private BufferedImage doorClosed;
	private BufferedImage grid;
	private BufferedImage wall;
	private BufferedImage floor;
	private BufferedImage heroKey;
	private BufferedImage heroDead;
	private BufferedImage ogreKeyIcon;
	private BufferedImage cross;
	
	private BufferedImage resizedHeroIcon;
	private BufferedImage resizedHeroArmed;
	private BufferedImage resizedGuardIcon;
	private BufferedImage resizedGuardSleeping;
	private BufferedImage resizedOgreIcon;
	private BufferedImage resizedMassiveClub;
	private BufferedImage resizedStunnedOgre;
	private BufferedImage resizedKey;
	private BufferedImage resizedDoorOpened;
	private BufferedImage resizedDoorClosed;
	private BufferedImage resizedWall;
	private BufferedImage resizedFloor;
	private BufferedImage resizedheroKey;
	private BufferedImage resizedheroDead;
	private BufferedImage resizedCross;
	private BufferedImage resizedogreKeyIcon;
	
	protected Guard editorGuard;
	protected Board board;
	protected int boardWidth;
	protected int boardHeight;
	protected int boardCells;
	protected int spriteWidth;
	protected int spriteHeight;

	private BufferedImage graphics2d;
	private Graphics graphicsBuffer;
	private Dimension windowSize;

	public DrawingArea()
	{
		this(10, 10);
	}

	protected DrawingArea(int w, int h)
	{
		initializeSprites();
		scaleSprites(32, 32);
		initializeBoard(w, h);
	}

	protected Dimension getWindowSize()
	{
		return windowSize;
	}

	private BufferedImage resizeImage(BufferedImage img, int newWidth, int newHeight)
	{
		final BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_4BYTE_ABGR);
		final Graphics2D g = resized.createGraphics();

		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, img.getWidth(), img.getHeight(), null);
		g.dispose();

		return resized;
	}

	protected void initializeBoard(int newWidth, int newHeight)
	{
		boardWidth = newWidth;
		boardHeight = newHeight;
		boardCells = boardWidth * boardHeight;
		board = new Board(boardWidth, boardHeight);

		updateWindow();
	}

	protected final void initializeBoard(Board board)
	{
		this.board = board;
		boardWidth = board.getWidth();
		boardHeight = board.getHeight();
		boardCells = boardWidth * boardHeight;

		updateWindow();
	}

	private void updateWindow()
	{
		windowSize = new Dimension(boardWidth * spriteWidth, boardHeight * spriteHeight);

		if (boardWidth != 0 && boardHeight != 0)
		{
			graphics2d = new BufferedImage(windowSize.width, windowSize.height, BufferedImage.TYPE_INT_ARGB_PRE);
			graphicsBuffer = graphics2d.getGraphics();
		}

		setPreferredSize(windowSize);
		revalidate();
		repaint();
	}

	protected void erase()
	{
		final char[][] newMatrix = new char[boardHeight][boardWidth];

		for (final char[] row : newMatrix)
		{
			Arrays.fill(row, ' ');
		}

		board.setMatrix(newMatrix,1);
		board.setMatrix(newMatrix,2);
		repaint();
	}

	private void initializeSprites(){
		try{
			heroIcon = ImageIO.read(this.getClass().getResource("/images/heroicon.png"));
			heroArmed = ImageIO.read(this.getClass().getResource("/images/heroiconArmed.png"));
			guardIcon = ImageIO.read(this.getClass().getResource("/images/guardicon.png"));
			guardSleeping = ImageIO.read(this.getClass().getResource("/images/guardiconSleeping.png"));
			ogreIcon= ImageIO.read(this.getClass().getResource("/images/ogreicon.png"));
			massiveClub = ImageIO.read(this.getClass().getResource("/images/massiveclub.png"));
			StunnedOgre = ImageIO.read(this.getClass().getResource("/images/stunnedogre.png"));
			key = ImageIO.read(this.getClass().getResource("/images/key.png"));
			doorOpened = ImageIO.read(this.getClass().getResource("/images/dooropened.png"));
			doorClosed = ImageIO.read(this.getClass().getResource("/images/doorclosed.png"));
			wall = ImageIO.read(this.getClass().getResource("/images/wall.jpg"));
			floor = ImageIO.read(this.getClass().getResource("/images/floor.png"));
			heroKey = ImageIO.read(this.getClass().getResource("/images/heroiconKey.png"));
			ogreKeyIcon = ImageIO.read(this.getClass().getResource("/images/ogreKeyIcon.png"));
			heroDead = ImageIO.read(this.getClass().getResource("/images/heroiconBleeding.png"));
			cross = ImageIO.read(this.getClass().getResource("/images/cross.png"));
		}catch (final IOException ex){
			GUIMain.close(ex, null);
			}
	}

	protected final void scaleSprites(int w, int h){
		spriteWidth = w;
		spriteHeight = h;
		resizedHeroIcon = resizeImage(heroIcon, w, h);
		resizedHeroArmed = resizeImage(heroArmed, w, h);
		resizedGuardIcon = resizeImage(guardIcon, w, h);
		resizedGuardSleeping = resizeImage(guardSleeping, w, h);
		resizedOgreIcon = resizeImage(ogreIcon, w, h);
		resizedMassiveClub = resizeImage(massiveClub, w, h);
		resizedStunnedOgre = resizeImage(StunnedOgre, w, h);
		resizedKey = resizeImage(key, w, h);
		resizedDoorOpened = resizeImage(doorOpened, w, h);
		resizedDoorClosed = resizeImage(doorClosed, w, h);
		resizedWall = resizeImage(wall, w, h);
		resizedFloor = resizeImage(floor, w, h);
		resizedheroKey = resizeImage(heroKey, w, h);
		resizedogreKeyIcon = resizeImage(ogreKeyIcon, w, h);
		resizedheroDead = resizeImage(heroDead, w, h);
		resizedCross= resizeImage(cross, w, h);
		updateWindow();
	}
	private void Cases(int j, int i, int x, int y){
		switch (board.symbolAt(j, i)){
		case 'H': graphicsBuffer.drawImage(resizedHeroIcon, x, y, null); break;			
		case 'K': graphicsBuffer.drawImage(resizedheroKey, x, y, null); break;	
		case 'A': graphicsBuffer.drawImage(resizedHeroArmed, x, y, null); break;
		case 'G':graphicsBuffer.drawImage(resizedGuardIcon, x, y, null); break;
		case 'g': graphicsBuffer.drawImage(resizedGuardSleeping, x, y, null); break;	
		case 'O': graphicsBuffer.drawImage(resizedOgreIcon, x, y, null); break;	
		case '8': graphicsBuffer.drawImage(resizedStunnedOgre, x, y, null); break;
		case '*':graphicsBuffer.drawImage(resizedMassiveClub, x, y, null);break;	
		case 'k': graphicsBuffer.drawImage(resizedKey, x, y, null);break;
		case 'I': graphicsBuffer.drawImage(resizedDoorClosed, x, y, null); break;
		case 'S': graphicsBuffer.drawImage(resizedDoorOpened, x, y, null); break;
		case 'X': graphicsBuffer.drawImage(resizedWall, x, y, null); break;
		case '$': graphicsBuffer.drawImage(resizedogreKeyIcon, x, y, null); break;
		case 'd': graphicsBuffer.drawImage(resizedheroDead, x, y, null); break;
		case 'x': graphicsBuffer.drawImage(resizedCross, x, y, null);break;
		}
	}
	@Override
	public final void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getSize().width, getSize().height);
		int y = 0;
		for (int i = 0; i < boardHeight; i++){	
			int x = 0;
			for (int j = 0; j < boardWidth; j++){
				graphicsBuffer.drawImage(resizedFloor, x, y, null);
				Cases(j,i,x,y);
				x += spriteWidth;
			} 
			y += spriteHeight;
		}
		Toolkit.getDefaultToolkit().sync();
		g.drawImage(graphics2d, 0, 0, null);
	}

	public void fillEmptySpaces() {
		board.fillEmptySpaces();	
	}	
}