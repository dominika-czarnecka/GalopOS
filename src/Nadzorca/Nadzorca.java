package Nadzorca;


import java.util.*;

import modul1.PCB;

public class Nadzorca {
	
	public static void IPLRTN()
	{
	PCB iplrtn = new PCB("*IPRTLN",0);
		
	//zawiadowca.nexttry=iplrtn;
	//zawiadowca.nexttry=iplrtn;
	
	PCB ibsup = new PCB("*IBSUP", 0);
	ibsup.name="*IBSUP";
	uruchomienie_procesu(ibsup);  // chyba tomek ma tak� funkcj�
	ibsup.blocked = false;
	ibsup.blocked = false;
	iplrtn.blocked = true;
	ibsup.first =ibsup;
	//zawiadowca.nexttry = ibsup
	//XPER();    fukcja steruj�ca systemem od zawiadowcy 
	}
	
	public static void uruchomienie_procesu(PCB pcb)
	{
		System.out.println("/n");
		pcb.blocked = false;
		System.out.println("Proces: " + pcb.name + "zosta� uruchomiony");
	}
	
}