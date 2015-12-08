package Nadzorca;


import java.util.*;

public class Nadzorca {
	
	public static void IPLRTN()
	{
	PCB iplrtn = new PCB("*IPRTLN",0);
	iplrtn.next = iplrtn;
	iplrtn.prev = iplrtn;
	iplrtn.first = iplrtn;
	iplrtn.stopped = false;	
	//zawiadowca.nexttry=iplrtn;
	//zawiadowca.nexttry=iplrtn;
	
	PCB ibsup = new PCB("*IBSUP", 0);
	ibsup.name="*IBSUP";
	uruchomienie_procesu(ibsup);  // chyba tomek ma tak¹ funkcjê
	ibsup.blocked = false;
	ibsup.blocked = false;
	iplrtn.blocked = true;
	ibsup.first =ibsup;
	//zawiadowca.nexttry = ibsup
	//XPER();    fukcja steruj¹ca systemem od zawiadowcy 
	}
	
	public static void uruchomienie_procesu(PCB pcb)
	{
		System.out.println("/n");
		pcb.blocked = false;
		System.out.println("Proces: " + pcb.name + "zosta³ uruchomiony");
	}
	
}