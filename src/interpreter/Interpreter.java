package interpreter;
import modul1.*;
import modul2.Pamiec;
import modul4.*;

public class Interpreter{

	static hdd_commander driver;
	static Pamiec pamiec;

	public Interpreter(hdd_commander driver,Pamiec pamiec)
	{
		this.driver=driver;
		this.pamiec=pamiec;
	}

	public static void Task() throws Exception{
		String buffor="jhgf";
		String[] linia= buffor.split(" ");
		Boolean jumped=false;

		switch(linia.length){

		case 1:
			switch(linia[0]){
			case "HLT":	

				//ZarzProc.removeProcess(Processor.RUNNING.name);						
				throw new Exception("HALT");
			//break;
			default:
				System.out.println("Podano bledna komende");
				
			}
			break;		

		case 2:
			switch(linia[0]){

			case "INR":
				switch(linia[1]){
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
				switch(linia[1]){
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
					Processor.reg.IP=Integer.parseInt(linia[1]);
					jumped=true;
				}
				break;

			case "JZ":
				if(Processor.reg.C==0){
					Processor.reg.IP=Integer.parseInt(linia[1]);					
					jumped=true;
				}
				break;

			case "WF":
				switch(linia[1]){
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
					String temp = linia[1].replace("_", " "); 
					System.out.println(temp);
				}
				break;

			case "RF":// na razie niesprawne, problem z odczytem jednej liczby
				switch(linia[1]){
				case "A":
					driver.read("prog2dane");
					break;

				case "B":
					driver.read("prog2dane");
					break;

				case "C":
					driver.read("prog2dane");
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
			switch(linia[0]){
			case "MVI":
				switch(linia[1]){
				case "A":
					Processor.reg.A=Integer.parseInt(linia[2]);
					break;
				case "B":
					Processor.reg.B=Integer.parseInt(linia[2]);
					break;
				case "C":
					Processor.reg.C=Integer.parseInt(linia[2]);
					break;
				default:
					System.out.println("Podano bledna komende");
				}
				break;
			
			case "ADD":
				switch(linia[1]){
				case "A":
					switch(linia[2]){
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
					switch(linia[2]){
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
					switch(linia[2]){
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
				switch(linia[1]){
				case "A":
					switch(linia[2]){
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
					switch(linia[2]){
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
					switch(linia[2]){
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
			System.out.println("B��dna d�ugo�� rozkazu");
		}
		if(!jumped) Processor.reg.IP+=buffor.length();
		Processor.time++;
	}
}
