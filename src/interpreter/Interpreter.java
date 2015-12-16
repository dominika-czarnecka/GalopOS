package interpreter;
import java.util.InputMismatchException;

import Nadzorca.Nadzorca;
import modul1.*;
import modul2.Pamiec;
import modul3.Message;
import modul3.ZarzProc;
import modul4.*;

public class Interpreter{

	static hdd_commander driver;

	public Interpreter(hdd_commander driver,Pamiec pamiec)
	{
		this.driver=driver;
	}

	public static void Task() throws Exception{
		
		String rozkaz=Pamiec.WczytajRozkaz(Processor.RUNNING.name,Processor.reg.IP );
		System.out.println("[Int]Rozkaz:" + rozkaz.replaceAll("\n", "\\n"));
		
		String[] line= rozkaz.split(" ");
		Boolean dontIncIP=false;

		switch(line.length) {
		
		case 1:
			switch(line[0]){
			case "HLT":	
				if(Processor.RUNNING.name=="prog3" && ZarzProc.findProcess("prog1")!=null){
						dontIncIP=true;
				}
				else
				{
				ZarzProc.notifySup(Processor.RUNNING);	
				}
				break;
			default:
				System.out.println("[Int]Podano bledna komende");
				
				break;
			}
			break;		

		case 2:
			switch(line[0]){
			
			case "RM":
				Message temp=ZarzProc.readMessage(Processor.RUNNING);
				if(temp!=null){
				
				switch(line[1]){
				case "A":
				Processor.reg.A=Integer.parseInt(temp.content);
				 break;
				case "B":
					Processor.reg.B=Integer.parseInt(temp.content);
					 break;
				case "C":
					Processor.reg.C=Integer.parseInt(temp.content);
					 break;
				}
				}
				else dontIncIP=true;
				break;
				
			case "PR":			
				if(Nadzorca.USERPROG(line[1])==false){
					System.out.println("[Int]Nie mozna utworzyc podprocesu.");
					ZarzProc.notifySup(Processor.RUNNING);	
				}
				break;

			case "INR":
				switch(line[1]){
				case "A":
					Processor.reg.A+=1;
					break;
				case "B":
					Processor.reg.B+=1;
					break;
				case "C":
					Processor.reg.C+=1;
					break;
				default:
					System.out.println("[Int]Podano bledna komende");
				}
				break;			

			case "IN":
				System.out.print("[Int]Podaj liczbe calkowita: ");
				switch(line[1]){
				case "A":
					try{
						Processor.reg.A=Nadzorca.s.nextInt();}
					catch(InputMismatchException e) {
						System.out.println("[Int]Nie podano liczby");
					}
					break;
				case "B":
					try{
						Processor.reg.B=Nadzorca.s.nextInt();}
					catch(InputMismatchException e) {
						System.out.println("[Int]Nie podano liczby");
					}
					break;
				case "C":
					try{
						Processor.reg.C=Nadzorca.s.nextInt();}
					catch(InputMismatchException e) {
						System.out.println("[Int]Nie podano liczby");
					}
					break;
				default:
					System.out.println("[Int]Podano bledna komende");
				}
				break;

			case "OUT":
				switch(line[1]){
				case "A":
					System.out.println("[Int]"+ Processor.reg.A);
					break;
				case "B":
					System.out.println("[Int]"+ Processor.reg.B);
					break;
				case "C":
					System.out.println("[Int]"+ Processor.reg.C);
					break;
				default:
					System.out.println("[Int]Podano bledna komende");
				}
				break;

			case "DCR":
				switch(line[1]){
				case "A":
					Processor.reg.A--;
					if(Processor.reg.A==0) Processor.reg.Z=true;
					break;
				case "B":
					Processor.reg.B--;
					if(Processor.reg.B==0) Processor.reg.Z=true;
					break;
				case "C":
					Processor.reg.C--;
					if(Processor.reg.C==0) Processor.reg.Z=true;
					break;
				default:
					System.out.println("[Int]Podano bledna komende");
				}
				break;

			case "JNZ":
				if(!Processor.reg.Z){
					Processor.reg.IP=Integer.parseInt(line[1]);
					dontIncIP=true;
				}
				break;

			case "JZ":
				if(Processor.reg.Z){
					Processor.reg.IP=Integer.parseInt(line[1]);					
					dontIncIP=true;
				}
				break;

			case "JS":
				if(Processor.reg.S){
					Processor.reg.IP=Integer.parseInt(line[1]);					
					dontIncIP=true;
				}
				break;

			case "JNS":
				if(!Processor.reg.S){
					Processor.reg.IP=Integer.parseInt(line[1]);					
					dontIncIP=true;
				}
				break;

			default:
				System.out.println("[Int]Podano bledna komende");	
			}
			break;

		case 3:
			switch(line[0]){

			case "SM":
				if(ZarzProc.findProcess(line[1])!=null){
				switch(line[2]){
				case "A":
					ZarzProc.sendMessage(Processor.RUNNING, ZarzProc.findProcess(line[1]),
							Integer.toString(Processor.reg.A));
					break;
				case "B":
					ZarzProc.sendMessage(Processor.RUNNING, ZarzProc.findProcess(line[1]),
							Integer.toString(Processor.reg.B));
					break;
				case "C":
					ZarzProc.sendMessage(Processor.RUNNING, ZarzProc.findProcess(line[1]),
							Integer.toString(Processor.reg.C));
					break;
				}
				}
				break;
			
			case "CMP":
				int a=0,b=0,c;
				switch(line[1]){
				case "A":
					a=Processor.reg.A;
					break;
				case "B":
					a=Processor.reg.B;
					break;
				case "C":
					a=Processor.reg.C;
					break;
				}
				
				switch(line[2]){
				case "A":
					b=Processor.reg.A;
					break;
				case "B":
					b=Processor.reg.B;
					break;
				case "C":
					b=Processor.reg.C;
					break;
				}
				
				c=a-b;
				if(c<0) Processor.reg.S=true;			
				break;

			case "WF":
				String plikw=line[2];				
				switch(line[1]){
				case "A":		
					driver.create(plikw,Processor.reg.A+"\n");
					break;
				case "B":
					driver.create(plikw,Processor.reg.B+"\n");
					break;
				case "C":
					driver.create(plikw,Processor.reg.C+"\n");
					break;
				default:
					driver.create(plikw,line[1]);
				}
				break;

			case "RF":
				String plikr=line[2]; 
				String read = driver.read(plikr);
				if (read == null) ZarzProc.notifySup(Processor.RUNNING);
				else
				{
					try{
				switch(line[1]){
				case "A":
					Processor.reg.A=Integer.parseInt(read);
					break;
				case "B":
					Processor.reg.B=Integer.parseInt(read);
					break;
				case "C":
					Processor.reg.C=Integer.parseInt(read);
					break;					
				default:
					System.out.println("[Int]Podano bledna komende");	
				}}catch(NumberFormatException e){System.out.println("[Int]Blad odczytu liczby z pliku"); ZarzProc.notifySup(Processor.RUNNING);}
				}
				break; 
				
			case "MVI":
				switch(line[1]){
				case "A":
					Processor.reg.A=Integer.parseInt(line[2]);
					break;
				case "B":
					Processor.reg.B=Integer.parseInt(line[2]);
					break;
				case "C":
					Processor.reg.C=Integer.parseInt(line[2]);
					break;
				default:
					System.out.println("[Int]Podano bledna komende.");
				}
				break;

			case "ADD":
				switch(line[1]){
				case "A":
					switch(line[2]){
					case "B":
						Processor.reg.A+=Processor.reg.B;
						break;
					case "C":
						Processor.reg.A+=Processor.reg.C;
						break;
					default:
						System.out.println("[Int]Podano bledna komende");
					}
					break;

				case "B":
					switch(line[2]){
					case "A":
						Processor.reg.B+=Processor.reg.A;
						break;						
					case "C":
						Processor.reg.B+=Processor.reg.C;
						break;
					default:
						System.out.println("[Int]Podano bledna komende");
					}
					break;

				case "C":
					switch(line[2]){
					case "A":
						Processor.reg.C+=Processor.reg.A;
						break;						
					case "B":
						Processor.reg.C+=Processor.reg.B;
						break;	
					default:
						System.out.println("[Int]Podano bledna komende");
					}
					break;
				default:
					System.out.println("[Int]Podano bledna komende");
				}
				break;

			case "MOD2":
				switch(line[1]){
				case "A":
					switch(line[2]){
					case "B":
						Processor.reg.A=Processor.reg.B%2;
						break;						
					case "C":
						Processor.reg.A=Processor.reg.C%2;
						break;	
					default:
						System.out.println("[Int]Podano bledna komende");
					}
					if(Processor.reg.A==0) Processor.reg.Z=true;
					break;

				case "B":
					switch(line[2]){
					case "A":
						Processor.reg.B=Processor.reg.A%2;
						break;
					case "C":
						Processor.reg.B=Processor.reg.C%2;
						break;	
					default:
						System.out.println("[Int]Podano bledna komende");
					}
					if(Processor.reg.B==0) Processor.reg.Z=true;
					break;

				case "C":
					switch(line[2]){
					case "A":
						Processor.reg.C=Processor.reg.A%2;
						break;
					case "B":
						Processor.reg.C=Processor.reg.B%2;
						break;
					default:
						System.out.println("[Int]Podano bledna komende");
					}
					if(Processor.reg.C==0) Processor.reg.Z=true;
					break;
				default:
					System.out.println("[Int]Podano bledna komende");
				}
			}	
			break;

		default:
			System.out.println("[Int]Bledna dlugosc rozkazu");
		}
		
		if(!dontIncIP) Processor.reg.IP+=rozkaz.length()+1;
		}
}
