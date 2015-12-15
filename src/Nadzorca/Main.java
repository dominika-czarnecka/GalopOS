package Nadzorca;
import interpreter.*;
import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import Nadzorca.*;


public class Main {

	public static void main(String[] args) {
		hdd_commander driver = new hdd_commander(32,32);
		Pamiec pamiec=new Pamiec();
		Nadzorca nadzorca=new Nadzorca(driver, pamiec);
		Interpreter interpreter= new Interpreter(driver, pamiec);
		
		driver.create("prog1","$JOB,68,FILEIN=IN,FILEOUT=OUT  \n" 
				+ "MVI C 10\n"//8
				+ "MVI B 1\n"//7
				+ "ADD A B\n"
				+ "INR B\n"
				+ "INR B\n"
				+ "DCR C\n"
				+ "JNZ 17\n"
				+ "SM prog3 A\n"
				+ "OUT A\n"
				+ "HLT\n\n"); //niepotrzebne XD
		
		driver.create("prog2", "$JOB,123,FILEIN=IN,FILEOUT=OUT \n"
				+ "RF A prog2l1\n"//13
				+ "RF B prog2l2\n"//13
				+ "ADD A B\n"//8
				+ "MOD2 C A\n"//9
				+ "JNZ 68\n"//7
				+ "WF Suma_parzysta\n"//17
				+ "JZ 94\n"//6
				+ "WF Suma_nieparzysta\n"//20
				+ "OUT C\n"
				+ "HLT\n");//4

		driver.create("prog3", "$JOB,63,FILEIN=IN,FILEOUT=OUT \n"
				+ "IN A\n"//5
				+ "PR prog1\n"//11
				+ "RM B\n"
				+ "CMP A B\n"//8
				+ "JS 40\n"//6
				+ "OUT A\n"//6
				+ "JNS 53\n"//7
				+ "OUT B\n"//6
				+ "HLT\n");

		nadzorca.IPLRTN();
		nadzorca.IBSUP();
	
		
	}

}
