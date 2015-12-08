////////////////// SZYMON KO£ODZIEJCZAK
package fat;
import java.util.*;

public class file
{
	byte busy=-126;
	byte unoccupied=-125;
	
	String name;
	int first_node, last_node; 
	int size, bsize;
	int access;

	public file(String name, int size, int bsize)
	{
		this.name=name;
		this.size=size;
		this.bsize=bsize;
		access=unoccupied;
	}
	public file(){}
	public void close(){ this.access=unoccupied;}
	

}
