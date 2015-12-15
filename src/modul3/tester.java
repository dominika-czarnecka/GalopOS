package modul3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import modul1.Processor;

public class tester {

	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			ZarzProc.createProcess("p1", 0);
			ZarzProc.startProcess("p1");
			Processor.set_to_run();
		} catch (procCreationError | procNotFoundError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Processor.waiting = false;

		String[] cmd = null;
		System.out.println("New, Delete, Print, Send, Read");

		do {
			try {
				cmd = br.readLine().split(" ");

				switch (cmd[0]) {
				case "n":
					ZarzProc.createProcess(cmd[1], 0);
					ZarzProc.startProcess(cmd[1]);
					break;
				case "d":
					ZarzProc.removeProcess(cmd[1]);
					break;
				case "p":
					ZarzProc.printProcessList();
					break;
				case "b":
					ZarzProc.printProcessListBack();
					break;
				case "pd":
					ZarzProc.printDetailedList();
					break;
				case "send":
					ZarzProc.sendMessage(Processor.RUNNING, ZarzProc.findProcess(cmd[1]), cmd[2]);
					break;
				case "read":
					Message msg = ZarzProc.readMessage(Processor.RUNNING);
					if (msg!=null) System.out.println(msg.content);
					else System.out.println("nie zczytano");
					break;
				case "r":
					Processor.run_proc();
					break;
				default:
					System.out.println("blad");
					break;
				}
			} catch(Exception e) {e.printStackTrace();}
		} while (!cmd[0].equals("exit"));


	}

}
