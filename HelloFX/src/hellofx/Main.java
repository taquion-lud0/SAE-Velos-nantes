/**
 * Classe Main, permet de lancer l'application JavaFX
 * @Author : Plantard Louis-Marie, Pineau Ludovic, Stephan Mathieu
 * @Version : 1.0
 */

package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * Main class de l'application JavaFX
 */
public class Main extends Application {

    @Override
    /**
     * Méthode start de l'application JavaFX
     * @param primaryStage - Stage de l'application
     * @throws Exception - Exception de l'application
     */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.getIcons().add(new Image("file:images/icon.jpeg"));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Accueil");
        primaryStage.setScene(scene);
        primaryStage.show();
   }
    public static void main(String[] args) {
        launch(args);
    }
}
