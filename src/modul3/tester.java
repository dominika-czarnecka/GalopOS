package modul3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import modul1.Processor;

public class tester {

	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			Processor.RUNNING = Processor.next_try = ZarzProc.createProcess("p1", 0);
		} catch (procCreationError | procNotFoundError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String[] cmd = null;
		do {
			try {
				cmd = br.readLine().split(" ");
				
				switch (cmd[0]) {
				case "n":
					ZarzProc.createProcess(cmd[1], 0);
					break;
				case "d":
					ZarzProc.removeProcess(cmd[1]);
					break;
				case "p":
					ZarzProc.printProcessList();
					break;
				case "s":
					ZarzProc.sendMessage(ZarzProc.findProcess(cmd[1]), ZarzProc.findProcess(cmd[2]), cmd[3]);
					break;
				case "r":
					Message msg = ZarzProc.readMessage(ZarzProc.findProcess(cmd[1]));
					if (msg!=null) System.out.println(msg.content);
					else System.out.println("nie zczytano");
					break;
				}
			} catch(Exception e) {e.printStackTrace();}
		} while (!cmd[0].equals("exit"));


	}

}
