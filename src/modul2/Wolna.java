package modul2;
import java.util.*;


//Blok wolnej pamieci FSB Free Storage Block
    //Dwa bloki nigdy ze sobą nie graniczą bo wedy zostałyby scalone w jeden (!)

public class Wolna {
///
    private ArrayList<Element> List;
    //UNUSED - niewykorzystane
    public static int wolna; //dodatkowa zmienna na wolne bloki; przyda się przy scalaniu

    //konstruktor
    public Wolna(int rozmiar){ //jako rozmiar podac cały MEMORY_SIZE
       // rozmiar = Pamiec.MEMORY_SIZE;
        List = new ArrayList<Element>() ; //zaalokowanie pamieci
        Element e= new Element(0, rozmiar);
        List.add(e); //na poczatku calosc wolna 0-pierwszy indeks
        System.out.println(e.ZwrocRozmiar());
        wolna = rozmiar;
        System.out.println("wolna: " + wolna);

    }

    public static int Wolna(){ //zwraca całkowitą ilość wolnego miejsca
        return wolna;
    }


    public void Wyswietl(){


        if (List.size() >0){
            System.out.println("-----LISTA WOLNA-------");
            for(int i=0; i< List.size(); i++){
                int nr = i+1;
                System.out.println( nr + ". " + "Indeks: " + List.get(i).Poczatek() + "\t Rozmiar:" + List.get(i).ZwrocRozmiar() );
        }
        System.out.println("Pamiec wolna - ilosc wolnego miejsca: " + wolna + "\n\n");
        }
        else{
            System.out.println("Pamiec wolna jest pusta\n");
        }


    }


    public void Ostatni(){

    }

    //ZWOLNIENIE BLOKU + scalanie bloków wolnych sąsiadujących
    //Gdy usunie sie jakis blok - dodanie WOLNEGO miejsca
    public void DodajWolnyBlok(int indeks, int rozmiar){
        wolna += rozmiar;
        for (int i=0; i < List.size(); i++){ //Poczatek to po prostu indeks do poczatku bloku
            if (List.get(i).Poczatek() > indeks ){ //gdy zwolnił sie jakis blok w dalszej czesci pamieci
                List.set(i, new Element(indeks, rozmiar));

            }
        }
        List.add(new Element(indeks, rozmiar));
        Scal(List.size()-1) ; //jak scalą się elementy - to rozmiar listy trzeba zmneijszyć o jeden (dwa scalone)-=1
    }



    //scal gdy dwa bloki ze sobą sąsiadują - UWAGA na pierwszy i ostatni blok listy - problemy w porównaniach
    private void Scal(int indeks){
        if(List.size() <=1)
            return; //Nie ma co scalac jak tak mało elementów

        if((indeks -1 )>= 0){ //NIE jest to pierwszy poczatkowy blok listy; numeracja bloków od 0
            if (List.get(indeks -1).Porownaj(List.get(indeks))){ //porownaj poprzedni z obecny czy ze sobą sąsiadują te bloki
                List.get(indeks -1).PowiekszRozmiar(List.get(indeks).ZwrocRozmiar());
                List.remove(indeks);
                indeks--;
            }
        }
        if ((indeks + 1) < List.size()){ //jeżei nie jest to ostatni element listy
            if(List.get(indeks).Porownaj(List.get(indeks+1))){ //czy te bloki ze soba sąsiadują?
                List.get(indeks).PowiekszRozmiar(List.get(indeks+1).ZwrocRozmiar());
                List.remove(indeks+1);
            }
        }

    }

    public int Wpisz(int list_index, int rozmiar){
        wolna -= rozmiar;
        int index = List.get(list_index).Poczatek();
        if(List.get(list_index).ZwrocRozmiar() == rozmiar){
            List.remove(list_index);
        }
        else{
            List.get(list_index).ZmniejszRozmiar(rozmiar);
        }
        return index;
    }

    public int ZnajdzWolne( int size){ //szukaj wolnego obszaru o rozmiarze size
        try{
        if(List.size() > 0) {
            for (int i = 0; i < List.size(); i++) {
                if (List.get(i).ZwrocRozmiar() >= size)
                    return i;
                else
                    return -1; //-1 jak nie ma wolnego bloku
            }
        }
        System.out.println("Brak Blokow wolnej pamieci\n");

    }catch(Exception ex){ ex.printStackTrace();}
        return -1;
    }

    public void Wykasuj(int indeks){
        List.clear(); //lisa staje się pussta
        List.add(new Element(indeks, Pamiec.MEMORY_SIZE - indeks) );
    }
}
