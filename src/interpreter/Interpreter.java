package interpreter;
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

			case "DCR":
				switch(line[1]){
				case "A":
					Processor.reg.A--;
					break;
				case "B":
					Processor.reg.B--;
					break;
				case "C":
					Processor.reg.C--;
					break;
				default:
					System.out.println("Podano bledna komende");
				}		

			case "JNZ":
				if(Processor.reg.C!=0){
					Processor.reg.IP=Integer.parseInt(line[1]);
					jumped=true;
				}
				break;

			case "JZ":
				if(Processor.reg.C==0){
					Processor.reg.IP=Integer.parseInt(line[1]);					
					jumped=true;
				}
				break;

			case "WF":
				switch(line[1]){
				case "A":		
					driver.edit("prog2dane",Processor.reg.A+"\n");
					break;
				case "B":
					driver.edit("prog2dane",Processor.reg.B+"\n");
					break;
				case "C":
					driver.edit("prog2dane",Processor.reg.C+"\n");
					break;
				default:
					System.out.println("Podano bledna komende");
				}
				break;
			default:
				System.out.println("Podano bledna komende");	
			}
			break;
			
		case 3:
			switch(line[0]){
			case "RF":// na razie niesprawne, problem z odczytem jednej liczby
				String plik=line[2]; 
				switch(line[1]){
				case "A":
					Processor.reg.A=Integer.parseInt(driver.read(plik));
					break;
				case "B":
					Processor.reg.A=Integer.parseInt(driver.read(plik));
					break;
				case "C":
					Processor.reg.A=Integer.parseInt(driver.read(plik));
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
