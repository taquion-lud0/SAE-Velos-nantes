package hellofx;

import java.sql.ResultSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChartExample {

    public static JFreeChart trafficJournalierGraph(ResultSet set){
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

        return chart;
    }

    public static JFreeChart pisteEnvironGraph(ResultSet set){
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
            "Pistes environnante de la piste", // Titre du graphique 
            "Compteurs", // Axe des x
            "Distance (km)", // Axe des y
            dataset, // Jeu de données
            PlotOrientation.VERTICAL, // Orientation du graphique
            true, // Afficher la légende
            true, // Générer les tooltips
            false // Générer les URLs
        );

        return chart;
        
    }

    public static JFreeChart affluenceSansHGraph(ResultSet set){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("affluenceGraph");

        try{
            while (set.next()) {
                String nomCompteur = set.getString("nomCompteur");
                String sens = set.getString("sens");
                //concaténation du nom du compteur et du sens pour avoir un nom unique
                nomCompteur = nomCompteur + " " + sens;
                String jour = set.getString("dateComptage");
                int nbVelos = set.getInt("nbVelos");
                dataset.addValue(nbVelos, nomCompteur, jour);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Création du graphique
        JFreeChart chart = ChartFactory.createBarChart(
            "Affluence", // Titre du graphique 
            "Compteurs", // Axe des x
            "Nombre de vélos", // Axe des y
            dataset, // Jeu de données
            PlotOrientation.VERTICAL, // Orientation du graphique
            true, // Afficher la légende
            true, // Générer les tooltips
            false // Générer les URLs
        );

        return chart;
    }

    public static JFreeChart affluenceAvecHGraph(ResultSet set){
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println("affluenceGraph");

        try{
            while (set.next()) {
                String nomCompteur = set.getString("nomCompteur");
                String sens = set.getString("sens");
                //concaténation du nom du compteur et du sens pour avoir un nom unique
                nomCompteur = nomCompteur + " " + sens;
                String h00 = set.getString("h00");
                String h01 = set.getString("h01");
                String h02 = set.getString("h02");
                String h03 = set.getString("h03");
                String h04 = set.getString("h04");
                String h05 = set.getString("h05");
                String h06 = set.getString("h06");
                String h07 = set.getString("h07");
                String h08 = set.getString("h08");
                String h09 = set.getString("h09");
                String h10 = set.getString("h10");
                String h11 = set.getString("h11");
                String h12 = set.getString("h12");
                String h13 = set.getString("h13");
                String h14 = set.getString("h14");
                String h15 = set.getString("h15");
                String h16 = set.getString("h16");
                String h17 = set.getString("h17");
                String h18 = set.getString("h18");
                String h19 = set.getString("h19");
                String h20 = set.getString("h20");
                String h21 = set.getString("h21");
                String h22 = set.getString("h22");
                String h23 = set.getString("h23");
                dataset.addValue(Integer.parseInt(h00), nomCompteur, "00h");
                dataset.addValue(Integer.parseInt(h01), nomCompteur, "01h");
                dataset.addValue(Integer.parseInt(h02), nomCompteur, "02h");
                dataset.addValue(Integer.parseInt(h03), nomCompteur, "03h");
                dataset.addValue(Integer.parseInt(h04), nomCompteur, "04h");
                dataset.addValue(Integer.parseInt(h05), nomCompteur, "05h");
                dataset.addValue(Integer.parseInt(h06), nomCompteur, "06h");
                dataset.addValue(Integer.parseInt(h07), nomCompteur, "07h");
                dataset.addValue(Integer.parseInt(h08), nomCompteur, "08h");
                dataset.addValue(Integer.parseInt(h09), nomCompteur, "09h");
                dataset.addValue(Integer.parseInt(h10), nomCompteur, "10h");
                dataset.addValue(Integer.parseInt(h11), nomCompteur, "11h");
                dataset.addValue(Integer.parseInt(h12), nomCompteur, "12h");
                dataset.addValue(Integer.parseInt(h13), nomCompteur, "13h");
                dataset.addValue(Integer.parseInt(h14), nomCompteur, "14h");
                dataset.addValue(Integer.parseInt(h15), nomCompteur, "15h");
                dataset.addValue(Integer.parseInt(h16), nomCompteur, "16h");
                dataset.addValue(Integer.parseInt(h17), nomCompteur, "17h");
                dataset.addValue(Integer.parseInt(h18), nomCompteur, "18h");
                dataset.addValue(Integer.parseInt(h19), nomCompteur, "19h");
                dataset.addValue(Integer.parseInt(h20), nomCompteur, "20h");
                dataset.addValue(Integer.parseInt(h21), nomCompteur, "21h");
                dataset.addValue(Integer.parseInt(h22), nomCompteur, "22h");
                dataset.addValue(Integer.parseInt(h23), nomCompteur, "23h");
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        // Création du graphique
        
        JFreeChart chart = ChartFactory.createBarChart(
            "Affluence", // Titre du graphique 
            "Compteurs", // Axe des x
            "Nombre de vélos", // Axe des y
            dataset, // Jeu de données
            PlotOrientation.VERTICAL, // Orientation du graphique
            true, // Afficher la légende
            true, // Générer les tooltips
            false // Générer les URLs
        );

        return chart;


    }
}
