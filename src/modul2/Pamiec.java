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
		char[] RAM = new char[MEMORY_SIZE];
		WolnaLista = new Wolna(MEMORY_SIZE);
		ZajetaLista = new Zajeta();
		System.out.println("Inicjalizacja pamieci o rozmiarze " + MEMORY_SIZE + " zakonczona pomyslnie.");
	}

	//Alokacja
	public static void XA(String NazwaProcesu, int rozmiar) throws Exception {

		System.out.println( " XA wolna pamiec:" + Wolna.iloscWolnej());
		System.out.println( " XA rozmiar:" + rozmiar);

		if (rozmiar > MEMORY_SIZE) {
			System.out.println("\n[BLAD]: proba przydzielenia bloku wiekszego od calej pamieci [!] \n");
			throw new Exception("nie ma tyle pamieci w systemie");
		}

		else {
			if (Wolna.iloscWolnej() < rozmiar) {
				System.out.println("\n[BLAD]: Brak wystarczajacej ilosci wolnej pamieci [!]\n");
				throw new Exception("nie ma tyle wolnej");
			}

			//Gdy blok przydzielania jest prawidlowo mniejszy od Pamieci Operacyjnej
			else {
				int list_index = WolnaLista.ZnajdzWolne(rozmiar); //indeks pierwszego wystarczajacego bloku

				if (list_index != -1) {
					ZajetaLista.Dodaj(WolnaLista.Zajmij(list_index, rozmiar), rozmiar, NazwaProcesu);
					System.out.println("Nowa ilosc wolnej: " + Wolna.iloscWolnej());
				} else {
					System.out.println("[*] Brak bloku o odpowiednim rozmiarze");

					throw new Exception("trzeba przesunac");

					/*System.out.println("[*] Nastepuje przesuniecie blokow pamieci operacyjnej"); //kosztowna operacja - unikam jak moge
					ZajetaLista.Przesun(RAM);
					WolnaLista.Wykasuj(ZajetaLista.Ostatni());
					list_index = WolnaLista.ZnajdzWolne(rozmiar);
					ZajetaLista.Dodaj(WolnaLista.Zajmij(list_index, rozmiar), rozmiar, NazwaProcesu);*/
				}

				System.out.println("[*]Przydzial pamieci dla procesu: " + NazwaProcesu);
				System.out.println("XA wolna pozostala:" + Wolna.iloscWolnej());

			}
		}
	}

	//Zwalnianie pamieci
	public static void XF(String NazwaProcesu) {
		System.out.println("[*]Zowlnienie pamieci zajmowanej przez proces: " + NazwaProcesu);
		WolnaLista.DodajWolnyBlok(Zajeta.Poczatek(NazwaProcesu), ZajetaLista.Rozmiar(NazwaProcesu));
		ZajetaLista.Usun(NazwaProcesu);
	}

	//Przepisuje dane juz zaalokowanego procesu
	public static void ZapiszDoPamieci(String NazwaProcesu, String daneProcesu) {

		char[] daneChar = daneProcesu.toCharArray();
		int ile = daneChar.length;
		int indeksPoczatek = Zajeta.Poczatek(NazwaProcesu);

		System.out.println("Zapisuje " + ile + " charow od" + indeksPoczatek);

		if (indeksPoczatek != -1) {
			for (int i = 0; i < ile; i++) {
				RAM[indeksPoczatek + i] = daneChar[i];
			}
		} else System.out.println("[BLAD] Dane wieksze od zaalokowanego obszaru Pamieci Operacyjnej [!]");

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
			System.out.print(RAM[i] + " "); //zeby nie interpretowalo znakow nowej linii
		}
	}

	public static void Przesun(int poczSkad, int poczGdzie, int ile){
		for (int i = 0; i < ile; i++) {
			RAM[poczGdzie + i] = RAM[poczGdzie + i];
		}
	}

	public static void WyswietlWolZaj() {
		WolnaLista.Wyswietl();
		Zajeta.Wyswietl();
	}

}