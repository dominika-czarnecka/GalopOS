package interpreter;

import java.awt.EventQueue;

import javax.swing.JFrame;
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
		String[] buffor= text.split("\n");
		
		for(;PCB.LR<buffor.length;PCB.LR++){
		
			buffor[PCB.LR]=buffor[PCB.LR].trim();
			task(buffor[PCB.LR],buffor);
		
		}
		
		String output = "A: " + PCB.A + "\nB: " + PCB.B;
		
		return output;
	}
	
	private void task(String taskText, String[] buffor){
		String[] buffor1= taskText.split(" ");
		
		int lenght=buffor1.length;
		
		switch(lenght){
		
			case 1:{
				if(buffor1[0]=="HLT"){
					//zakoñczenie procesu
				}
				break;
			}
			
			case 2:{
				switch(buffor1[0]){
				
					case "INR":{
						switch(buffor1[1]){
						case "A":
							PCB.A+=1;
							break;
						case "B":
							PCB.B++;
							break;
						case "C":
							PCB.C++;
							break;
						}
						break;
					}
				
					case "DCR":{
						switch(buffor1[1]){
						case "A":
							PCB.A--;
							break;
						case "B":
							PCB.B--;
							break;
						case "C":
							PCB.C--;
							break;
						}
						break;
					}
				
				case "JNZ":{
					if(PCB.C!=0){
						int temp=Integer.parseInt(buffor1[1]);
						task(buffor[temp-1], buffor);
					}
					break;
				}
			
				case "JZ":{
					if(PCB.C==0){
						int temp=Integer.parseInt(buffor1[1]);
						task(buffor[temp-1], buffor);
					}
					break;
				}
			
				case "OPENF":{
					break;
					}
				}
				break;
			}
			
			case 3:{

				
				break;
			}
		}
	}
	
}

class  PCB{
	static int A;
	static int B;
	static int C;
	static int LR;			
}
