package modele;

public class Utilisateur {
    private Ville chVille;
    private String chPseudo;

    public Utilisateur(String parPseudo, Ville parVille) {
        chVille = parVille;
        chPseudo = parPseudo;
    }

    public Ville getChVille() {
        return chVille;
    }

    public String getChPseudo() {
        return chPseudo;
    }

    public String toString() {
        return chPseudo + " habite à " + chVille.getChNom();
    }
}
