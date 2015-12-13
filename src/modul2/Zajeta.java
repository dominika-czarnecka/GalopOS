package modul2;
import javax.swing.*;
import java.util.*;


public class Zajeta {
    private static List<Element2> List;

    public Zajeta(){
        List= new ArrayList<Element2>();
    }


    public static int Poczatek(String NazwaProcesu){
        for(int i=0; i<List.size(); i++){
            if(List.get(i).NAZWA() == NazwaProcesu )
                return List.get(i).Poczatek();
        }
        return -1;
    }


    public void Dodaj(int indeks, int rozmiar, String NazwaProcesu){
        for(int i=0; i< List.size(); i++){
            if(List.get(i).Poczatek() > indeks ) {
                List.set(i, new Element2(indeks, rozmiar, NazwaProcesu));
                return;
            }
        }
        List.add(new Element2(indeks, rozmiar, NazwaProcesu));
    }

    public void Usun(String NazwaProcesu){
        for(int i=0; i<List.size(); i++){
            if(List.get(i).NAZWA() == NazwaProcesu){
                List.remove(i);
                return;
            }
        }
    }
    public int Rozmiar(String NazwaProcesu){
        for(int i=0; i< List.size(); i++) {
            if(List.get(i).NAZWA() == NazwaProcesu )
                return List.get(i).ZwrocRozmiar();
        }
        return -1;
    }


    public static void Przesun(char[] RAM){
        int nastepny=0;
        int przesuniecie;

        for(int i=0; i < List.size(); i++){
            przesuniecie = List.get(i).Poczatek() -nastepny;
            if(przesuniecie !=0 ){
                List.get(i).ZmienRozmiar(nastepny);
                for(int j=0 ; j<List.get(i).ZwrocRozmiar(); j++){
                    RAM[nastepny+j] = RAM[nastepny + przesuniecie + j]; //kopiowanie obszarów
                }
            }
            nastepny = List.get(i).Poczatek() + List.get(i).ZwrocRozmiar(); //Nastepny przesuniety na kolejny blok
        }
    }

    public static int Ostatni(){
        int i= List.size();
        return List.get(i-1).Poczatek() + List.get(i-1).ZwrocRozmiar();
    }

    public  static void Wyswietl(){
        int indeks, rozmiar =0;
       System.out.println("---------------Zajęta Lista - bloki ------------------");
        for(int i=0; i< List.size(); i++){
            indeks = i+1;
            rozmiar += List.get(i).ZwrocRozmiar();
            System.out.println("["+indeks + "]" + "Indeks bloku: " + List.get(i).Poczatek() + "identyfikator procesu: " + List.get(i).NAZWA() + "Rozmiar bloku:" + List.get(i).ZwrocRozmiar());
        }
        System.out.println("Rozmiar Pamieci zajętej: " + rozmiar + "\n");
    }
}


