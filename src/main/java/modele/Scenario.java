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
            chUtilisateurs.put(element.split(" ")[0],new Utilisateur(element.split(" ")[0], element.split(" ")[1]));
        }
    }

    public void ajouterVilles(ArrayList<String> parListe) {
        for (int i = 0; i < parListe.size(); i++) {
            Ville ville = new Ville(parListe.get(i).split(" ")[0]);
            int valeursVides = 0;
            for (int j = 1; j < parListe.get(i).split(" ").length - 1 - valeursVides; j++) {
                if (parListe.get(i).split(" ")[j].isEmpty()) {
                    valeursVides++;
                    ville.ajouterDistance(parListe.get(j).split(" ")[0], 99999999);
                }
                else {
                    ville.ajouterDistance(parListe.get(j).split(" ")[0], Integer.parseInt(parListe.get(i).split(" ")[j]));
                }
            }
            chVilles.put(ville.getChNom() + " +", ville);
            chVilles.put(ville.getChNom() + " -", ville);
        }
    }

    public void ajouterCommandes(ArrayList<String> parListe) {
        for (String element : parListe) {
            chCommandes.add(new Commande(element.split(" -> ")[0], element.split(" -> ")[1]));
        }
    }

    public ArrayList<String> trouverChemin() {
        ArrayList<String> chemin = new ArrayList<>();
        HashMap<Ville,ArrayList<Ville>> voisinSortant = new HashMap<>();
        voisinSortant.put(chVilles.get("Velizy +"), new ArrayList<>());
        for (Commande commande : chCommandes) {
            String ville = chUtilisateurs.get(commande.getChVendeur()).getChVille();
            if (voisinSortant.containsKey(ville)) {
                voisinSortant.put(chVilles.get(ville), new ArrayList<>());
            }
        }
        return chemin;
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
