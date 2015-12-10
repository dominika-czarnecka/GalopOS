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
		System.out.println("REJESTR PROC: "+reg.A);
		System.out.println("REJESTR PROC: "+reg.B);
		System.out.println("REJESTR PROC: "+reg.C);
		System.out.println("REJESTR PROC: "+reg.D);
		
		
		load_all_registers();
		System.out.println("REJESTR ob: "+ RUNNING.register.A);
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.B);
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.C);
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.D);
		}
		//catch(Exception ex) {System.out.println("blad find_to_run");}
		catch(Exception ex) {ex.printStackTrace();}
		Interpreter.Rozkaz("INR A\nADD A");
	}
	static private void run_proc(){ //wykonaj instrukcjê
		//Interpreter.Rozkaz();
//		counter++;
		if (counter<3) run_proc();
		else XPER();
	}
	
	static public void XPER(){
		save_all_registers();
		RUNNING.stopped = true;
		set_to_run();
		}
	
	static private void save_all_registers(){
		 reg.A = RUNNING.register.A;
		 /*reg.B = RUNNING.register.B;
		 reg.C = RUNNING.register.C;
		 reg.D = RUNNING.register.D;*/
	}
	static private void load_all_registers(){
		//RUNNING.register.A = reg.A;
		//RUNNING.register.B = reg.B;
		//RUNNING.register.C = reg.C;
		//RUNNING.register.D = reg.D;
		reg.A = RUNNING.register.A;
	}	
	static public void show_registers(){
		System.out.println(RUNNING.register.A);
	}
	
			

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}*/

}
