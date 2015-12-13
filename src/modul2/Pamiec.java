package modul2;
import java.lang.*;
import java.util.*;


public class Pamiec {

    private static Wolna WolnaLista;
    private static Zajeta ZajetaLista;
    public static int MEMORY_SIZE = 256;
    public static char[] RAM;

    public static int licznik =0 ; //LICZNIK KTÓRY ZAPAMIETUJE GDZIE SKONCZYLO SIE POBIERANIE Z BLOKU RAMU [!]


//-----------------------------[PAMIEC KONSTRUKTOR]------------------------------------------------
    public Pamiec() {
        char[] RAM = new char[MEMORY_SIZE];
        WolnaLista = new Wolna(MEMORY_SIZE);
        ZajetaLista = new Zajeta();
        System.out.println("Inicjalizacja Pamieci o rozmiarze " + MEMORY_SIZE + " zakonczona pomyslnie");
    }

    //[!!!] ----------------------------- [NAJWAZNIEJSZA FUNKCJA]-------------------------------------
//-------------------------------------------[ALOKOWANIE Pamieci]-------------------------------------
    //operacja XB zmiany połączeń.  [NIE UŻYWAM NAZWY XB]
    // Adres obszaru przydzelonego wpisywany jest do własciwego obszaru na liście argumentów
    // i po wykonaniu operacji V na semaforze FSBSEM następuje powrót z programu XA
    //Przydział wolnego bloku pamięci
    //od tego co tworzy proces biorę tylko [NAZWA PROCESU], [ROZMIAR PROCESU]

    //Calkowicie rezygnuje z rejestrow - bo nie korzystamy z assemblera
    public static void XA(String NazwaProcesu, int rozmiar) {

        if (rozmiar > MEMORY_SIZE) {
            System.out.println("[!] BLAD: próba przydzielenia bloku większego od całej pamieci [!] ");
        } else {
            if (WolnaLista.Wolna() < rozmiar) {
                System.out.println("[*]Brak wystarczającej ilości wolnej pamięci [*]\n[!] Zablokowanie procesu[!]");
            }
            //Gdy blok przydzielania jest prawidlowo mniejszy od Pamieci Operacyjnej
            else {
                int list_index = WolnaLista.ZnajdzWolne(rozmiar); //list_index przechwuje tylkoe rozmiar wolnego obszaru
                if (list_index != -1) { //jesli jest dodstatecznie duży blok aby przydzielic pamiec
                    ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);//Zajecie bloku
                } else {
                    System.out.println("[*] Brak bloku o odpowiednim rozmiarze [*]");
                    System.out.println("Następuje przesuniecie blokow pamięci");
                    ZajetaLista.Przesun(RAM);
                    WolnaLista.Wykasuj(ZajetaLista.Ostatni());
                    list_index = WolnaLista.ZnajdzWolne(rozmiar);
                    ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);
                }
                System.out.println("Przydzial pamieci dla procesu" + NazwaProcesu);
            }
        }
        Wyswietl(); //wyswietla Wolne bloki Pamieci
    }

    //--------------------------[XF - Usuwanie procesu po nazwie]---------------------------------------//
    //@parametry: String NazwaProcesu - nazwaProcesu ktora nalezy usunac
    //@zwraca: brak
    // [!] Po zakończeniu wyswietla bezposrednią pamiec RAM i wolne bloki pamieci
    public static void XF(String NazwaProcesu) {
        System.out.println("Zowlnienie pamieci zajmowanej przez proces: " + NazwaProcesu);
        WolnaLista.DodajWolnyBlok(ZajetaLista.Poczatek(NazwaProcesu), ZajetaLista.Rozmiar(NazwaProcesu));
        ZajetaLista.Usun(NazwaProcesu);
            WyswietlRAM(); //wyswietla bezposredi RAM pamiec po zwolnieniu
        Wyswietl(); // Wyswietla wolne bloki
    }


    // -------------------------[ZAPIS bezposredni do RAM] ----------------------------------------//
