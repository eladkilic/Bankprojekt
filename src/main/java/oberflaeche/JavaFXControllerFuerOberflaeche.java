package oberflaeche;

import bankprojekt.geld.Waehrung;
import bankprojekt.verarbeitung.Konto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class JavaFXControllerFuerOberflaeche {
	@FXML private Text nummer;
	@FXML private Text stand;
	@FXML private CheckBox gesperrt;
	@FXML private TextArea adresse;
	@FXML private Text meldung;
	@FXML private ChoiceBox<Waehrung> waehrung;
	@FXML private Button einzahlen;
	@FXML private Button abheben;
	@FXML private TextField betrag;
	
	private KontoController controller;
	private Konto model;
	
	public JavaFXControllerFuerOberflaeche(KontoController controller) {
		this.controller = controller;
		this.model = controller.getModel();
	}
	
	@FXML private void initialize() {
		nummer.setText(String.valueOf(model.getKontonummer()));
		stand.textProperty().bind(model.kontostandProperty().asString());
		gesperrt.selectedProperty().bindBidirectional(model.gesperrtProperty());
		adresse.textProperty().bindBidirectional(model.getInhaber().adresseProperty());
		
		waehrung.setItems(FXCollections.observableArrayList(Waehrung.values()));
		waehrung.getSelectionModel().select(0);
		
		einzahlen.setOnAction(e -> controller.handleEinzahlen(betrag, waehrung.getValue(), meldung));
		
		abheben.setOnAction(e -> controller.handleAbheben(betrag, waehrung.getValue(), meldung));
		
	}
	
	@FXML public Konto getModel() {
		return this.model;
	}
	
	@FXML private void schliessen() {
		controller.schliessen();
	}
	

}
