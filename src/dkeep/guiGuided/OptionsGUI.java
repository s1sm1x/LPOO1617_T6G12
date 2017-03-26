package dkeep.guiGuided;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OptionsGUI extends JFrame {

	private static final long serialVersionUID = -6532716622683837657L;
	private int	ogresNumber=1;
	private int guardType=1;

	public OptionsGUI() {
		initialize();
	}

	public int getOgresNumber(){
		return ogresNumber;
	}
	public int getGuardType(){
		return guardType;
	}

	private void init1(){
		setResizable(false);
		setBounds(100, 100, 319, 254);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		textField_Ogres = new JTextField();
		textField_Ogres.setText(ogresNumber+"");
		textField_Ogres.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c=e.getKeyChar();
				if(((!Character.isDigit(c) && c!=KeyEvent.VK_BACK_SPACE) || c==KeyEvent.VK_DELETE) ||(Character.isDigit(c) && c<'0') ||(Character.isDigit(c) && c>'5')||(textField_Ogres.getText().length() >0)){
					Toolkit.getDefaultToolkit().beep();
					e.consume();}  }  });	
	}

	private void init2(){
		textField_Ogres.setBounds(153, 44, 116, 22);
		getContentPane().add(textField_Ogres);
		textField_Ogres.setColumns(10);

		lblNewLabel = new JLabel("Number of Ogres:");
		lblNewLabel.setBounds(35, 47, 126, 16);
		getContentPane().add(lblNewLabel);
		String[] guardPersonality={"Rookie", "Drunken", "Suspicious"};
		comboBox_GuardPersonality = new JComboBox<String>(guardPersonality);
		comboBox_GuardPersonality.setBounds(153, 83, 116, 29);
		getContentPane().add(comboBox_GuardPersonality);
		lblGuardPersonality = new JLabel("Guard Personality:");
		lblGuardPersonality.setBounds(35, 89, 126, 16);
		getContentPane().add(lblGuardPersonality);
		btnReturn = new JButton("Return");
	}
	private void returnListener(){
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InterfaceGUI.setOgresNumber(Integer.parseInt((textField_Ogres.getText())));
				InterfaceGUI.setguardType(comboBox_GuardPersonality.getSelectedIndex()+1);
				dispose();} });
		btnReturn.setBounds(172, 137, 97, 25);
		btnReturn.setFocusable(false);
		getContentPane().add(btnReturn);
	}
	private void initialize() {
		init1();
		init2();
		returnListener();
	}

	public void setvisible(boolean b) {
		setVisible(b);
	}

	private JTextField textField_Ogres;
	private JComboBox<String> comboBox_GuardPersonality ;
	private JLabel lblNewLabel;
	private JLabel lblGuardPersonality ;
	private JButton btnReturn;

}
