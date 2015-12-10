package modul1;

import interpreter.*;

public class Processor {

	static Registers reg = new Registers();
	
	public static PCB RUNNING = null;
	public static PCB next_try = PCB.first;
	public static int counter=1;
	
	static public void set_to_run(){ //znajdŸ pierwszy mo¿liwy do wykonania
		
		try
		{
		while(Processor.next_try.blocked == true || Processor.next_try.stopped == true )
		{
			Processor.next_try = Processor.next_try.next;	
		}
		Processor.RUNNING = next_try;
		//next_try = RUNNING.next;
		//counter = 1;
		//load_all_registers();
		}
		catch(Exception ex) {System.out.println("blad find_to_run");}
		//Processor.run_proc();
	}
	static private void run_proc(){ //wykonaj instrukcjê
		//Interpreter.Rozkaz();
//		counter++;
		if (counter<3) run_proc();
		else expropriation();
	}
	
	static public void XPER(){
		expropriation();
		}
	
	static public void expropriation(){ //wyw³aszczenie
		save_all_registers();
		RUNNING.stopped = true;
		set_to_run();
	}
	
	static private void save_all_registers(){
		 reg.A = RUNNING.register.A;
		 reg.B = RUNNING.register.B;
		 reg.C = RUNNING.register.C;
		 reg.D = RUNNING.register.D;
	}
	static private void load_all_registers(){
		RUNNING.register.A = reg.A;
		RUNNING.register.B = reg.B;
		RUNNING.register.C = reg.C;
		RUNNING.register.D = reg.D;
	}	
	
			

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}*/

}
