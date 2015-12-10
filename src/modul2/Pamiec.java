package modul2;
import modul1.Registers;
import modul1.Semaphores;

// rejestry.reg2 = FSBSEM.value; /// [!!!] nie potrzebuję rejetrów !

import java.util.*;

//KOMENTARZ DODANY NA NOWO -PROBA
public class Pamiec {
    private static Semaphores FSBSEM  ; //semafor wolnej pamięci >0 można dokonać operacje Ay odblokować dostep do wolnej pamieci
    private static Semaphores MEMORY_SEM  ;//Jesli proces dokonuje próby uzyskania przydziału, ale nie może go otrzymać,
    // wykonuje operację P a semaforze Memory, w ten sposób zablokowując go. Gdy aktywuje go inny proces zwalniający jakiś fragment pamięci,
    //proces ten ponawia próę uzyskania przydziału, zablokowując się może ponownie dopókiadanie jego nie będzie możliwe
    // (!) Zawsze gdy zwalnia się blok pamięci, na semaforze MEMORY wykonywana jest operacja V.
    private static Wolna WolnaLista;
    private static Zajeta ZajetaLista;
    private static Registers rejestry;


    public static int MEMORY_SIZE = 256;
    public static byte []RAM;

    //typ[][] nazwa_tablicy2 = new typ[liczba1][liczba2]; //deklaracja i przypisanie (utworzenie)

    public Pamiec() {
        byte[] RAM = new byte[MEMORY_SIZE];
        WolnaLista = new Wolna(MEMORY_SIZE);
        ZajetaLista = new Zajeta();
        MEMORY_SEM = new Semaphores(0);
        FSBSEM = new Semaphores(1); // na poczatku ma wartosc 1
        //=-------------------------------------------------
       // rejestry = new Registers(); // /// [!!!] nie potrzebuję rejetrów !
        //-----------------------------------------------------
        System.out.println("Inicjalizacja Pamieci o rozmiarze "+ MEMORY_SIZE +" zakonczona pomyslnie");

    }

    //operacja XB zmiany połączeń.  [NIE UŻYWAM NAZWY XB]
    // Adres obszaru przydzelonego wpisywany jest do własciwego obszaru na liście afgumentów
    // i po wykonaniu operacji V na semaforze FSBSEM następuje powrót z programu XA
    //Przydział wolnego bloku pamięci
    //od tego co tworzy proces biorę tylko [NAZWA PROCESU], [ROZMIAR PROCESU]

    //Calkowicie rezygnuje z rejestrow - bo nie korzystamy z assemblera
    public static void XA(String NazwaProcesu,int rozmiar){
        // 1) operacja P na semaforze FSBSEM
        //2)przeszukuje obszar pamięci aż znajdzie tak duży który się zmieści
        //3) zmiany połączeń elementów w liście dokonuje się za pomocą operacji XB; powrót do XA

        System.out.println("Wartość semafora FSBSEM: " + FSBSEM.value);
        System.out.println("Wartość semafora MEMORY_SEM: " + MEMORY_SEM.value);
        if( rozmiar > MEMORY_SIZE){
            System.out.println("[!] BLAD: próba przydzielenia bloku większego od całej pamieci [!] ");
        }
        else{
            //rejestry.reg2 = FSBSEM.value; /// [!!!] nie potrzebuję rejetrów !
            if (WolnaLista.Wolna() < rozmiar){
                System.out.println("[*]Brak wystarczającej ilości wolnej pamięci [*]\n[!] Zablokowanie procesu[!]");
                rejestry.reg2=MEMORY_SEM.value;
             //  Nie moge przydzielic bloku - operacja na semaforach
                FSBSEM.XP(); //Semafor blokow wolnej pamieci zablokowany
                MEMORY_SEM.XP(); //Blokuje dostep do struktur pamieci OP
            }
            //Gdy blok przydzielania jest prawidlowo mniejszy od Pamieci Operacyjnej
            else{
                int list_index =WolnaLista.ZnajdzWolne(rozmiar); //lis_index przechwuje tylkoe rozmiar wolnego obszaru
                if(list_index != -1){ //jesli jest dodstatecznie duży blok aby przydzielic pamiec
                    ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);//Zajecie bloku
                }
                else{
                    System.out.println("[*] Brak bloku o odpowiednim rozmiarze [*]");
                    System.out.println("Następuje przesunięcie bloków pamięci");
                    ZajetaLista.Przesun(RAM);
                    WolnaLista.Wykasuj(ZajetaLista.Ostatni());
                    list_index = WolnaLista.ZnajdzWolne(rozmiar);
                    ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);
                }
                System.out.println("Przydział pamieci dla procesu" + NazwaProcesu);
            }
           // rejestry.reg2 = FSBSEM.value; /// [!!!] nie potrzebuję rejetrów !
            //Semafory.XV(); Nika zakomentowane
            FSBSEM.XV(); //znowu można zapisywać

        }
        Wyswietl();

    }
