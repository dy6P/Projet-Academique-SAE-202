package modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

public class Scenario {
    private HashMap<String, Utilisateur> chUtilisateurs;
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

    public void trouverChemin(String parDepart) {
        ArrayList<String> chemin = new ArrayList<>();
        TreeMap<String, TreeMap<String, Integer>> voisinsSortants = new TreeMap<>();
        voisinsSortants.put(parDepart + " - ", new TreeMap<>());
        voisinsSortants.put(parDepart + " + ", new TreeMap<>());
        for (Commande commande : chCommandes) {
            if (!commande.getChVendeur().getChVille().getChNom().equals(parDepart) && !commande.getChAcheteur().getChVille().getChNom().equals(parDepart)) {
                String villeVente = commande.getChVendeur().getChVille().getChNom() + " + ";
                String villeAchat = commande.getChAcheteur().getChVille().getChNom() + " - ";
                if (!voisinsSortants.containsKey(villeVente)) {
                    voisinsSortants.put(villeVente, new TreeMap<>());
                }
                if (!voisinsSortants.containsKey(villeAchat)) {
                    voisinsSortants.put(villeAchat, new TreeMap<>());
                }
                voisinsSortants.get(villeVente).put(villeAchat, chVilles.get(villeVente.split(" ")[0]).getChDistances().get(chVilles.get(villeAchat.split(" ")[0])));
                voisinsSortants.get(villeVente).put(villeVente.split(" ")[0] + " - ", chVilles.get(villeVente.split(" ")[0]).getChDistances().get(chVilles.get(villeVente.split(" ")[0])));
                voisinsSortants.get(villeVente).put(parDepart + " - ", chVilles.get(villeVente.split(" ")[0]).getChDistances().get(chVilles.get(parDepart)));
            }
        }
        for (String ville : new HashSet<>(voisinsSortants.keySet())) { // copie de voisinsSortants pour éviter une erreur (on aurait pu faire la boucle avec les indices aussi)
            if (!ville.equals(parDepart + " + ")) {
                voisinsSortants.get(parDepart + " + ").put(ville, chVilles.get(parDepart).getChDistances().get(chVilles.get(ville.split(" ")[0])));
            }
            if (!ville.split(" ")[0].equals(parDepart) && ville.split(" ")[1].equals("+")) {
                for (String v : voisinsSortants.get(ville).keySet()) {
                    if (!v.split(" ")[0].equals(ville.split(" ")[0]) && v.split(" ")[1].equals("-")) {
                        if (!voisinsSortants.containsKey(ville.split(" ")[0] + " - ")) {
                            voisinsSortants.put(ville.split(" ")[0] + " - ", new TreeMap<>());
                        }
                        voisinsSortants.get(ville.split(" ")[0] + " - ").put(v, chVilles.get(ville.split(" ")[0]).getChDistances().get(chVilles.get(v.split(" ")[0])));
                    }
                }
            }
        }
        Digraphe d = new Digraphe(voisinsSortants);
        d.DegresEntrants();
        d.triTopologique(parDepart);
        System.out.println(d);
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
