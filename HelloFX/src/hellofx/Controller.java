package hellofx;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.File;
//

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Requetes requetes;
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "password";
    private FileChooser fileChooser;

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
    @FXML
    ComboBox<String> pisteDepart;
    @FXML
    ComboBox<String> pisteArrivee;
    @FXML
    Label jourFérie;
    @FXML
    Label jourVacances;
    @FXML
    Label jourSemaine;
    @FXML
    Label jourWeekend;
    @FXML
    Label saison;

    @FXML
    AnchorPane anchorPaneDragDrop;
    @FXML
    Button deleteDataButton;
    

    public Controller() {
        this.requetes = new Requetes(new ArrayList<String>());
        this.fileChooser = new FileChooser();
    }

    public void initializeValueHeure(MouseEvent event) throws IOException {
        if (heureDebut.getItems().isEmpty()) {
            heureDebut.getItems().addAll("h00", "h01", "h02", "h03", "h04", "h05","h06","h07","h08","h09","h10","h11",
                                        "h12","h13","h14","h15","h16","h17","h18","h19", "h20", "h21", "h22", "h23");
        } else if (heureArrivee.getItems().isEmpty()) {
            heureArrivee.getItems().addAll("h00", "h01", "h02", "h03", "h04", "h05","h06","h07","h08","h09","h10","h11",
                                        "h12","h13","h14","h15","h16","h17","h18","h19", "h20", "h21", "h22", "h23");
        }
    }

    public void initializeValueHeureRech(MouseEvent event) throws IOException {
        if (heureRech.getItems().isEmpty()) {
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

    public void initializeValuePisteDepArr(MouseEvent event) throws IOException {
        if (pisteDepart.getItems().isEmpty()) {
            requetes.addToPisteList();
            ArrayList<String> lesCompteurs = requetes.getPisteList();
            for(String piste : lesCompteurs) {
                this.pisteDepart.getItems().add(piste);
            }
        } else if (pisteArrivee.getItems().isEmpty()) {
            requetes.addToPisteList();
            ArrayList<String> lesCompteurs = requetes.getPisteList();
            for(String piste : lesCompteurs) {
                this.pisteArrivee.getItems().add(piste);
            }
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

    public void downloadMap(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir l'emplacement de téléchargement");

        // Filtre de fichier pour n'afficher que les fichiers d'images
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);

        String userHomeDirectory = System.getProperty("user.home");
        String downloadsDirectoryPath = userHomeDirectory + File.separator + "Downloads";
        File downloadsDirectory = new File(downloadsDirectoryPath);
        fileChooser.setInitialDirectory(downloadsDirectory);

        // Affichage de la boîte de dialogue et récupération du fichier choisi
        File file = fileChooser.showSaveDialog(new Stage());
        String imagePath = "images/map.png"; //chemin de l'image à télécharger
        File imageFile = new File(imagePath);
        Files.copy(imageFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    } 

    public void downloadData(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir l'emplacement de téléchargement");

        // Filtre de fichier pour n'afficher que les fichiers d'images
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv");
        fileChooser.getExtensionFilters().add(imageFilter);

        String userHomeDirectory = System.getProperty("user.home");
        String downloadsDirectoryPath = userHomeDirectory + File.separator + "Downloads";
        File downloadsDirectory = new File(downloadsDirectoryPath);
        fileChooser.setInitialDirectory(downloadsDirectory);

        // Affichage de la boîte de dialogue et récupération du fichier choisi
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            String dataPath = "data/data.csv"; //chemin du fichier à télécharger
            File dataFile = new File(dataPath);
            Files.copy(dataFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void anchorPaneRootOnDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    
    public void anchorPaneRootOnDragDropped(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            List<File> files = event.getDragboard().getFiles();
            files.forEach((file -> System.out.println(file.getAbsolutePath())));
            for (File file : files) {
                if (file.getName().endsWith(".csv")) {
                    String source = file.getAbsolutePath();
                    String target = "data/newData.csv";
                    try {
                        Files.copy(Paths.get(source), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } 
        }
    }

    public void deleteNewData(MouseEvent event) throws IOException {
        String target = "data/newData.csv";
        File file = new File(target);
        if (file.exists()) {
            if (file.canWrite()) {
                file.delete();
            } else {
                System.out.println("Permissions insuffisantes pour supprimer le fichier.");
            }
        }
    }
}