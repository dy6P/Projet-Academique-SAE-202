package modele;

public class Commande {
    private String chVendeur;
    private String chAcheteur;

    public Commande(String parVendeur, String parAcheteur) {
        chVendeur = parVendeur;
        chAcheteur = parAcheteur;
    }

    public String getChVendeur() {
        return chVendeur;
    }

    public String getChAcheteur() {
        return chAcheteur;
    }

    public String toString() {
        return chVendeur + " Vend une carte à " + chAcheteur;
    }
}
