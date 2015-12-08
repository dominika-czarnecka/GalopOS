package modul4;

public class main_class {

	public static void main(String[] args) 
	{
		hdd_commander driver= new hdd_commander(32,32);
		driver.create("pliczek", "siema pezet z tej strony nagrywamy rap one shot. cos... cos sie popsulo");
		driver.create("na_pozny_wieczor",
				"To coœ czego siê nie da opisaæ, "
				+ "bo nadal nie mam tu ¿ycia, "
				+ "bo to co w genach nie daje mi tu na splifa."
				+ "Nie daje na nic, dlatego brak mi powagi, "
				+ "bo robiê tu godzinami za grosze i prawie zdycham."
				+ "Tacy pijani zbieramy siê rano bladzi, "
				+ "mam plany raczej bez granic, a jeden z nich to jest p³yta."
				+ "Widzê to, tam scena, ja i muzyka, "
				+ "ka¿dy chcia³ by tu sos, a ja staram siê tym oddychaæ."
				+ "Sos jest dodatkowy (hee..), to mam raczej z g³owy, bo"
				+ "z takim flow raczej mogê liczyæ na plony (hee..)."
				+ "Nie wiem czy to najlepsze dni jak u Eisa,"
				+ "na razie zbieram na tytoñ i pewnie siê gdzieœ poszwendam."
				+ "Najlepiej solo, tak dla mnie jest kolorowo, "
				+ "mam fajne ¿ycie i ponoæ gram fajnie i dajê s³owo, "
				+ "¿e bêdê kuma³ s³uchaczy i moich ludzi, "
				+ "i mo¿e mi siê uda by z trasy wraca³y t³umy");
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
