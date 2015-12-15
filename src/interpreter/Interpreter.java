package interpreter;
import java.util.InputMismatchException;

import Nadzorca.Nadzorca;
import modul1.*;
import modul2.Pamiec;
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
		String[] line= rozkaz.split(" ");
		Boolean jumped=false;

		switch(line.length){

		case 1:
			switch(line[0]){
			case "HLT":	
				ZarzProc.notifySup(Processor.RUNNING);				
				throw new Exception("HALT");
			//break;
			default:
				System.out.println("Podano bledna komende");	
			}
			break;		

		case 2:
			switch(line[0]){

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
					System.out.println("Podano bledna komende");
				}
				break;			

			case "IN":
				System.out.print("Podaj liczbe calkowita: ");
				switch(line[1]){
					case "A":
						try{
							Processor.reg.A=Nadzorca.s.nextInt();}
						catch(InputMismatchException e) {
						System.out.println("Nie podano liczby");
							}
					break;
					case "B":
						try{
							Processor.reg.B=Nadzorca.s.nextInt();}
						catch(InputMismatchException e) {
						System.out.println("Nie podano liczby");
							}
					break;
					case "C":
						try{
							Processor.reg.C=Nadzorca.s.nextInt();}
						catch(InputMismatchException e) {
						System.out.println("Nie podano liczby");
							}
					break;
					default:
						System.out.println("Podano bledna komende");
				}
				break;
				
			case "OUT":
				switch(line[1]){
				case "A":
					System.out.println(Processor.reg.A);
					break;
				case "B":
					System.out.println(Processor.reg.B);
					break;
				case "C":
					System.out.println(Processor.reg.C);
					break;
				default:
					System.out.println("Podano bledna komende");
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
					System.out.println("Podano bledna komende");
				}		

			case "JNZ":
				if(Processor.reg.Z==true){
					Processor.reg.IP=Integer.parseInt(line[1]);
					jumped=true;
				}
				break;

			case "JZ":
				if(Processor.reg.Z==false){
					Processor.reg.IP=Integer.parseInt(line[1]);					
					jumped=true;
				}
				break;
				
			case "JS":
				if(Processor.reg.S==false){
					Processor.reg.IP=Integer.parseInt(line[1]);					
				jumped=true;
				}
				break;
				
			case "JNS":
				if(Processor.reg.S==true){
					Processor.reg.IP=Integer.parseInt(line[1]);					
				jumped=true;
				}
				break;
				
			default:
				System.out.println("Podano bledna komende");	
			}
			break;
			
		case 3:
			switch(line[0]){
			
			case "PR":				
				Nadzorca.USERPROG(line[1]);///////////////////////////////////////				
				break;
			
			case "CMP":
				int a=Integer.parseInt(line[1]);
				int b=Integer.parseInt(line[2]);
				int c=a-b;
				if(c<0) Processor.reg.S=true;			
				break;
			
			case "WF":
				String plikw=line[2];				
				switch(line[1]){
				case "A":		
					driver.edit(plikw,Processor.reg.A+"\n");
					break;
				case "B":
					driver.edit(plikw,Processor.reg.B+"\n");
					break;
				case "C":
					driver.edit(plikw,Processor.reg.C+"\n");
					break;
				default:
					driver.edit(plikw,line[2]);
				}
				break;
				
			case "RF":// na razie niesprawne, problem z odczytem jednej liczby
				String plikr=line[2]; 
				switch(line[1]){
				case "A":
					Processor.reg.A=Integer.parseInt(driver.read(plikr));
					break;
				case "B":
					Processor.reg.A=Integer.parseInt(driver.read(plikr));
					break;
				case "C":
					Processor.reg.A=Integer.parseInt(driver.read(plikr));
					break;					
				default:
					System.out.println("Podano bledna komende");	
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
					System.out.println("Podano bledna komende");
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
						System.out.println("Podano bledna komende");
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
						System.out.println("Podano bledna komende");
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
						System.out.println("Podano bledna komende");
					}
					break;
				default:
					System.out.println("Podano bledna komende");
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
						System.out.println("Podano bledna komende");
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
						System.out.println("Podano bledna komende");
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
						System.out.println("Podano bledna komende");
					}
					if(Processor.reg.C==0) Processor.reg.Z=true;
					break;
				default:
					System.out.println("Podano bledna komende");
				}
			}	
			break;
			
		default:
			System.out.println("Bledna dlugosc rozkazu");
		}
		if(!jumped) Processor.reg.IP+=rozkaz.length();
		Processor.time++;
	}
}
