package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;

public class Requetes {
    private ArrayList<String> lesCompteurs = new ArrayList<String>(); 
    private ArrayList<String> lesHeures = new ArrayList<String>();
    static final String URL = "jdbc:mysql://localhost:3306/velo_bdd";
    static final String USER = "root";
    static final String PASSWORD = "123456";

    public static void main(String[] args) {
        //Exemple de requete
        String pisteD = "Vn751A St Leger les Vignes";
        String pisteA = "50 Otages Sud";
        String dateD = "2022-01-01";
        String dateA = "2022-01-03";

        String hD = "h16";
        String hA = "h17";
        int affluence = 0;

        
        ResultSet res = affluenceAvecH(dateD, dateA, pisteA, 2);
        JFreeChart chart = BarChartExample.affluenceAvecHGraph(res);
        //affiche le graphique
        ChartFrame frame = new ChartFrame("Affluence", chart);
        frame.pack();
        frame.setVisible(true);

        
        //bce.trafficJournalierGraph(res);
        //bce.pisteEnvironGraph(res);

        //affluence = affluence(date, hD, hA, pisteD, pisteA);
        //String test = itineraire(pisteD, pisteA);
        //System.out.println(test);
    }
    
    public Requetes () {
        //this.lesCompteurs = lesCompteurs;
    }

    public void addToPisteList() {
         try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String query = "SELECT DISTINCT CONCAT(nomCompteur, sens) AS resultat FROM Compteur ORDER BY resultat;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                this.lesCompteurs.add(resultSet.getString("resultat"));
            }

            resultSet.close();
            statement.close();
            connection.close(); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //getteur de USER
    static String getUSER() {
        return USER;
    }

    //getteur de PASSWORD
    static String getPASSWORD() {
        return PASSWORD;
    }

    public ArrayList<String> getPisteList() {
        return this.lesCompteurs;
    }

