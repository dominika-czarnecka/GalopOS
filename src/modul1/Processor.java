package modul1;

import interpreter.*;

public class Processor {

	public static Registers reg = new Registers();

	public static PCB RUNNING = null;
	public static PCB next_try = PCB.first;
	public static int time=1;
	public static boolean waiting = false;
	
	static public void set_to_run(){ //znajd� pierwszy mo�liwy do wykonania
		waiting = false;
		try
		{
			PCB start = next_try;
			do {
				if (next_try.moznaUruchomic()) break;
				next_try = next_try.next;
			} while (next_try != start);
			
			if(next_try == start && !next_try.moznaUruchomic()) waiting = true;
			
			RUNNING = next_try;
			next_try = next_try.next;
			time=1;
			load_all_registers();
<<<<<<< HEAD
			System.out.println("[Proc]NEW RUNNING: "+RUNNING.name); 
=======
			if(!RUNNING.name.contains("*")){
			System.out.println("[Proc]NEW RUNNING: "+RUNNING.name);}
>>>>>>> origin/master
		}

		catch(Exception ex) {ex.printStackTrace();}
	}
	
	static public void run_proc(){ //wykonaj instrukcj�
<<<<<<< HEAD
		if (RUNNING.moznaUruchomic()){
			try{
=======
		if(n_proc()!=3){
		if (RUNNING.moznaUruchomic()) {
			try
			{
>>>>>>> origin/master
				if (waiting) System.out.println("[Proc]Procesor jest w stanie czekania.");
				else{
				if (RUNNING.name!=null && waiting == false )
				{
					if (time<4)
					{
						System.out.println("[Proc]Wykonanie instrukcji: " + time + " procesu: " + RUNNING.name);
						Interpreter.Task(); 
						time++;
					}
					else XPER();
				}
				}
			}
<<<<<<< HEAD
			catch(Exception ex) {ex.printStackTrace();;}}
		
		else {
			set_to_run();
		}
}
=======
			catch(Exception ex) {ex.printStackTrace();}
		}
		else {
			set_to_run();
		}
	} else waiting = true;
	}
>>>>>>> origin/master

	static public void XPER()
	{
		next_try = RUNNING.next;
		System.out.println("[Proc]Wyw�aszczam proces: " + RUNNING.name);
		save_all_registers();
		//time = 1;
		set_to_run();
	}

	static private void save_all_registers(){
		try
		{
			RUNNING.register.A = reg.A;
			RUNNING.register.B = reg.B;
			RUNNING.register.C = reg.C;
			RUNNING.register.D = reg.D;
			RUNNING.register.IP = reg.IP;
			RUNNING.register.Z = reg.Z;
			RUNNING.register.S = reg.S;
		}
		catch(Exception e){System.out.println("");}

	}
	static private void load_all_registers(){
		reg.A = RUNNING.register.A;
		reg.B = RUNNING.register.B;
		reg.C = RUNNING.register.C;
		reg.D = RUNNING.register.D;
		reg.IP = RUNNING.register.IP;
		reg.Z = RUNNING.register.Z;
		reg.S = RUNNING.register.S;
	}	

	static public void show_register_processor(){
		System.out.println("[Proc]Rejestry procesora: "+'\n'+"Rejestr A: "+Processor.reg.A+'\n'+"Rejestr B: "+Processor.reg.B+'\n'+"Rejestr C: "+Processor.reg.C+'\n'+"Rejestr D: "+Processor.reg.D+'\n'+"Rejestr IP: "+Processor.reg.IP+'\n');
	}

	static public void show_register_process(){
		System.out.println("[Proc]Rejestry procesora: "+'\n'+"Rejestr A: "+RUNNING.register.A+'\n'+"Rejestr B: "+RUNNING.register.B+'\n'+"Rejestr C: "+RUNNING.register.C+'\n'+"Rejestr D: "+RUNNING.register.D+'\n'+"Rejestr IP: "+RUNNING.register.IP+'\n');
	}
	static public int n_proc(){ 
		PCB start = next_try;
		int n=0;
		do { next_try = next_try.next;n++;} while (next_try != start);
		return n;
	}
}
