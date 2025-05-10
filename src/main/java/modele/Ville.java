package modele;

import java.util.HashMap;

public class Ville {
    private String chNom;
    private HashMap<Ville, Integer> chDistances;

    public Ville(String parNom) {
        chNom = parNom;
        chDistances = new HashMap<>();
    }

    public void ajouterDistance(Ville parVille, int parDistance) {
        chDistances.put(parVille, parDistance);
    }

    public HashMap<Ville, Integer> getChDistances() {
        return chDistances;
    }

    public String getChNom() {
            return chNom;
    }

    public String toString() {
        return getChNom();
    }
}
