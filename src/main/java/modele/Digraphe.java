package modele;

import java.util.*;

public class Digraphe {
    private TreeMap<String, TreeMap<String, Integer>> chVoisinsSortants;
    private TreeMap<String, Integer> chDegresEntrants;
    private ArrayList<String> chTriTopologique;

    public Digraphe(TreeMap<String, TreeMap<String, Integer>> parVoisinsSortants) {
        chVoisinsSortants = parVoisinsSortants;
        chDegresEntrants = new TreeMap<>();
    }

    public void triTopologique(String parDepart) {
        ArrayList<String> triTopologique = new ArrayList<>();
        TreeMap<String, Integer> degresEntrants = getDegresEntrants();
        ArrayList<String> sources = identifierSources(parDepart);
        while (!sources.isEmpty()) {
            String source = sources.removeFirst();
            triTopologique.add(source);
            if (chVoisinsSortants.get(source) != null) {
                for (String voisin : chVoisinsSortants.get(source).keySet()) {
                    degresEntrants.put(voisin, degresEntrants.get(voisin) - 1);
                    if (degresEntrants.get(voisin) == 0) {
                        sources.add(voisin);
                    }
                }
            }
        }
        chTriTopologique = triTopologique;
    }

    public void DegresEntrants() {
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
        chDegresEntrants = degresEntrants;
    }

    public ArrayList<String> identifierSources(String parVilleComparaison) {
        ArrayList<String> sources = new ArrayList<>();
        for (String ville : chDegresEntrants.keySet()) {
            if (chDegresEntrants.get(ville) == 0) {
                sources.add(ville);
            }
        }
        return sources;
    }

    public TreeMap<String, Integer> getDegresEntrants() {
        return chDegresEntrants;
    }

    public String toString() {
        String resultat = "CHEMIN :";
        String precedent = "";
        for (String tache : chTriTopologique) {
            tache = tache.split(" ")[0];
            if (!tache.equals(precedent)) {
                resultat += " -> " + tache;
            }
            precedent = tache;
        }
        return resultat;
    }
}
