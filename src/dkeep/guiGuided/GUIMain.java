package dkeep.guiGuided;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dkeep.cli.DungeonKeepCLI;

//import java.io.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class GUIMain extends JFrame
{
	private static final long serialVersionUID = -3154907517184624651L;


	public GUIMain()
	{
		setResizable(false);
		initComponents();
	}

	private void setInitialFeatures(){
		setBounds(0, 0, 687, 541);
		btnCLI = new JButton();
		btnCLI.setBounds(35, 232, 198, 30);
		btnCLI.setFocusable(false);
		btnPlay = new JButton();
		btnPlay.setBounds(35, 140, 198, 79);
		btnEditor = new JButton();
		btnEditor.setBounds(35, 275, 198, 30);
		btnEditor.setFocusable(false);
		btnQuit = new JButton();
		btnQuit.setBounds(35, 323, 198, 30);
		lblTitle = new JLabel();
		lblTitle.setBounds(12, 13, 269, 168);
		lblimage = new JLabel();
		lblimage.setBounds(245, 13, 433, 542);
	}
	private void buttonListeners(){
		btnCLI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				DungeonKeepCLI.main(null);	}  });
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				InterfaceGUI interfaceGUI = new InterfaceGUI();
				interfaceGUI.setVisible(true);	}  });
		btnEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				GUIBoardEditor guiBoardEditor = new GUIBoardEditor();
				guiBoardEditor.setVisible(true);	}	});
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();	} });	
	}
	private void setLayout1(){
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/gameicon.png")));
		setTitle("Main Menu");
		lblimage.setIcon(new ImageIcon(getClass().getResource("/images/first.png")));
		btnCLI.setText("CLI");
		btnCLI.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPlay.setText("Play Game");
		btnPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnPlay.setFocusable(false);

		btnEditor.setText("Board Editor");
		btnEditor.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnEditor.setFocusable(false);
		
	}
	private void setLayout2(){
		btnQuit.setText("Quit");
		btnQuit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnQuit.setFocusable(false);
		lblTitle.setFont(lblTitle.getFont().deriveFont(lblTitle.getFont().getStyle() | Font.BOLD, lblTitle.getFont().getSize() + 13));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setText("Dungeon Keep");
		lblTitle.setFocusable(false);
		getContentPane().setLayout(null);
		getContentPane().add(btnQuit);
		getContentPane().add(btnCLI);
		getContentPane().add(btnPlay);
		getContentPane().add(lblimage);
		getContentPane().add(lblTitle);
		getContentPane().add(btnEditor);
		//pack();
		setLocationRelativeTo(null);
	}
	
	private void initComponents() 
	{
		setInitialFeatures();
		 buttonListeners();
		 setLayout1();
		 setLayout2();
			
	}

	public static void main(String args[])
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
		{
			close(ex, null);
		}
		EventQueue.invokeLater(() -> {
			new GUIMain().setVisible(true);
		});
	}
	protected static void close(Exception ex, JFrame parent)
	{
		JOptionPane.showMessageDialog(parent, ex.getLocalizedMessage() + ".\nThe game will be closed...", "ERROR", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}

	protected static void warning(Exception ex, JFrame parent)
	{
		JOptionPane.showMessageDialog(parent, ex.getLocalizedMessage(), "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	protected static void warning(String message, JFrame parent)
	{
		JOptionPane.showMessageDialog(parent, message, "WARNING", JOptionPane.WARNING_MESSAGE);
	}

	private JButton btnCLI;
	private JButton btnEditor;
	private JButton btnPlay;
	private JButton btnQuit;
	private JLabel lblTitle;
	private JLabel lblimage;
}