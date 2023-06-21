package hellofx;

import java.sql.ResultSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartExample {
    public static void main(String[] args) {

    }

    public void trafficJournalierGraph(ResultSet set){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("trafficJournalierGraph");
        try {
            while (set.next()) {
                int nbVelos = set.getInt("nbVelos");
                System.out.println("nbVelos : " + nbVelos);
                String nomCompteur = set.getString("nomCompteur");
                String sens = set.getString("sens");
                //concaténation du nom du compteur et du sens pour avoir un nom unique
                nomCompteur = nomCompteur + " " + sens;
                dataset.addValue(nbVelos, nomCompteur, " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du graphique
        JFreeChart chart = ChartFactory.createBarChart(
            "Traffic journalier", // Titre du graphique
            "Compteurs", // Axe des x
            "Nombre de vélos", // Axe des y
            dataset, // Jeu de données
            PlotOrientation.VERTICAL, // Orientation du graphique
            true, // Afficher la légende
            true, // Générer les tooltips
            false // Générer les URLs
        );

        // Affichage du graphique dans une fenêtre
        ChartFrame frame = new ChartFrame("Bar Chart Example", chart);
        frame.pack();
        frame.setVisible(true);
    }

    public void pisteEnvironGraph(ResultSet set){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("pisteEnvironGraph");

        try {
            while (set.next()) {
                String compteursProches = set.getString("compteursProches");
                System.out.println("compteursProches : " + compteursProches);
                double dist_km = set.getDouble("dist_km");
                System.out.println("dist_km : " + dist_km);
                dataset.addValue(dist_km, compteursProches, " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Création du graphique
        JFreeChart chart = ChartFactory.createBarChart(
            "Piste environnante", // Titre du graphique
            "Compteurs", // Axe des x
            "Distance (km)", // Axe des y
            dataset, // Jeu de données
            PlotOrientation.VERTICAL, // Orientation du graphique
            true, // Afficher la légende
            true, // Générer les tooltips
            false // Générer les URLs
        );

        // Affichage du graphique dans une fenêtre
        ChartFrame frame = new ChartFrame("Bar Chart Example", chart);
        frame.pack();
        frame.setVisible(true);
        
    }
}
