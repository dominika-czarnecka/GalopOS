package pamiecOperacyjna;

class Element2 extends Element{

    private String nazwa_procesu;

    public Element2(int indeks, int rozmiar, String NazwaProcesu){
        super(indeks, rozmiar);
        this.nazwa_procesu = NazwaProcesu;
    }

    public String NAZWA(){
        return nazwa_procesu;
    }
}
