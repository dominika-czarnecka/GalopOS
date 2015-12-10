package interpreter;
import modul1.*;
import modul2.Pamiec;
import modul4.*;
//import java.awt.EventQueue;
import java.io.FileNotFoundException;

public class Interpreter{

	static hdd_commander driver;
	static Pamiec pamiec;
	
	public Interpreter(hdd_commander driver,Pamiec pamiec)
	{
		this.driver=driver;
		this.pamiec=pamiec;
	}
	
	//private Scanner scanner;
	//private PrintWriter writer;	
	
	public static void Rozkaz(String text){
		String output = "";
		String[] buffor= text.split("\n");
		
	int i=Processor.counter;
				buffor[i]=buffor[i].trim();
				try {
					task(buffor[i],buffor);
				} catch (FileNotFoundException e) {
					System.out.println("File not found! :( ");
				} catch (Exception e) {
					if(e.getMessage().equals("HALT")){
						output = "Completed :)";
					}else{
						i = Integer.parseInt(e.getMessage());
					}
				}		
		Processor.counter++;
		output = "A: " + Processor.reg.A + "\nB: " + Processor.reg.B + "\nC: " + Processor.reg.C + "\nLR:" + Processor.RUNNING.Line + "\n" + output;
		System.out.println(output);
	}
	
	private static void task(String taskText, String[] buffor) throws Exception{
		String[] linia= taskText.split(" ");
		
		switch(linia.length){
		
			case 1:{
				switch(linia[0]){
					case "HLT":{
						
						//
						
						throw new Exception("HALT");
					}
				/*	
				    case "CFR":{
						scanner.close();
						break;
					}
					case "CFW":{
						System.out.println("close\n");
						writer.flush();
						writer.close();
						break;
					}
					*/
				}
				break;
				
			}
			
			case 2:{
				switch(linia[0]){
				
					case "INR":{
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
						}
						break;
					}
				
					case "DCR":{
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
						}
						break;
					}
				
				case "JNZ":{
					if(Processor.reg.C!=0){
						int temp=Integer.parseInt(linia[1]) - 2;
						throw new Exception(temp+"");
						//task(buffor[temp-1], buffor);
					}
					break;
				}
			
				case "JZ":{
					if(Processor.reg.C==0){
						int temp=Integer.parseInt(linia[1]) - 2;
						throw new Exception(temp+"");
						//task(buffor[temp-1], buffor);
					}
					break;
				}
			/*
				case "OFR":{
					File plik = new File(linia[1]);
					scanner = new Scanner(plik);
					break;
					}
			
				case "OFW":{
					writer = new PrintWriter(linia[1]);
					
					break;
					}
					*/
				case "WF":{
					switch(linia[1]){
					case "A":{		
						driver.edit("prog2dane",Processor.reg.A+"\n");
						break;
					}
					case "B":{
						driver.edit("prog2dane",Processor.reg.B+"\n");
						break;
					}
					case "C":{
						driver.edit("prog2dane",Processor.reg.C+"\n");
						break;
					}
					
					default:{
						String temp = linia[1].replace("_", " "); 
						System.out.println(temp);
					}
				}
					
					break;
				}
				
				case "RF":{// na razie niesprawne, problem z odczytem jednej liczby
					switch(linia[1]){
					case "A":{		
						driver.read("prog2dane");
						break;
					}
					case "B":{
						driver.read("prog2dane");
						break;
					}
					case "C":{
						driver.read("prog2dane");
						break;
					}
					}
				}
					break;
				}
				break;
			}
			
			case 3:{
				switch(linia[0]){
					case "MVI":{
						switch(linia[1]){
							case "A":{
								Processor.reg.A=Integer.parseInt(linia[2]);
								break;}
							case "B":{
								Processor.reg.B=Integer.parseInt(linia[2]);
								break;}
							case "C":{
								Processor.reg.C=Integer.parseInt(linia[2]);
								break;}
						}
						break;
					}
					case "ADD":{
						switch(linia[1]){
							case "A":{
								switch(linia[2]){
									case "B":{
										Processor.reg.A+=Processor.reg.B;
										break;
									}
									case "C":{
										Processor.reg.A+=Processor.reg.C;
										break;
									}
								}
								break;
							}
							case "B":{
								switch(linia[2]){
								case "A":{
									Processor.reg.B+=Processor.reg.A;
									break;
								}
								case "C":{
									Processor.reg.B+=Processor.reg.C;
									break;
								}
							}
								break;
							}
							case "C":{
								switch(linia[2]){
								case "A":{
									Processor.reg.C+=Processor.reg.A;
									break;
								}
								case "B":{
									Processor.reg.C+=Processor.reg.B;
									break;
								}
							}
							break;
							}
						}
						break;
					}
				
					case "MOD2":{
						switch(linia[1]){
							case "A":{
								switch(linia[2]){
									case "B":{
										Processor.reg.A=Processor.reg.B%2;
										break;
									}
									case "C":{
										Processor.reg.A=Processor.reg.C%2;
										break;
									}									
								}
								break;
							}
							case "B":{
								switch(linia[2]){
									case "A":{
										Processor.reg.B=Processor.reg.A%2;
										break;
									}
									case "C":{
										Processor.reg.B=Processor.reg.C%2;
										break;
									}									
								}
								break;
							}
							case "C":{
								switch(linia[2]){
									case "A":{
										Processor.reg.C=Processor.reg.A%2;
										break;
									}
									case "B":{
										Processor.reg.C=Processor.reg.B%2;
										break;
									}	
								}
								break;
							}
						}
					}		
				}
				break;
			}
		}
	}
	
}
/*
Queue<String>komendy=new LinkedList<String>();

void odczytaj_komende()
{
String komenda;

int index=0;
while(plik_pamieci[index]!=-1; // kod pustego miejsca
	while(plik_pamiec[i]!=10) // kod ascii entera
	{// budowanie stringa
	komenda+=(char)plik_pamieci[index].toString(); // w kazdym razie rzutowanie na string
	index++;
	}
index++; //drugi raz zeby "ominac" ENTER
komendy.offer(komenda);
}

}
/////dopoki kolejka nie jest pusta zi¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹¹
String pobierz_komende(){return komendy.poll();} */
