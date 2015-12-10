package interpreter;
import modul1.*;
import modul2.Pamiec;
import modul4.*;
//import java.awt.EventQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


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
	
	public String Rozkaz(String text){
		String output = "";
		String[] buffor= text.split("\n");
		
	int i=Processor.counter+1;
				buffor[i]=buffor[i].trim();
				try {
					task(buffor[i],buffor);
				} catch (FileNotFoundException e) {
					return "File not found! :(";
				} catch (Exception e) {
					if(e.getMessage().equals("HALT")){
						output = "Completed :)";
					}else{
						i = Integer.parseInt(e.getMessage());
					}
				}		
		Processor.counter++;
		output = "A: " + PCB.A + "\nB: " + PCB.B + "\nC: " + PCB.C + "\nLR:" + PCB.LR + "\n" + output;
		return output;
	}
	
	private void task(String taskText, String[] buffor) throws Exception{
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
							PCB.A+=1;
							break;
						case "B":
							PCB.B++;
							break;
						case "C":
							PCB.C++;
							break;
						}
						break;
					}
				
					case "DCR":{
						switch(linia[1]){
						case "A":
							PCB.A--;
							break;
						case "B":
							PCB.B--;
							break;
						case "C":
							PCB.C--;
							break;
						}
						break;
					}
				
				case "JNZ":{
					if(PCB.C!=0){
						int temp=Integer.parseInt(linia[1]) - 2;
						throw new Exception(temp+"");
						//task(buffor[temp-1], buffor);
					}
					break;
				}
			
				case "JZ":{
					if(PCB.C==0){
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
						driver.edit("prog2dane",PCB.A+"\n");
						break;
					}
					case "B":{
						driver.edit("prog2dane",PCB.B+"\n");
						break;
					}
					case "C":{
						driver.edit("prog2dane",PCB.C+"\n");
						break;
					}
					
					default:{
						String temp = linia[1].replace("_", " "); 
						System.out.println(temp);
					}
				}
					
					break;
				}
				
				case "RF":{
					switch(linia[1]){
					case "A":{					
						PCB.A = scanner.nextInt();
						break;
					}
					case "B":{
						PCB.B = scanner.nextInt();
						break;
					}
					case "C":{
						PCB.C = scanner.nextInt();
						break;
					}
				}
					break;
				}
				
				}
				break;
			}
			
			case 3:{
				switch(linia[0]){
					case "MVI":{
						int temp=Integer.parseInt(linia[2]);
					switch(linia[1]){
						case "A":{
							PCB.A=temp;
							break;}
						case "B":{
							PCB.B=temp;
							break;}
						case "C":{
							PCB.C=temp;
							break;}
					}
					break;
					}
					case "ADD":{
						switch(linia[1]){
							case "A":{
								switch(linia[2]){
									case "B":{
										PCB.A+=PCB.B;
										break;
									}
									case "C":{
										PCB.A+=PCB.C;
										break;
									}
								}
								break;
							}
							case "B":{
								switch(linia[2]){
								case "A":{
									PCB.B+=PCB.A;
									break;
								}
								case "C":{
									PCB.B+=PCB.C;
									break;
								}
							}
								break;
							}
							case "C":{
								switch(linia[2]){
								case "A":{
									PCB.C+=PCB.A;
									break;
								}
								case "B":{
									PCB.C+=PCB.B;
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
										PCB.A=PCB.B%2;
										break;
									}
									case "C":{
										PCB.A=PCB.C%2;
										break;
									}									
								}
								break;
							}
							case "B":{
								switch(linia[2]){
									case "A":{
										PCB.B=PCB.A%2;
										break;
									}
									case "C":{
										PCB.B=PCB.C%2;
										break;
									}									
								}
								break;
							}
							case "C":{
								switch(linia[2]){
									case "A":{
										PCB.C=PCB.A%2;
										break;
									}
									case "B":{
										PCB.C=PCB.B%2;
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

class  PCB{
	static int A;
	static int B;
	static int C;
	static int LR;
}
