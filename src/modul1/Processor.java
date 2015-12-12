package modul1;

import interpreter.*;

public class Processor {

	public static Registers reg = new Registers();
	
	public static PCB RUNNING = null;
	public static PCB next_try = PCB.first;
	public static int counter=1;
	
	static public void set_to_run(){ //znajdŸ pierwszy mo¿liwy do wykonania
		
		try
		{
		while(next_try.blocked == true || next_try.stopped == true )
		{
			next_try = next_try.next;	
		}
		RUNNING = next_try;
		next_try = next_try.next;	
				
		load_all_registers();
		System.out.println("NEW RUNNING: "+RUNNING.name);
		System.out.println("");
		System.out.println("");
		
		}
		catch(Exception ex) {ex.printStackTrace();}
		//Interpreter.Rozkaz("INR A\nADD A");
	}
	static public void run_proc(){ //wykonaj instrukcjê
		//Interpreter.Rozkaz();
		try
		{
		if (RUNNING.name!=null)
		{
			if (counter<4)
			{
				System.out.println("Wykonanie intstrukcji: " + counter + " procesu: " + RUNNING.name);
				counter++;
			}
			else XPER();
			}
		}
		catch(Exception ex) {System.out.println("Cannot run instruction");}
	}
	
	static public void XPER()
	{
		System.out.println("Wyw³aszczam proces: " + RUNNING.name);
		save_all_registers();
		counter = 1;
		set_to_run();
		}
	
	static private void save_all_registers(){
		try
		{
		RUNNING.register.A = reg.A;
		RUNNING.register.B = reg.B;
		RUNNING.register.C = reg.C;
		RUNNING.register.D = reg.D;
		}
		catch(Exception e){System.out.println("");}
		 
	}
	static private void load_all_registers(){
		reg.A = RUNNING.register.A;
		reg.B = RUNNING.register.B;
		reg.C = RUNNING.register.C;
		reg.D = RUNNING.register.D;
	}	
	
	
			

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}*/

}
