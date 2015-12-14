package modul2;
import java.lang.*;
import java.util.*;

public class Pamiec {

    private static Wolna WolnaLista;
    private static Zajeta ZajetaLista;
    public static int MEMORY_SIZE = 256;
    public static char[] RAM;

    public static int licznik =0 ; //LICZNIK KTÓRY ZAPAMIETUJE GDZIE SKONCZYLO SIE POBIERANIE Z BLOKU RAMU [!]

//-----------------------------[PAMIEC konstruktor]------------------------------------------------
    public Pamiec() {
        char[] RAM = new char[MEMORY_SIZE];
        WolnaLista = new Wolna(MEMORY_SIZE);
        ZajetaLista = new Zajeta();
        System.out.println("Inicjalizacja pamieci o rozmiarze " + MEMORY_SIZE + " zakonczona pomyslnie.");
    }

    //[!!!] ----------------------------- [NAJWAZNIEJSZA FUNKCJA]-------------------------------------
//-------------------------------------------[XA - Alokowanie Pamieci]-------------------------------------
    //@parametry: String NazwaProcesu - nazwaProcesu dla ktorego alokuje sie pamiec, int rozmiar - wielkosc bloku ktory nalezy zaalokowac
    //@zwraca: brak

    //Calkowicie rezygnuje z rejestrow - bo nie korzystamy z assemblera
    public static void XA(String NazwaProcesu, int rozmiar) {

        if (rozmiar > MEMORY_SIZE) {
            System.out.println("\n[BLAD]: proba przydzielenia bloku wiekszego od calej pamieci [!] \n");
        } else {

            try {
                if (WolnaLista.Wolna() < rozmiar) {
                    System.out.println("\n[BLAD]: Brak wystarczającej ilosci wolnej pamieci [!]\n");
                }
                //Gdy blok przydzielania jest prawidlowo mniejszy od Pamieci Operacyjnej
                else {
                    int list_index = WolnaLista.ZnajdzWolne(rozmiar); //list_index przechwuje tylkoe rozmiar wolnego obszaru
                    if (list_index != -1) { //jesli jest dodstatecznie duży blok aby przydzielic pamiec
                        ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);//Zajecie bloku
                    } else {
                        System.out.println("\n[*] Brak bloku o odpowiednim rozmiarze [*]\n");
                        System.out.println("\n[*]Następuje przesuniecie blokow pamięci operacyjnej[*]\n"); //kosztowna operacja - unikam jak moge
                        ZajetaLista.Przesun(RAM);
                        WolnaLista.Wykasuj(ZajetaLista.Ostatni());
                        list_index = WolnaLista.ZnajdzWolne(rozmiar);
                        ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);
                    }
                    System.out.println("\n[*]Przydzial pamieci dla procesu: " + NazwaProcesu + "[*]\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } ///////////////////////////////////////////////////////najwyżej do zmiany
            Wyswietl(); //wyswietla Wolne bloki Pamieci
        }
    }

    //--------------------------[XF - Usuwanie procesu po nazwie]---------------------------------------//
    //@parametry: String NazwaProcesu - nazwaProcesu ktora nalezy usunac
    //@zwraca: brak
    // [!] Po zakończeniu wyswietla bezposrednią pamiec RAM i wolne bloki pamieci
    public static void XF(String NazwaProcesu) {
        System.out.println("\n[*]Zowlnienie pamieci zajmowanej przez proces: " + NazwaProcesu + "[*]\n");
        WolnaLista.DodajWolnyBlok(ZajetaLista.Poczatek(NazwaProcesu), ZajetaLista.Rozmiar(NazwaProcesu));
        ZajetaLista.Usun(NazwaProcesu);
            WyswietlRAM(); //wyswietla bezposredi RAM pamiec po zwolnieniu
        Wyswietl(); // Wyswietla wolne i zajete bloki
    }


    // -------------------------[ZAPISZDOPAMIECI bezposredni do RAM] ----------------------------------------//
//@parametry: String NazwaProcesu - nazwa procesu do ktorego nalezy zapisac daneProcesu, String DaneProcesu - string ktory zapisac w RAM
//@Zwraca: brak
    //[UWAGA !] DaneProcesu < Zaalokowana Pamiec dla Procesu
    public static void ZapiszDoPamieci(String NazwaProcesu, String daneProcesu) {

        //pobranie konkretnego bloku po nazwie
        int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu);
        if (indeksPoczatek != -1) {
            int dlugoscDanych = daneProcesu.length();
            int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);

            if (dlugoscDanych <= rozmiar) {
                char[] daneChar = daneProcesu.toCharArray();
                //zapisywanie do konkretnego bloku danych bajtowych bit po bicie do Tablicy RAM
                for (int i = 0; i < rozmiar; i++) {
                    RAM[indeksPoczatek + i] = daneChar[i];
                }
            } else System.out.println("\n [BLAD] Dane wieksze od zaalokowanego obszaru Pamieci Operacyjnej [!]\n");
        } else {
            System.out.println("\n[BLAD]: Blok pamieci: " + NazwaProcesu + " nie istnieje [!]\n");
        }
    }


    //------------------[ODCZYTZPAMIECI -  bezposredni odczyt z tablicy RAM ]--------------------------------
    //@Parametry: String NazwaProcesu - z ktorego procesu odczytac , int ilePobrac - liczba charow ktore chce odczytac z bloku
    //@Zwraca: String/null jesli blad
    // [licznik] zapamiętuje w ktorym miejscu zatrzymalo sie ostatnio pobieranie bloku - ZMIENNA GLOBALNA potrzebuje jej dla interpretera
    public static String OdczytZPamieci(String NazwaProcesu, int ilePobrac){

        // [licznik] zapamietuje w ktorym miejscu zatrzymalo sie ostatnio pobieranie bloku - ZMIENNA GLOBALNA potrzebna interpretorowi
        licznik = ilePobrac; //globalnie dostepny licznik do bloku pamieci - do pobierania kawalkow bloku pamieci
        int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu);
        int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);
        if (ilePobrac <= rozmiar) {
            char[] fragmentDoZwrocenia = new char[ilePobrac];

            for (int i = 0; i < ilePobrac; i++)
                fragmentDoZwrocenia[i] = RAM[indeksPoczatek + i]; //nie kombinuje z generatorami czy yield

             //Zwraca taką ilosc danych z bloku jaki chcial ktos wywolujacy funkcje
            String str = String.valueOf(fragmentDoZwrocenia);
            return str;

        } else {
            System.out.println("\n[Blad]: Proba pobrania wiekszej ilosci danych niz posiada zaalokowanej pamieci proces: " + NazwaProcesu + "\n");
        }
        return null;
    }


