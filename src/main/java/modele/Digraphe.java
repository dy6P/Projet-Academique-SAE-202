package modele;

import java.util.*;

public class Digraphe {
    private TreeMap<String, TreeMap<String, Integer>> chVoisinsSortants;

    public Digraphe(TreeMap<String, TreeMap<String, Integer>> parVoisinsSortants) {
        chVoisinsSortants = parVoisinsSortants;
    }

    public ArrayList<String> triTopologique() {

    }

    public TreeMap<String, Integer> getDegresEntrants() {
        TreeMap<String, Integer> degresEntrants = new TreeMap<>();
        for (String ville : chVoisinsSortants.keySet()) {
            int compteur = 0;
            for (String v : chVoisinsSortants.keySet()) {
                for (String voisin : chVoisinsSortants.get(v).keySet()) {
                    if (ville.equals(voisin)) {
                        compteur++;
                    }
                }
            }
            degresEntrants.put(ville, compteur);
        }
        return degresEntrants;
    }

    /*public String toString() {
        String resultat = "";
        for (int sommet : chVoisinsSortants.keySet()) {
            resultat += sommet + " : " + chVoisinsSortants.get(sommet) + "\n";
        }
        return resultat;
    }*/
}
