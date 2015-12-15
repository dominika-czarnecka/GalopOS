////////////////// SZYMON KOŁODZIEJCZAK
package modul4;
import java.text.SimpleDateFormat;
import java.util.*;

public class file
{

	//access signs
	static byte in_use=-85;
	static byte free=-70;

	public String name;
	int first_node, last_node; 
	int size, bsize;
	int access;
	String date;
	static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"); //format daty

	public file(String name, int size, int bsize)
	{
		this.name=name;
		this.size=size;
		this.bsize=bsize;
		access=free;
		set_date();
	}
	public void set_date()
	{
		Date currentDate = new Date();
		date=dateFormat.format(currentDate);	
	}
	
}