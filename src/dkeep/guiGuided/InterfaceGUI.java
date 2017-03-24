package dkeep.guiGuided;


import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.KeyStroke;
import dkeep.logic.Direction;
import dkeep.logic.GameEngine;
import dkeep.logic.Point;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class InterfaceGUI extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameEngine gameEngine = new GameEngine();
	private final int KEY_LEFT=37; 
	private final int KEY_RIGHT=39; 
	private final int KEY_UP=38; 
	private final int KEY_DOWN=40; 
	private static int ogresDefaultNumber=0;
	private static int guardDefaultType=1;

	private JLabel lblStatus = new JLabel(""); // static!!
	private JButton btnUp = new JButton("Up");
	private JButton btnLeft = new JButton("Left");
	private JButton btnRight = new JButton("Right"); 
	private JButton btnDown= new JButton("Down");;
	private OptionsGUI option= new OptionsGUI();
	private DrawingArea panelInterface = new DrawingArea();
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem btnRestart = new JMenuItem ("Restart");
	private JMenuItem btnLoadLevel = new JMenuItem ("Load Board...");
	private JMenuItem btnLoadState = new JMenuItem ("Load State");
	private JMenuItem btnSaveState = new JMenuItem ("Save State");
	private JMenuItem btnExit = new JMenuItem("Exit");
	private JMenuItem btnOptions = new JMenuItem("Options");
	private JMenuItem btnNewGame = new JMenuItem("New Game");
	private JMenu mnGame = new JMenu();
	private final JPanel panel = new JPanel();
	private final JMenu mnHelp = new JMenu();
	private final JMenuItem btnAbout = new JMenuItem("About");


	/**
	 * Create the application.
	 */
	public InterfaceGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		pack();
		setResizable(true);
		setTitle("Dungeon Keep");
		setBounds(100, 100, 336, 438);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {

				int keyPressed= evt.getKeyCode();

				switch(keyPressed){
				case KEY_LEFT:
					try {
						gameEngine.handleSelectedHeroMovement(Direction.LEFT);
						displayMessageStatus("");
						//panelInterface.setBoard(gameEngine.getBoard());
						panelInterface.repaint();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					break;
				case KEY_RIGHT:
					try {
						gameEngine.handleSelectedHeroMovement(Direction.RIGHT);
						displayMessageStatus("");

						panelInterface.repaint();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					break;
				case KEY_UP:
					try {
						gameEngine.handleSelectedHeroMovement(Direction.UP);
						displayMessageStatus("");
						panelInterface.repaint();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					break;
				case KEY_DOWN:
					try {
						gameEngine.handleSelectedHeroMovement(Direction.DOWN);
						displayMessageStatus("");
						panelInterface.repaint();
					} catch (Throwable e) {
						e.printStackTrace();
					}
					break;

				}
			}

		});

		panelInterface.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelInterface, BorderLayout.CENTER);

		btnOptions.setBounds(245, 399, 97, 25);

		getContentPane().add(menuBar, BorderLayout.NORTH);

		mnGame.setText("Game");
		mnGame.setBackground(SystemColor.menu);

		btnExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		btnExit.setIcon(new ImageIcon(getClass().getResource("/res/door_open.png")));

		btnRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
		btnRestart.setIcon(new ImageIcon(getClass().getResource("/res/arrow_rotate.png")));
	
		btnLoadLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_MASK));
		btnLoadLevel.setIcon(new ImageIcon(getClass().getResource("/res/world.png")));
		btnLoadState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadState();
			}
		});
		btnLoadState.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
		btnLoadState.setIcon(new ImageIcon(getClass().getResource("/res/folder.png")));
		btnSaveState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveState();
			}
		});
		btnSaveState.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		btnSaveState.setIcon(new ImageIcon(getClass().getResource("/res/disk.png")));
		btnNewGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		btnNewGame.setIcon(new ImageIcon(getClass().getResource("/res/arrow_rotate.png")));

		mnGame.add(btnNewGame);
		mnGame.add(btnRestart);
		mnGame.add(btnLoadLevel);
		mnGame.add(btnLoadState);
		mnGame.add(btnSaveState);
		mnGame.add(btnOptions);
		mnGame.add(btnExit);
		menuBar.add(mnGame);

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		displayMessageStatus("YOU CAN START A NEW GAME");
		btnOptions.addMouseListener(new MouseAdapter() {
		});
		btnOptions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				option.setvisible(true);
				option.OptionsMenu();
			}
		});


		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameEngine= new GameEngine();
				gameEngine.reinitialize();
				gameEngine.createOgresArray(ogresDefaultNumber);
				toggleButtonsEnabled(true);
				gameEngine.setGuardType(guardDefaultType);
				panelInterface.initializeBoard(gameEngine.getBoard());
				displayMessageStatus("YOU MUST ESCAPE FROM THE DUNGEON KEEP!");
			}
		});
		btnNewGame.setBackground(UIManager.getColor("Button.background"));
		mnHelp.setText("Help");
		mnHelp.setBackground(SystemColor.menu);

		menuBar.add(mnHelp);
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUIAbout guiAbout = new GUIAbout(InterfaceGUI.this);
				guiAbout.setVisible(true);
			}
		});

		btnAbout.setIcon(new ImageIcon(getClass().getResource("/res/help.png")));
		mnHelp.add(btnAbout);
		getContentPane().add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{1, 45, 57, 59, 51, 0};
		gbl_panel.rowHeights = new int[]{23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStatus.setBackground(Color.DARK_GRAY);
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.gridwidth = 5;
		gbc_lblStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 0;
		panel.add(lblStatus, gbc_lblStatus);


		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					gameEngine.handleSelectedHeroMovement(Direction.UP);
					displayMessageStatus("");
					panelInterface.repaint();
				} catch (Throwable e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUp.setEnabled(false);
		btnUp.setFocusable(false);
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.ipady = 2;
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.anchor = GridBagConstraints.NORTH;
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 3;
		gbc_btnUp.gridy = 1;
		panel.add(btnUp, gbc_btnUp);
		btnRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					gameEngine.handleSelectedHeroMovement(Direction.RIGHT);
					panelInterface.repaint();
				} catch (Throwable e1) {

					e1.printStackTrace();
				}
			}
		});
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					gameEngine.handleSelectedHeroMovement(Direction.DOWN);
					displayMessageStatus("");
					panelInterface.repaint();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDown.setFocusable(false);
		btnLeft.setEnabled(false);
		btnLeft.setFocusable(false);
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.ipady = 2;
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnLeft.gridx = 2;
		gbc_btnLeft.gridy = 2;
		panel.add(btnLeft, gbc_btnLeft);

		btnLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					gameEngine.handleSelectedHeroMovement(Direction.LEFT);
					displayMessageStatus("");
					panelInterface.repaint();
				} catch (Throwable e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDown.setEnabled(false);
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.ipady = 2;
		gbc_btnDown.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDown.gridx = 3;
		gbc_btnDown.gridy = 3;
		panel.add(btnDown, gbc_btnDown);
		btnDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnRight.setEnabled(false);
		btnRight.setFocusable(false);
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.ipady = 2;
		gbc_btnRight.insets = new Insets(0, 0, 5, 5);
		gbc_btnRight.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRight.gridx = 4;
		gbc_btnRight.gridy = 2;
		panel.add(btnRight, gbc_btnRight);

		btnRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		pack();
		setResizable(false);
		setLocationRelativeTo(null);

	}

	private void loadState()
	{
		FileInputStream fin;
		ObjectInputStream oin;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Dungeon Keep saved games (*.state)", "state"));

		if (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}

		try
		{
			fin = new FileInputStream(fileChooser.getSelectedFile());
			oin = new ObjectInputStream(fin);
			gameEngine= (GameEngine) oin.readObject();
			//GameEngine.read(oin);
			panelInterface.initializeBoard(GameEngine.getBoard());
			resumeGame();
			displayMessageStatus("YOU MUST ESCAPE FROM THE DUNGEON KEEP");
			oin.close();
			fin.close();
		}
		catch (IOException | ClassNotFoundException ex)
		{
			//GUIGlobals.warn(ex, this);
			System.out.println("tratar excepção");
		}
	}




	private void saveState()
	{
		FileOutputStream fout;
		ObjectOutputStream oout;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter("Dungeon Keep saved games (*.state)", "state"));

		if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}

		try
		{
			fout = new FileOutputStream(fileChooser.getSelectedFile() + ".state");
			oout = new ObjectOutputStream(fout);
			oout.writeObject(gameEngine);
			oout.close();
			fout.close();
		}
		catch (final IOException ex)
		{
			//warn(ex, this);
			System.out.println("tratar warning");
		}
	}
	private void resumeGame(){

		panelInterface.setPreferredSize(panelInterface.getWindowSize());
		panelInterface.repaint();
		revalidate();
		pack();

	}
	public void displayMessageStatus(String message)
	{

		if (GameEngine.getGameOver())
		{
			if (GameEngine.getPlayerWon())
			{
				lblStatus.setText("");
				lblStatus.setText(lblStatus.getText()+ "YOU REACHED THE EXT ON THE DUNGEON KEEP :)");
			}
			else
			{
				lblStatus.setText("");
				switch(gameEngine.getBoard().getLevel()){
				case 1:
					lblStatus.setText(lblStatus.getText()+"YOU HAVE BEEN CAUGHT BY THE GUARD :(");
					break;
				case 2:
					lblStatus.setText(lblStatus.getText()+"YOU HAVE BEEN CAUGHT BY AN OGRE :(");
					break;
				}
			}
		}
		else{
			lblStatus.setText("");
			lblStatus.setText(lblStatus.getText()+ message );
		}
	}

	private void toggleButtonsEnabled(boolean desired){
		btnUp.setEnabled(desired);
		btnDown.setEnabled(desired);
		btnLeft.setEnabled(desired);
		btnRight.setEnabled(desired);
	}

	public static void setOgresNumber(int i) {

		ogresDefaultNumber = i;
	}

	public static  void setguardType(int i) {

		guardDefaultType= i;
	}



}



