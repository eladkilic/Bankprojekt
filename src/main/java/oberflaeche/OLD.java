////package oberflaeche;
////
////import bankprojekt.verarbeitung.Girokonto;
////import bankprojekt.verarbeitung.Konto;
////import javafx.application.Application;
////import javafx.fxml.FXMLLoader;
////import javafx.stage.Stage;
////import javafx.scene.Parent;
////import javafx.scene.Scene;
////
////public class KontoStarter extends Application{
////	
////	@Override
////	public void start(Stage primaryStage) throws Exception {
////		//1. create model
////		Konto model = new Girokonto();
////		
////		//2. load fxml with controller
////		FXMLLoader loader = new FXMLLoader(getClass().getResource("kontooberflaeche2.fxml"));
////		
////		//pass model to controller
////		loader.setController(new JavaFXControllerFuerOberflaeche(model));
//////		loader.setControllerFactory(type -> {
//////			if (type == KontoController.class) {
//////				return new KontoController(model);
//////			} 
//////			throw new IllegalArgumentException("Unexpected controller type");
//////		});
////		
////		//3. Load UI
////		Parent root = loader.load();
////
////		Scene scene = new Scene(root, 300, 275);
////		primaryStage.setScene(scene);
////		primaryStage.setTitle("Konto");
////		primaryStage.show();
////	}
////}
//
//package oberflaeche;
//
//import bankprojekt.geld.Waehrung;
//import bankprojekt.verarbeitung.Geldbetrag;
//import bankprojekt.verarbeitung.GesperrtException;
//import bankprojekt.verarbeitung.Girokonto;
//import bankprojekt.verarbeitung.Konto;
//import javafx.scene.control.TextField;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//
//public class KontoController {
//	private Konto model;
//
//	public KontoController(Konto model) {
//		this.model = model;
//	}
//	
//	public Konto getModel() {
//		return model;
//	}
//
//	public Object farbeAendern() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void handleEinzahlen(TextField betrag, Waehrung neu, Text meldung) {
//		try {
//		double betro = Double.parseDouble(betrag.getText());
//		Geldbetrag einz = new Geldbetrag(betro,neu);
//		Geldbetrag neusumme = einz.umrechnen(neu);
//		model.einzahlen(neusumme);
//		meldung.setText(String.format("Einzahlung: +%.2f %s", betro, neu));
//		meldung.setFill(Color.GREEN);
//		} catch (IllegalArgumentException ex) {
//			 meldung.setText("Fehler: " + ex.getMessage());
//		     meldung.setFill(Color.RED);
//		 } catch (GesperrtException e) {
//			 meldung.setText("Fehler: " + e.getMessage());
//		     meldung.setFill(Color.RED);
//		} 
//	}
//
//	public void handleAbheben(TextField betrag, Waehrung neu, Text meldung) {
//		try {
//			double betro = Double.parseDouble(betrag.getText());
//			Geldbetrag einz = new Geldbetrag(betro,neu);
//			Geldbetrag neusumme = einz.umrechnen(neu);
//			
//			model.abheben(neusumme);
//			meldung.setText(String.format("Abhebung: -%.2f %s", betro, neu));
//			meldung.setFill(Color.RED);
//		} catch (GesperrtException e) {
//			meldung.setText("Fehler: " + e.getMessage());
//		} catch (IllegalArgumentException ex) {
//			 meldung.setText("Fehler: " + ex.getMessage());
//		}
//	}
//	
//
//}
//
