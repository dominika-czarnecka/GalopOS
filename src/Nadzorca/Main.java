package Nadzorca;
import interpreter.*;
import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import Nadzorca.*;


public class Main {
	public static void start_print()
	{
		System.out.println("Prima [Version 3.0.607]\n(c) 2015 Wszelkie prawa zastrzezone.");
	}
	public static void set_cmd()
	{
		System.out.print("[User]: ");
	}
	public static void main(String[] args) {
		start_print();
		set_cmd();
		hdd_commander driver = new hdd_commander(32,32);
		Pamiec pamiec=new Pamiec();
		Nadzorca nadzorca=new Nadzorca(driver, pamiec);
		Interpreter interpreter= new Interpreter(driver, pamiec);
		
		driver.create("prog1","$JOB,95,FILEIN=IN,FILEOUT=OUT  \n" 
				+"MVI C 10\n"
				+ "MVI B 1\n"
				+ "ADD A B\n"
				+ "INR B\n"
				+ "INR B\n"
				+ "DCR C\n"
				+ "JNZ 2\n"
				+ "HLT\n");
		
		driver.create("prog2", "$JOB,173,FILEIN=IN,FILEOUT=OUT \n"
				+ "OFR prog2dane.txt" + "\n"
				+ "RF A" + "\n"
				+ "RF B" + "\n"
				+ "ADD A B" + "\n"
				+ "MOD2 C A" + "\n"
				+ "CFR" + "\n"
				+ "OFW prog2wynik.txt" + "\n"
				+ "JNZ 11" + "\n"
				+ "WF Suma_parzysta" + "\n"
				+ "JZ 13" + "\n"
				+ "WF Suma_nieparzysta" + "\n"
				+ "CFW" + "\n"
				+ "HLT"+ "\n");
	}

}
