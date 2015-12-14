package modul1;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import modul3.*;
import modul2.*;


public class tester {

	static int a = 0;
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void menu() throws procNotFoundError
	{
		
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
	
	public static void show_registers_process(String name) throws procNotFoundError{
		try
		{
			PCB temp = ZarzProc.findProcess(name);
		System.out.println("REJESTR ob: "+temp.register.A);
		System.out.println("REJESTR ob: "+temp.register.B);
		System.out.println("REJESTR ob: "+temp.register.C);
		System.out.println("REJESTR ob: "+temp.register.D);
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
		
		Pamiec pamiecioszki = new Pamiec();
		
		ZarzProc.createProcess("p1", 10);
		ZarzProc.createProcess("p2", 5);
		ZarzProc.createProcess("p3", 300);
		ZarzProc.createProcess("p4", 5);
		ZarzProc.createProcess("p5", 5);
		ZarzProc.createProcess("p6", 10);
		
		try {
			ZarzProc.findProcess("p1").register.A = 1;
			ZarzProc.findProcess("p2").register.A = 2;
			ZarzProc.findProcess("p3").register.A = 3;
			ZarzProc.findProcess("p4").register.A = 4;
			ZarzProc.findProcess("p5").register.A = 5;
			ZarzProc.findProcess("p6").register.A = 6;
		} catch(Exception e) {}
		
		ZarzProc.startProcess("p1");
		ZarzProc.startProcess("p2");
		ZarzProc.startProcess("p3");
		ZarzProc.startProcess("p4");
		ZarzProc.startProcess("p5");
		ZarzProc.startProcess("p6");
		
		ZarzProc.printProcessList();
		
		Semaphore s = new Semaphore(2);
			
		while(true)
		{
				String[] input = null;
				System.out.println("--=MENU=--: ");
				System.out.println("1): Set next RUNNING");
				System.out.println("2): Show processor registers");
				System.out.println("3): Show process register with name");
				System.out.println("4): Stop process with name");
				System.out.println("P): Semaphore operation P on: ");
				System.out.println("V): Semaphore operation V on: ");
				
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
								show_registers_process(input[1]);
								break;
							case "4":
								ZarzProc.stopProcess(input[1]);
								break;
							case "P":
								s.P(ZarzProc.findProcess(input[1]));
								break;
							case "V":
								s.V(ZarzProc.findProcess(input[1]));
								break;
							case "sem":
								s.print_queue();
								break;
							case "val":
								System.out.println(s.get_value());
								break;
							case "0":
								Processor.run_proc();
								break;
							default:
								System.out.println("Wrong argument");
						}
					}
		catch(Exception n){n.printStackTrace();}	
		}
			
	
	}

}