    public static ResultSet trafficJournalier(String date, String heure){
        ResultSet res = null;

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT SUM("+heure+") AS nbVelos, nomCompteur, sens FROM Comptage, Compteur WHERE Comptage.leCompteur = Compteur.idCompteur AND dateComptage = '"+date+"' Group BY nomCompteur, sens;";

            Statement statement = connection.createStatement();
            res = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static ResultSet pisteEnviron(String piste){
        ResultSet res = null;
        String[] pistSplit = clean(piste);
        String nomPiste = pistSplit[0];
        String sens = pistSplit[1];
        System.out.println(nomPiste);


        try {

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT DISTINCT(CONCAT(c2.nomCompteur, c2.sens)) AS compteursProches, ACOS(SIN(RADIANS(c1.COORD_X)) * SIN(RADIANS(c2.COORD_X)) + COS(RADIANS(c1.COORD_X)) * COS(RADIANS(c2.COORD_X)) * COS(RADIANS(c2.COORD_Y - c1.COORD_Y))) * 6371  AS dist_km FROM Compteur c1, Compteur c2 WHERE c1.nomCompteur = '"+ nomPiste+"' AND c1.sens = '"+sens+"' AND c1.nomCompteur != c2.nomCompteur ORDER BY dist_km LIMIT 5;";

            Statement statement = connection.createStatement();
            res = statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String itineraire(String pisteD, String pisteA){
        String itineraire = "";
        String[] pistSplitD = clean(pisteD);
        String[] pistSplitA = clean(pisteA);
        String nomPisteD = pistSplitD[0];
        String sensD = pistSplitD[1];
        String nomPisteA = pistSplitA[0];
        String sensA = pistSplitA[1];

        try{

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            String query = "SELECT CONCAT(c2.nomCompteur, c2.sens) AS compteur_Intermédiaire, ACOS(SIN(RADIANS(c1.COORD_X)) * SIN(RADIANS(c2.COORD_X)) + COS(RADIANS(c1.COORD_X)) * COS(RADIANS(c2.COORD_X)) * COS(RADIANS(c2.COORD_Y - c1.COORD_Y))) * 6371  AS dist_Départ_Chemin FROM Compteur c1, Compteur c2, Compteur c3  WHERE c1.nomCompteur != c2.nomCompteur AND c2.nomCompteur  != c3.nomCompteur AND c1.nomCompteur != c3.nomCompteur AND UPPER(c1.nomCompteur) = '"+nomPisteD+"' AND UPPER(c1.sens) = '"+ sensD +"' AND UPPER(c3.nomCompteur) = '"+nomPisteA+"'  AND UPPER(c3.sens) = '"+sensA+"' AND c1.COORD_X <= c2.COORD_X  AND c2.COORD_X <= c3.COORD_X AND c1.COORD_Y <= c2.COORD_Y AND c2.COORD_Y <= c3.COORD_Y ORDER BY dist_Départ_Chemin;";
            
            Statement statement = connection.createStatement();
            ResultSet listSest = statement.executeQuery(query);
            while(listSest.next()){
                itineraire += listSest.getString("compteur_Intermédiaire") + " -> ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(itineraire.equals("")){
            itineraire = "Pas d'itinéraire possible";
            return itineraire;
        }else{
            //supprime les 4 derniers caracteres de la chaine de caractere ( -> )
            itineraire = itineraire.substring(0, itineraire.length() - 4);
            itineraire += " -> " + pisteA + " (itinéraire le plus court)";
        }
        return itineraire;

    }

    public static ResultSet affluenceSansH(String dateD, String dateF, String piste, int affluence){
        ResultSet res = null;
        String[] pistSplit = clean(piste);
        String nomPiste = pistSplit[0];
        String sens = pistSplit[1];

        //met les dates dans l'order
        if(LocalDate.parse(dateD).isAfter(LocalDate.parse(dateF))){
            String tmp = dateD;
            dateD = dateF;
            dateF = tmp;
        }

        //nb de jours entre les 2 dates (dateF - dateD)
        int nbJours = (int) ChronoUnit.DAYS.between(LocalDate.parse(dateD), LocalDate.parse(dateF)) +1;
        System.out.println(nbJours);
        String affluenceRes = "ASC";
        if(affluence == 1){
            affluenceRes = "DESC";
        }
     
        if(affluence == 0){
            try {
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String query = "SELECT nomCompteur, sens, dateComptage, SUM(h00 + h01 + h02 + h03 + h04 + h05 + h06 + h07 + h08 + h09 + h10 + h11 + h12 + h13 + h14 + h15 + h16 + h17 + h18 + h19 + h20 + h21 + h22 + h23) AS nbVelos FROM Comptage, Compteur WHERE Comptage.leCompteur = Compteur.idCompteur AND dateComptage BETWEEN '"+dateD+"' AND '"+dateF+"' AND nomCompteur = '"+nomPiste+"' AND sens = '"+sens+"' GROUP BY nomCompteur, sens, dateComptage ORDER BY dateComptage ASC  LIMIT " + nbJours +";";
                System.out.println(query);
                Statement statement = connection.createStatement();
                res = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String query = "SELECT nomCompteur, sens, dateComptage, SUM(h00 + h01 + h02 + h03 + h04 + h05 + h06 + h07 + h08 + h09 + h10 + h11 + h12 + h13 + h14 + h15 + h16 + h17 + h18 + h19 + h20 + h21 + h22 + h23) AS nbVelos FROM Comptage, Compteur WHERE Comptage.leCompteur = Compteur.idCompteur AND dateComptage BETWEEN '"+dateD+"' AND '"+ dateF +"' GROUP BY nomCompteur, sens, dateComptage ORDER BY nbVelos "+affluenceRes+" LIMIT 1;";
                Statement statement = connection.createStatement();
                res = statement.executeQuery(query);
            }
            catch(SQLException e){
                e.printStackTrace();
            }

        }
        return res;
    }

    public static ResultSet affluenceAvecH(String dateD, String dateF, String piste, int affluence){
        ResultSet res = null;
        String[] pistSplit = clean(piste);
        String nomPiste = pistSplit[0];
        System.out.println(nomPiste);
        String sens = pistSplit[1];

        //met les dates dans l'order
        if(LocalDate.parse(dateD).isAfter(LocalDate.parse(dateF))){
            String tmp = dateD;
            dateD = dateF;
            dateF = tmp;
        }

        //nb de jours entre les 2 dates
        int nbJours = (int) ChronoUnit.DAYS.between(LocalDate.parse(dateD), LocalDate.parse(dateF)) + 1;
        String affluenceRes = "ASC";
        if(affluence == 1){
            affluenceRes = "DESC";
        }
        if(affluence == 0){
            try {
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                String query = "SELECT nomCompteur, sens, h00 , h01 , h02 , h03 , h04 , h05 , h06 , h07 , h08 , h09 , h10 , h11 , h12 , h13 , h14 , h15 , h16 , h17 , h18 , h19  ,h20 , h21 , h22 , h23 FROM Comptage, Compteur WHERE Comptage.leCompteur = Compteur.idCompteur AND dateComptage BETWEEN '"+dateD+"' AND '"+dateD+"' AND nomCompteur = '"+nomPiste+"' AND sens = '"+sens+"' GROUP BY nomCompteur, sens, h00 , h01 , h02 , h03 , h04 , h05 , h06 , h07 , h08 , h09 , h10 , h11 , h12 , h13 , h14 , h15 , h16 , h17 , h18 , h19  ,h20 , h21 , h22 , h23  LIMIT 1;";
                System.out.println(query);
                Statement statement = connection.createStatement();
                res = statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else{
            try{
                    Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    String query = "SELECT nomCompteur, sens,  h00, h01, h02, h03, h04, h05, h06, h07, h08, h09, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23 FROM Comptage, Compteur WHERE Comptage.leCompteur = Compteur.idCompteur AND dateComptage BETWEEN '"+dateD+"' AND '"+ dateD +"' GROUP BY nomCompteur, sens, h00, h01, h02, h03, h04, h05, h06, h07, h08, h09, h10, h11, h12, h13, h14, h15, h16, h17, h18, h19, h20, h21, h22, h23  LIMIT 1;";
                    Statement statement = connection.createStatement();
                    res = statement.executeQuery(query);
                }
                catch(SQLException e){
                    e.printStackTrace();
                }
        }

        return res;
    }

    public static String[] clean(String r){
        String[] res = new String[2];
        //verifie si la chaine se termine par "Gare Sud"
        if(r.endsWith("Gare Sud")){
            res[0] = r.substring(0, r.length()-9) + " ";
            res[1] = "Gare Sud";
        }
        //verifie si la chaine se termine par "St Leger les Vignes"
        else if(r.endsWith("St Leger les Vignes")){
            res[0] = r.substring(0, r.length()-20) + " ";
            res[1] = "St Leger les Vignes";
        }
        //verifie si la chaine se termine par "Suce sur Erdre"
        else if(r.endsWith("Suce sur Erdre")){
            res[0] = r.substring(0, r.length()-14);
            res[1] = "Suce sur Erdre";
        }
        else{
            //res[1] est le dernier mot de la chaine
            res[1] = r.substring(r.lastIndexOf(" ")+1);
            //res[0] est la chaine sans le dernier mot
            res[0] = r.substring(0, r.lastIndexOf(" ")) + " ";
        }
        return res;
    }
}