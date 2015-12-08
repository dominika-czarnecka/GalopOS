package modul1;
import java.util.List;

public class PCB {
		public String name;
        public int ID;
        public PCB next;
        public PCB prev;      
        public static PCB first;
        public PCB next_semaphore_waiter;
        
       
        List<Message> Messages;
        Semaphores msgSemaphore;
       
        public boolean stopped;
        public boolean blocked;
       
        Registers register;
        int memoryLimit;
        //inf. o otwartych plikach
       
        public PCB(String name, int memory) {
                this.name = name;
                this.memoryLimit = memory;
                stopped = true;
                blocked = false;
                
        }
        
        
}