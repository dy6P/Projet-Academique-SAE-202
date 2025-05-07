package modele;

import java.util.TreeMap;

public class Ville {
    private String chNom;
    private TreeMap<String, Integer> chDistances;

    public Ville(String parNom) {
        chNom = parNom;
        chDistances = new TreeMap<>();
    }

    public void ajouterDistance(String parVille, int parDistance) {
        chDistances.put(parVille, parDistance);
    }

    public TreeMap<String, Integer> getDistances() {
        return chDistances;
    }

    public String getChNom() {
        return chNom;
    }

    public String toString() {
        String resultat = "";
        for (String key : chDistances.keySet()) {
            if (chNom.split(" ")[1].equals("-")) {
                resultat += chNom.split(" ")[0] + "est à une distance de " + chDistances.get(key) + " km de " + key + "\n";
            }
        }
        return resultat;
    }
}
