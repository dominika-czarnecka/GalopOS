package modul1;

import interpreter.*;

public class Processor {

	public static Registers reg = new Registers();

	public static PCB RUNNING = null;
	public static PCB next_try = PCB.first;
	public static int time=1;
	public static boolean waiting = true;

	static public void set_to_run(){ //znajdŸ pierwszy mo¿liwy do wykonania
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
			

			load_all_registers();
			System.out.println("NEW RUNNING: "+RUNNING.name);
			System.out.println("");
			System.out.println("");

		}
		catch(Exception ex) {ex.printStackTrace();}

	}
	static public void run_proc(){ //wykonaj instrukcjê
		try
		{
			if (RUNNING.name!=null && waiting == false)
			{
				if (time<4)
				{
					System.out.println("Wykonanie intstrukcji: " + time + " procesu: " + RUNNING.name);
					//Interpreter.Task();
					time++;
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
		time = 1;
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
		}
		catch(Exception e){System.out.println("");}

	}
	static private void load_all_registers(){
		reg.A = RUNNING.register.A;
		reg.B = RUNNING.register.B;
		reg.C = RUNNING.register.C;
		reg.D = RUNNING.register.D;
		reg.IP = RUNNING.register.IP;
	}	
	
	static public void show_register_processor(){
		System.out.println("Rejestry procesora: "+'\n'+"Rejestr A: "+Processor.reg.A+'\n'+"Rejestr B: "+Processor.reg.B+'\n'+"Rejestr C: "+Processor.reg.C+'\n'+"Rejestr D: "+Processor.reg.D+'\n'+"Rejestr IP: "+Processor.reg.IP+'\n');
	}
	
	static public void show_register_process(){
		System.out.println("Rejestry procesora: "+'\n'+"Rejestr A: "+RUNNING.register.A+'\n'+"Rejestr B: "+RUNNING.register.B+'\n'+"Rejestr C: "+RUNNING.register.C+'\n'+"Rejestr D: "+RUNNING.register.D+'\n'+"Rejestr IP: "+RUNNING.register.IP+'\n');
	}




	/*public static void main(String[] args) {
		// TODO Auto-generated method stub


	}*/

}