//-------------------------------------------------------------------------//
    //Nazwy procesów nadaje moduł procesora niższy
    public static void XF(String  NazwaProcesu){
        System.out.println("Zowlnienie pamieci zajmowanej przez proces: " + NazwaProcesu);
        //rejestry.reg2=FSBSEM.value;
        //Semafory.XP();
        FSBSEM.XP();
        MEMORY_SEM.XV();
        WolnaLista.DodajWolnyBlok(ZajetaLista.Poczatek(NazwaProcesu), ZajetaLista.Rozmiar(NazwaProcesu));
        ZajetaLista.Usun(NazwaProcesu);

        if(MEMORY_SEM.value < 0){
          //  rejestry.reg2 = MEMORY_SEM.value;
            //Semafory.XV(); //zawsze gsy zwalniany jest blok pamięci wykonywana jest operacja XV; lwywoałanXV == lZwolnienPamieci
            FSBSEM.XV();
            MEMORY_SEM.XV(); //Gdy zwolni się blok +1
            Wyswietl(); //wyswietla pamiec po zwolnieniu
        }
    }
//-----------------------------------------------------------------------//
    public void WyswietlPamiec(){
        System.out.println("\n -------------------PAMIEC - zawartość -----------------");
        for (int i=0; i < MEMORY_SIZE; i++){
            System.out.print(RAM[i] + " "); //zeby nie interpretowalo znakow nowej linii
        }

        Scanner s = new Scanner(System.in);
        String string = s.nextLine();
}

    public static void Wyswietl(){
        WolnaLista.Wyswietl();
        ZajetaLista.Wyswietl();
        Scanner s =new Scanner(System.in);
        String string = s.nextLine();
    }




// -------------------------Zapisywanie danych bezposrednie do tablicy RAM ----------------------------------------//
////////////////////////////////////////////////////////////////////////////////
//zapisywanie danych do już zajętęgo bloku pamięci
    public void ZapiszDoPamieci(String NazwaProcesu, String daneProcesu)
    {
        //1 blokuje semafor żeby nikt w tym czasie nie zapisywal
        FSBSEM.XP();

        //pobranie konkretnego bloku po nazwie

        int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu); //nagle przestalo poprawnie pobierac ;/  (!)
        if(indeksPoczatek != -1){
            int dlugoscDanych=daneProcesu.length();
            int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);

            if(dlugoscDanych <= rozmiar)
            {
                byte[] daneByte=daneProcesu.getBytes();
                //zapisywanie do konkretnego bloku danych bajtowych bit po bicie do Tablicy RAM
                for( int i=0; i<rozmiar; i++){
                RAM[indeksPoczatek + i] = daneByte[i];
                }
            }
             else System.out.println("[BLAD] Dane większe od zaalokowanego obszaru Pamieci Operacyjnej");
                FSBSEM.XV(); //odblokowuje semafor - nie wiem czy potrzebnie
        }
        else{
            System.out.println("[Błąd] Blok pamieci: " + NazwaProcesu + " nie istnieje !");
            FSBSEM.XV(); //odblokowuje semafor
        }
    }
public byte[] odczytzpamieci()
{
	
}
    //odczyt bzposredni z tablicy RAM
public byte[] OdczytZPamieci(String NazwaProcesu, int ilePobrac){ //ilePobrac - liczba bajtow ktore chce się odczytac z bloku
    //1 blokuje semafor żeby nikt w tym czasie nie zapisywal
    FSBSEM.XP();

    // [licznik] zapamiętuje w którym miejscu zatrzymało się ostatnio pobieranie bloku - ZROBIC JA GLOBALNĄ ?
    int licznik=ilePobrac; //globalnie dostepny licznik do bloku pamieci - do pobierania kawalków bloku pamieci
    int indeksPoczatek = ZajetaLista.Poczatek(NazwaProcesu);
    int rozmiar = ZajetaLista.Rozmiar(NazwaProcesu);
    if(ilePobrac <= rozmiar) {
        byte[] fragmentDoZwrocenia = new byte[ilePobrac];

        for (int i = 0; i <ilePobrac; i++) {
            fragmentDoZwrocenia[i] = RAM[indeksPoczatek + i]; //nie kombinuje z generatorami czy yield
        }
        FSBSEM.XV(); //odblokowuje semafor - nie wiem czy porzebnie
        return fragmentDoZwrocenia; //Zwraca taką ilosć danych z bloku jaki chciał ktoś wywołujacy funkcje
    }
    else
        System.out.println("[Blad] Proba pobrania wiekszej ilosci danych niz posiada zaalokowanej pamieci proces: " + NazwaProcesu + "\n");
    FSBSEM.XV(); //odblokowuje semafor - nie wiem czy porzebnie
    return null;
}

////



    ///////////////////////////////////////////////////////////////////////////////////////
    public void WyswietlRAM(){
        System.out.println("------------------------ RAM - Zawartość ---------------------------\n");
        for( int i=0; i<MEMORY_SIZE; i++){
            System.out.println(RAM[i] + " ");
        }
        System.out.println("\n\n");
    }}