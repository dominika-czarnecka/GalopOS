package modul3;

import modul1.*;
import modul2.*;
import Nadzorca.*;

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

	static public PCB createProcess(String name, int memory) throws procCreationError {
		if (findProcess(name)!=null)
			throw new procCreationError();
		else {
			try {
				//Pamiec.XA(name, memory);
			} catch (Exception e) {
				throw new procCreationError();
			}
			PCB process = new PCB(name, memory);
			pushProcess(process);
			System.out.println("Utworzono proces: "+process.name);
			return process;
		}
	}

	static public void removeProcess(String name) throws procNotFoundError {
		PCB process = findProcess(name);
		if (process == null) {
			throw new procNotFoundError();
		}
		else {
			popProcess(process);
			process.Messages.clear(); //bufor czyszczony wiadomosci
			Pamiec.XF(process.name); //zwalnianie pamieci
			System.out.println("Usuniêto proces: "+process.name);
		}
	}

	//XH - zatrzymanie zlecenia i powiadomienie nadzorcy
	static public void notifySup(PCB stopped) {
		stopped.stopped = true;
		Nadzorca.IBSUPmsg.add(new Message(stopped, "stopped"));
	}

	static public void popProcess(PCB process) { //usuwa z listy PCB
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
		//wpisywanie stanu rejestrï¿½w
		process.stopped = false;
	}


	static public void stopProcess(String name) throws procNotFoundError {
		if (name.startsWith("*")) return; //proces systemowy nie moï¿½e byï¿½ zatrzymany
		PCB process = findProcess(name);
		if (process == null) throw new procNotFoundError();
		process.stopped = true;
	}


	static public void sendMessage(PCB sender, PCB receiver, String content) {
		receiver.Messages.add(new Message(sender, content));
		receiver.msgSemaphore.V(receiver);
	}

	static public Message readMessage(PCB process) {
		if (!process.waitingForMessage) {
			if (process.msgSemaphore.P(process) < 0) {
				process.waitingForMessage = true;
				return null;
			}
		}
		process.waitingForMessage = false;
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
	
	static public void printDetailedList() {
		if (PCB.first == null) {
			System.out.println("lista jest pusta"); 
			return;
		}
		
		System.out.println("nazwa\tblocked\tstopped\tmsgSem\trejestry");
		System.out.println("-----------------------------------------");
		PCB it = PCB.first;
		do {
			System.out.print(it.name);
			if (it == Processor.RUNNING) System.out.print(" <-");
			System.out.println("\t" + it.blocked + "\t" + it.stopped + "\t" + it.msgSemaphore.value + "\t" + it.register.toString());
			
			it=it.next;
		} while(it!=PCB.first);
	}
}
