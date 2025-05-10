package modele;

public class Commande {
    private Utilisateur chVendeur;
    private Utilisateur chAcheteur;

    public Commande(Utilisateur parVendeur, Utilisateur parAcheteur) {
        chVendeur = parVendeur;
        chAcheteur = parAcheteur;
    }

    public Utilisateur getChVendeur() {
        return chVendeur;
    }

    public Utilisateur getChAcheteur() {
        return chAcheteur;
    }

    public String toString() {
        return chVendeur.getChPseudo() + " (" + chVendeur.getChVille() + ")" + " Vend à " + chAcheteur.getChPseudo() + " (" + chAcheteur.getChVille() + ")";
    }
}
