package modul2;

class ElementZaj extends Element{

    private String nazwa_procesu;

    public ElementZaj(int indeks, int rozmiar, String NazwaProcesu){
        super(indeks, rozmiar);
        this.nazwa_procesu = NazwaProcesu;
    }

    public String NAZWA(){
        return nazwa_procesu;
    }
}
