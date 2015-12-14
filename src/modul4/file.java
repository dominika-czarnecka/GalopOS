////////////////// SZYMON KO£ODZIEJCZAK
package modul4;
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

	public file(String name, int size, int bsize)
	{
		this.name=name;
		this.size=size;
		this.bsize=bsize;
		access=free;
	}
}