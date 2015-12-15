package modul2;

import modul3.ZarzProc;
import modul3.procCreationError;
import modul3.procNotFoundError;

public class tester {

	public static void main(String[] args) {
		Pamiec pamiec = new Pamiec();
//		
		try {
			ZarzProc.createProcess("a", 10);
			ZarzProc.createProcess("b", 10);
			ZarzProc.createProcess("c", 10);
		} catch (procCreationError e) {
			e.printStackTrace();
		}
//		
//		Pamiec.WyswietlWolZaj();
//		
//		try {
//			ZarzProc.removeProcess("b");
//		} catch (procNotFoundError e) {
//			e.printStackTrace();
//		}
//		Pamiec.WyswietlWolZaj();
		String imie="1234567890";
		
		pamiec.ZapiszDoPamieci("a", imie);
	}

}
