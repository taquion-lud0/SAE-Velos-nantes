package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class Requetes {
    private ArrayList<String> lesCompteurs = new ArrayList<String>(); 
    private final String URL = "jdbc:mysql://localhost:3306/velo_bdd";
    private final String USER = "root";
    private final String PASSWORD = "azerty123";
    
    public Requetes (ArrayList<String> lesCompteurs) {
        this.lesCompteurs = lesCompteurs;
    }

    public void addNomCompteur() {
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
    public ArrayList<String> getNomCompteur() {
        return this.lesCompteurs;
    }
}