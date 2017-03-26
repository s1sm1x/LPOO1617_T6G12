package dkeep.guiGuided;

import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

import dkeep.logic.Board;
import dkeep.logic.Direction;
import dkeep.logic.Guard;
import dkeep.logic.Ogre;
import dkeep.logic.Point;

public class EditingArea extends DrawingArea implements MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	private char boardSymbol;
	private int maxOgres;
	private int maxDoors;
	private int maxHeros;
	private int maxKeys;
	private int maxGuards;
	protected int numOgres;
	protected int numGuards;
	protected int numDoorsFirstLevel;
	protected int numDoorsSecondLevel;
	protected int numHerosFirstLevel;
	protected int numHerosSecondLevel;
	protected int numKeysFirstLevel;
	protected int numKeysSecondLevel;



	public EditingArea()
	{
		this(10, 10);
	}

	public EditingArea(int w, int h)
	{
		super(w, h);

		boardSymbol = ' ';


		initializeCounters();
		resetCounters();
		updateCounters();
		revalidate();
		repaint();
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	private void initializeCounters()
	{
		maxOgres = 5;
		maxDoors = 1;
		maxKeys = 1;
		maxGuards = 1;
		maxHeros = 1;
	}

	private void resetCounters()
	{
		numOgres = 0;
		numDoorsFirstLevel = 0;
		numDoorsSecondLevel = 0;
		numKeysFirstLevel = 0;
		numKeysSecondLevel = 0;
		numGuards = 0;
		numHerosFirstLevel = 0;
		numHerosSecondLevel = 0;
	}

	private void updateFirstLevelCounters(){
		board.setLevel(1);
		for (int i = 0; i < boardHeight; i++)
		{
			for (int j = 0; j < boardWidth; j++)
			{
				switch (board.symbolAt(j, i))
				{
				case 'H':
					++numHerosFirstLevel;
					break;
				case 'I':
					++numDoorsFirstLevel;
					break;
				case 'k':
					++numKeysFirstLevel;
					break;
				case 'G':
					++numGuards;
					break;}}}
	}
	private void updateSecondLevelCounters(){
		board.setLevel(2);
		for (int i = 0; i < boardHeight; i++)
		{
			for (int j = 0; j < boardWidth; j++)
			{
				switch (board.symbolAt(j, i))
				{
				case 'H':
					++numHerosSecondLevel;
					break;
				case 'O':
					++numOgres;
					break;
				case 'I':
					++numDoorsSecondLevel;
					break;
				case 'k':
					++numKeysSecondLevel;
					break;}}}
	}
	private void updateCounters()
	{
		int initialLevel= board.getLevel();
		resetCounters();
		//First Level Counters
		updateFirstLevelCounters();
		//Second Level Counters
		updateSecondLevelCounters();
		board.setLevel(initialLevel);
	}
	protected void writeBoard(ObjectOutputStream s) throws IOException
	{
		s.writeObject(board);

	}
	@Override
	protected void initializeBoard(int w, int h)
	{
		super.initializeBoard(w, h);
		initializeCounters();
		resetCounters();
	}

	@Override
	protected void erase()
	{
		super.erase();

	}

	protected void setSymbol(char s)
	{
		this.boardSymbol = s;
	}

	private void placeDoor(int x, int y)
	{
		boolean placementAllowed= false;
		switch(board.getLevel())
		{
		case 1:
			placementAllowed= (numDoorsFirstLevel< maxDoors);
			break;
		case 2:
			placementAllowed= (numDoorsSecondLevel< maxDoors);
			break;
		}

		if ( placementAllowed )
		{
			if (board.symbolAt(x, y)== 'X')
			{
				if (board.isCorner(x, y))
				{
					JOptionPane.showMessageDialog(getParent(), "Doors must not be placed in corners!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					writeSymbol(x, y, 'I');
				}
			}
			else
			{
				JOptionPane.showMessageDialog(getParent(), "Doors must be placed on board borders!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(getParent(), "Number of doors must not be greater than 1.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void placeOgre(int x, int y)
	{

		if (numOgres < maxOgres)
		{
			if (board.symbolAt(x, y)== 'X')
			{
				JOptionPane.showMessageDialog(getParent(), "Ogres must not be placed on board borders!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				writeSymbol(x, y, 'O');
				board.addOgrePosition(new Point(x,y));
			}
		}
		else
		{
			JOptionPane.showMessageDialog(getParent(), "Number of ogres must not be greater than " + maxOgres + "!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void placeHero(int x, int y)
	{
		boolean placementAllowed= false;
		switch(board.getLevel())
		{
		case 1:
			placementAllowed= (numHerosFirstLevel < maxHeros);
			break;
		case 2:
			placementAllowed= (numHerosSecondLevel < maxHeros);
			break;
		}

		if ( placementAllowed )
		{
			if (board.symbolAt(x, y)== 'X')
			{
				JOptionPane.showMessageDialog(getParent(), "Hero must not be placed on board borders!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				writeSymbol(x, y, 'H');
				switch(board.getLevel())
				{
				case 1 :
					board.setHeroPositionLevel1(new Point(x,y));
					break;
				case 2:
					board.setHeroPositionLevel2(new Point(x,y));
					break;
				}


			}
		}
		else
		{
			JOptionPane.showMessageDialog(getParent(), "Number of heros must not be greater than 1!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void placeGuard(int x, int y)
	{
		if (numGuards < maxGuards)
		{
			if (board.symbolAt(x, y)== 'X')
			{
				JOptionPane.showMessageDialog(getParent(), "Guards must not be placed on board borders!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				writeSymbol(x, y, 'G');
				board.setGuardPosition( new Point (x,y));
			}
		}
		else
		{
			JOptionPane.showMessageDialog(getParent(), "Number of guards must not be greater than 1!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	private void placeKey(int x, int y)
	{
		boolean placementAllowed= false;
		switch(board.getLevel())
		{
		case 1:
			placementAllowed= (numKeysFirstLevel <  maxKeys);
			break;
		case 2:
			placementAllowed= (numKeysSecondLevel <  maxKeys);
			break;
		}
		if ( placementAllowed )
		{
			if (board.symbolAt(x, y)== 'X')
			{
				JOptionPane.showMessageDialog(getParent(), "Keys must not be placed on board borders!", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				writeSymbol(x, y, 'k');
				switch(board.getLevel())
				{
				case 1:
					board.setKeyPositionLevel1(new Point(x,y));
					break;
				case 2:
					board.setKeyPositionLevel2(new Point(x,y));
					break;				
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(getParent(), "Number of keys must not be greater than 1!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void placeWall(int x, int y)
	{
		writeSymbol(x, y, 'X');
	}

	private void placeBlank(int x, int y)
	{
		if( board.symbolAt(x, y)=='O')
		{
			board.removeOgrePosition(new Point(x,y));

		}
		writeSymbol(x, y, ' ');

	}


	private void writeSymbol(int x, int y, char s)
	{

		board.placeSymbol(x, y, s);
		repaint();
	}

	protected void placeSymbol(int x, int y, char s)
	{
		if (x < 0 || x >= boardWidth || y < 0 || y >= boardHeight)
		{
			return;
		}

		if (board.symbolAt(x, y) == s)
		{
			return;
		}

		updateCounters();

		switch (s)
		{
		case 'H':
			placeHero(x, y);
			break;
		case 'O':
			placeOgre(x, y);
			board.draw();
			break;
		case 'X':
			placeWall(x, y);
			break;
		case ' ':
			placeBlank(x, y);
			break;
		case 'I':
			placeDoor(x, y);
			break;
		case 'G':
			placeGuard(x, y);
			break;
		case 'k':
			placeKey(x, y);
			break;
		}
	}

	protected boolean validateBoard()
	{
		boolean validationSuccessful = true;
		updateCounters();
		String dialogMessage = "";
		if (numHerosFirstLevel < 1 || numHerosSecondLevel < 1)
		{
			dialogMessage += "You must place the hero first in both levels.\n";
			validationSuccessful = false;
		}
		if (numDoorsFirstLevel < 1  || numDoorsSecondLevel < 1)
		{
			dialogMessage += "You must place an exit in both levels.\n";
			validationSuccessful = false;
		}
		if (numOgres < 1)
		{
			dialogMessage += "You must place at least one ogre on second level.\n";
			validationSuccessful = false;
		}
		if (numKeysFirstLevel < 1 || numKeysSecondLevel < 1)
		{
			dialogMessage += "You must place the key in both levels.\n";
			validationSuccessful = false;
		}
		if (numGuards < 1)
		{
			dialogMessage += "You must place the guard on first level.\n";
			validationSuccessful = false;
		}
		if (!checkBoundaries())
		{
			dialogMessage += "Board boundaries must have exactly one exit and everything else walls.\n";
			validationSuccessful = false;
		}
		boolean guardPathValidated= true;
		board.fillEmptySpaces();
		board.setLevel(1);
		if( board.getGuardPosition()!= null)
		{
			Guard tempGuard= new Guard( board.getGuardPosition());
			Direction[] guardRelativeMoves= tempGuard.getguardMoves();
			for (int i = 0; i < guardRelativeMoves.length; i++)
			{
				Point relativeBoardPoint= tempGuard.directionToRelativePoint(guardRelativeMoves[i]);
				Point absoluteBoardPoint = new Point (tempGuard.getX()+relativeBoardPoint.getX(),tempGuard.getY()+relativeBoardPoint.getY());
				tempGuard.setPosition(absoluteBoardPoint);
				if( board.symbolAt(absoluteBoardPoint.getX(), absoluteBoardPoint.getY())!=' ' && board.symbolAt(absoluteBoardPoint.getX(), absoluteBoardPoint.getY())!='G')
				{
					board.placeSymbol(absoluteBoardPoint.getX(), absoluteBoardPoint.getY(), 'x');
					validationSuccessful = false;
					guardPathValidated= false;
				}
			}
		}
		if (validationSuccessful)
		{
			JOptionPane.showMessageDialog(getParent(), "Board validated successfully!", "Validation results", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
			if(!guardPathValidated)
			{
				dialogMessage += "Guard path must be empty and not to go outside board boundaries.\n";
				repaint();
			}
			JOptionPane.showMessageDialog(getParent(), dialogMessage, "Validation results", JOptionPane.ERROR_MESSAGE);

		}
		return validationSuccessful;
	}

	private boolean checkBoundaries()
	{boolean foundExit = false;
	int initialLevel= board.getLevel();
	for ( int i =1 ; i<3; i++)
	{board.setLevel(i);
	for (int x = 0; x < boardWidth; x++)
	{if (board.symbolAt(x, 0) != 'X' && board.symbolAt(x, 0) != 'I')return false;
	if (board.symbolAt(x, boardHeight - 1) != 'X' && board.symbolAt(x, boardHeight - 1) != 'I')	return false;
	if (board.symbolAt(x, 0) == 'I' || board.symbolAt(x, boardHeight - 1) == 'I')foundExit = true;}
	for (int y = 0; y < boardHeight; y++)
	{	if (board.symbolAt(0, y) != 'X' && board.symbolAt(0, y) != 'I')
	{return false;}
	if (board.symbolAt(boardWidth - 1, y) != 'X' && board.symbolAt(boardWidth - 1, y) != 'I')return false;
	if (board.symbolAt(0, y) == 'I' || board.symbolAt(boardWidth - 1, y) == 'I')foundExit = true;}}
	board.setLevel(initialLevel);return foundExit;
	}

	@Override
	public void mouseDragged(MouseEvent me)
	{
		placeSymbol(me.getX() / spriteWidth, me.getY() / spriteHeight, boardSymbol);

	}

	@Override
	public void mousePressed(MouseEvent me)
	{
		placeSymbol(me.getX() / spriteWidth, me.getY() / spriteHeight, boardSymbol);

	}

	public void setBoardLevel(int level) {
		board.setLevel(level);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
	}

	@Override
	public void mouseReleased(MouseEvent me)
	{
	}

	@Override
	public void mouseMoved(MouseEvent me)
	{
	}

	@Override
	public void mouseEntered(MouseEvent me)
	{
	}

	@Override
	public void mouseExited(MouseEvent me)
	{
	}

	public int getBoardLevel() {
		return board.getLevel();
	}

	public void fillEmptySpaces() {
		super.fillEmptySpaces();
	}






}