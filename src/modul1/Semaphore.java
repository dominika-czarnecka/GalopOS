package modul1;

import java.util.ArrayList;
import java.util.List;

public class Semaphore {

	public int value;
	public List<PCB> queue; 
	
	public Semaphore(int value) {
		this.value = value;
		this.queue = new ArrayList<PCB>();
	}
	
	public int P(PCB caller) {
		value--;
		if (value < 0) {
			queue.add(caller);
			caller.blocked = true;
			Processor.XPER();
			Processor.time--;
		}
		return value;
	}
	
	
	public int V(PCB caller) {
		value++;
		if (value <= 0) {
			Processor.next_try = queue.remove(0);
			Processor.next_try.blocked = false;
		}
		return value; 
	}
	
	public int get_value(){
		return value;
	}
	
	public void print_queue(){
		for(PCB x : queue)
            System.out.println(x.name);
	}
}