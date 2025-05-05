package Modele;

import java.util.ArrayList;
import java.util.HashSet;

public class Scenario {
    private HashSet<Utilisateur> chUtilisateurs;
    private HashSet<Ville> chVilles;
    private HashSet<Commande> chCommandes;

    public Scenario() {
        chUtilisateurs = new HashSet<>();
        chVilles = new HashSet<>();
        chCommandes = new HashSet<>();
    }

    public void ajouterUtilisateurs(ArrayList<String> parListe) {
        for (String element : parListe) {
            chUtilisateurs.add(new Utilisateur(element.split(" ")[0], element.split(" ")[1]));
        }
    }

    public void ajouterVilles(ArrayList<String> parListe) {
        for (int i = 0; i < parListe.size(); i++) {
            Ville ville = new Ville(parListe.get(i).split(" ")[0]);
            int valeursVides = 0;
            for (int j = 1; j < parListe.get(i).split(" ").length - 1 - valeursVides; j++) {
                if (parListe.get(i).split(" ")[j].isEmpty()) {
                    valeursVides++;
                    ville.ajouterDistance(parListe.get(j).split(" ")[0], 999999);
                }
                else {
                    ville.ajouterDistance(parListe.get(j).split(" ")[0], Integer.parseInt(parListe.get(i).split(" ")[j]));
                }
            }
            chVilles.add(ville);
        }
    }

    public void ajouterCommandes(ArrayList<String> parListe) {
        for (String element : parListe) {
            chCommandes.add(new Commande(element.split(" -> ")[0], element.split(" -> ")[1]));
        }
    }

    public HashSet<Utilisateur> getChUtilisateurs() {
        return chUtilisateurs;
    }

    public HashSet<Ville> getChVilles() {
        return chVilles;
    }

    public HashSet<Commande> getChCommandes() {
        return chCommandes;
    }

    public String toString() {
        String resultat = "Utilisateurs :\n";
        for (Utilisateur utilisateur : chUtilisateurs) {
            resultat += "- " + utilisateur.toString() + "\n";
        }
        resultat += "Villes :\n";
        for (Ville ville : chVilles) {
            resultat += "- " + ville.toString() + "\n";
        }
        resultat += "Commandes :\n";
        for (Commande commande : chCommandes) {
            resultat += "- " + commande.toString() + "\n";
        }
        return resultat;
    }
}
