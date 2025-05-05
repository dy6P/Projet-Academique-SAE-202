package Modele;

public class Utilisateur {
    private String chVille;
    private String chPseudo;

    public Utilisateur(String parPseudo, String parVille) {
        chVille = parVille;
        chPseudo = parPseudo;
    }

    public String getChVille() {
        return chVille;
    }

    public String getChPseudo() {
        return chPseudo;
    }

    public String toString() {
        return chPseudo + " habite à " + chVille;
    }
}