//------------------------------------------------[WCZYTAJROZKAZ - potrzbne interpreterowi]---------------------------------------------------------
    //@Parametry: Nazwa Procesu, NrKomorki - podaje interpreter od ktorej komorki odczytywac pamiec
    //@Zwraca: jedna komede od NrKomorki
    public  static String WczytajRozkaz(String NazwaProcesu, int NrKomorki){
        char DoKtoregoZnakuCzytac = '\n';
        int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu); //Potrzebuje zeby znac poczatek bloku z ktorego czytam
        int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);

        int ilePobrac=0; //Zliczanie ile znakow mam zwrocic
        do{
            ilePobrac++;
            //zeby zwracac od miejsca na ktorym skonczyl Interpreter - potrojne dodawanie
        }while(RAM[indeksPoczatek + NrKomorki + ilePobrac] != DoKtoregoZnakuCzytac);

        if (ilePobrac <= rozmiar) {
            char[] fragmentDoZwrocenia = new char[ilePobrac];

            for (int i = 0; i<ilePobrac; i++) //zliczam ile znakow mam zwrocic
                fragmentDoZwrocenia[i] = RAM[indeksPoczatek + NrKomorki +  i];

            //Zwraca taką ilosc danych z bloku jaki chcial ktos wywolujacy funkcje
            String str = String.valueOf(fragmentDoZwrocenia); //fragment zrzucam na stringa i daje interpreterowi
            return str;
        }

        else{
            System.out.println("[BLAD]: Proba pobrania wiekszej ilosci danych niz posiada zaalokowanej pamieci proces: " + NazwaProcesu + "[!]/n");
            return null;
        }
    }

    //-----------------------------------[Wyswietlanie Tablicy RAM]-------------------------------------------
    //@oarametry: brak
    //@zwraca: brak
    public static void WyswietlRAM() {
        System.out.println("\n -------------------PAMIEC - zawartość -----------------\n");
        for (int i = 0; i < MEMORY_SIZE; i++) {
            System.out.print(RAM[i] + " "); //zeby nie interpretowalo znakow nowej linii
        }

        Scanner s = new Scanner(System.in);
        String string = s.nextLine();
    }

    //---------------------[WYSWIETL Wyswietlanie WOLNYCH i ZAJETYCH blokow pamieci]--------------------------------------//
    //@parametr: brak
    //@zwraca brak
    public static void Wyswietl() {
        WolnaLista.Wyswietl();  //Tutaj blad
        ZajetaLista.Wyswietl(); //Wyswetlanie Zajetych Blokow
        Scanner s = new Scanner(System.in);
        String string = s.nextLine();
    }

}//klamra zamykająca cala klase Pamiec