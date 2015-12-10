import interpreter.*;
import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;


public class Main {

	public static void main(String[] args) {

		hdd_commander driver = new hdd_commander(32,32);
		
		driver.create("prog1", "MVI C 10" + "\n"
				+ "MVI B 1" + "\n"
				+ "ADD A B" + "\n"
				+ "INR B" + "\n"
				+ "INR B" + "\n"
				+ "DCR C" + "\n"
				+ "JNZ 2" + "\n"
				+ "HLT" + "\n");
		
		driver.create("prog2", "$JOB,20,CZYTNIK=*IN,DRUKARKA=*OUT"
				+ "OFR prog2dane.txt" + "\n"
				+ "RF A" + "\n"
				+ "RF B" + "\n"
				+ "ADD A B" + "\n"
				+ "MOD2 C A" + "\n"
				+ "CFR" + "\n"
				+ "OFW prog2wynik.txt" + "\n"
				+ "JNZ 11" + "\n"
				+ "WF Suma_parzysta" + "\n"
				+ "JZ 12" + "\n"
				+ "WF Suma_nieparzysta" + "\n"
				+ "CFW" + "\n"
				+ "HLT"+ "\n");
	}

}
