package Nadzorca;

import modul1.*;
import java.util.*;

import modul1.PCB;

public class Nadzorca {
	
	static Processor Processor = new Processor();
	
	public static void IPLRTN()
	{
		
		
	PCB iplrtn = new PCB("*IPRTLN",0);
		
	//zawiadowca.nexttry=iplrtn;
	//zawiadowca.nexttry=iplrtn;

	PCB ibsup = new PCB("*IBSUP", 0);

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