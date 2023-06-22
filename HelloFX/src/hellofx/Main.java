/**
 * Main.java
 * @version 1.0
 * @package hellofx
 * @project velo_bdd
 * @date 22/06/2023 
 */

package hellofx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Main extends Application {

    @Override
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
