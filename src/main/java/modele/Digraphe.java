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
                chVoisinsSortants.get(ville).put(voisin, ville.getDistances().get(voisin));
            }
        }
    }

    public ArrayList<String> triTopologique() {
    }

    public TreeMap<Integer, Integer> getDegresEntrants() {
        TreeMap<Integer, Integer> degresEntrants = new TreeMap<>();
        for (int sommetCourant = 0; sommetCourant < chVoisinsSortants.size(); sommetCourant++) {
            int nombreDegresEntrants = 0;
            for (int indiceSommet = 0; indiceSommet < chVoisinsSortants.size(); indiceSommet++) {
                for (Integer voisinSortant : chVoisinsSortants.get(indiceSommet)) {
                    if (voisinSortant == sommetCourant) {
                        nombreDegresEntrants ++;
                    }
                }
            }
            degresEntrants.put(sommetCourant, nombreDegresEntrants);
        }
        return degresEntrants;
    }

    public ArrayList<Integer> getSources() {
        ArrayList<Integer> sources = new ArrayList<>();
        TreeMap<Integer, Integer> degresEntrants = getDegresEntrants();
        for (int sommet : degresEntrants.keySet()) {
            if (degresEntrants.get(sommet) == 0) {
                sources.add(sommet);
            }
        }
        return sources;
    }

    public String toString() {
        String resultat = "";
        for (int sommet : chVoisinsSortants.keySet()) {
            resultat += sommet + " : " + chVoisinsSortants.get(sommet) + "\n";
        }
        return resultat;
    }
}
