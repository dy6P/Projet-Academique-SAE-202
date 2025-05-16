package modele;

import java.util.*;

public class Digraphe {
    private TreeMap<String, TreeSet<String>> chVoisinsSortants;
    private TreeMap<String, Integer> chDegresEntrants;
    private ArrayList<String> chSources;
    private ArrayList<String> chTriTopologique;
    private TreeMap<String, TreeMap<String,Integer>> chDistances;

    public Digraphe(TreeMap<String, TreeSet<String>> parVoisinsSortants, TreeMap<String, TreeMap<String,Integer>> parDistances) {
        chVoisinsSortants = parVoisinsSortants;
        chDegresEntrants = new TreeMap<>();
        chTriTopologique = new ArrayList<>();
        chSources = new ArrayList<>();
        chDistances = parDistances;
    }

    public String extraireSource(String parSource) {
        if (parSource == null) {
            return chSources.removeFirst();
        }
        int distance = chDistances.get(parSource.split(" ")[0]).get(chSources.getFirst().split(" ")[0]);
        int indice = 0;
        for (int i = 0; i < chSources.size(); i++) {
            if ((chDistances.get(parSource.split(" ")[0]).get(chSources.get(i).split(" ")[0])  <  distance)   &&   (parSource.split(" ")[0].equals(chSources.get(i).split(" ")[0])  ||  parSource.split(" ")[1].equals(chSources.get(i).split(" ")[1]))) { // (Si la distance entre la source courante et l'ancienne source est plus petite que l'ancienne distance) et (si la source courante a le même signe ou le a le même nom que l'ancienne source).
                distance = chDistances.get(parSource.split(" ")[0]).get(chSources.get(i).split(" ")[0]);
                indice = i;
            }
        }
        return chSources.remove(indice);
    }

    public void triTopologique(String parDepart) {
        chSources.add(parDepart + " + ");
        String source = null;
        while (!chSources.isEmpty()) {
            source = extraireSource(source);
            chTriTopologique.add(source);
            for (String voisin : chVoisinsSortants.get(source)) {
                chDegresEntrants.put(voisin, chDegresEntrants.get(voisin) - 1);
                if (chDegresEntrants.get(voisin) == 0 && !chSources.contains(voisin)) {
                    chSources.add(voisin);
                }
            }
        }
    }

    public void DegresEntrants() {
        for (String ville : chVoisinsSortants.keySet()) {
            int compteur = 0;
            for (String v : chVoisinsSortants.keySet()) {
                for (String voisin : chVoisinsSortants.get(v)) {
                    if (ville.equals(voisin)) {
                        compteur++;
                    }
                }
            }
            chDegresEntrants.put(ville, compteur);
        }
    }

    public TreeMap<String, Integer> getDegresEntrants() {
        return chDegresEntrants;
    }

    public ArrayList<String> getChSources() {
        return chSources;
    }

    public ArrayList<String> getChTriTopologique() {
        return chTriTopologique;
    }

    public TreeMap<String, TreeSet<String>> getChVoisinsSortants() {
        return chVoisinsSortants;
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
