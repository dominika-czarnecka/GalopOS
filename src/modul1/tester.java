package modul1;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import modul3.*;


public class tester {

	static int a = 0;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void menu() throws procNotFoundError
	{
		while(true)
		{
				String[] input = null;
				System.out.println("--=MENU=--: ");
				System.out.println("1): Set next RUNNING");
				System.out.println("2): Show processor registers");
				System.out.println("3): Show process registers");
				System.out.println("0): Run one instruction");
				try
					{
						input = br.readLine().split(" ");
						switch(input[0])
						{
							case "1":
								Processor.set_to_run();
								break;
							case "2":
								show_registers_processor();
								break;
							case "3":
								show_registers_process();
								break;
							case "4":
								ZarzProc.stopProcess(input[1]);
								break;
							case "0":
								Processor.run_proc();
								break;
							default:
						}
					}
		catch(Exception n){n.printStackTrace();}	
		}
	}
	
	public static void stop_process(){
		
	}
	
	public static void show_registers_processor(){
		System.out.println("REJESTR PROC: "+Processor.reg.A);
		System.out.println("REJESTR PROC: "+Processor.reg.B);
		System.out.println("REJESTR PROC: "+Processor.reg.C);
		System.out.println("REJESTR PROC: "+Processor.reg.D);
		System.out.println("");
		System.out.println("");
	}
	
	public static void show_registers_process() throws procNotFoundError{
		try
		{
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.A);
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.B);
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.C);
		System.out.println("REJESTR ob: "+Processor.RUNNING.register.D);
		System.out.println("");
		System.out.println("");
		}
		catch(Exception e) 
		{
		System.out.println("Brak procesu RUNNING!");System.out.println("");
		System.out.println("");System.out.println("");
		System.out.println("");menu();}
		}
	
	
	public static void main(String[] args) throws InterruptedException, procCreationError, procNotFoundError {
		// TODO Auto-generated method stub
		
		ZarzProc.createProcess("p1", 0);
		ZarzProc.createProcess("p2", 0);
		ZarzProc.createProcess("p3", 0);
		ZarzProc.createProcess("p4", 0);
		ZarzProc.createProcess("p5", 0);
		ZarzProc.createProcess("p6", 0);
		
		ZarzProc.startProcess("p1");
		ZarzProc.startProcess("p2");
		ZarzProc.startProcess("p3");
		ZarzProc.startProcess("p4");
		ZarzProc.startProcess("p5");
		ZarzProc.startProcess("p6");
		
		ZarzProc.printProcessList();
		
		/*p1 = new PCB("p1", 0);
		p2 = new PCB("p2", 0);
		p3 = new PCB("p3", 0);
		p4 = new PCB("p4", 0);
		p5 = new PCB("p5", 0);
		p6 = new PCB("p6", 0);
		PCB.first = p1;
		p1.next = p2;
		p1.prev = p6;
		
		p1.stopped = true;
		p1.blocked = true;
		p1.register.A = 1;
		p1.register.B = 1;
		p1.register.C = 1;
		p1.register.D = 1;
		
		p2.next = p3;
		p2.prev = p1;
				
		p2.stopped = false;
		p2.blocked = false;
		p2.register.D = 2;
		p2.register.C = 2;
		p2.register.B = 2;
		p2.register.A = 2;
		
		p3.next = p4;
		p3.prev = p2;
		
		p3.stopped = false;
		p3.blocked = true;
		
		p4.next = p5;
		p4.prev = p3;
		
		p4.stopped = false;
		p4.blocked = false;
		
		p5.next = p6;
		p5.prev = p4;
		
		p5.stopped = false;
		p5.blocked = false;
		
		p6.next = p1;
		p6.prev = p5;
		
		p6.stopped = false;
		p6.blocked = false;*/
			
		menu();
			
	
	}

}
