package Nadzorca;

import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import java.util.*;



public class Nadzorca 
{
	
	//static Processor Procesor = new Processor();
	static hdd_commander driver;
	static Pamiec pamiec;
	public Nadzorca(hdd_commander driver,Pamiec pamiec)
	{
		this.driver=driver;
		this.pamiec=pamiec;
	}
	
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
		int wybor = Integer.parseInt(komenda);
		
		menu1();
		
		switch (wybor) 
		{
		  case 1:   //utworz proces 
		  {
			  menu2();
			  komenda=s.nextLine();  // wybranie programu dla ktorego ma zostac utworzony proces 
			  UtworzProces(komenda);
		  }

		  case 2:  //wyswietl pamiec
		  {
			  pamiec.WyswietlPamiec();
		  }
		  
		  case 10:  //wyswietl RAM
		  {
			  pamiec.WyswietlRAM();
		  }
		  
		  case 3:  //wyswietl dysk
		  {
			  driver.driver_show();
		  }
		  
		  case 4: //wyswietl tablice FAT
		  {
			  driver.fat_show();
		  }
		  
		  case 5: //wyswietl katalogi
		  {
			  driver.catalog_show();
		  }
		  
		  case 6: //wyswietl liste procesow
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
	
	public static void menu1()
	{
		System.out.println("Opcje /n");
		System.out.println("1. Otworz program /n");
		System.out.println("2. Wyswietl pamiec /n");
		System.out.println("3. Wyswietl dysk /n");
		System.out.println("4. Wyswietl tablice FAT /n");
		System.out.println("5. Wyswietl liste procesow /n");
	}
	public static void menu2()
	{
		System.out.println(" 1. Prog1 /n");
		System.out.println(" 2. Prog2 /n");
		System.out.println(" 3. Prog3 /n");
	}
}