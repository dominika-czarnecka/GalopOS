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
		System.out.println("RUNNING: "+RUNNING.name);
		}
		catch(Exception ex) {ex.printStackTrace();}
		//Interpreter.Rozkaz("INR A\nADD A");
	}
	static public void run_proc(){ //wykonaj instrukcjê
		//Interpreter.Rozkaz();
		System.out.println("Wykonanie intstrukcji");
		counter++;
		if (counter<4) run_proc();
		else XPER();
	}
	
	static public void XPER(){
		System.out.println("Wyw³aszczam proces");
		save_all_registers();
		RUNNING.stopped = true;
		counter = 1;
		set_to_run();
		}
	
	static private void save_all_registers(){
		RUNNING.register.A = reg.A;
		RUNNING.register.B = reg.B;
		RUNNING.register.C = reg.C;
		RUNNING.register.D = reg.D;
		 
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
