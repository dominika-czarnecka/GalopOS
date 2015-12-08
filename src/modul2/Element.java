package pamiecOperacyjna;
//dodaje komentarz Pawlaczyk
public class Element {
    private int indeks;
    private int rozmiar;

    public Element(int i, int r){
        this.indeks = i;
        this.rozmiar = r;
    }

    public int Poczatek(){ //jakby wskaznik poczatkowy do bloku - potrzebne przy scalaniu
        return indeks;
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

    public boolean Porownaj ( Element inny){ //sprawdza czy bloki ze soba sąsiadują
        if( (indeks + rozmiar) == inny.indeks){
            return true;
        }
        return false;
    }

    //Dwa bloki nigdy ze sobą nie graniczą bo wedy zostałyby scalone w jeden (!)
    public void Scal(Element inny){
        rozmiar += inny.rozmiar;
    }

}
