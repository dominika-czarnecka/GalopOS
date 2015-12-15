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
	public static Scanner s = new Scanner(System.in);	

	static String[] komenda;
	static String input;


	public static String type_cmd()
	{
		System.out.print("[User]: ");
		String cmd=s.nextLine();   //wybranie opcji z menu
		return cmd;
	}
	public static void list_cmd()
	{
		System.out.println(
				"CREATE		\ttworzy nowy plik.\n"
						+	"DELETE	plik	\tusuwa plik.\n"
						+	"EDIT plik		edytuj plik.\n"
						+	"READ plik		czytaj plik.\n"
						+	"DIR			wyswietla liste plików.\n"
						+	"FAT			wyswietla tablice FAT.\n"
						+	"PNDSK			wyswietla dysk.\n"
						+   "CRPROC	nazwa	\ttworzy nowy proces.\n"
						+	"PROC			wyswietla liste procesow.\n"
						+   "PROCB			wyswietla liste procesow od tylu. \n"
						+	"CHMEM			wyswietla pamiec.\n"
						+   "PROCD			wyswietla szczegolowa liste procesow. \n" 			
						+	"HELP			wyswietla liste komend.\n"
						+   "SHUTDOWN		zakonczenie pracy.\n");
	}

	public static void readMessages()  //Sprawdzanie stanu listy wiadomosci, jesli cos jest to usuwa proces
	{
		while(!IBSUPmsg.isEmpty())
		{
			Message msg = IBSUPmsg.remove(0);
			if(msg.content.equals("stopped"))
			{
				try {
					ZarzProc.removeProcess(msg.sender.name);
					System.out.println("Proces " + msg.sender.name + "został usunięty");
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
			readMessages();

			input =type_cmd();
			komenda= input.split(" ");
			komenda[0] = komenda[0].toUpperCase();

			switch(komenda.length) {
			case 0:
				Processor.run_proc();
				break;
				
			case 1:
				switch(komenda[0])
				{
				case "WPISZ":
					Pamiec.RAM[4]='\n';
					break;
				
				case "DIR":
					driver.catalog_show();
					break;

				case "FAT":
					driver.fat_show();
					break;

				case "PNDSK":
					driver.driver_show();
					break;

				case "PROC":
					ZarzProc.printProcessList();
					break;

				case "PROCB":
					ZarzProc.printProcessListBack();
					break;

				case "CHMEM":
					Pamiec.WyswietlRAM();
					break;

				case "HELP":
					list_cmd();
					break;
					
				case "PROCD":
					ZarzProc.printDetailedList();
					break;

				case "":
					try {
						Interpreter.Task();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;

				default:
					System.out.println("'"+ input + "'" + " nieznana komenda");
					break;
				}
				break;

			case 2:
				switch (komenda[0]) {
				case "CREATE":{
					System.out.println("Podaj zawartosc pliku:"); String zawartosc=s.nextLine();
					driver.create(komenda[1], zawartosc);
					break;}
				
				case "DELETE":
					if (driver.delete(komenda[1])){
						System.out.println("Pomyślnie usunięto plik.");
					} else {
						System.out.println("Blad usuwania pliku");
					}
					break;

				case "EDIT":
					if(driver.find_file(komenda[1]))
					{
						System.out.println("dodatkowa zawartosc:"); String zawartosc=s.nextLine();
						driver.edit(komenda[1], zawartosc);
					}
					else System.out.println("Plik o podanej nazwie nie istnieje");
					break;

				case "READ":
					if(driver.find_file(komenda[1]))
					{
						System.out.println(driver.get_file(komenda[1]).name);
						System.out.println(driver.read(komenda[1]));
					}
					else System.out.println("Plik o podanej nazwie nie istnieje");
					break;
					
				case "CRPROC":
					USERPROG(komenda[1]);
					break;
					
				default:
					System.out.println("'"+ input + "'" + " nieznana komenda");
				}
				break;

			default:
				System.out.println("'"+ input + "'" + " niewlasciwa ilosc argumentow");
			}
		} while(komenda[0] != "SHUTDOWN");

	}

	static hdd_commander driver;
	static Pamiec pamiec;
	public Nadzorca(hdd_commander driver,Pamiec pamiec)
	{
		this.driver=driver;
		this.pamiec=pamiec;
	}

	public static void IPLRTN()
	{
		try {
			
			Processor.next_try=ZarzProc.createProcess("*IBSUP", 0);
			ZarzProc.createProcess("*IN", 0);
			ZarzProc.createProcess("*OUT", 0);

		} catch (procCreationError e1){

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
		//	Processor.XPER();
	}

	//Tworzenie procesu USERPROG
	public static void USERPROG(String nazwa)
	{
		if(driver.find_file(nazwa))
		{
			String kod = driver.read(nazwa);
	//		System.out.println(kod);  //////// do mojej zmiennej kod funkcja dysku przypisuje nulla
			String[] komendy = kod.split("\n", 2);
			System.out.println("Odczytywanie karty $JOB: " + komendy[0]); // $JOB/
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

				} catch (procCreationError e){ 
						e.printStackTrace();
				}
			}
			String DoPamieci = komendy[1];
			for(int i=2;i<komendy.length;i++)
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
		else
		{
			System.out.println("Nie ma takiego programu na dysku");
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

}
