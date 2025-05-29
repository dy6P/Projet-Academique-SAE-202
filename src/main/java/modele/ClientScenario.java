package modele;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.TreeMap;

public class ClientScenario {
    public static void main(String[] args) {
        try {
            String[] nomsFichiers = {"distances.txt", "membres_APPLI.txt", "scenario_0.txt", "scenario_1.txt", "scenario_2.txt", "scenario_3.txt", "scenario_4.txt", "scenario_5.txt", "scenario_6.txt", "scenario_7.txt", "scenario_8.txt"};
            String[] nomsListes = {"distances", "membres", "s0", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8"};
            TreeMap<String, ArrayList<String>> correspondances = new TreeMap<>();
            for (int i = 0; i < nomsListes.length; i++) {
                ArrayList<String> liste = new ArrayList<>();
                File fichier = new File("données/" + nomsFichiers[i]);
                Scanner reader = new Scanner(fichier);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    liste.add(data);
                }
                correspondances.put(nomsListes[i], liste);
                reader.close();
            }
            Scenario s0 = new Scenario("Velizy", 10);
            s0.ajouterVilles(correspondances.get("distances"));
            s0.ajouterDistances();
            s0.ajouterUtilisateurs(correspondances.get("membres"));
            s0.ajouterCommandes(correspondances.get("s0"));
            s0.trouverChemins();
            s0.cheminsNet();
            System.out.println(s0);
        } catch (FileNotFoundException parException) {
            System.out.println("Erreur lors de la lecture du fichier text.");
            parException.printStackTrace();
        }
    }
}
