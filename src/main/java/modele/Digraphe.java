package modele;

import java.util.*;

public class Digraphe {
    private HashMap<Ville, HashMap<Ville, Integer>> chVoisinsSortants;

    public Digraphe(ArrayList<Ville> parVoisinsSortants) {
        chVoisinsSortants = new HashMap<>();
        for (Ville ville : parVoisinsSortants) {
            for (Ville voisin : parVoisinsSortants) {
                if (!chVoisinsSortants.containsKey(ville)) {
                    chVoisinsSortants.put(ville, new HashMap<>());
                }
                chVoisinsSortants.get(ville).put(voisin, ville.getChDistances().get(voisin));
            }
        }
    }

    /*public ArrayList<String> triTopologique() {

    }*/

    public HashMap<Ville, Integer> getDegresEntrants() {
        HashMap<Ville, Integer> degresEntrants = new HashMap<>();
        for (Ville ville : chVoisinsSortants.keySet()) {
            int nombreDegresEntrants = 0;
            for (Ville voisin : chVoisinsSortants.get(ville).keySet()) {
                if (ville.getChNom().equals(voisin.getChNom())) {
                    nombreDegresEntrants ++;
                }
            }
            degresEntrants.put(ville, nombreDegresEntrants);
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
