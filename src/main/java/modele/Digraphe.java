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

    public void ajouterSource(String parSource, String parNouvelleSource) {
        chSources.add(parNouvelleSource);
        if (parSource != null) {
            for (int i = 1; i < chSources.size(); i++) { // tri par distance croissante
                String currentSource = chSources.get(i);
                int j = i - 1;
                while (j >= 0 && (chDistances.get(parSource.split(" ")[0]).get(currentSource.split(" ")[0]) < chDistances.get(parSource.split(" ")[0]).get(chSources.get(j).split(" ")[0]))) {
                    chSources.set(j + 1, chSources.get(j));
                    j--;
                }
                chSources.set(j + 1, currentSource);
            }
            List<String> positifs = new ArrayList<>();
            List<String> negatifs = new ArrayList<>();
            for (String source : chSources) {
                if (source.split(" ")[1].equals("+")) {
                    positifs.add(source);
                } else {
                    negatifs.add(source);
                }
            }
            chSources.clear();
            chSources.addAll(positifs);
            chSources.addAll(negatifs);
        }
    }


    public void triTopologique(String parDepart) {
        ajouterSource(null, parDepart + " + ");
        while (!chSources.isEmpty()) {
            String source = chSources.removeFirst();
            chTriTopologique.add(source);
            for (String voisin : chVoisinsSortants.get(source)) {
                chDegresEntrants.put(voisin, chDegresEntrants.get(voisin) - 1);
                if (chDegresEntrants.get(voisin) == 0 && !chSources.contains(voisin)) {
                    ajouterSource(source, voisin);
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
