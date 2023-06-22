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

import java.sql.ResultSet;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFrame;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.File;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Requetes requetes;
    static final String USERNAME = "root";
    static final String PASSWORD = "mYsqldEV-32!";
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
    Button pisteEnvButton;
    @FXML 
    Label errorPiste;
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
    @FXML
    AnchorPane anchorPaneResPistEnv;
    @FXML
    DatePicker dateTrafic;

    @FXML
    Label labelResRechTrajet;

    @FXML	
    Label error;

    
    public Controller() {
        this.requetes = new Requetes();
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

    public String getNomPiste() {
        String nomPiste = this.nomPiste.getSelectionModel().getSelectedItem();
        return nomPiste;
    }

    
    public Date getDateDebut() {
        Date dateDeb = Date.valueOf(dateDebut.getValue());
        return dateDeb;
    }

    public Date getDateArrivee() {
        Date dateArr = Date.valueOf(dateArrivee.getValue());
        return dateArr;
    }

    public String getHeureArrivee() {
        String heureArr =  heureArrivee.getSelectionModel().getSelectedItem();
        return heureArr;
    }

    public String getHeureDebut() {
        String heureDeb =  heureDebut.getSelectionModel().getSelectedItem();
        return heureDeb;
    }

    public String getHeureRech() {
        String heureRech =  this.heureRech.getSelectionModel().getSelectedItem();
        return heureRech;
    }

    public String getDateTrafic(){
        String dateTrafic =  this.dateTrafic.getValue().toString();
        return dateTrafic;
    }

    public int getTypeQueryAffluence() {
        int typeQueryInt = 1;
        String typeQuery =  typeQueryAffluence.getSelectionModel().getSelectedItem();
        if (typeQuery.equals("Affluence d'une piste spécifique")) {
            typeQueryInt = 0;
        } else if (typeQuery.equals("Piste ayant le plus d'affluence")) {
            typeQueryInt = 1;
        } else if (typeQuery.equals("Piste ayant le moins d'Affluence")) {
            typeQueryInt = 2;
        } 
        return typeQueryInt;
    }

    public String getNomPisteDep() {
        if (this.pisteDepart.getSelectionModel().getSelectedItem() == null) {
            throw new NullPointerException("Aucune piste de départ sélectionnée");
        } else {
        String nomPisteDep =  this.pisteDepart.getSelectionModel().getSelectedItem();
        return nomPisteDep;
        }
    }

    public String getNomPisteArr() {
        if (this.pisteArrivee.getSelectionModel().getSelectedItem() == null) {
            throw new NullPointerException("Aucune piste d'arrivée sélectionnée");
        } else {
            String nomPisteArr =  this.pisteArrivee.getSelectionModel().getSelectedItem();
            return nomPisteArr;
        }
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
        stage.setTitle("Accueil");
        stage.show();
    }

    public void switchToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Connexion");
        stage.show();
    }

    public void switchToAffluence(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Affluence.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Affluence");
        stage.show();
    }

    public void switchToCredit(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Credit.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Crédits");
        stage.show();
    }

    public void switchToPistesEnv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PistesEnv.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pistes Environnantes");
        stage.show();
    }

    //pour RechTrajet.fxml
    public void switchToRechTrajet(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RechTrajet.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Recherche itinéraire");
        stage.show();
    }

    //pour ResAffluence.fxml
    public void switchToResAffluence(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResAffluence.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Résultats Affluence");
        stage.show();
    }

    //pour ResPistesEnv.fxml
    public void switchToResPistesEnv(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResPistesEnv.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Résultats Pistes Environnantes");
        stage.show();
    }

    //pour ResRechTrajet.fxml
    public void switchToResRechTrajet(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResRechTrajet.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Résultats Itinéraire");
        stage.show();
        
    }

    //pour ResTrafic.fxml
    public void switchToResTrafic(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResTrafic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Résultats Trafic Journalier");
        stage.show(); 
    }

    //pour Trafic.fxml
    public void switchToTrafic(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Trafic.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Trafic Journalier");
        stage.show(); 
    }

    //pour UpdateData.fxml
    public void switchToUpdateData(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UpdateData.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Mise à jour des données");
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

    public void afficherPistesEnv(ActionEvent event) throws IOException {
        System.out.println("Affichage des pistes environnantes");
        try {
            System.out.println("Test1");
            String nomPiste = getNomPiste();
            System.out.println(nomPiste);
            ResultSet res = Requetes.pisteEnviron(nomPiste);
            JFreeChart chart = BarChartExample.pisteEnvironGraph(res);
            chart.setTitle("Pistes environnantes " + nomPiste);
            // affichage du graphique
            
            
            ChartFrame frame = new ChartFrame("Pistes environnantes " + nomPiste, chart);
            frame.pack();
            frame.setVisible(true);


            
        } catch (NullPointerException e) {
            errorPiste.setText("Aucune piste sélectionnée.");
        }
    }

    public void afficherTrafficJournalier(ActionEvent event) throws IOException {
        System.out.println("Affichage du trafic journalier");
        try {
            String date = getDateTrafic();
            String heure = getHeureRech();
            ResultSet res = Requetes.trafficJournalier(date, heure);
            JFreeChart chart = BarChartExample.trafficJournalierGraph(res);
            chart.setTitle("Trafic journalier le " + date + " à " + heure);
            // affichage du graphique
            ChartFrame frame = new ChartFrame("Trafic journalier", chart);
            frame.pack();
            frame.setVisible(true);
        } catch (NullPointerException e) {
            errorPiste.setText("Aucune date/heure sélectionnée.");
        }
    }

    public void afficherRechTrajet(ActionEvent event) throws IOException {
        System.out.println("Affichage du trajet : ");
        try {
            System.out.println("Affichage du trajet : " + getNomPisteDep() + " - " + getNomPisteArr());
            String nomPisteDep = getNomPisteDep();
            String nomPisteArr = getNomPisteArr();
            String itineraire = Requetes.itineraire(nomPisteDep, nomPisteArr);
            System.out.println(itineraire);
            //remplace labelResRechTrajet par le résultat de la requête
            labelResRechTrajet.setText(itineraire);
            //met le label en avant
            labelResRechTrajet.setVisible(true);
            pisteArrivee.setVisible(false);
            pisteDepart.setVisible(false);

            
        } catch (NullPointerException e) {
            errorPiste.setText("Aucune piste sélectionnée.");
        }
    }


    public void afficherAffluence(ActionEvent event) throws IOException{
        System.out.println("Affichage de l'affluence");
        try {

            String dateD = getDateDebut().toString();
            String dateA = getDateArrivee().toString();
            String heureD = "h00";
            heureD = getHeureDebut();
            System.out.println(heureD);
            String heureA = "h23";
            heureA = getHeureArrivee();
            System.out.println(heureA);
            int affluence = getTypeQueryAffluence();
            String nomPiste = getNomPiste();
            ResultSet res = null;
            JFreeChart chart = null;
            if(heureA == null || heureD == null || nomPiste == null){
                heureA = "h23";
                heureD = "h00";
            }
            if(heureD.equals("h00") && heureA.equals("h23")){
                res = Requetes.affluenceSansH(dateD, dateA, nomPiste, affluence);
                chart = BarChartExample.affluenceSansHGraph(res);
            }
            else{
                res = Requetes.affluenceAvecH(dateD, dateA, nomPiste, affluence);
                chart = BarChartExample.affluenceAvecHGraph(res);
            }
            
            chart.setTitle("Affluence " + nomPiste);
            // affichage du graphique
            ChartFrame frame = new ChartFrame("Affluence " + nomPiste, chart);
            frame.pack();
            frame.setVisible(true);
        } catch (NullPointerException e) {
            error.setText("Aucune date/piste sélectionnée.");
        }
    }

}