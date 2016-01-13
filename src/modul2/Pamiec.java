package modul2;
import java.lang.*;

import java.util.*;

import modul3.ZarzProc;
import modul3.*;


public class Pamiec {

	public static Wolna WolnaLista;
	public static Zajeta ZajetaLista;
	public static int MEMORY_SIZE = 256;
	public static char[] RAM;

	// public static int licznik = 0 ; //LICZNIK KTÓRY ZAPAMIETUJE GDZIE SKONCZYLO SIE POBIERANIE Z BLOKU RAMU [!]

	public Pamiec() {
		RAM = new char[MEMORY_SIZE];
		WolnaLista = new Wolna(MEMORY_SIZE);
		ZajetaLista = new Zajeta();
		System.out.println("[Pam] Inicjalizacja pamieci o rozmiarze " + MEMORY_SIZE + " zakonczona pomyslnie.");
	}

	//Alokacja
	public static void XA(String NazwaProcesu, int rozmiar) throws Exception {

		if (rozmiar > MEMORY_SIZE) {
			System.out.println("[Pam] proba przydzielenia bloku wiekszego od calej pamieci [!]");
			throw new Exception("[Pam] nie ma tyle pamieci w systemie");
		}

		else {
			if (Wolna.iloscWolnej() < rozmiar) {
				System.out.println("[Pam] Brak wystarczajacej ilosci wolnej pamieci [!]");
				throw new Exception("[Pam]nie ma tyle wolnej pamieci");
			}

			//Gdy blok przydzielania jest prawidlowo mniejszy od Pamieci Operacyjnej
			else {
				int list_index = WolnaLista.ZnajdzWolne(rozmiar); //indeks pierwszego wystarczajacego bloku

				if (list_index != -1) {
					ZajetaLista.Dodaj(WolnaLista.Zajmij(list_index, rozmiar), rozmiar, NazwaProcesu);
					if(!NazwaProcesu.contains("*"))
					System.out.println("[Pam] Nowa ilosc wolnej: " + Wolna.iloscWolnej());
				} else {
					System.out.println("[Pam] Brak bloku o odpowiednim rozmiarze");

					//throw new Exception("[Pam]trzeba przesunac");

					System.out.println("[Pam]Nastepuje przesuniecie blokow pamieci operacyjnej"); //kosztowna operacja - unikam jak moge

					przesunWszystko();
				}
				if(!NazwaProcesu.contains("*"))
				System.out.println("[Pam]Przydzial pamieci dla procesu: " + NazwaProcesu);
			}
		}
	}

	//Zwalnianie pamieci
	public static void XF(String NazwaProcesu) {
		System.out.println("[Pam]Zwolnienie pamieci zajmowanej przez proces: " + NazwaProcesu);
		WolnaLista.DodajWolnyBlok(Zajeta.Poczatek(NazwaProcesu), ZajetaLista.Rozmiar(NazwaProcesu));
		ZajetaLista.Usun(NazwaProcesu);
	}

	//Przepisuje dane juz zaalokowanego procesu
	public static void ZapiszDoPamieci(String NazwaProcesu, String daneProcesu) {

		int ile = daneProcesu.length();
		char[] daneChar=daneProcesu.toCharArray();

		int indeksPoczatek = Zajeta.Poczatek(NazwaProcesu);

		//System.out.println("Zapisuje " + ile + " charow od " + indeksPoczatek);

		if (indeksPoczatek != -1) {
			for (int i = 0; i < ile; i++) {
				RAM[indeksPoczatek + i] = daneChar[i];
			}
		} else System.out.println("[Pam]Dane wieksze od zaalokowanego obszaru Pamieci Operacyjnej [!]");

	}

	public static String WczytajRozkaz(String NazwaProcesu, int odKomorki){

		int indeksPoczatek = Zajeta.Poczatek(NazwaProcesu);
		int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);

		int ilePobrac=0;

		do {
			ilePobrac++;
		} while((RAM[indeksPoczatek + odKomorki + ilePobrac] != '\n') && ilePobrac < rozmiar );


		char[] fragmentDoZwrocenia = new char[ilePobrac];

		for (int i = 0; i < ilePobrac; i++)
			fragmentDoZwrocenia[i] = RAM[indeksPoczatek + odKomorki +  i];

		return String.valueOf(fragmentDoZwrocenia);
	}

	public static void WyswietlRAM() {
		System.out.println("\n -------------------PAMIEC - zawartosc-----------------\n");
		for (int i = 0; i < MEMORY_SIZE; i++) {
			System.out.print(String.valueOf(RAM[i]).replace('\n', '\\') + " "); //zeby nie interpretowalo znakow nowej linii
			if (i%64==63) System.out.println("\n");
		}
	}

	public static void Przesun(int poczSkad, int poczGdzie, int ile){
		System.out.println("[Pam]przesuwam " + ile + " bajtow z " + poczSkad + " do " + poczGdzie);
		for (int i = 0; i < ile; i++) {
			RAM[poczGdzie + i] = RAM[poczSkad + i];
		}
	}

	public static void przesunWszystko() {
		sortujZajPolozeniem();
		int wsk = 0;
		for(int i=0; i < Zajeta.List.size(); i++){
			int skad = Zajeta.List.get(i).Poczatek();
			int rozmiar = Zajeta.List.get(i).ZwrocRozmiar();
			Przesun(skad, wsk, rozmiar);
			Zajeta.List.get(i).ustawPoczatek(wsk);
			wsk += rozmiar;
		}
		
		Wolna.List.clear();
		WolnaLista.DodajWolnyBlok(wsk, MEMORY_SIZE - wsk);
	}

	public static void sortujZajPolozeniem(){
		boolean zamieniono = true;
		ElementZaj temp;

		while (zamieniono) {
			zamieniono= false;
			for(int j=0; j< Zajeta.List.size()-1; j++) {
				if ( Zajeta.List.get(j).Poczatek() > Zajeta.List.get(j+1).Poczatek() )
				{
					temp = Zajeta.List.get(j); 
					Zajeta.List.set(j, Zajeta.List.get(j+1));
					Zajeta.List.set(j+1, temp);
					zamieniono = true; 
				} 
			} 
		} 
	}

	public static void WyswietlWolZaj() {
		WolnaLista.Wyswietl();
		Zajeta.Wyswietl();
	}

}