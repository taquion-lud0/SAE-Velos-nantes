package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Requetes {
    private ArrayList<String> lesCompteurs = new ArrayList<String>(); 
    private ArrayList<String> lesHeures = new ArrayList<String>();
    private final String URL = "jdbc:mysql://localhost:3306/velo_bdd";
    private final String USER = "root";
    private final String PASSWORD = "azerty123";

    public static void main(String[] args) {
        //Exemple de requete
        String pisteD = "Vn751A vers St Leger Les Vignes";
        String pisteA = "Vn vers Suce Sur Erdre";
        String date = "2022-01-01";
        String hD = "h16";
        String hA = "h17";
        int affluence = 0;

        ResultSet res = trafficJournalier(date, hD);
        BarChartExample bce = new BarChartExample();
        bce.trafficJournalierGraph(res);
        //res = pisteEnviron(pisteA);
        //bce.pisteEnvironGraph(res);
        //String test = itineraire(pisteD, pisteA);
        //System.out.println(test);
    }
    
    public Requetes (ArrayList<String> lesCompteurs) {
        this.lesCompteurs = lesCompteurs;
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

    public ArrayList<String> getPisteList() {
        return this.lesCompteurs;
    }

    public static ResultSet trafficJournalier(String date, String heure){
        ResultSet res = null;

        try {
            String url = "jdbc:mysql://localhost:3306/velo_bdd";
            String user = "root";
            String password = "azerty123";

            Connection connection = DriverManager.getConnection(url, user, password);
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
        //separe le nom de la piste et le sens tous ce qui est avant "vers" est le nom de la piste et ce qui est apres est le sens
        String nomPiste = piste.split(" vers ")[0];
        String sens = piste.split(" vers ")[1];
        nomPiste = nomPiste + " ";

        try {
            String url = "jdbc:mysql://localhost:3306/velo_bdd";
            String user = "root";
            String password = "azerty123";

            Connection connection = DriverManager.getConnection(url, user, password);
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
        //separe le nom de la piste et le sens tous ce qui est avant "vers" est le nom de la piste et ce qui est apres est le sens
        String nomPisteD = pisteD.split(" vers ")[0];
        String sensD = pisteD.split(" vers ")[1];
        String nomPisteA = pisteA.split(" vers ")[0];
        String sensA = pisteA.split(" vers ")[1];
        nomPisteD = nomPisteD + " ";
        nomPisteA = nomPisteA + " ";

        try{
            String url = "jdbc:mysql://localhost:3306/velo_bdd";
            String user = "root";
            String password = "azerty123";

            Connection connection = DriverManager.getConnection(url, user, password);
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

    public static ResultSet affluence(String dateD, String dateF, String hD, String hA, String piste, int affluence){
        ResultSet res = null;
        //separe le nom de la piste et le sens tous ce qui est avant "vers" est le nom de la piste et ce qui est apres est le sens
        String nomPiste = piste.split(" vers ")[0];
        String sens = piste.split(" vers ")[1];
        nomPiste = nomPiste + " ";

        //nb de jours entre les 2 dates
        int nbJours = (int) ChronoUnit.DAYS.between(LocalDate.parse(dateD), LocalDate.parse(dateF));
        String affluenceRes = "ASC";
        if(affluence == 1){
            affluenceRes = "DESC";
        }
        String url = "jdbc:mysql://localhost:3306/velo_bdd";
        String user = "root";
        String password = "azerty123";

        if(affluence == 0){
            if(hD.equals("h00") && hA.equals("h23")){

            }

        }
        else{

        }
        return res;
    }
}