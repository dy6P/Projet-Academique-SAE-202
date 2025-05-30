package modele;

import java.util.*;

public class Digraphe {
    private TreeMap<String, TreeSet<String>> chVoisinsSortants;
    private TreeMap<String, TreeMap<String,Integer>> chDistances;
    private String chDepart;

    public Digraphe(TreeMap<String, TreeSet<String>> parVoisinsSortants, TreeMap<String, TreeMap<String,Integer>> parDistances, String parDepart) {
        chVoisinsSortants = parVoisinsSortants;
        chDistances = parDistances;
        chDepart = parDepart;
    }

    public int calculerDistance(ArrayList<String> parChemin) {
        int distance = 0;
        for (int i = 0; i < parChemin.size() - 1; i++) {
            distance += chDistances.get(parChemin.get(i).split(" ")[0]).get(parChemin.get(i + 1).split(" ")[0]);
        }
        return distance;
    }

    public int extraireSource(String parSource, ArrayList<String> parSources, int parComparateur) {
        int indice = 0;
        if (parSource != null) {
            for (int i = 0; i < parSources.size(); i++) {
                String sourceVille = parSource.split(" ")[0];
                String villeCandidate = parSources.get(i).split(" ")[0];
                String villeActuelle = parSources.get(indice).split(" ")[0];
                if (chDistances.get(sourceVille).get(villeCandidate) == 0) {
                    return i;
                }
                else if (parComparateur == 1 && chDistances.get(sourceVille).get(villeCandidate) < chDistances.get(sourceVille).get(villeActuelle)) {
                    indice = i;
                }
            }
        }
        return indice;
    }

    public ArrayList<String> triTopologique(String parDepart, int parComparateur) {
        ArrayList<String> triTopologique = new ArrayList<>();
        ArrayList<String> sources = new ArrayList<>();
        TreeMap<String, Integer> degresEntrants = degresEntrants();
        sources.add(parDepart + " + ");
        String source = null;
        while (!sources.isEmpty()) {
            source = sources.remove(extraireSource(source, sources, parComparateur));
            triTopologique.add(source);
            for (String voisin : chVoisinsSortants.get(source)) {
                degresEntrants.put(voisin, degresEntrants.get(voisin) - 1);
                if (degresEntrants.get(voisin) == 0 && !sources.contains(voisin)) {
                    sources.add(voisin);
                }
            }
        }
        return triTopologique;
    }

    public TreeMap<Integer, ArrayList<String>> kSolutions(String parCourant, TreeMap<Integer, ArrayList<String>> parChemins, ArrayList<String> parChemin, TreeMap<String, Integer> parDegresEntrants, ArrayList<String> parSources, int parSeuil, int parDistanceCourante, int parMaxVoisins) {
        parChemin.add(parCourant);
        if ((parDistanceCourante > parSeuil) || (chVoisinsSortants.get(parCourant).size() < parMaxVoisins)) {
            return parChemins;
        }
        if (parCourant.split(" ")[0].equals(chDepart) && parCourant.split(" ")[1].equals("-")) {
            parChemins.put(parDistanceCourante, new ArrayList<>(parChemin));
            return parChemins;
        }
        for (String voisin : chVoisinsSortants.get(parCourant)) {
            parDegresEntrants.put(voisin, parDegresEntrants.get(voisin) - 1);
            if (parDegresEntrants.get(voisin) == 0 && !parSources.contains(voisin)) {
                parSources.add(voisin);
            }
        }
        for (int i = 0; i < parSources.size(); i++) {
            ArrayList<String> newSources = new ArrayList<>(parSources);
            String newCourant = newSources.remove(i);
            int distanceAjoutee = chDistances.get(parCourant.split(" ")[0]).get(newCourant.split(" ")[0]);
            ArrayList<String> newChemin = new ArrayList<>(parChemin);
            kSolutions(newCourant, parChemins, newChemin, new TreeMap<>(parDegresEntrants), newSources, parSeuil, parDistanceCourante + distanceAjoutee, calculMaxVoisinsSortants(newChemin));
        }
        return parChemins;
    }

    public TreeMap<Integer, ArrayList<String>> solutions(int parK) {
        TreeMap<Integer, ArrayList<String>> solutions = new TreeMap<>();
        TreeMap<Integer, ArrayList<String>> candidats = new TreeMap<>();
        for (int comparateur : new int[] {0, 1}) {
            ArrayList<String> chemin = triTopologique(chDepart, comparateur);
            candidats.put(calculerDistance(chemin), chemin);
        }
        candidats = kSolutions( chDepart + " + ", candidats, new ArrayList<>(), degresEntrants(), new ArrayList<>(), candidats.firstKey(), 0, 0);
        int i = 0;
        for (ArrayList<String> solution : candidats.values()) {
            if (!solutions.containsValue(solution)) {
                solutions.put(calculerDistance(solution), solution);
                i ++;
            }
            if (i >= parK) {
                break;
            }
        }
        return solutions;
    }

    public int calculMaxVoisinsSortants(ArrayList<String> parVillesVisites) {
        int maxVoisins = 0;
        for (String ville : chVoisinsSortants.keySet()) {
            int voisins = chVoisinsSortants.get(ville).size();
            if (voisins > maxVoisins && !parVillesVisites.contains(ville)) {
                maxVoisins = voisins;
            }
        }
        return maxVoisins;
    }

    public TreeMap<String, Integer> degresEntrants() {
        TreeMap<String, Integer> degresEntrants = new TreeMap<>();
        for (String ville : chVoisinsSortants.keySet()) {
            int compteur = 0;
            for (String v : chVoisinsSortants.keySet()) {
                for (String voisin : chVoisinsSortants.get(v)) {
                    if (ville.equals(voisin)) {
                        compteur++;
                    }
                }
            }
            degresEntrants.put(ville, compteur);
        }
        return degresEntrants;
    }

    public String toString() {
        StringBuilder resultat = new StringBuilder("Digraphe : \n");
        for (String ville : chVoisinsSortants.keySet()) {
            resultat.append("- ").append(ville).append(" -> ").append(chVoisinsSortants.get(ville)).append("\n");
        }
        return resultat.toString();
    }
}
