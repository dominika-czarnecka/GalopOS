package modul2;

import modul3.ZarzProc;
import modul3.procCreationError;
import modul3.procNotFoundError;

public class tester {

	public static void main(String[] args) {
		Pamiec pamiec = new Pamiec();
		
		try {
			ZarzProc.createProcess("a", 10);
			ZarzProc.createProcess("b", 10);
		} catch (procCreationError e) {
			e.printStackTrace();
		}
		
		Pamiec.WyswietlWolZaj();
	}

}