//@parametry: String NazwaProcesu - nazwa procesu do ktorego nalezy zapisac daneProcesu, String DaneProcesu - string ktory zapisac w RAM
//@Zwraca: brak
    //[UWAGA !] DaneProcesu < Zaalokowana Pamiec dla Procesu
    public static void ZapiszDoPamieci(String NazwaProcesu, String daneProcesu) {

        //pobranie konkretnego bloku po nazwie
        int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu); //nagle przestalo poprawnie pobierac ;/  (!)
        if (indeksPoczatek != -1) {
            int dlugoscDanych = daneProcesu.length();
            int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);

            if (dlugoscDanych <= rozmiar) {
                char[] daneChar = daneProcesu.toCharArray();
                //zapisywanie do konkretnego bloku danych bajtowych bit po bicie do Tablicy RAM
                for (int i = 0; i < rozmiar; i++) {
                    RAM[indeksPoczatek + i] = daneChar[i];
                }
            } else System.out.println("[BLAD] Dane większe od zaalokowanego obszaru Pamieci Operacyjnej");
        } else {
            System.out.println("[Błąd] Blok pamieci: " + NazwaProcesu + " nie istnieje !");
        }
    }


    //------------------[ODCZYT bezposredni z RAM ]--------------------------------
    //@Parametry: String NazwaProcesu - z ktorego procesu odczytac , int ilePobrac - ilosc charow do pobrania
    //@Zwraca: String/null jesli blad
    // [licznik] zapamiętuje w którym miejscu zatrzymało się ostatnio pobieranie bloku - ZMIENNA GLOBALNA potrzebna interpretorowi
    public static String OdczytZPamieci(String NazwaProcesu, int ilePobrac){ //ilePobrac - liczba bajtow ktore chce się odczytac z bloku

        // [licznik] zapamiętuje w którym miejscu zatrzymało się ostatnio pobieranie bloku - ZMIENNA GLOBALNA potrzebna interpretorowi
        licznik = ilePobrac; //globalnie dostepny licznik do bloku pamieci - do pobierania kawalków bloku pamieci
        int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu);
        int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);
        if (ilePobrac <= rozmiar) {
            char[] fragmentDoZwrocenia = new char[ilePobrac];

            for (int i = 0; i < ilePobrac; i++)
                fragmentDoZwrocenia[i] = RAM[indeksPoczatek + i]; //nie kombinuje z generatorami czy yield

             //Zwraca taką ilosć danych z bloku jaki chciał ktoś wywołujacy funkcje
            String str = String.valueOf(fragmentDoZwrocenia);
            return str;

        } else
            System.out.println("[Blad] Proba pobrania wiekszej ilosci danych niz posiada zaalokowanej pamieci proces: " + NazwaProcesu + "\n");
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
            System.out.println("[BLAD] Proba pobrania wiekszej ilosci danych niz posiada zaalokowanej pamieci proces: " + NazwaProcesu + "\n");
            return null;
        }
    }

    //-----------------------------------[Wyswietlanie Tablicy RAM]-------------------------------------------
    //@oarametry: brak
    //@zwraca: brak
    public static void WyswietlRAM() {
        System.out.println("\n -------------------PAMIEC - zawartość -----------------");
        for (int i = 0; i < MEMORY_SIZE; i++) {
            System.out.print(RAM[i] + " "); //zeby nie interpretowalo znakow nowej linii
        }

        Scanner s = new Scanner(System.in);
        String string = s.nextLine();
    }

    //---------------------[Wyswietlanie WOLNEGO bloku pamieci]--------------------------------------//
    //@parametr: brak
    //@zwraca brak
    public static void Wyswietl() {
        WolnaLista.Wyswietl();
        ZajetaLista.Wyswietl();
        Scanner s = new Scanner(System.in);
        String string = s.nextLine();
    }

}//klamra zamykająca cala klase Pamiec