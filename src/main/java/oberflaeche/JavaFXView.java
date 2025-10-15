package oberflaeche;

import java.io.IOException;

import bankprojekt.verarbeitung.Konto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class JavaFXView {
	
	public Parent getView(Konto model, KontoController mvcController) {
		JavaFXControllerFuerOberflaeche oberflaeche = 
				new JavaFXControllerFuerOberflaeche(mvcController);
		FXMLLoader loader = 
				new FXMLLoader(getClass().
					getResource("/KontoOberflaeche2.fxml"));
		loader.setController(oberflaeche);
		try {
			return loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
