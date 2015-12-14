package Nadzorca;

import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import interpreter.Interpreter;



public class Nadzorca 
{
	public static List<Message> IBSUPmsg = new ArrayList<Message>();

	//////////
	static Scanner s = new Scanner(System.in);	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
						+   "PROCB      wyswietla liste procesow od tylu"
						+	"CHMEM		wyswietla pamiec.\n"
						+	"HELP		wyswietla liste komend.\n"
						+   "SHUTDOWN	zakonczenie pracy.\n");
	}

	public static void StanProcesu()  //Sprawdzanie stanu listy wiadomosci, jesli cos jest to usuwa proces
	{
		while(!IBSUPmsg.isEmpty())
		{
		Message msg = IBSUPmsg.remove(0);
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
		}
	}
	
	public static void IBSUP()
	{
		list_cmd();
		do {
			StanProcesu();
			
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
				if(driver.get_file(nazwa)!=null)
				{
					System.out.println("dodatkowa zawartosc:"); String zawartosc=s.nextLine();
					driver.edit(nazwa, zawartosc);
				}
				else System.out.println("Plik o podanej nazwie nie istnieje");
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
				System.out.println("Na dysku jest " + (free_blocks*driver.size_block) + " wolnych bajtow, " + (size_driver -free_blocks*driver.size_block) + " bajtow jest zajetych.");
				break;
			}
			case "PNDSK":
			{
				driver.driver_show();
				break;
			}
			case "CRPROC": 
			{	
				System.out.print("Podaj nazwe procesu ktory ma zostac utworzony: ");
				USERPROG(s.nextLine());
				break;
			}
			case "PROC":
			{
				ZarzProc.printProcessList();
				break;
			}
			case "PROCB":
			{
				ZarzProc.printProcessListBack();
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
				try {
					Interpreter.Task();
				} catch (Exception e) {
					e.printStackTrace();
				}
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

	//Tworzenie procesu USERPROG
	public static void USERPROG(String nazwa)
	{
		if(nazwa.equals("prog1") || nazwa.equals("prog2") || nazwa.equals("prog3"))
		{
			String kod = driver.read(nazwa);
			String[] komendy = kod.split("\n");
			System.out.println(komendy[0]); // $JOB/
			int p = SprawdzJOB(komendy[0]);
			if(p==-1)
			{
				System.out.println("Błędny program");
			}
			else 
			{
				try 
				{
					ZarzProc.createProcess(nazwa, p);
				} catch (procCreationError e) 
				{
					e.printStackTrace();
				}
			}
				String DoPamieci = null;
				for(int i=1;i<komendy.length;i++)
				{
					DoPamieci+=komendy[i];
				}
			
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
	public static int SprawdzJOB(String KartaJOB)
	{
		String[] tmp;
		int pamiec;
		tmp = KartaJOB.split(",");
			if(tmp[0].equals("$JOB"))
			{
				pamiec = Integer.parseInt(tmp[1]);
				return pamiec;
			}
			else
				return -1;
	}

	public static void start()
	{
		IPLRTN();
		IBSUP();
	}
}
