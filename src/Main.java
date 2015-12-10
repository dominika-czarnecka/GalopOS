import interpreter.*;
import modul1.*;
import modul2.*;
import modul3.*;
import modul4.*;



public class Main {

	public static void main(String[] args) {

		hdd_commander driver = new hdd_commander(32,32);
		driver.create("prog1", 
			   "tekst"
		+"\n"+ "tekst2"
		+"\n"+ "tekst3"
		+"\n"+ "tekst4");
		System.out.println(driver.read("prog1"));
		driver.driver_show();
		
		
	}

}
