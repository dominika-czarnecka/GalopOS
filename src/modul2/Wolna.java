package modul2;
import java.util.*;

public class Wolna {

	public static ArrayList<Element> List = new ArrayList<Element>();
	public static int wolna; //ilosc wolnej calkowitej

	public Wolna(int rozmiar) {
		List.add(new Element(0, rozmiar));
		wolna = rozmiar;
	}

	public static int iloscWolnej() {
		//return wolna;

		int wynik = 0;
		for (Element e : List) {
			wynik += e.ZwrocRozmiar();
		}
		return wynik;
	}


	public void Wyswietl(){
		if (List.size() > 0){
			System.out.println("-------- Wolna Lista - bloki --------");
			for(int i=0; i< List.size(); i++){
				int nr = i+1;
				System.out.println("[Pam]"+ nr + ". " + "Indeks: " + List.get(i).Poczatek() + "\t Rozmiar: " + List.get(i).ZwrocRozmiar() );
			}
			System.out.println("[Pam] Pamiec wolna - ilosc wolnego miejsca: " + iloscWolnej() + "\n\n");
		}
		else{
			System.out.println("[Pam] Pamiec wolna jest pusta\n");
		}


	}
	
	//ZWOLNIENIE BLOKU + scalanie bloków wolnych sąsiadujących
	//Gdy usunie sie jakis blok - dodanie WOLNEGO miejsca
	public void DodajWolnyBlok(int indeks, int rozmiar){
		wolna += rozmiar;

		Element nowy = new Element(indeks, rozmiar);
		List.add(nowy);
		
		Element lewy = null;
		Element prawy = null;
		
		
		for (int i=0; i < List.size() - 1; i++){
			
			if (List.get(i).czySasiaduja(nowy)) {
				if (List.get(i).Poczatek() < nowy.Poczatek()) {
					lewy = List.get(i);
				} else {
					prawy = List.get(i);
				}
			}
			
		}
		
		if (prawy != null) {
			scal(nowy, prawy);
		}
		
		if (lewy != null) {
			scal(lewy, nowy);
		}
	}

	public void scal(Element lewy, Element prawy) {
		lewy.PowiekszRozmiar(prawy.ZwrocRozmiar());
		List.remove(prawy);
	}

	public int Zajmij(int list_index, int rozmiar){
		wolna -= rozmiar;
				
		Element blokWolny = List.get(list_index);
		int poprzedniPocz = blokWolny.Poczatek();
		
		blokWolny.ZmniejszRozmiar(rozmiar);
		blokWolny.ustawPoczatek(blokWolny.Poczatek() + rozmiar);
		
		if (blokWolny.ZwrocRozmiar() == 0) {
			List.remove(blokWolny);
		}
		
		return poprzedniPocz;
	}

	public int ZnajdzWolne( int size){ //szukaj wolnego obszaru o rozmiarze size
		try{
			if(!List.isEmpty()) {
				for (int i = 0; i < List.size(); i++) {
					if (List.get(i).ZwrocRozmiar() >= size)
						return i;
				}
				return -1; //-1 jak nie ma wolnego bloku
			}
			System.out.println("[Pam] Brak Blokow wolnej pamieci\n");

		}catch(Exception ex){ ex.printStackTrace();}
		return -1;
	}

}
