package modul3;
import modul1.*;


public class ZarzProc {
	
	PCB findProcess(String name) {
		if(PCB.first==null) return null;
		PCB it = PCB.first;
		do {
			if (it.name.equals(name)) return it;
			it = it.next;
		} while (it != PCB.first);
		return null;
	}
	
	void createProcess(String name, int memory) throws procCreationError {
		if (findProcess(name)!=null)
			throw new procCreationError();
		else {
			//XA(name, memory); przydzielanie pamiêci
			PCB process = new PCB(name, memory);
			pushProcess(process);
		}
	}
	
	void removeProcess(String name) throws procNotFoundError {
		PCB process = findProcess(name);
		if (process == null) {
			throw new procNotFoundError();
		}
		else {
			popProcess(process);
			process.Messages.clear();
			//XF(name); zwalnianie pamiêci
		}
	}
	
	//XH - zatrzymanie zlecenia i powiadomienie nadzorcy
	
	void popProcess(PCB process) {
		if (process.next==process)
			PCB.first = null;
		else {
			if (PCB.first == process)
				PCB.first = process.next;
			process.next.prev = process.prev;
			process.prev.next = process.next;
		}
	}

	void pushProcess(PCB process) {
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

	void startProcess(String name) throws procNotFoundError {
		PCB process = findProcess(name);
		if (process == null) throw new procNotFoundError();
		//wpisywanie stanu rejestrów
		process.stopped = false;
	}
	
	
	void stopProcess(String name) throws procNotFoundError {
		if (name.startsWith("*")) return; //proces systemowy nie mo¿e byæ zatrzymany
		PCB process = findProcess(name);
		if (process == null) throw new procNotFoundError();
		process.stopped = true;
	}
	
	
	void sendMessage(PCB sender, PCB receiver, String content) {
		receiver.Messages.add(new Message(sender, content));
		receiver.msgSemaphore.XV();
	}
	
	Message readMessage(PCB process) {
		//P na semaforze
		return process.Messages.remove(0);
	}
	
	
	void printProcessList() {
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
	
	void printProcessListBack() {
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
