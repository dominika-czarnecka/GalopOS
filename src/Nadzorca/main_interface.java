package Nadzorca;
import interpreter.*;
import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;
import Nadzorca.*;

import java.util.Scanner;

public class main_interface 
{
	static Scanner s = new Scanner(System.in);	
	public static void start_print()
	{
		System.out.println("Prima [Version 3.0.607]\n(c) 2015 Wszelkie prawa zastrzezone.");
	}
	public static void set_cmd()
	{
		System.out.print("[User]: ");
	}
	public static void list_cmd()
	{
		System.out.println(
		"CREATE		tworzy nowy plik."
	+	"DELETE		usuwa plik."
	+	"EDIT		edytuj plik."
	+	"READ		czytaj plik."
	+	"DIR		wyswietla liste plików."
	+	"FAT		wyswietla tablice FAT."
	+	"CHDSK		wyswietla ile jest wolnego miejsca."
	+	"PNDSK		wyswietla dysk."
	+   "CRPROC		tworzy nowy proces."
	+	"PROC		wyswietla liste procesow."
	+	"CHMEM		wyswietla pamiec."
	+	"HELP		wyswietla liste komend."
	+   "SHUTDOWN	zakonczenie pracy.");
	}
	public static String type_cmd()
	{
		set_cmd();
		String cmd=s.nextLine();   //wybranie opcji z menu
		cmd.toUpperCase();
		return cmd;
	}
	
	public static void run_cmd()
	{
		list_cmd();
do {
	switch(type_cmd())
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
		 Interpreter.Rozkaz("INR A");
		 break;
	}
	default:
	{
		  System.out.println("Wybrano nieznana opcje");
		  break;
	}
	}
	
} while(type_cmd() != "SHUTDOWN");

	}
}


