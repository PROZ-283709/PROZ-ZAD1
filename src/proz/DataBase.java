package proz;

public class DataBase {
	
	private String[][] proUsers;
	private String[][] tesUsers;
	private String[][] dewUsers;

	/**
	 * Metoda zwracajaca pary User/Password dla aktualnie wybranego srodowiska 
	 * 
	 * @param env aktualna wartosc pola "Srodowisko"
	 * @return  tablica typu String zawierajaca nazwy uzytkownikow dla akutalnego srodowiska
	 */
	public String[] getUsers(String env)
	{
		switch(env)
		{
			case "Produkcyjne": return proUsers[0];
				
			case "Testowe": return tesUsers[0];
				
			case "Deweloperskie": return dewUsers[0];			
				
			default: return null;
		}
	}

	/**
	 * 
	 * @param env aktualna wartosc pola "Srodowisko"
	 * @param user aktualna wartosc pola "Uzytkownik"
	 * @param password aktualna wartosc pola "Haslo"
	 * @return wartosc boolowska reprezentujaca poprawnosc wartosci pol Srodowisko, Uzytkownik, Haslo
	 */
	public boolean isUserPassCorrect(String env, String user, String password)
	{
		String[][] users;
		
		switch(env)
		{
			case "Produkcyjne":
				users = proUsers;
				break;
			
			case "Testowe":
				users = tesUsers;
				break;
				
			case "Deweloperskie":
				users = dewUsers;		
				break;
				
			default: users = null;
		}
		
		if(users == null) return false;
		
		for(int i = 0 ; i<5 ; ++i)
		{
			if(users[0][i].equals(user) && users[1][i].equals(password) ) return true;
		}
		
		return false;
	}

	/**
	 *  Konstruktor klasy DataBase
	 *  wczytujace pary User/Password do odpowiednich dla srodowisk tablic
	 */
	public DataBase()
	{
		proUsers = new String[2][5];
		tesUsers = new String[2][5];
		dewUsers = new String[2][5];
		
		proUsers[0][0] = "Jakub.Nowak"; 	proUsers[1][0] = "123";
		proUsers[0][1] = "Lena.Kowalska"; 	proUsers[1][1] = "razdwatrzy";
		proUsers[0][2] = "Antoni.Mazur"; 	proUsers[1][2] = "Antos";
		proUsers[0][3] = "Julia.Wozniak";   proUsers[1][3] = "ha slo";
		proUsers[0][4] = "Szymon.Krawczyk"; proUsers[1][4] = "12trzy";
		
		tesUsers[0][0] = "Maja.Zajac";		tesUsers[1][0] = "qwe";
		tesUsers[0][1] = "Jan.Duda"; 		tesUsers[1][1] = "asdfg";
		tesUsers[0][2] = "Zofia.Szewczyk"; 	tesUsers[1][2] = "Szewc";
		tesUsers[0][3] = "Filip.Kiszczak"; 	tesUsers[1][3] = "Filipek";
		tesUsers[0][4] = "Hanna.Obalek"; 	tesUsers[1][4] = "Hanka";
		
		dewUsers[0][0] = "Marcin.Janda"; 	dewUsers[1][0] = "qwaszx";
		dewUsers[0][1] = "Alicja.Lizuk"; 	dewUsers[1][1] = "asdf";
		dewUsers[0][2] = "Kacper.Cieslak"; 	dewUsers[1][2] = "asdfg";
		dewUsers[0][3] = "Maria.Wilk"; 		dewUsers[1][3] = "trudne haslo";
		dewUsers[0][4] = "Tomasz.Lis";		dewUsers[1][4] = "Lis Ek";
	}
}

