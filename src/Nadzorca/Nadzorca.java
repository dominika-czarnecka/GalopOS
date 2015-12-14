package Nadzorca;

import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import java.util.*;

import interpreter.Interpreter;



public class Nadzorca 
{
    public static List<Message> IBSUPmsg = new ArrayList<Message>();
    
    //////////
static Scanner s = new Scanner(System.in);	
	
	static String komenda;
	

	public static String type_cmd()
	{
		System.out.print("[User]: ");
		String cmd=s.nextLine();   //wybranie opcji z menu
		return cmd;
	}
	public static void list_cmd()
	{
		System.out.println(
		"CREATE		tworzy nowy plik.\n"
	+	"DELETE		usuwa plik.\n"
	+	"EDIT		edytuj plik.\n"
	+	"READ		czytaj plik.\n"
	+	"DIR		wyswietla liste plików.\n"
	+	"FAT		wyswietla tablice FAT.\n"
	+	"CHDSK		wyswietla ile jest wolnego miejsca.\n"
	+	"PNDSK		wyswietla dysk.\n"
	+   "CRPROC		tworzy nowy proces.\n"
	+	"PROC		wyswietla liste procesow.\n"
	+	"CHMEM		wyswietla pamiec.\n"
	+	"HELP		wyswietla liste komend.\n"
	+   "SHUTDOWN	zakonczenie pracy.\n");
	}
		
	public static void run_cmd()
	{
		list_cmd();
do {
	komenda=type_cmd();
	switch(komenda.toUpperCase())
	{
	case "CREATE":
	{
		System.out.print("Podaj nazwe pliku, ktory ma byc utworzony: "); String nazwa=s.nextLine();
		System.out.println("Podaj zawartosc pliku:"); String zawartosc=s.nextLine();
		driver.create(nazwa, zawartosc);
		break;
	}
	case "DELETE":
	{
		System.out.print("Podaj nazwe pliku, ktory ma byc usuniety: "); String nazwa=s.nextLine();
		driver.delete(nazwa);
		break;
	}
	case "EDIT":
	{
		System.out.print("Podaj nazwe pliku, ktory ma byc edytowany: "); String nazwa=s.nextLine();
		System.out.println("dodatkowa zawartosc:"); String zawartosc=s.nextLine();
		driver.edit(nazwa, zawartosc);
		break;
	}
	case "READ":
	{
		System.out.print("Podaj nazwe pliku, ktory chcesz otworzyc: "); String nazwa=s.nextLine();
		if(driver.get_file(nazwa)!=null)
		{
		System.out.println(driver.get_file(nazwa).name);
		System.out.println(driver.read(nazwa));
		}
		else System.out.println("Plik o podanej nazwie nie istnieje");
		break;
	}
	case "DIR":
	{
		driver.catalog_show();
		break;
	}
	case "FAT":
	{
		driver.fat_show();
		break;
	}
	case "CHDSK":
	{
		int free_blocks=driver.count_free_space();
		int size_driver=driver.size_block*driver.number_blocks;
		System.out.println("Na dysku jest " + (free_blocks*driver.size_block) + " wolnych bajtow.\n" + (size_driver-free_blocks) + "bajtow jest zajetych.");
		break;
	}
	case "PNDSK":
	{
		driver.driver_show();
		break;
	}
	case "CRPROC":
	{
		break;
	}
	case "PROC":
	{
		break;
	}
	case "CHMEM":
	{
		break;
	}
	case "HELP":
	{
		list_cmd();
		break;
	}
	case "":
	{
		 //Interpreter.Rozkaz("INR A");
		 break;
	}
	default:
	{
		  System.out.println("'"+ komenda + "'" + " nieznana komenda");
		  break;
	}
	}
	
} while(komenda != "SHUTDOWN");

	}
    /////////
    
	public static Boolean end = false;
	//static Processor Procesor = new Processor();
	static hdd_commander driver;
	static Pamiec pamiec;
	public Nadzorca(hdd_commander driver,Pamiec pamiec)
	{
		this.driver=driver;
		this.pamiec=pamiec;
	}
	
	
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
		Scanner s = new Scanner(System.in);	    

		while(true)
		{
			//if(end == true)
			//{				
				while(!IBSUPmsg.isEmpty())
				{Message msg = IBSUPmsg.remove(0);
				if(msg.content.equals("stopped"))
				{
					try {
						ZarzProc.removeProcess(msg.sender.name);
						} catch (procNotFoundError e) 
							{
							e.printStackTrace();
							}
			//end = false;
				}
			
			menu1();
			String komenda=s.nextLine();   //wybranie opcji z menu
			int wybor = Integer.parseInt(komenda);
		
		
		switch (wybor) 
		{
		  case 1:   //utworz proces 
		  {
			  menu2();
			  komenda=s.nextLine();  // wybranie programu dla ktorego ma zostac utworzony proces 
			  USERPROG(komenda);
			  break;
		  }

		  case 2:  //wyswietl pamiec
		  {
			  Pamiec.Wyswietl();
			  break;
		  }
		  
		  case 10:  //wyswietl RAM
		  {
			  Pamiec.WyswietlRAM();
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
		  
		  case 7: //Takt Procesora
		  {			  
			  try {
				Interpreter.Task();
			} catch (Exception e) {				
				e.printStackTrace();
			}
			  break;
		  }		  
		  
		  default:
		    System.out.println("Wybrano nieznana opcje");
		    break;
		}
		  
				}		
	}
		}
	//Tworzenie procesu USERPROG
	public static void USERPROG(String komenda)
	{	String nazwa=null;
		if(komenda=="1"){nazwa="prog1";}
		if(komenda=="2"){nazwa="prog2";}
		if(komenda=="3"){nazwa="prog3";}
		if(nazwa=="prog1" || nazwa=="prog2" || nazwa=="prog3")
		{
			char[] tab = new char[32];
			tab = driver.read(nazwa, 32);  //funkcja ktďż˝ra zwrďż˝ci mi karte $JOB
			String kartaJOB = String.valueOf(tab);
			String[] wynik1 = null;
			wynik1 = kartaJOB.split(",");
			if(wynik1[1]=="$JOB");
			{
				int pamiec = Integer.parseInt(wynik1[2]);
				try 
				{
					ZarzProc.createProcess(nazwa, pamiec);
				} catch (procCreationError e) 
					{
						e.printStackTrace();
					}
			
				char[] kod = driver.read(nazwa);
				char[] dane = new char[pamiec+1];
				
				for(int i=33; i<=pamiec; i++)
				{
					dane[i-33] = kod[i];
				}
				
				String DoPamieci = String.valueOf(dane);
				Pamiec.ZapiszDoPamieci(nazwa,DoPamieci);
				
				try 
				{
					ZarzProc.startProcess(nazwa);
				} catch (procNotFoundError e) 
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
		System.out.println("5. Wyswietl katalogi /n");
		System.out.println("6. Wyswietl liste procesow /n");
		System.out.println("7. Wykonaj krok procesora /n");		
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
