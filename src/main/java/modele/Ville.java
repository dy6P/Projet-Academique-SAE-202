package modele;

import java.util.TreeMap;

public class Ville {
    private Boolean chVente = false;
    private Boolean chAchat = false;
    private String chNom;
    private TreeMap<Ville, Integer> chDistances;

    public Ville(String parNom) {
        chNom = parNom;
        chDistances = new TreeMap<>();
    }

    public void ajouterDistance(Ville parVille, int parDistance) {
        chDistances.put(parVille, parDistance);
    }

    public TreeMap<Ville, Integer> getDistances() {
        return chDistances;
    }

    public String getChNom() {
        return chNom;
    }

    public void setChAchatTrue() {
        chAchat = true;
    }

    public void setChVenteTrue() {
        chVente = true;
    }

    public void setChAchatFalse() {
        chAchat = false;
    }

    public void setChVenteFalse() {
        chVente = false;
    }

    public Boolean getChVente() {
        return chVente;
    }

    public Boolean getChAchat() {
        return chAchat;
    }

    public String toString() {
        String resultat = "";
        for (Ville key : chDistances.keySet()) {
            resultat += chNom + " est à une distance de " + chDistances.get(key) + " km de " + key + "\n";
        }
        return resultat;
    }
}
