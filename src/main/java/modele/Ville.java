package modele;

import java.util.HashMap;

public class Ville {
    private Boolean chVente = false;
    private Boolean chAchat = false;
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
        return chNom;
    }
}
