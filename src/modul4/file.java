////////////////// SZYMON KO£ODZIEJCZAK
package modul4;
import java.text.SimpleDateFormat;
import java.util.*;

import modul1.Semaphore;

public class file
{

	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //format daty

	public String name;
	int first_node, last_node; 
	int size, bsize;
	boolean access;
	String date;


	public file(String name, int size, int bsize)
	{
		this.name=name;
		this.size=size;
		this.bsize=bsize;
		access=true;
		set_date();
	}
	public void set_date()
	{
		Date currentDate = new Date();
		date=dateFormat.format(currentDate);	
	}
	
}