package interpreter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JEditorPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Interpreter {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interpreter window = new Interpreter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Interpreter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JEditorPane editorPane = new JEditorPane();
		editorPane.setBounds(10, 11, 202, 208);
		frame.getContentPane().add(editorPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(222, 11, 202, 208);
		frame.getContentPane().add(textPane);
		
		JButton btnWykonaj = new JButton("Wykonaj");
		btnWykonaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textPane.setText(Text(editorPane.getText()));
				
			}
		});
		btnWykonaj.setBounds(335, 227, 89, 23);
		frame.getContentPane().add(btnWykonaj);
	}
	
	private String Text(String text){
	
		
		return text;
	}
}
