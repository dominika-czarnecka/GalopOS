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
					System.out.println("[NADZ]Proces " + msg.sender.name + " został usunięty");
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
					
				case "WB":
					Pamiec.WyswietlWolZaj();
					break;
					
				case "REG":
					Processor.show_register_processor();
					break;
					
				case "SHUTDOWN":
					break;
					
				case "":
					Processor.run_proc();
					break;

				default:
					System.out.println("[NADZ]'"+ input + "'" + " nieznana komenda");
					break;
				}
				break;

			case 2:
				switch (komenda[0]) {
				case "CREATE":{
					System.out.println("[NADZ]Podaj zawartosc pliku:"); String zawartosc=s.nextLine();
					driver.create(komenda[1], zawartosc);
					break;}
				
				case "DELETE":
					if (driver.delete(komenda[1])){
						System.out.println("[NADZ]Pomyślnie usunięto plik.");
					} else {
						System.out.println("[NADZ]Blad usuwania pliku");
					}
					break;

				case "EDIT":
					if(driver.find_file(komenda[1]))
					{
						System.out.println("[NADZ]dodatkowa zawartosc:"); String zawartosc=s.nextLine();
						driver.edit(komenda[1], zawartosc);
					}
					else System.out.println("[NADZ]Plik o podanej nazwie nie istnieje");
					break;

				case "READ":
					if(driver.find_file(komenda[1]))
					{
						System.out.println(driver.get_file(komenda[1]).name);
						System.out.println(driver.read(komenda[1]));
					}
					else System.out.println("[NADZ]Plik o podanej nazwie nie istnieje");
					break;
					
				case "CRPROC":
					USERPROG(komenda[1]);
					break;
					
				default:
					System.out.println("[NADZ]'"+ input + "'" + " nieznana komenda");
				}
				break;

			default:
				System.out.println("[NADZ]'"+ input + "'" + " niewlasciwa ilosc argumentow");
			}
		} while(!komenda[0].equals("SHUTDOWN"));
		System.out.println("\n");
		String shutdown="Mozesz teraz bezpiecznie wylaczyc komputer.";
		char[]shtdwn=shutdown.toCharArray();
		for(int i=0;i<shutdown.length();i++)
		System.out.printf("%-2s", shtdwn[i]);
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
			
			ZarzProc.findProcess("*IBSUP").blocked = true;
			ZarzProc.findProcess("*IN").blocked = true;
			ZarzProc.findProcess("*OUT").blocked = true;
			Processor.set_to_run();

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
	public static boolean USERPROG(String nazwa)
	{
		if(driver.find_file(nazwa))
		{
			String kod = driver.read(nazwa);
	//		System.out.println(kod);  //////// do mojej zmiennej kod funkcja dysku przypisuje nulla
			String[] komendy = kod.split("\n", 2);
			System.out.println("[NADZ]Odczytywanie karty $JOB: " + komendy[0]); // $JOB/
			int p = SprawdzJOB(komendy[0]);
			if(p==-1)
			{

				System.out.println("[NADZ]Błędny program");
				return false;
			}
			else 
			{
				try 
				{
					ZarzProc.createProcess(nazwa, p);

				} catch (procCreationError e){ 

					System.out.println("[NADZ]Nie mozna utworzyc procesu.");
					return false;
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
				return false;
			}

		}
		else
		{

			System.out.println("[NADZ]Nie ma takiego programu na dysku");
			return false;
		}
		return true;
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
