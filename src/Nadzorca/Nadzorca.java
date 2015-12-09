package Nadzorca;

import modul1.*;
import modul2.*;
import java.util.*;

import modul1.PCB;

public class Nadzorca {
	
	static Processor Procesor = new Processor();
	
	public static void IPLRTN()
	{
				
	PCB iplrtn = new PCB("*iplrtn",0);
	Procesor.RUNNING=iplrtn;
	Procesor.next_try=iplrtn;
	Pamiec pamiec = new Pamiec();	
		
	
	//zawiadowca.nexttry=iplrtn;


	uruchomienie_procesu(ibsup);  // chyba tomek ma tak¹ funkcjê
	ibsup.first =ibsup;	
	//zawiadowca.nexttry = ibsup
	Processor.XPER();   // fukcja steruj¹ca systemem od zawiadowcy 
	}
	
	public static void uruchomienie_procesu(PCB pcb)
	{
		System.out.println("/n");
		pcb.blocked = false;
		System.out.println("Proces: " + pcb.name + "zosta³ uruchomiony");
	}
	
}