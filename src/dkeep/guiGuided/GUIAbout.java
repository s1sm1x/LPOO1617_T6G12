package dkeep.guiGuided;

import java.awt.Frame;

//import javax.swing.ImageIcon;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;


public class GUIAbout extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc_panel;
	private JPanel panel ;
	private JLabel lblOla ;
	private GridBagConstraints gbc_lblOla ;
	private GridBagConstraints gbc_lblUpfeuppt_1;
	private JLabel lblUpfeuppt_1;
	private void init1(){
		setTitle("About");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		JLabel lblDungeonKeep = new JLabel("Dungeon Keep");
		lblDungeonKeep.setFont(new Font("Tahoma", Font.BOLD, 19));
		GridBagConstraints gbc_lblDungeonKeep = new GridBagConstraints();
		gbc_lblDungeonKeep.insets = new Insets(0, 0, 5, 5);
		gbc_lblDungeonKeep.gridx = 3;
		gbc_lblDungeonKeep.gridy = 0;
		getContentPane().add(lblDungeonKeep, gbc_lblDungeonKeep);
		panel = new JPanel();
		gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);

	}
	private void init2(){
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 2;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		lblOla = new JLabel("Pedro Oliveira");
		gbc_lblOla = new GridBagConstraints();
		gbc_lblOla.insets = new Insets(0, 0, 5, 5);
		gbc_lblOla.gridx = 2;
		gbc_lblOla.gridy = 1;

	}
	private void init3(){
		panel.add(lblOla, gbc_lblOla);
		JLabel lblUpfeuppt = new JLabel("up201507152@fe.up.pt");
		GridBagConstraints gbc_lblUpfeuppt = new GridBagConstraints();
		gbc_lblUpfeuppt.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpfeuppt.gridx = 2;
		gbc_lblUpfeuppt.gridy = 2;
		panel.add(lblUpfeuppt, gbc_lblUpfeuppt);
		JLabel lblDiogoCosta = new JLabel("Diogo Costa");
		GridBagConstraints gbc_lblDiogoCosta = new GridBagConstraints();
		gbc_lblDiogoCosta.insets = new Insets(0, 0, 5, 5);
		gbc_lblDiogoCosta.gridx = 2;
		gbc_lblDiogoCosta.gridy = 3;
		panel.add(lblDiogoCosta, gbc_lblDiogoCosta);
		lblUpfeuppt_1 = new JLabel("up201507146@fe.up.pt");
		gbc_lblUpfeuppt_1 = new GridBagConstraints();

	}
	private void init4(){
		gbc_lblUpfeuppt_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblUpfeuppt_1.gridx = 2;
		gbc_lblUpfeuppt_1.gridy = 4;
		panel.add(lblUpfeuppt_1, gbc_lblUpfeuppt_1);
		JLabel lblFeupMib = new JLabel("FEUP - MIB - LPOO 2017");
		GridBagConstraints gbc_lblFeupMib = new GridBagConstraints();
		gbc_lblFeupMib.insets = new Insets(0, 0, 0, 5);
		gbc_lblFeupMib.gridx = 2;
		gbc_lblFeupMib.gridy = 5;
		panel.add(lblFeupMib, gbc_lblFeupMib);
		pack();
		setLocationRelativeTo(null);
	}
	private void initComponents(){
		init1();
		init2();
		init3();
		init4();

	}
	public GUIAbout(Frame parent)
	{
		super(parent, true);
		initComponents();

	}
}
