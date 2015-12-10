package modul1;

import modul3.ZarzProc;


public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PCB p1 = new PCB("p1", 0);
		PCB p2 = new PCB("p2", 0);
		PCB p3 = new PCB("p3", 0);
		PCB p4 = new PCB("p4", 0);
		PCB p5 = new PCB("p5", 0);
		PCB p6 = new PCB("p6", 0);
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
		p3.blocked = false;
		
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
			
		try
		{
			Processor.set_to_run();
		}
		catch(NullPointerException e) {System.out.println("blad find_to_runtester");}
						
			try
			{
				System.out.println(p1.name);
				System.out.println(p2.name);
				System.out.println(p3.name);
				System.out.println("RUNNING: "+Processor.RUNNING.name);
			}
			catch(NullPointerException e){System.out.println("blad wyswietl_nazwe_running");}
	}

}
