package modul1;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			PCB p1 = new PCB("proc1", 0);
			PCB p2 = new PCB("proc2", 0);
			PCB p3 = new PCB("proc3", 0);
			PCB p4 = new PCB("proc4", 0);
			PCB p5 = new PCB("proc5", 0);
			
			PCB.first = p1;
			
			p1.blocked = true;
			p1.stopped = true;
			p1.next = p2;
			
			p2.blocked = false;
			p2.stopped = false;
			p2.next = p3;
			p2.prev = p1;
			
			p3.blocked = true;
			p3.stopped = false;
			p3.next = p1;
			p3.prev = p2;
			
			Processor.RUNNING = Processor.find_to_run();
			//Processor.RUNNING = p2;
			
			try
			{
				System.out.println(p1.next.name + p1.next.blocked + p1.next.stopped);
				System.out.println(p2.next.name + p2.next.blocked + p2.next.stopped);
				System.out.println(p3.next.name + p3.next.blocked + p3.next.stopped);
				System.out.println("RUNNING: "+Processor.RUNNING.name);
			}
			catch(Exception e){System.out.println("b³ad2");}
	}

}
