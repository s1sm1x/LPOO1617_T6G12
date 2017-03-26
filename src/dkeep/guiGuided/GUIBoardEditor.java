package dkeep.guiGuided;

import java.awt.*;
import java.awt.event.*;
//import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import dkeep.logic.Board;
//import lpoo.gui.GUIGlobals;
import dkeep.logic.Ogre;
import dkeep.logic.Point;


import javax.swing.GroupLayout.Alignment;


public class GUIBoardEditor extends JFrame
{
	protected static int editorWidth = 10;
	protected static int editorHeight = 10;
	private static final long serialVersionUID = 1L;

	private File buffer;

	public GUIBoardEditor()
	{
		initComponents();
	}

	public void newBoard(int w, int h)
	{
		editingArea = new EditingArea(w, h);
		pnlEditor.setViewportView(editingArea);
		buttonGroup1.clearSelection();

	}
	private void init1(){
		buttonGroup1 = new ButtonGroup();
		tbDefault = new JToolBar();
		btnWall = new JToggleButton();
		btnWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol('X');
			}
		});
		btnHero = new JToggleButton();
		btnHero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol('H');
			}
		});
		btnOgre = new JToggleButton();
	}
	
	private void init2(){
		btnOgre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol('O');
			}
		});
		btnDoor = new JToggleButton();
		btnDoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol('I');
			}
		});
		btnKey = new JToggleButton();
		btnKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol('k');	}	});
	}
	
	private void init3(){
		btnErase = new JToggleButton();
		btnErase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol(' ');
			}
		});

		pnlEditor = new JScrollPane();
		editingArea = new EditingArea();
		mbDefault = new JMenuBar();
		mnEdit = new JMenu();
		btnClear = new JMenuItem();
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.erase();	} });
	}
	
	private void init4(){
		jSeparator6 = new JPopupMenu.Separator();
		btnValidate = new JMenuItem();
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.validateBoard();} 	});
		mnHelp = new JMenu();
		btnAbout = new JMenuItem();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("Board Editor");
		addWindowListener(new WindowAdapter()
		{@Override
			public void windowClosing(WindowEvent evt)
			{formWindowClosing(evt);}});
		tbDefault.setRollover(true);
		btnWall.setBackground(new Color(255, 150, 100));
	}
	
	private void init5(){
		buttonGroup1.add(btnWall);
		btnWall.setIcon(new ImageIcon(getClass().getResource("/images/wallicon.png")));
		btnWall.setText("Wall");
		btnWall.setFocusable(false);
		btnWall.setHorizontalAlignment(SwingConstants.TRAILING);
		btnWall.setHorizontalTextPosition(SwingConstants.CENTER);
		btnWall.setVerticalTextPosition(SwingConstants.BOTTOM);
		tbDefault.add(btnWall);
		buttonGroup1.add(btnHero);
		btnHero.setIcon(new ImageIcon(getClass().getResource("/images/heroiconicon.png")));
		btnHero.setText("Hero");
		btnHero.setFocusable(false);
		btnHero.setHorizontalAlignment(SwingConstants.TRAILING);
		btnHero.setHorizontalTextPosition(SwingConstants.CENTER);
		btnHero.setVerticalTextPosition(SwingConstants.BOTTOM);
		
	}
	
	private void init6(){
		tbDefault.add(btnHero);
		buttonGroup1.add(btnOgre);
		btnOgre.setIcon(new ImageIcon(getClass().getResource("/images/ogreiconicon.png")));
		btnOgre.setText("Ogre");
		btnOgre.setFocusable(false);
		btnOgre.setHorizontalAlignment(SwingConstants.TRAILING);
		btnOgre.setHorizontalTextPosition(SwingConstants.CENTER);
		btnOgre.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnOgre.setEnabled(false);
		btnGuard = new JToggleButton();
		btnGuard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editingArea.setSymbol('G');}});
		buttonGroup1.add(btnGuard);
		btnGuard.setIcon(new ImageIcon(getClass().getResource("/images/guardiconicon.png")));
		
	}
	
	private void init7(){
		btnGuard.setText("Guard");
		btnGuard.setFocusable(false);
		btnGuard.setHorizontalAlignment(SwingConstants.TRAILING);
		btnGuard.setHorizontalTextPosition(SwingConstants.CENTER);
		btnGuard.setVerticalTextPosition(SwingConstants.BOTTOM);
		tbDefault.add(btnGuard);
		tbDefault.add(btnOgre);
		buttonGroup1.add(btnDoor);
		btnDoor.setIcon(new ImageIcon(getClass().getResource("/images/doorclosedicon.png")));
		btnDoor.setText("Door");
		btnDoor.setFocusable(false);
		btnDoor.setHorizontalAlignment(SwingConstants.TRAILING);
		btnDoor.setHorizontalTextPosition(SwingConstants.CENTER);
		btnDoor.setVerticalTextPosition(SwingConstants.BOTTOM);
		tbDefault.add(btnDoor);
		
	}
	
	private void init8(){
		buttonGroup1.add(btnKey);
		btnKey.setIcon(new ImageIcon(getClass().getResource("/images/keyicon.png")));
		btnKey.setText("Key");
		btnKey.setFocusable(false);
		btnKey.setHorizontalAlignment(SwingConstants.TRAILING);
		btnKey.setHorizontalTextPosition(SwingConstants.CENTER);
		btnKey.setVerticalTextPosition(SwingConstants.BOTTOM);
		tbDefault.add(btnKey);
		buttonGroup1.add(btnErase);
		btnErase.setIcon(new ImageIcon(getClass().getResource("/images/eraser.png")));
		btnErase.setText("Erase");
		btnErase.setFocusable(false);
		btnErase.setHorizontalAlignment(SwingConstants.TRAILING);
		btnErase.setHorizontalTextPosition(SwingConstants.CENTER);
		btnErase.setVerticalTextPosition(SwingConstants.BOTTOM);
		
	}
	
	private void init9(){
		tbDefault.add(btnErase);
		getContentPane().add(tbDefault, BorderLayout.PAGE_START);
		 btnLevel = new JToggleButton();
		btnLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( editingArea.getBoardLevel()==1){
					editingArea.setBoardLevel(2);
					btnLevel.setIcon(new ImageIcon(getClass().getResource("/images/2.png")));
					btnGuard.setEnabled(false);
					btnOgre.setEnabled(true);
					btnLevel.setSelected(false);}
				else{
					editingArea.setBoardLevel(1);
					btnLevel.setIcon(new ImageIcon(getClass().getResource("/images/1.png")));
					btnGuard.setEnabled(true);
					btnOgre.setEnabled(false);
					btnLevel.setSelected(false);}}});
		
	}
	
	private void init10(){
		btnLevel.setIcon(new ImageIcon(getClass().getResource("/images/1.png")));
		btnLevel.setText("Editing Level");
		btnLevel.setFocusable(false);
		btnLevel.setHorizontalAlignment(SwingConstants.TRAILING);
		btnLevel.setHorizontalTextPosition(SwingConstants.CENTER);
		btnLevel.setVerticalTextPosition(SwingConstants.BOTTOM);
		tbDefault.add(btnLevel);
		editingArea.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		editingArea.setMinimumSize(new Dimension(64, 64));
		editingArea.setPreferredSize(new Dimension(640, 480));
		 editingAreaLayout = new GroupLayout(editingArea);
		editingAreaLayout.setHorizontalGroup(
				editingAreaLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 638, Short.MAX_VALUE)
				);
		
	}
	
	private void init11(){
		editingAreaLayout.setVerticalGroup(
			editingAreaLayout.createParallelGroup(Alignment.LEADING)
			.addGap(0, 493, Short.MAX_VALUE));
	editingArea.setLayout(editingAreaLayout);
	pnlEditor.setViewportView(editingArea);
	pnlEditor.getVerticalScrollBar().setUnitIncrement(24);
	pnlEditor.getHorizontalScrollBar().setUnitIncrement(24);
	getContentPane().add(pnlEditor, BorderLayout.CENTER);
	mnEdit.setText("Edit");
	btnClear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
	btnClear.setIcon(new ImageIcon(getClass().getResource("/images/clearicon.png")));
	btnClear.setText("Clear");
	mntmChangeDimensions = new JMenuItem();
	}
	
	private void init12(){
		mntmChangeDimensions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewBoardGUI newBoardDimensions = new NewBoardGUI(GUIBoardEditor.this);
				newBoardDimensions.setVisible(true);
				newBoard(editorWidth, editorHeight);}});
		mntmChangeDimensions.setText("Change Dimensions");
		mnEdit.add(mntmChangeDimensions);
		mnEdit.add(btnClear);
		mnEdit.add(jSeparator6);
		btnValidate.setIcon(new ImageIcon(getClass().getResource("/images/validateicon.png")));
		btnValidate.setText("Validate");
		mnEdit.add(btnValidate);
		mbDefault.add(mnEdit);
		mntmMainMenu = new JMenuItem();
		
	}
	
	private void init13(){
		mntmMainMenu.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			GUIMain guiMain= new GUIMain();
			guiMain.setVisible(true);
			dispose();}});
	mntmMainMenu.setText("Main Menu");
	mnEdit.add(mntmMainMenu);
	mnHelp.setText("Help");
	btnAbout.setIcon(new ImageIcon(getClass().getResource("/images/abouticon.png")));
	btnAbout.setText("About");
	btnAbout.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			GUIAbout guiAbout = new GUIAbout(GUIBoardEditor.this);
			guiAbout.setVisible(true);}});
	mnHelp.add(btnAbout);
	}
	private void init14(){
		mbDefault.add(mnHelp);
		setJMenuBar(mbDefault);
		mnFile = new JMenu();
		btnLoad = new JMenuItem();
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadFile();}});
		btnSave = new JMenuItem();
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(false);}});
		btnSaveAs = new JMenuItem();
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveFile(true);}});
		
	}
	private void init15(){
		jSeparator2 = new JPopupMenu.Separator();
		btnExit = new JMenuItem();
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmExit();}});
		mnFile.setText("File");
		btnLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		btnLoad.setIcon(new ImageIcon(getClass().getResource("/images/loadicon.png")));
		btnLoad.setText("Load");
		mnFile.add(btnLoad);
		btnSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		btnSave.setIcon(new ImageIcon(getClass().getResource("/images/saveicon.png")));
		btnSave.setText("Save");
		mnFile.add(btnSave);
		btnSaveAs.setIcon(new ImageIcon(getClass().getResource("/images/saveicon.png")));
		
	}
	private void init16(){
		btnSaveAs.setText("Save As...");
		mnFile.add(btnSaveAs);
		mnFile.add(jSeparator2);
		btnExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		btnExit.setIcon(new ImageIcon(getClass().getResource("/images/exiticon.gif")));
		btnExit.setText("Exit");
		mnFile.add(btnExit);
		mbDefault.add(mnFile);
		pack();
		setLocationRelativeTo(null);
	}
	
	
	private void initComponents()
	{
		init1();
		init2();
		init3();
		init4();
		init5();
		init6();
		init7();
		init8();
		init9();
		init10();
		init11();
		init12();
		init13();
		init14();
		init15();
		init16();
	}

	private boolean loadFile()
	{
		FileInputStream fin;
		ObjectInputStream oin;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Maze Run maze files (*.board)", "board"));

		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return false;
		}

		buffer = fileChooser.getSelectedFile();

		if (buffer == null)
		{
			return false;
		}

		try
		{
			fin = new FileInputStream(buffer);
			oin = new ObjectInputStream(fin);
			setTitle("Board Builder - " + buffer.getAbsolutePath());
			editingArea.initializeBoard((Board) oin.readObject());
			editingArea.repaint();

			fin.close();
			oin.close();
		}
		catch (IOException | ClassNotFoundException ex)
		{
			GUIMain.close(ex, this);
		}

		return true;
	}



	private boolean saveFile(boolean overwrite)
	{
		if(editingArea.validateBoard())
		{
			editingArea.fillEmptySpaces();
			FileOutputStream fout;
			ObjectOutputStream oout;

			if (buffer == null || overwrite)
			{
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter(" Board  files (*.)", "board"));

				if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
				{
					return false;
				}

				buffer = new File(fileChooser.getSelectedFile() + ".board");
				setTitle(" Builder - " + buffer.getAbsolutePath());
			}

			try
			{
				fout = new FileOutputStream(buffer);
				oout = new ObjectOutputStream(fout);
				editingArea.writeBoard(oout);

				fout.close();
				oout.close();

			}
			catch (IOException ex)
			{
				GUIMain.close(ex, this);
			}

			return true;
		}

		return false;
	}


	private void confirmExit()
	{
		final String confirmMessage = "Do you want to save your changes before exiting?";

		if (JOptionPane.showConfirmDialog(this, confirmMessage, "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			if (!saveFile(false))
			{
				return;
			}
		}

		System.exit(0);
	}

	private void formWindowClosing(WindowEvent evt)
	{
		confirmExit();
	}


	private EditingArea editingArea;
	private JMenuItem btnAbout;
	private JMenuItem btnClear;
	private JToggleButton btnDoor;
	private JToggleButton btnOgre;
	private JToggleButton btnErase;
	private JMenuItem btnExit;
	private JMenuItem btnLoad;
	private JToggleButton btnHero;
	private JMenuItem btnSave;
	private JMenuItem btnSaveAs;

	private JToggleButton btnKey;
	private JToggleButton btnGuard;
	private JMenuItem btnValidate;
	private JToggleButton btnWall;
	private ButtonGroup buttonGroup1;
	private JPopupMenu.Separator jSeparator2;
	private JPopupMenu.Separator jSeparator6;
	private JMenuBar mbDefault;
	private JMenu mnEdit;
	private JMenu mnFile;
	private JMenu mnHelp;
	private JScrollPane pnlEditor;
	private JToolBar tbDefault;
	private JMenuItem mntmChangeDimensions;
	private JMenuItem mntmMainMenu;
	private JToggleButton btnLevel;
	private GroupLayout editingAreaLayout;




}