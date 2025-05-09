package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Scenario {
    private HashMap<String,Utilisateur> chUtilisateurs;
    private HashMap<String, Ville> chVilles;
    private HashSet<Commande> chCommandes;

    public Scenario() {
        chUtilisateurs = new HashMap<>();
        chVilles = new HashMap<>();
        chCommandes = new HashSet<>();
    }

    public void ajouterUtilisateurs(ArrayList<String> parListe) {
        for (String element : parListe) {
            chUtilisateurs.put(element.split(" ")[0], new Utilisateur(element.split(" ")[0], chVilles.get(element.split(" ")[1])));
        }
    }

    public void ajouterVilles(ArrayList<String> parListe) {
        for (String element : parListe) {
            chVilles.put(element.split(" ")[0], new Ville(element.split(" ")[0]));
        }
        for (String element : parListe) {
            int valeursVides = 0;
            for (int j = 1; j < element.split(" ").length - 1 - valeursVides; j++) {
                if (element.split(" ")[j].isEmpty()) {
                    valeursVides++;
                    chVilles.get(element.split(" ")[0]).ajouterDistance(chVilles.get(parListe.get(j).split(" ")[0]), 99999999);
                }
                else {
                    chVilles.get(element.split(" ")[0]).ajouterDistance(chVilles.get(parListe.get(j).split(" ")[0]), Integer.parseInt(element.split(" ")[j]));
                }
            }
        }
    }

    public void ajouterCommandes(ArrayList<String> parListe) {
        for (String element : parListe) {
            chCommandes.add(new Commande(chUtilisateurs.get(element.split(" -> ")[0]), chUtilisateurs.get(element.split(" -> ")[1])));
        }
    }

    public ArrayList<String> trouverChemin() {
        ArrayList<String> chemin = new ArrayList<>();
        HashMap<Ville, HashMap<Ville, Integer>> voisinsSortants = new HashMap<>();
        chVilles.get("Velizy").setChVenteTrue(); // On doit commencer à Vélizy (V+)
        chVilles.get("Velizy").setChAchatTrue(); // On doit finir à Vélizy (V-)
        voisinsSortants.put(chVilles.get("Velizy"), new HashMap<>());
        for (Commande commande : chCommandes) {
            commande.getChVendeur().getChVille().setChVenteTrue();
            commande.getChAcheteur().getChVille().setChAchatTrue();
            voisinsSortants.put(commande.getChVendeur().getChVille(), new HashMap<>());
            voisinsSortants.put(commande.getChAcheteur().getChVille(), new HashMap<>());
        }
        return d.triTopologique();
    }

    public HashMap<String,Utilisateur> getChUtilisateurs() {
        return chUtilisateurs;
    }

    public HashMap<String, Ville> getChVilles() {
        return chVilles;
    }

    public HashSet<Commande> getChCommandes() {
        return chCommandes;
    }

    public String toString() {
        String resultat = "Utilisateurs :\n";
        for (Utilisateur utilisateur : chUtilisateurs.values()) {
            resultat += "- " + utilisateur.toString() + "\n";
        }
        resultat += "Villes :\n";
        for (Ville ville : chVilles.values()) {
            resultat += "- " + ville.toString() + "\n";
        }
        resultat += "Commandes :\n";
        for (Commande commande : chCommandes) {
            resultat += "- " + commande.toString() + "\n";
        }
        return resultat;
    }
}
