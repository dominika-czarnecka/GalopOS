
package modul2;

import java.util.Comparator;

public class Element {
    private int indeks;
    private int rozmiar;

    public Element(int indeks, int rozmiar){
        this.indeks = indeks;
        this.rozmiar = rozmiar;
    }

    public int Poczatek(){ //jakby wskaznik poczatkowy do bloku - potrzebne przy scalaniu
        return indeks;
    }

    public void ustawPoczatek(int i){
        indeks = i;
    }
    
    public int ZwrocRozmiar(){
        return rozmiar;
    }

    public void PowiekszRozmiar(int rozmiar){
        this.rozmiar += rozmiar;
    }

    public void ZmniejszRozmiar(int rozmiar){
        this.rozmiar -= rozmiar;
    }

    public void ZmienRozmiar(int rozmiar){
        this.indeks = rozmiar;
    }

    public boolean czySasiaduja ( Element inny){
        if( (indeks + rozmiar) == inny.indeks || (inny.indeks + inny.rozmiar) == indeks){
            return true;
        }
        return false;
    }
}
