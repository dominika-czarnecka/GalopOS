package Nadzorca;

import modul1.*;
import modul2.*;
import modul3.*;
import java.util.*;
import java.util.Scanner;
import modul1.PCB;

public class Nadzorca {
	
	static Processor Procesor = new Processor();
	
	public static void IPLRTN()
	{

		Pamiec pamiec = new Pamiec();
		try {
			ZarzProc.createProcess("*IPSUB", 0);
			ZarzProc.createProcess("*IN", 0);
			ZarzProc.createProcess("*OUT", 0);
			} catch (procCreationError e1) 
				{
					e1.printStackTrace();
				}
		try {
			ZarzProc.startProcess("*IBSUP");
			ZarzProc.startProcess("*IN");
			ZarzProc.startProcess("*OUT");		
			} catch (procNotFoundError e) 
				{
					e.printStackTrace();
				}
		Processor.next_try = PCB.first;	
		Processor.XPER();		
	}
	
	public static void IBSUP()
	{
		
		
		
	}	
	
}