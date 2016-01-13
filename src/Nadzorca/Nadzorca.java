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
		System.out.print("[Uzytkownik]: ");
		String cmd=s.nextLine();   //wybranie opcji z menu
		return cmd;
	}
	public static void list_cmd()
	{
		System.out.println(
				"\nCREATE nazwa		tworzy nowy plik.\n"
						+	"DELETE	nazwa	\tusuwa plik.\n"
						+	"EDIT nazwa		edytuj plik.\n"
						+	"READ nazwa		czytaj plik.\n"
						+	"DIR			wyswietla liste plików.\n"
						+	"FAT			wyswietla tablice FAT.\n"
						+	"PNDSK			wyswietla dysk.\n"
						+	"FORMAT			formatuj dysk.\n"	
						+	"REG			wyswietla stan rejestrow biezacego procesu.\n"
						+   "CRPROC	nazwa	\ttworzy nowy proces.\n"
						+	"DELPROC nazwa	\tusuwa dany proces\n"
						+	"PROC			wyswietla liste procesow.\n"
						+   "PROCB			wyswietla liste procesow od tylu. \n"
						+	"CHMEM			wyswietla pamiec RAM.\n"
						+	"WB			wyswietla struktury pamieci\n"
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
					System.out.println("[Nadz]Proces " + msg.sender.name + " został usunięty");
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
					
				case "FORMAT":
					driver.format();
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
					
				case "MOVE":
					Pamiec.przesunWszystko();
					break;
					
				case "SHUTDOWN":
					break;
					
				case "":
					Processor.run_proc();
					break;

				default:
					System.out.println("[Nadz]'"+ input + "'" + " nieznana komenda");
					break;
				}
				break;

			case 2:
				switch (komenda[0]) {
				case "CREATE":{
					System.out.println("[Nadz]Podaj zawartosc pliku:"); String zawartosc=s.nextLine();
					driver.create(komenda[1], zawartosc);
					break;}
				
				case "DELETE":
					driver.delete(komenda[1]);
						
					break;

				case "EDIT":
					if(driver.find_file(komenda[1]))
					{
						System.out.println("[Nadz]dodatkowa zawartosc:"); String zawartosc=s.nextLine();
						driver.edit(komenda[1], zawartosc);
					}
					else System.out.println("[Dysk]Plik o podanej nazwie nie istnieje");
					break;

				case "READ":
					if(driver.find_file(komenda[1]))
					{
						System.out.println(driver.get_file(komenda[1]).name);
						System.out.println(driver.read(komenda[1]));
					}
					else System.out.println("[Dysk]Plik o podanej nazwie nie istnieje");
					break;
					
				case "CRPROC":
					if(komenda[1].contains("*"))System.out.println("[Nadz]Znak '*' jest zarezerwowany dla procesow systemowych.");
					else {
					USERPROG(komenda[1]);
					}
					break;

				case "DELPROC":
					if(komenda[1].contains("*"))System.out.println("[Nadz]Znak '*' jest zarezerwowany dla procesow systemowych.GalopOS");
					else {
					PCB proc = ZarzProc.findProcess(komenda[1]);
					if (proc != null) ZarzProc.notifySup(proc);
					else System.out.println("[Nadz]Nie znaleziono procesu o tej nazwie");
					}
					break;
					
				default:
					System.out.println("[Nadz]'"+ input + "'" + " nieznana komenda");
				}
				break;

			default:
				System.out.println("[Nadz]'"+ input + "'" + " niewlasciwa ilosc argumentow");
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
			System.out.println("[Nadz]Odczytywanie karty $JOB: " + komendy[0]); // $JOB/
			int p = SprawdzJOB(komendy[0]);
			if(p==-1)
			{

				System.out.println("[Nadz]Błędny program");
				return false;
			}
			else 
			{
				try 
				{
					ZarzProc.createProcess(nazwa, p);

				} catch (procCreationError e){ 

					System.out.println("[Nadz]Nie mozna utworzyc procesu.");
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

			System.out.println("[Nadz]Nie ma takiego programu na dysku");
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
