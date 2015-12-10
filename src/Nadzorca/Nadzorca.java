package Nadzorca;

import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import java.util.*;



public class Nadzorca 
{
	
	static Processor Procesor = new Processor();
	
	static Scanner s = new Scanner(System.in);
    static String komenda = s.nextLine();
	@SuppressWarnings("static-access")
	
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
		komenda=s.nextLine();   //wybranie opcji z menu
		
		switch (komenda) 
		{
		  case "Utworz Proces":
		  {
			  komenda=s.nextLine();  // wybranie programu dla ktorego ma zostac utworzony proces 
			  UtworzProces(komenda);
		  }

		  case "Wyœwietl Pamiêæ":
		  {
		  
		  }
		  
		  case "Wyœwietl dysk":
		  {
		  
		  }		  
		  
		  case "Wyœwietl listê procesów":
		  {
			  ZarzProc.printProcessList();
		  }		  
		  
		  
		  default:
		    System.out.println("Wybrano nieznana opcje");
		}
		  
				
	}
	//Tworzenie procesu USERPROG
	public static void UtworzProces(String komenda)
	{		
		if(komenda=="prog1" || komenda=="prog2" || komenda=="program3")
		{
			String JOB = null;  // pobranie linijki $JOB  tymczasowo =null;
			String[] wynik1 = null;
			String[] karta1 = null;
			wynik1 = JOB.split(",");
			if(wynik1[1]=="$JOB");
			{
				int Pamiec = Integer.parseInt(wynik1[2]);
				try 
				{
					ZarzProc.createProcess(komenda, Pamiec);
				} catch (procCreationError e) 
					{
						e.printStackTrace();
					}
			}				
		}
	}
	
}