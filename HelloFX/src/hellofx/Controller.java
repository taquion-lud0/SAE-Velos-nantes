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
import java.sql.Date;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Requetes requetes;
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
    ComboBox<String> typeQueryAffluence;
    @FXML
    Button importDataButton;
    @FXML 
    DatePicker dateDebut;
    @FXML
    DatePicker dateArrivee;
    @FXML
    ComboBox<String> heureRech;
    @FXML
    ComboBox<String> specSelec;
    @FXML 
    DatePicker dateSelec;
    

    public Controller() {
        this.requetes = new Requetes(new ArrayList<String>());
    }

    public void initializeValueHeure(MouseEvent event) throws IOException {
        if (heureDebut.getItems().isEmpty() && heureArrivee.getItems().isEmpty()) {
            heureDebut.getItems().addAll("h00", "h01", "h02", "h03", "h04", "h05","h06","h07","h08","h09","h10","h11",
                                        "h12","h13","h14","h15","h16","h17","h18","h19", "h20", "h21", "h22", "h23");

            heureArrivee.getItems().addAll("h00", "h01", "h02", "h03", "h04", "h05","h06","h07","h08","h09","h10","h11",
                                        "h12","h13","h14","h15","h16","h17","h18","h19", "h20", "h21", "h22", "h23");

            heureRech.getItems().addAll("h00", "h01", "h02", "h03", "h04", "h05","h06","h07","h08","h09","h10","h11",
                                        "h12","h13","h14","h15","h16","h17","h18","h19", "h20", "h21", "h22", "h23");
        }
    }

    public void initializeValueTypeQueryAffluence(MouseEvent event) throws IOException {
        if (typeQueryAffluence.getItems().isEmpty()) {
            typeQueryAffluence.getItems().addAll("Affluence d'une piste spécifique", "Piste ayant le plus d'affluence", "Piste ayant le moins d'Affluence");
        }
    }

    public void initializeValuePiste(MouseEvent event) throws IOException {
        if (nomPiste.getItems().isEmpty()) {
            requetes.addToPisteList();
            ArrayList<String> lesCompteurs = requetes.getPisteList();
            for(String piste : lesCompteurs) {
                this.nomPiste.getItems().add(piste);
            }
        }
    }

    public void initializeSpecSelec (MouseEvent event) throws IOException {
        if (specSelec.getItems().isEmpty()) {
            specSelect.getItems().addAll("Piste", "Compteur", "Capteur");
        }
    }

    @FXML 
    //getting the value of the selected item in the combobox
    public String getNomPiste(ActionEvent event) {
        String nomPiste =  this.nomPiste.getSelectionModel().getSelectedItem();
        return nomPiste;
    }

    @FXML
    public Date getDateDebut(ActionEvent event) {
        Date dateDeb = Date.valueOf(dateDebut.getValue());
        return dateDeb;
    }

    @FXML
    public Date getDateArrivee(ActionEvent event) {
        Date dateArr = Date.valueOf(dateArrivee.getValue());
        return dateArr;
    }


    @FXML
    public String getHeureArrivee(ActionEvent event) {
        String heureArr =  heureArrivee.getSelectionModel().getSelectedItem();
        return heureArr;
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