package proz;

import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**************************************
 * Moja klasa implementujaca okienko logowania
 * @author Kamil Buczko
 * @version 1.0
 *************************************/
public class LogWindow extends Application 
{	
	private String[][] proUsers;
	private String[][] tesUsers;
	private String[][] dewUsers;
	
	private Dialog<ButtonType> dialog = new Dialog<>();
	
	private ChoiceBox<String> srodowiskoBox;
	private ComboBox<String> uzytkownikBox;
	private PasswordField hasloField;
	
	private Node logonButton;
	
	/**
	 * Metoda wypelniajaca zarzadce "GridPane" obiektami typu Label, ComboBox, ChoiceBox, PasswordField
	 *  
	 * @param grid obiekt typu GridPane, jest on wypelniany obiektami typu Label, ComboBox, ChoiceBox, PasswordField
	 */
	private void fillGrid(GridPane grid)
	{
	//VAR
		Label srodowiskoLabel = new Label("Środowisko:");
		Label uzytkownikLabel = new Label("Uzytkownik:");
		Label hasloLabel = new Label("Haslo:");
		
		srodowiskoBox = new ChoiceBox<>();
		uzytkownikBox = new ComboBox<>();
		hasloField = new PasswordField();
		
	//CHOICEBOX
		srodowiskoBox.setPrefSize(175, 40);
		srodowiskoBox.getItems().addAll("Produkcyjne", "Testowe", "Deweloperskie");
		srodowiskoBox.setOnAction( e -> srodowiskoBoxChanged() );				
		srodowiskoBox.valueProperty().addListener((obs, oldVal, newVal) -> fieldChanged() );
				
	//COMBO BOX
		uzytkownikBox.setPrefSize(175,40);
		uzytkownikBox.setEditable(true);		
		uzytkownikBox.getEditor().textProperty().addListener((obs, oldVal, newVal) -> fieldChanged() );
				
	//PASSWORD FIELD
		hasloField.setPrefSize(175, 40);
		hasloField.textProperty().addListener((obs, oldVal, newVal) -> fieldChanged() );
					
	//GRID PANE
		grid.setVgap(20);
		grid.setHgap(20);	
		grid.setPadding(new Insets(30, 20, 20, 30));
			
		grid.getChildren().addAll(srodowiskoLabel, uzytkownikLabel, hasloLabel, srodowiskoBox, uzytkownikBox, hasloField);
				
		GridPane.setConstraints(srodowiskoLabel, 0, 0);
		GridPane.setConstraints(uzytkownikLabel, 0, 1);
		GridPane.setConstraints(hasloLabel, 0, 2);
			
		GridPane.setConstraints(srodowiskoBox, 1, 0);
		GridPane.setConstraints(uzytkownikBox, 1, 1);
		GridPane.setConstraints(hasloField, 1, 2);
	}
	
	/**
	 * Metoda odpowiedzialna za zmiane zawartosci obiektu uzytkownikBox, przy zmiane obiektu srodowiskoBox
	 */
	private void srodowiskoBoxChanged()
	{
		String[][] users = getUsers(srodowiskoBox.getValue());
		
		uzytkownikBox.getItems().clear();
		
		if(users == null) return;
		
		uzytkownikBox.getItems().addAll(users[0][0], users[0][1], users[0][2]);
	}
	
	/**
	 * Metoda zwracajaca pary User/Password dla akutalnie wybranego srodowiska 
	 * 
	 * @param env aktualna wartosc pola "Srodowisko"
	 * @return dwuwymiarowa tablica typu String zawierajaca pary User/Password dla akutalnego srodowiska
	 */
	private String[][] getUsers(String env)
	{
		switch(env)
		{
			case "Produkcyjne": return proUsers;
				
			case "Testowe": return tesUsers;
				
			case "Deweloperskie": return dewUsers;			
				
			default: return null;
		}
	}
	
	/**
	 * Metoda odpowiedzialna za wlaczanie i wylaczanie przycisku logon, wywolywana przy zmiane dowolnej wartosci z pól Srodowisko, Uzytkownik, Haslo
	 */
	private void fieldChanged()
	{	
		logonButton.setDisable( srodowiskoBox.getValue() == null || uzytkownikBox.getEditor().getText().trim().isEmpty() || hasloField.getText().isEmpty());
	}
	
	/**
	 * 
	 * @param env aktualna wartosc pola "Srodowisko"
	 * @param user aktualna wartosc pola "Uzytkownik"
	 * @param password aktualna wartosc pola "Haslo"
	 * @return wartosc boolowska reprezentujaca poprawnosc wartosci pol Srodowisko, Uzytkownik, Haslo
	 */
	private boolean isUserPassCorrect(String env, String user, String password)
	{
		String[][] users = getUsers(env);
		
		if(users == null) return false;
		
		for(int i = 0 ; i<5 ; ++i)
		{
			if(users[0][i].equals(user) && users[1][i].equals(password) ) return true;
		}
		
		return false;
	}
	
	/**
	 * Metoda konwertujaca wartosc zwrocona przez dialog na obiekt typu Pair<String, String>
	 * 
	 * @param buttonType obiekt zwracany przez dialog przy jego zamknieciu
	 * @param logonBut obiekt Typu ButtonType sluzacy do sprawdzenia ktory przycisk zostal klikniety przy zamykaniu okienka
	 * @return Przy poprawnych danych logowania Para Srodowisko/Uzytkownik zalogowanego uzytkownika, w przeciwnym przypadku null
	 */
	private Pair<String,String> resultConverter(Optional<ButtonType> buttonType, ButtonType logonBut)
	{
		if(buttonType.isPresent() && buttonType.get() == logonBut)
		{
			return isUserPassCorrect(srodowiskoBox.getValue(), uzytkownikBox.getEditor().getText(), hasloField.getText() )
				    ? new Pair<>(srodowiskoBox.getValue(), uzytkownikBox.getEditor().getText() )
				    : null;
		}
		
		return null;
	}
	
	/**
	 * Glowna metoda uruchamiajaca okienko
	 * 
	 * @param args parametr przekazywany do javafx.application.Application.launch(String[] args)
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
	
	/**
	 * Metoda wczytujaca pary User/Password do odpowiednich dla srodowisk tablic
	 */
	@Override
	public void init()
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
	
	/**
	 * Metoda konstruujaca dialog oraz wyswietlajaca okienko.
	 * Wypisuje wynik dzialania okienka
	 */
	@Override
	public void start(Stage primaryStage) throws Exception
	{
	//VAR
		GridPane grid = new GridPane();
		
		ButtonType logonBut = new ButtonType("Logon");
		ButtonType anulujBut = new ButtonType("Anuluj");
		
	//FILLING GRID
		fillGrid(grid);
		
	//DIALOG
		dialog.getDialogPane().getButtonTypes().addAll(logonBut, anulujBut);
		dialog.setTitle("Logowanie");
		dialog.setHeaderText("Logowanie do systemu STYLEman");
		dialog.getDialogPane().setContent(grid);
	
		logonButton = dialog.getDialogPane().lookupButton(logonBut);
		logonButton.setDisable(true);
			
	//URuCHOMIENIE DIALOGU I KONWEROWANIE JEGO REZULTATU
		Pair<String, String> result = resultConverter( dialog.showAndWait(), logonBut );
		
	//WYSWIETLENIE REZULTATU
		if(result != null) System.out.println("Środowisko=" + result.getKey() + ", Użytkownik=" + result.getValue());
	}
}
