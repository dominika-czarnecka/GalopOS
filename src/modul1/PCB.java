package modul1;
import java.util.List;

public class PCB {
        String name;
        int ID;
        PCB next;
        PCB prev;      
        static PCB first;
        PCB next_semaphore_waiter;
        
       
        List<Message> Messages;
        Semaphores msgSemaphore;
       
        boolean stopped;
        boolean blocked;
       
        Registers register;
        int memoryLimit;
        //inf. o otwartych plikach
       
        PCB(String name, int memory) {
                this.name = name;
                this.memoryLimit = memory;
                stopped = true;
                blocked = false;
                
        }
        
        
}