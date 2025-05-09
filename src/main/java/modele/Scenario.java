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
        for (int i = 0; i < parListe.size(); i++) {
            chVilles.put(parListe.get(i).split("\\s+")[0], new Ville(parListe.get(i).split("\\s+")[0]));
        }
        for (int i = 0; i < parListe.size(); i++) {
            for (int j = 1; j < parListe.get(i).split("\\s+").length; j++) {
                if (!parListe.get(i).split("\\s+")[j].isEmpty()) {
                    chVilles.get(parListe.get(i).split("\\s+")[0]).ajouterDistance(chVilles.get(parListe.get(j - 1).split("\\s+")[0]), Integer.parseInt(parListe.get(i).split("\\s+")[j]));
                }
            }
        }
    }

    public void ajouterCommandes(ArrayList<String> parListe) {
        for (String element : parListe) {
            chCommandes.add(new Commande(chUtilisateurs.get(element.split(" -> ")[0]), chUtilisateurs.get(element.split(" -> ")[1])));
        }
    }

    public void trouverChemin() {
        ArrayList<String> chemin = new ArrayList<>();
        ArrayList<Ville> villes = new ArrayList<>();
        chVilles.get("Velizy").setChVenteTrue(); // On doit commencer à Vélizy (V+)
        chVilles.get("Velizy").setChAchatTrue(); // On doit finir à Vélizy (V-)
        villes.add(chVilles.get("Velizy"));
        for (Commande commande : chCommandes) {
            commande.getChVendeur().getChVille().setChVenteTrue();
            commande.getChAcheteur().getChVille().setChAchatTrue();
            villes.add(commande.getChVendeur().getChVille());
            villes.add(commande.getChAcheteur().getChVille());
        }
        Digraphe d = new Digraphe(villes);
        System.out.println(d.getDegresEntrants());
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
            for (Ville v : ville.getChDistances().keySet()) {
                resultat += "- " + ville.getChNom() + " est à une distance de " + ville.getChDistances().get(v) + " km de " + v.getChNom() + "\n";
            }
        }
        resultat += "\n";
        resultat += "Commandes :\n";
        for (Commande commande : chCommandes) {
            resultat += "- " + commande.toString() + "\n";
        }
        return resultat;
    }
}
