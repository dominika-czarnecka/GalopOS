package fat;

public class main_class {

	public static void main(String[] args) 
	{
		hdd_commander driver= new hdd_commander(32,32);
		driver.create("pliczek", "siema pezet z tej strony nagrywamy rap one shot. cos... cos sie popsulo");
		driver.create("na_pozny_wieczor",
				"To co� czego si� nie da opisa�, "
				+ "bo nadal nie mam tu �ycia, "
				+ "bo to co w genach nie daje mi tu na splifa."
				+ "Nie daje na nic, dlatego brak mi powagi, "
				+ "bo robi� tu godzinami za grosze i prawie zdycham."
				+ "Tacy pijani zbieramy si� rano bladzi, "
				+ "mam plany raczej bez granic, a jeden z nich to jest p�yta."
				+ "Widz� to, tam scena, ja i muzyka, "
				+ "ka�dy chcia� by tu sos, a ja staram si� tym oddycha�."
				+ "Sos jest dodatkowy (hee..), to mam raczej z g�owy, bo"
				+ "z takim flow raczej mog� liczy� na plony (hee..)."
				+ "Nie wiem czy to najlepsze dni jak u Eisa,"
				+ "na razie zbieram na tyto� i pewnie si� gdzie� poszwendam."
				+ "Najlepiej solo, tak dla mnie jest kolorowo, "
				+ "mam fajne �ycie i pono� gram fajnie i daj� s�owo, "
				+ "�e b�d� kuma� s�uchaczy i moich ludzi, "
				+ "i mo�e mi si� uda by z trasy wraca�y t�umy");
		driver.fat_show();
		driver.catalog_show();
		driver.driver_show();
		driver.create("plik2", "chyba juz zatoczylem kolo");
		driver.edit("plik2", " chyba juz zatoczylem kolo");
		System.out.println(driver.read("plik2"));
		driver.fat_show();
		driver.catalog_show();
		driver.driver_show();
		driver.delete("na_pozny_wieczor");
		driver.create("plik3", ".");
		//System.out.println(driver.read("plik2"));
		driver.fat_show();
		driver.catalog_show();
		driver.driver_show();
	}
	

}
