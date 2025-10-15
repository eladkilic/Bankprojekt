package oberflaeche;

import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class KontoStarter extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// 1. Create model
        Konto model = new Girokonto();
        
        // 2. Load FXML with controller
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource("/KontoOberflaeche2.fxml")
        );
        
//        // 3. Create controller with dependencies
        KontoController controll = new KontoController(model);
        JavaFXControllerFuerOberflaeche controller = new JavaFXControllerFuerOberflaeche(controll);
        loader.setController(controller);
//        
        // 4. Load UI
        Parent root = loader.load();
		
		Scene scene = new Scene(root, 300, 275);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Konto");
		primaryStage.show();
	}
}
