import interpreter.*;
import modul1.*;
import modul4.*;
import modul1.*;
import modul2.*;



public class Main {

	public static void main(String[] args) {
		Thread t1 = new Thread(new Interpreter());
		t1.start();
		hdd_commander driver = new hdd_commander(32,32);
		Processor processor
	}

}
