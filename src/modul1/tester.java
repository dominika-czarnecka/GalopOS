package modul1;

import java.util.Scanner;

import modul3.ZarzProc;


public class tester {

	static int a = 0;
	static PCB p1;
	static PCB p2;
	static PCB p3;
	static PCB p4;
	static PCB p5;
	static PCB p6;
	static Scanner scanner = new Scanner(System.in);
	public static void menu()
	{
		
		System.out.println("MENU: ");
		try
		{
			a = scanner.nextInt();
		}
		catch (Exception ex) {ex.printStackTrace();}
		switch(a){
		case 1:
			Processor.set_to_run();
			menu();
			break;
		case 2:
			elo();
			menu();
			break;
		default:
				
		}
			
	}
	
	public static void elo(){
		try
		{
			System.out.println(p1.name);
			System.out.println(p2.name);
			System.out.println(p3.name);
			System.out.println(p4.name);
			System.out.println(p5.name);
			System.out.println(p6.name);
			System.out.println("RUNNING: "+Processor.RUNNING.name);
		}
		catch(NullPointerException e){System.out.println("blad wyswietl_nazwe_running");}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		p1 = new PCB("p1", 0);
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
		
		p2.next = p3;
		p2.prev = p1;
		
		p2.stopped = false;
		p2.blocked = false;
		
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
		p6.blocked = false;
			
		menu();
			
	
	}

}
