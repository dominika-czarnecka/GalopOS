package modul2;
import javax.swing.*;
import java.util.*;


public class Zajeta {
    private static ArrayList<ElementZaj> List;

    public static int zajeta;
    
    public Zajeta(){
        List= new ArrayList<ElementZaj>();
        zajeta = 0;
    }


    public static int Poczatek(String NazwaProcesu){
        for(int i=0; i<List.size(); i++){
            if(List.get(i).pobierzNazwe().equals(NazwaProcesu) )
                return List.get(i).Poczatek();
        }
        return -1;
    }


    public void Dodaj(int indeks, int rozmiar, String NazwaProcesu){
        /*for(int i=0; i< List.size(); i++){
            if(List.get(i).Poczatek() > indeks ) {
                List.set(i, new ElementZaj(indeks, rozmiar, NazwaProcesu));
                return;
            }
        }*/
        List.add(new ElementZaj(indeks, rozmiar, NazwaProcesu));
    }

    public void Usun(String NazwaProcesu){
        for(int i=0; i<List.size(); i++){
            if(List.get(i).pobierzNazwe().equals(NazwaProcesu)){
                List.remove(i);
                return;
            }
        }
    }
    
    public int Rozmiar(String NazwaProcesu){
        for(int i=0; i< List.size(); i++) {
            if(List.get(i).pobierzNazwe().equals(NazwaProcesu) )
                return List.get(i).ZwrocRozmiar();
        }
        return -1;
    }

    public  static void Wyswietl(){
        int indeks, rozmiar =0;

        if(List.size() >0) {
            System.out.println("---------------Zajeta Lista - bloki ------------------");
            for (int i = 0; i < List.size(); i++) {
                indeks = i + 1;
                rozmiar += List.get(i).ZwrocRozmiar();
                System.out.println("[" + indeks + "]" + "Indeks bloku: " + List.get(i).Poczatek() + "identyfikator procesu: " + List.get(i).pobierzNazwe() + "Rozmiar bloku:" + List.get(i).ZwrocRozmiar());
            }
            System.out.println("Rozmiar Pamieci zajetej: " + rozmiar + "\n");
        }
        else{
            System.out.println("Lista Zajeta jest pusta\n");
        }

    }
}


