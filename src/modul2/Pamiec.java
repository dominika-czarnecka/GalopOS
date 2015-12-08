package pamiecOperacyjna;
import java.util.*;

public class Pamiec {
    private static Semafory FSBSEM  ; //semafor wolnej pamięci >0 można dokonać operacje
    private static Semafory MEMORY_SEM  ;
    private static Wolna WolnaLista;
    private static Zajeta ZajetaLista;
    private static Rejestry rejestry;


    public static int MEMORY_SIZE = 256;
    public static byte []RAM;

    //typ[][] nazwa_tablicy2 = new typ[liczba1][liczba2]; //deklaracja i przypisanie (utworzenie)

    public Pamiec() {
        byte[] RAM = new byte[MEMORY_SIZE];
        WolnaLista = new Wolna(MEMORY_SIZE);
        ZajetaLista = new Zajeta();
        MEMORY_SEM = new Semafory(0);
        FSBSEM = new Semafory(1); // na poczatku ma wartosc 1
        //=-------------------------------------------------
        rejestry = new Rejestry(); // Tylko do mnie
        //-----------------------------------------------------
        System.out.println("Inicjalizacja Pamieci o rozmiarze "+ MEMORY_SIZE +" zakonczona pomyslnie");

    }

    //Przydział wolnego bloku pamięci
    public static void XA(String NazwaProcesu,int rozmiar){
        // 1) operacja P na semaforze FSBSEM
        //2)przeszukuje obszar pamięci aż znajdzie tak duży który się zmieści
        //3) zmiany połączeń elementów w liście dokonuje się za pomocą operacji XB; powrót do XA

        System.out.println("Wartość semafora FSBSEM: " + FSBSEM.VALUE);
        System.out.println("Wartość semafora MEMORY_SEM: " + MEMORY_SEM.VALUE);
        if( rozmiar > MEMORY_SIZE){
            System.out.println("[!] BLAD: próba przydzielenia bloku większego od całej pamieci [!] ");
        }
        else{
            rejestry.r2 = FSBSEM;
            Semafory.XP();
            if (WolnaLista.Wolna() < rozmiar){
                System.out.println("[*]Brak wystarczającej ilości wolnej pamięci [*]\n[!] Zablokowanie procesu[!]");
                rejestry.r2=MEMORY_SEM;
                Semafory.XP();
            }
            else{
                int list_index =WolnaLista.ZnajdzWolne(rozmiar);
                if(list_index != -1){
                    ZajetaLista.Dodaj(WolnaLista.Wpisz(list_index, rozmiar), rozmiar, NazwaProcesu);
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
            rejestry.r2 = FSBSEM;
            Semafory.XV();

        }
        Wyswietl();

    }
//-------------------------------------------------------------------------//
    //Nazwy procesów nadaje moduł procesora niższy
    public static void XF(String  NazwaProcesu){
        System.out.println("Zowlnienie pamieci zajmowanej przez proces: " + NazwaProcesu);
        rejestry.r2=FSBSEM;
        Semafory.XP();
        WolnaLista.DodajWolnyBlok(ZajetaLista.Poczatek(NazwaProcesu), ZajetaLista.Rozmiar(NazwaProcesu));
        ZajetaLista.Usun(NazwaProcesu);
        if(MEMORY_SEM.VALUE < 0){
            rejestry.r2 = MEMORY_SEM;
            Semafory.XV(); //zawsze gsy zwalniany jest blok pamięci wykonywana jest operacja XV; lwywoałanXV == lZwolnienPamieci
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

}
