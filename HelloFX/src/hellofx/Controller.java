package hellofx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";

    @FXML
    TextField nameLoginTextField;
    @FXML
    TextField passwordLoginTextField;
    @FXML
    Label errorLabel;
    @FXML
    Button downloadDataButton;
    @FXML
    ComboBox<String> heureDebut;
    @FXML
    ComboBox<String> heureArrivee;
    @FXML
    ComboBox<String> nomPiste;
    @FXML
    Button importDataButton;

    public void initializeValueHeure(MouseEvent event) {
        if (heureDebut.getItems().isEmpty() && heureArrivee.getItems().isEmpty()) {
            heureDebut.getItems().addAll("00:00", "01:00", "02:00", "03:00", "04:00", "05:00","06:00","07:00","08:00","09:00","10:00","11:00",
                                        "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00", "20:00", "21:00", "22:00", "23:00");

            heureArrivee.getItems().addAll("00:00", "01:00", "02:00", "03:00", "04:00", "05:00","06:00","07:00","08:00","09:00","10:00","11:00",
                                        "12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00", "20:00", "21:00", "22:00", "23:00");
        }
    }

    public void initializeValuePiste(MouseEvent event) {
        if (nomPiste.getItems().isEmpty()) {
            nomPiste.getItems().addAll("Piste 1", "Piste 2", "Piste 3", "Piste 4", "Piste 5", "Piste 6", "Piste 7", "Piste 8", "Piste 9", "Piste 10");
        }
    }

        
    @FXML
    public String getHeureDebut(ActionEvent event) {
        String heureDeb =  heureDebut.getSelectionModel().getSelectedItem();
        return heureDeb;
    }
    /* 
    public void downloadData(){
        String fileURL = "https://example.com/file-to-download.txt"; // URL du fichier à télécharger

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un emplacement de téléchargement");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            try {
                downloadFile(fileURL, file);
                System.out.println("Le fichier a été téléchargé avec succès !");
            } catch (IOException e) {
                System.out.println("Une erreur s'est produite lors du téléchargement du fichier : " + e.getMessage());
            }
        }
    }

    private void downloadFile(String fileURL, File destinationFile) throws IOException {
        URL url = new URL(fileURL);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(destinationFile);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    */

    public void Connexion(ActionEvent event) throws IOException {
        String username = nameLoginTextField.getText();
        String password = passwordLoginTextField.getText();

        if (username.equals(USERNAME) && password.equals(PASSWORD)) {
            this.switchToUpdateData(event);
        } else {
            nameLoginTextField.clear();
            passwordLoginTextField.clear();
            errorLabel.setText("Nom d'utilisateur ou mot de passe incorrect");
    
        }

        
    }

    public void switchToMain(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAffluence(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Affluence.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCredit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Credit.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPistesEnv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PistesEnv.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //pour RechTrajet.fxml
    public void switchToRechTrajet(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RechTrajet.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //pour ResAffluence.fxml
    public void switchToResAffluence(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResAffluence.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //pour ResPistesEnv.fxml
    public void switchToResPistesEnv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResPistesEnv.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //pour ResRechTrajet.fxml
    public void switchToResRechTrajet(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResRechTrajet.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //pour ResTrafic.fxml
    public void switchToResTrafic(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResTrafic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }

    //pour Trafic.fxml
    public void switchToTrafic(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Trafic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }

    //pour UpdateData.fxml
    public void switchToUpdateData(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UpdateData.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show(); 
    }
}