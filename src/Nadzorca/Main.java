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
		
		driver.create("prog1","$JOB,59,FILEIN=IN,FILEOUT=OUT  \n" 
				+ "MVI C 10\n"
				+ "MVI B 1\n"
				+ "ADD A B\n"
				+ "INR B\n"
				+ "INR B\n"
				+ "DCR C\n"
				+ "JNZ 10\n"
				+ "HLT\n");
		
		driver.create("prog2", "$JOB,104,FILEIN=IN,FILEOUT=OUT \n"
				+ "RF A prog2l1\n"//13
				+ "RF B prog2l2\n"//13
				+ "ADD A B\n"//8
				+ "MOD2 C A\n"//9
				+ "JNZ 68\n"//7
				+ "WF Suma_parzysta\n"//17
				+ "JZ 94\n"//6
				+ "WF Suma_nieparzysta\n"//20
				+ "HLT\n");//4

		nadzorca.run_cmd();
	
	}

}
