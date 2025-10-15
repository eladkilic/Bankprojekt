package oberflaeche;

import bankprojekt.geld.Waehrung;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class KontoController {
	private Konto model;
	private Parent view;
	
	public Parent getView() {
		return view;
	}
	private Stage stage;
	
	public KontoController(Konto model) {
		this.model=model;
//		this.stage = primaryStage;
//		model = new Girokonto();
//		this.view = view.getView(model, this);
	}
	
	public Konto getModel() {
		return model;
	}

	public Object farbeAendern() {
		// TODO Auto-generated method stub
		return null;
	}

	protected void handleEinzahlen(TextField betrag, Waehrung neu, Text meldung) {
		try {
		double betro = Double.parseDouble(betrag.getText());
		Geldbetrag einz = new Geldbetrag(betro,neu);
		Geldbetrag neusumme = einz.umrechnen(neu);
		model.einzahlen(neusumme);
		meldung.setText(String.format("Einzahlung: +%.2f %s", betro, neu));
		meldung.setFill(Color.GREEN);
		} catch (IllegalArgumentException ex) {
			 meldung.setText("Fehler: " + ex.getMessage());
		     meldung.setFill(Color.RED);
		 } catch (GesperrtException e) {
			 meldung.setText("Fehler: " + e.getMessage());
		     meldung.setFill(Color.RED);
		} 
	}

	protected void handleAbheben(TextField betrag, Waehrung neu, Text meldung) {
		try {
			double betro = Double.parseDouble(betrag.getText());
			Geldbetrag einz = new Geldbetrag(betro,neu);
			Geldbetrag neusumme = einz.umrechnen(neu);
			
			model.abheben(neusumme);
			meldung.setText(String.format("Abhebung: -%.2f %s", betro, neu));
			meldung.setFill(Color.RED);
		} catch (GesperrtException e) {
			meldung.setText("Fehler: " + e.getMessage());
		} catch (IllegalArgumentException ex) {
			 meldung.setText("Fehler: " + ex.getMessage());
		}
	}
	
	public void schliessen() {
		stage.close();
	}
	

}
