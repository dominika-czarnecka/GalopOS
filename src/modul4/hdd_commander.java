////////////////// SZYMON KO£ODZIEJCZAK
package modul4;

import java.util.*;

public class hdd_commander
{
	
byte end_of_file=-110; // CODE EOF
byte empty_slot=-1; // CODE OF EMPLY SLOT


int []fat;
byte [][]driver;
List<file> main_catalog;

public int number_blocks;
public int size_block;



/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
/////							B A S I C	M E T H O D S					/////////
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
public hdd_commander(int number_blocks, int size_block)     /// ONLY ONE BUILDER FOR HDD
{
	driver_init(number_blocks, size_block);
	fat_init(number_blocks);
	main_catalog= new ArrayList<file>();
	this.number_blocks=number_blocks;
	this.size_block=size_block;
	System.out.println("Utworzony dysk o rozmiarze "+number_blocks*size_block+" bajtow.");
	
}
public int count_free_space()
{
	int counter=0;
	for(int i=0;i<number_blocks;i++)
		if (fat[i]==empty_slot) counter++;
	return counter;
}
private int search_free_space() // THE INDEX OF FIRST FAT'S EMPTY SPACE
{
	for(int i=0;i<number_blocks;i++)
		if (fat[i]==empty_slot) return i;
	return 0;
}
private int search_next_free_space() // THE INDEX OF SECOND FAT'S EMPTY SPACE
{
	int first_free_block=search_free_space()+1;  // except this one
	for(int i=first_free_block;i<number_blocks;i++)
		if (fat[i]==empty_slot)return i ;
	return 0;
}
public file get_file(String name)
{
	for(int i=0;i<main_catalog.size();i++)
		if(main_catalog.get(i).name==name) return main_catalog.get(i);
	return null;
}
private boolean open(String name){
	if (get_file(name)!=null && get_file(name).access==get_file(name).unoccupied){	
		get_file(name).access=get_file(name).busy;
		return true;}
	else return false;
}
private boolean is_enough_space(int bsize)
{
	if(bsize<=count_free_space()) return true;
	else return false;
	}
//////////////////////////////////// INIT METHODS ////////////////////////////////////////////

private void driver_init(int number_blocks, int size_block)
{
	driver=new byte[number_blocks][size_block];
	for(int i=0; i<number_blocks; i++){
		for(int j=0; j<size_block; j++){
			driver[i][j]=empty_slot;
		}
	}
}
private void fat_init(int number_blocks)
{
	fat=new int[number_blocks];
	for(int i=0;i<number_blocks;i++) fat[i]=empty_slot;
}
private void format() // CATALOG, DRIVE & FAT
{
	main_catalog.clear();
	driver_init(number_blocks, size_block);
	fat_init(number_blocks);
}
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
/////							P R I N T	M E T H O D S					/////////
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
public void driver_show()
{
		for(int i=0; i<number_blocks; i++){
			for(int j=0; j<size_block; j++){
				System.out.print(driver[i][j] + " ");
			 }							
			System.out.println();
		 }
		System.out.println();
}
public void fat_show()
{
	for(int i=0;i<number_blocks;i++)
	{
		System.out.println("["+ i +":" +fat[i] +"]");
	}
}
public void catalog_show()
{
	if(main_catalog.size()==0) System.out.println("empty");
	else{
	for(int i=0;i<main_catalog.size();i++)
	{
		System.out.println(main_catalog.get(i).name + " " + main_catalog.get(i).size + " bytes");
	}
	}
}
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
/////							S U B	M E T H O D S						/////////
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////

private char[] recc_read(int location, int size, int bsize, int index, char[]content)
{	
	if(bsize==1)
	{
		for(int j=0; j<size; j++){
			content[index]=(char)driver[location][j]; index++;}
		bsize--;
		return content;
	}
	else
	{
		for(int i=0;i<size_block;i++){
			content[index]=(char)driver[location][i]; index++;}
		bsize--;
		size-=size_block;
		return recc_read(fat[location],size,bsize,index,content);
	}
}
private char[] recc_read(int location, int size, int bsize, int index, char[]content, int content_size)
{	
	if(bsize==1)
	{
		for(int j=0; j<size; j++){
			content[index]=(char)driver[location][j]; 
			index++;
			if(index==content_size) return content;
		}
		bsize--;
		return content;
	}
	else
	{
		for(int i=0;i<size_block;i++){
			content[index]=(char)driver[location][i]; 
			index++;
			if(index==content_size) return content;}
		bsize--;
		size-=size_block;
		return recc_read(fat[location],size,bsize,index,content, content_size);
	}
}
private void recc_delete(int location, int bsize)
{
	int removable=fat[location];
	if(bsize==1)
	{
		fat[location]=empty_slot;
		for(int j=0;j<size_block;j++)
			driver[location][j]=empty_slot;  
		}
	else
	{
		fat[location]=empty_slot;
		for(int j=0;j<size_block;j++)
			driver[location][j]=empty_slot;   
		bsize--;
		recc_delete(removable,bsize);
	}
}
private file save(byte[] content, int size, int bsize, int index, file save)
{
	while(bsize>1)
	{
		for(int j=0; j<size_block; j++)							//wiadomo ze pierwsze bloki beda w pelni zajete
		{
			driver[search_free_space()][j]=content[index];
			index++;
		}
		bsize--;												// zapis jednego bloku skonczony, zdejmuje z licznika
		size-=size_block;										//ile jeszcze trzeba zgrac znakow(potrzebne do size==1)													
		fat[search_free_space()]=search_next_free_space();  	//odnosnik do nastepnego bloku, bo wiadomo ze bedzie potrzebny
	}															//jednoczesne wpisanie ze blok jest zajety
	if(bsize==1)
	{
		for(int j=0; j<size; j++){ 								//reszta znakow; index to obecna pozycja w content 
			driver[search_free_space()][j]=content[index]; 
			index++;}
		save.last_node=search_free_space();						//brak odnosnika, wiadomo ze blok bedzie ostatnim
		fat[search_free_space()]=end_of_file;					//ostatni blok zajety
	}
	return save;
}
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
/////							M A I N 	M E T H O D S					/////////
/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////

public void create(String name, String data)
{

		int size=data.length();  									//ilosc znakow w data
		
		int bsize;
		if(size%size_block==0)  bsize=size/size_block;					//jezeli ilosc znakow jest rowna wielokrotnosci block_size
		else bsize=(size/size_block)+1; 							    //ilosc blokow potrzebna dla data      	
		
		if(is_enough_space(bsize))
		{  															//jesli jest miejsce
		byte[] content=new byte[size]; 				 				//utworzenie tablicy o rozmiarze data
		content=data.getBytes();									//konwersja stringa na tablice content
		int index=0;												//index content
		file new_file=new file(name,size,bsize); 					//utworzenie pliku i dodanie go do listy
		new_file.first_node=search_free_space();
		new_file=save(content,size,bsize,index,new_file);
		main_catalog.add(new_file);
		}
		else System.out.println("Sorry, not possible: not enough space for this operation"); 
}
public void edit(String name, String data) // dwie weryfikacje: czy udalo sie otworzyc i czy jest miejsce
{
 if(open(name))
 {  																					
	file edit=get_file(name);
	byte[] content=new byte[data.length()]; 
	content=data.getBytes();
	
	int index=0;
	int index_last_character=edit.size%size_block; 			// indeks ostatniego znaku
	int fill=size_block-(edit.size%size_block);				// liczba wolnych bajtow w bloku
	int size=data.length()-fill;							// ilosc znakow ktore trzeba zapisac PO dopelnieniu, jezeli ujemna to znaczy ze nie dojdzie do dopelnienia
	int bsize;												//ILE MUSI BYC DOPISANYCH BLOKOW POZA DOPELNIENIEM DO BLOKU
	if(size%size_block==0)bsize=(data.length()-fill)/size_block; else bsize=((data.length()-fill)/size_block)+1;
	if(size<0) ///////SYTUACJA KIEDY NAWET BLOK SIE NIE SKONCZY
	{
		for(int j=index_last_character; j<index_last_character+data.length(); j++)		// dopelnienie ostatniego bloku
																						// PRZY DUZYCH WIELKOSCIACH INDEX_LAST CHARACTER BYL WIEKSZY OD DATA LENGTH
		{
			driver[edit.last_node][j]=content[index];
			index++;
		}
		edit.size+=data.length();
		edit.close();
		return;  // nie idzie dalej
	}
	if(is_enough_space(bsize))
	{
		for(int j=index_last_character; j<size_block; j++)		// dopelnienie ostatniego bloku
		{
		driver[edit.last_node][j]=content[index];
		index++;
		}
		edit.size+=data.length();
		edit.bsize+=bsize;
	
		if(data.length()>fill) 									// jezeli wiecej niz dopelnienie
		{
			fat[edit.last_node]=search_free_space();
			save(content,size, bsize, index,edit);
		}
	
		edit.close();
	}
	else System.out.println("Sorry, not possible: not enough space");
 }
else System.out.println("Sorry, not possible: file is arleady opened or file is not existing");
}
public char[] read(String name)
{ 
	if(open(name))
	{  																						//jezeli mozemy dzialac
		file read=get_file(name);  															//plik z ktorego czytamy
		char[]content=new char[read.size]; 													//bufor do ktorego zostana wczytane dane
		int index=0;
		content=recc_read(read.first_node, read.size, read.bsize, index, content);  		//wywolanie funkcji z rekurencja(odczyt i przepis)
		read.close();																//zamyka plik
		
		return content;
	}
	else {System.out.println("Sorry, not possible: file is arleady opened or file is not existing."); return null;}
}
public char[] read(String name, int content_size)
{ 
	if(open(name))
	{  																						//jezeli mozemy dzialac
		file read=get_file(name);  														//plik z ktorego czytamy
		if(content_size>read.size)
		{
			System.out.println("File is not as large as you think");
			return null;
		}
		char[]content=new char[content_size]; 													//bufor do ktorego zostana wczytane dane
		int index=0;
		content=recc_read(read.first_node, read.size, read.bsize, index, content, content_size);  		//wywolanie funkcji z rekurencja(odczyt i przepis)
		read.close();																//zamyka plik
		
		return content;
	}
	else {System.out.println("Sorry, not possible: file is arleady opened or file is not existing."); return null;}
}
public void delete(String name)
{
	if(open(name))
	{  
		file delete=get_file(name);
		recc_delete(delete.first_node,delete.bsize);            // wywolanie funkcji rekurencyjnej
		main_catalog.remove(get_file(name));					// usuniecie z katalogu
		delete.close();
	}
	else System.out.println("Sorry, not possible: file is arleady opened or file is not existing.");
}
}

	
									  




