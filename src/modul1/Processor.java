package modul1;

import interpreter.*;

public class Processor {

	static Registers reg = new Registers();
	static PCB PROCESS = PCB.first;
	public static PCB RUNNING = null;
	public static PCB next_try = PCB.first;
	public static int counter=1;
	
	static public PCB find_to_run(){ //znajdŸ pierwszy mo¿liwy do wykonania
		PCB it = PCB.first;
		try
		{
		while(it.blocked == true && it.stopped == true )
		{
			it = it.next;	
		}
		next_try = it;
		counter=0;
		load_all_registers();
		}
		catch(Exception ex) {System.out.println("b³ad");}
		
		return next_try;

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
		RUNNING = find_to_run();
	}
	
	static private void save_all_registers(){
		 reg.reg1 = RUNNING.register.reg1;
		 reg.reg2 = RUNNING.register.reg2;
		 reg.reg3 = RUNNING.register.reg3;
		 reg.reg4 = RUNNING.register.reg4;
		 reg.reg5 = RUNNING.register.reg5;
		 reg.reg6 = RUNNING.register.reg6;
		 reg.reg7 = RUNNING.register.reg7;
		 reg.reg8 = RUNNING.register.reg8;
		 reg.reg9 = RUNNING.register.reg9;
	}
	static private void load_all_registers(){
		RUNNING.register.reg1 = reg.reg1;
		RUNNING.register.reg2 = reg.reg2;
		RUNNING.register.reg3 = reg.reg3;
		RUNNING.register.reg4 = reg.reg4;
		RUNNING.register.reg5 = reg.reg5;
		RUNNING.register.reg6 = reg.reg6;
		RUNNING.register.reg7 = reg.reg7;
		RUNNING.register.reg8 = reg.reg8;
		RUNNING.register.reg9 = reg.reg9;
	}	
	static private int count_of_proc(){
		int count=0;
		PCB first = PCB.first;
		while(first.next!=PCB.first){
			first = first.next;
			count++;
		}
		return count;
	}
	static private void process_show(){
		if (PCB.first == null) System.out.println("There is no process");
		else{
			PCB first = PCB.first;
			System.out.println("NAME		"+"ID		");
			
				while(PCB.first.next != first){			
				
			}
		
		}
			
	}
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	}*/

}
