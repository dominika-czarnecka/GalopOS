package modul3;
import modul1.*;


public class ZarzProc {
	
	static public PCB findProcess(String name) {
		if(PCB.first==null) return null;
		PCB it = PCB.first;
		do {
			if (it.name.equals(name)) return it;
			it = it.next;
		} while (it != PCB.first);
		return null;
	}
	
	static public void createProcess(String name, int memory) throws procCreationError {
		if (findProcess(name)!=null)
			throw new procCreationError();
		else {
			//XA(name, memory); przydzielanie pami�ci
			PCB process = new PCB(name, memory);
			pushProcess(process);
		}
	}
	
	static public void removeProcess(String name) throws procNotFoundError {
		PCB process = findProcess(name);
		if (process == null) {
			throw new procNotFoundError();
		}
		else {
			popProcess(process);
			process.Messages.clear();
			//XF(name); zwalnianie pami�ci
		}
	}
	
	//XH - zatrzymanie zlecenia i powiadomienie nadzorcy
	
	static public void popProcess(PCB process) {
		if (process.next==process)
			PCB.first = null;
		else {
			if (PCB.first == process)
				PCB.first = process.next;
			process.next.prev = process.prev;
			process.prev.next = process.next;
		}
	}

	static public void pushProcess(PCB process) {
		if (PCB.first == null) {
			PCB.first = process;
			process.next = process;
			process.prev = process;
		} else {
			process.next = PCB.first;
			process.prev = PCB.first.prev;
			PCB.first.prev.next = process;
			PCB.first.prev = process;
			/*if (PCB.first.next==PCB.first)
				PCB.first.next=process;*/
		}
	}

	static public void startProcess(String name) throws procNotFoundError {
		PCB process = findProcess(name);
		if (process == null) throw new procNotFoundError();
		//wpisywanie stanu rejestr�w
		process.stopped = false;
	}
	
	
	static public void stopProcess(String name) throws procNotFoundError {
		if (name.startsWith("*")) return; //proces systemowy nie mo�e by� zatrzymany
		PCB process = findProcess(name);
		if (process == null) throw new procNotFoundError();
		process.stopped = true;
	}
	
	
	static public void sendMessage(PCB sender, PCB receiver, String content) {
		receiver.Messages.add(new Message(sender, content));
		receiver.msgSemaphore.XV();
	}
	
	static public Message readMessage(PCB process) {
		//P na semaforze
		return process.Messages.remove(0);
	}
	
	
	static public void printProcessList() {
		if (PCB.first == null) System.out.println("Puste");
		else {
			PCB it = PCB.first;
			System.out.print(it.name);
			it = it.next;
			while (it != PCB.first) {
				System.out.print("->" + it.name);
				it = it.next;				
			}
			System.out.print("\n");
		}
	}
	
	static public void printProcessListBack() {
		if (PCB.first == null) System.out.println("Puste");
		else {
			PCB it = PCB.first;
			System.out.print(it.name);
			it = it.prev;
			while (it != PCB.first) {
				System.out.print("->" + it.name);
				it = it.prev;				
			}
			System.out.print("\n");
		}
	}
}
