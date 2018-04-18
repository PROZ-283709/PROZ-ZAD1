package proz;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class LogonDialog
{
	private Dialog<ButtonType> dialog = new Dialog<>();
	
	private DataBase dataBase = new DataBase();

	private ChoiceBox<String> srodowiskoBox;
	private ComboBox<String> uzytkownikBox;
	private PasswordField hasloField;
	
	private ButtonType logonBut;
	private ButtonType anulujBut;
	
	Label srodowiskoLabel;
	Label uzytkownikLabel;
	Label hasloLabel;

	private Node logonButton;
	
	/**
	 * Metoda wypełniająca zarządce "GridPane" obiektami typu Label, ComboBox, ChoiceBox, PasswordField
	 *  
	 * @param grid obiekt typu GridPane, jest on wypełniany obiektami typu Label, ComboBox, ChoiceBox, PasswordField
	 */
	
	private void fillGrid(GridPane grid)
	{
	//VAR
		srodowiskoLabel = new Label("Środowisko:");
		uzytkownikLabel = new Label("Uzytkownik:");
		hasloLabel = new Label("Haslo:");
		
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
		String[] users = dataBase.getUsers(srodowiskoBox.getValue());
		
		uzytkownikBox.getItems().clear();
		
		if(users == null) return;
		
		uzytkownikBox.getItems().addAll(users[0], users[1], users[2]);
	}
	
	/**
	 * Metoda odpowiedzialna za wlaczanie i wylaczanie przycisku logon, wywolywana przy zmiane dowolnej wartosci z pól Srodowisko, Uzytkownik, Haslo
	 */
	private void fieldChanged()
	{	
		logonButton.setDisable( srodowiskoBox.getValue() == null || uzytkownikBox.getEditor().getText().trim().isEmpty() || hasloField.getText().isEmpty());
	}
	
	/**
	 *  Konwerter wyniku zwracanego przez okienko
	 * @buttonType Wynik zwrocony przez okienko
	 * @return Skonwertowany wynik
	 */
	private Optional<Pair<String, String>> resultConverter(Optional<ButtonType> buttonType)
	{	
		if(buttonType.isPresent() && buttonType.get() == logonBut)
		{
			return dataBase.isUserPassCorrect(srodowiskoBox.getValue(), uzytkownikBox.getEditor().getText(), hasloField.getText() )
				    ? Optional.of( new Pair<String,String>(srodowiskoBox.getValue(), uzytkownikBox.getEditor().getText() ) )
				    : Optional.empty();
		}
		
		return Optional.empty();
	}
	
	/**
	 * Konstruktor klasy LogonDialog 
	 * @param title tytul okienka logowania
	 * @param headerText naglowek okienka logowania
	 */
	public LogonDialog(String title, String headerText)
	{
	//VAR
		GridPane grid = new GridPane();
		
		logonBut = new ButtonType("Logon");
	    anulujBut = new ButtonType("Anuluj");
		
	//FILLING GRID
		fillGrid(grid);
		
	//DIALOG
		dialog.getDialogPane().getButtonTypes().addAll(logonBut, anulujBut);
		dialog.setTitle(title);
		dialog.setHeaderText(headerText);
		dialog.getDialogPane().setContent(grid);
	
		logonButton = dialog.getDialogPane().lookupButton(logonBut);
		logonButton.setDisable(true);
	}

	
	/**
	 * Wsywietlnie okienka i czekanie na uzytkownika
	 * @return skonwertowana wartosc zawierajaca pare Srodowisko, Uzytkownik, badz null w przypadku nieudanego logowania
	 */
	public Optional<Pair<String, String>> showAndWait()
	{
		return resultConverter(dialog.showAndWait());
	}
}