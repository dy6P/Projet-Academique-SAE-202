package modele;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Digraphe {
    private TreeMap<Integer, Set<Integer>> chVoisinsSortants;

    public Digraphe(int [][] parVoisinsSortants) {
        chVoisinsSortants = new TreeMap<>();
        for (int indice_sommet = 0; indice_sommet < parVoisinsSortants.length; indice_sommet++) {
            chVoisinsSortants.put(indice_sommet, new TreeSet<>());
            for (int voisinSortant : parVoisinsSortants[indice_sommet]) {
                chVoisinsSortants.get(indice_sommet).add(voisinSortant);
            }
        }
    }

    public void triTopologique() {
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
