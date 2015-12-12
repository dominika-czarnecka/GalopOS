package modul1;

public class Semaphores {

	public int value = 0;
	public PCB first_semaphore_waiter = null;
	
	public Semaphores(int value) {this.value = value;}
	public Semaphores(){}
	
	public void XP(){
		PCB first = PCB.first;
		value--;
		if (value < 0){
			if(first_semaphore_waiter == null){
				first_semaphore_waiter = Processor.RUNNING;
				first_semaphore_waiter.next_semaphore_waiter = null;
				Processor.RUNNING.blocked = true;
				Processor.XPER();
				//Processor.RUNNING = Processor.find_to_run();
			}
			else{
				while(first.next_semaphore_waiter != null){
					first = first.next_semaphore_waiter;
				}
				first.next_semaphore_waiter = Processor.RUNNING;
				Processor.RUNNING.blocked = true;
				Processor.XPER();
				//Processor.RUNNING = Processor.find_to_run();
			}
		}
	}	
	public void XV()
	{
		value++;
		if(value<=0){
			
				Processor.next_try = first_semaphore_waiter;
				first_semaphore_waiter.blocked = false;
				first_semaphore_waiter = first_semaphore_waiter.next_semaphore_waiter;			
		}
	}
}