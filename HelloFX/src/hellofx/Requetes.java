package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Requetes {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/velo_bdd";
        String user = "root";
        String password = "azerty123";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String query = "SELECT idCompteur FROM Compteur WHERE leQuartier IS NULL;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int idCompteur = resultSet.getInt("idCompteur");
                System.out.println("idCompteur : " + idCompteur);
            }

            resultSet.close();
            statement.close();
            connection.close(); 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}