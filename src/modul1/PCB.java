package modul1;
import java.awt.List;
import java.util.*;
import java.util.ArrayList;

//import java.util.List;
import modul3.*;

public class PCB {
		public String name;

        public PCB next;
        public PCB prev;      
        public static PCB first;
       // public PCB next_semaphore_waiter;
        
        public ArrayList<Message> Messages = new ArrayList<Message>();
        
        public Semaphore msgSemaphore;
        //public int Line;
        //public String buffor;
        public boolean stopped;
        public boolean blocked;
       
        Registers register = new Registers();
        int memoryLimit;
        //inf. o otwartych plikach
       
        public PCB(String name, int memory) {
                this.name = name;
                this.memoryLimit = memory;
                stopped = true;
                blocked = false;
                
        }
        
        boolean moznaUruchomic() {
        	return !blocked && !stopped;
        }
        
        
}