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
		while(true)
		{
		menu1();
		komenda=s.nextLine();   //wybranie opcji z menu
		int wybor = Integer.parseInt(komenda);
		
		
		switch (wybor) 
		{
		  case 1:   //utworz proces 
		  {
			  menu2();
			  komenda=s.nextLine();  // wybranie programu dla ktorego ma zostac utworzony proces 
			  UtworzProces(komenda);
			  break;
		  }

		  case 2:  //wyswietl pamiec
		  {
			  pamiec.WyswietlPamiec();
			  break;
		  }
		  
		  case 10:  //wyswietl RAM
		  {
			  pamiec.WyswietlRAM();
			  break;
		  }
		  
		  case 3:  //wyswietl dysk
		  {
			  driver.driver_show();
			  break;
		  }
		  
		  case 4: //wyswietl tablice FAT
		  {
			  driver.fat_show();
			  break;
		  }
		  
		  case 5: //wyswietl katalogi
		  {
			  driver.catalog_show();
			  break;
		  }
		  
		  case 6: //wyswietl liste procesow
		  {
			  ZarzProc.printProcessList();
			  break;
		  }		  
		  
		  
		  default:
		    System.out.println("Wybrano nieznana opcje");
		    break;
		}
		  
		}		
	}
	//Tworzenie procesu USERPROG
	public static void UtworzProces(String komenda)
	{	String program=null;
		if(komenda=="1"){program="prog1";}
		if(komenda=="2"){program="prog2";}
		if(komenda=="3"){program="prog3";}
		
		if(program=="prog1" || program=="prog2" || program=="program3")
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
					ZarzProc.createProcess(program, Pamiec);
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
		System.out.println("1. Prog1 /n");
		System.out.println("2. Prog2 /n");
		System.out.println("3. Prog3 /n");
	}
	public static void start()
	{
		IPLRTN();
		IBSUP();
	}
}
