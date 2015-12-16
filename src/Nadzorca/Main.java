package Nadzorca;
import interpreter.*;
import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import Nadzorca.*;


public class Main {

	public static void logo()
	{
		System.out.print("\t\t\t\t\t\t\t _________\n"
						+"\t\t\t\t\t\t\t│o  ______│\n"
						+"\t\t\t\t\t\t\t│o │\n"
						+"\t\t\t\t\t\t\t│ o│______ \n"
						+"\t\t\t\t\t\t\t│ o ______│\n"
						+"\t\t\t\t\t\t\t│ o│\n"
						+"\t\t\t\t\t\t\t│o │\n"
						+"\t\t\t\t\t\t\t│__│");
	System.out.print ("\t ____________\n"
					+"\t\t\t\t\t\t\t\t│____  o ____│\n"
					+"\t\t\t\t\t\t\t\t     │o │\n"
					+"\t\t\t\t\t\t\t\t     │ o│\n"
					+"\t\t\t\t\t\t\t\t     │ o│\n"
					+"\t\t\t\t\t\t\t\t     │o │\n"
					+"\t\t\t\t\t\t\t\t     │__│\n");
	System.out.printf("\n\t\t\t\t\t\t      "+ "%-5s", "F");
	System.out.printf("%-5s", "A");
	System.out.printf("%-5s", "N");
	System.out.printf("%-5s", "T");
	System.out.printf("%-5s", "O");
	System.out.printf("%-5s", "M\n");
	System.out.println("\n\t\t\t\t\t\t     Fantom [Version 9.5032308953]\n"
			+ "\t\t\t\t\t\t    (c) Wszelkie prawa zastrzezone.");
	}
	public static void main(String[] args) {
		logo();
		Nadzorca.s.nextLine();
		hdd_commander driver = new hdd_commander(32,32);
		Pamiec pamiec=new Pamiec();
		Nadzorca nadzorca=new Nadzorca(driver, pamiec);
		Interpreter interpreter= new Interpreter(driver, pamiec);
		driver.create("prog1","$JOB,70,FILEIN=IN,FILEOUT=OUT  \n" 
				+ "MVI C 3\n"//8
				+ "MVI B 1\n"//8
				+ "ADD A B\n"
				+ "INR B\n"
				+ "INR B\n"
				+ "DCR C\n"
				+ "JNZ 16\n"
				+ "SM prog3 A\n"
				+ "OUT A\n"
				+ "HLT\n");
		
		driver.create("prog2", "$JOB,120,FILEIN=IN,FILEOUT=OUT \n"
				+ "RF A prog2l1\n"//13
				+ "RF B prog2l2\n"//13
				+ "ADD A B\n"//8
				+ "MOD2 C A\n"//9
				+ "JNZ 75\n"//7
				+ "WF Suma_parzysta prog2wy\n"//17
				+ "JZ 101\n"//6
				+ "WF Suma_nieparzysta prog2wy\n"//20
				+ "OUT C\n"
				+ "HLT\n");//4

		driver.create("prog3", "$JOB,56,FILEIN=IN,FILEOUT=OUT \n"
				+ "IN A\n"//5
				+ "PR prog1\n"//11
				+ "RM B\n"
				+ "CMP A B\n"//8
				+ "JS 39\n"//6
				+ "OUT A\n"//6
				+ "JNS 52\n"//7
				+ "OUT B\n"//6
				+ "HLT\n");
		driver.create("prog4", "$JOB,56,FILEIN=IN,FILEOUT=OUT \n"
				+ "IN A\n"//5
				+ "PR prog1\n"//11
				+ "RM B\n"
				+ "CMP A B\n"//8
				+ "JS 39\n"//6
				+ "OUT A\n"//6
				+ "JNS 52\n"//7
				+ "OUT B\n"//6
				+ "HLT\n");

		nadzorca.IPLRTN();
		nadzorca.IBSUP();
	
		
	}

}
