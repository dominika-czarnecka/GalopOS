package modul2;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    //----------------------------------------------------------------------//
    public static void  OdczytPliku(String nazwaPliku) throws FileNotFoundException{
        File file = new File(nazwaPliku);
        Scanner in = new Scanner(file);

        String zdanie = in.nextLine();
        System.out.println(zdanie);
    }

    public static void Zapis() throws FileNotFoundException{
        PrintWriter zapis = new PrintWriter("ala.txt");
        zapis.println("Ala ma kota, a kot ma Alę");
        zapis.close();
    }

//----------------------------------------------------------------------
    public static void ZapiszDoPamieci(){
        ;
    }

    //-------------------------------------------MAIN---------------------------------------------------------------
    public static void main(String[] args)  throws FileNotFoundException{

        Pamiec T_Pamiec = new Pamiec(); //utworzenie testowej pamieci


        //T_ZapiszDoPamieci();
        OdczytPliku("test.txt");

    }




}
