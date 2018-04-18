package proz;

import java.util.Optional;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

/**************************************
 * Klasa Glowna
 * @author Kamil Buczko
 * @version 1.3
 *************************************/

public class Main extends Application 
{	/**
	 * Glowna metoda uruchamiajaca okienko
	 * 
	 * @param args parametr przekazywany do javafx.application.Application.launch(String[] args)
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	
	/**
	 * Metoda wyswietlajaca okienko
	 * Wypisuje wynik dzialania okienka
	 */
	@Override
	public void start(Stage primaryStage)
	{	
		Optional<Pair<String, String>> result = (new LogonDialog("Logowanie",  "Logowanie do systemu STYLEman")).showAndWait();
		
		if(result.isPresent())
		{
			System.out.println("Zalogowano użytkownika: " + result.get().getValue() + " do srodowiska: " + result.get().getKey());
		}
		else
		{
			System.out.println("Nie udalo sie zalogowac");
		}
	}
	
}
