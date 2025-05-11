package modele;

import java.util.*;

public class Scenario {
    private TreeMap<String, Utilisateur> chUtilisateurs;
    private TreeMap<String, Ville> chVilles;
    private HashSet<Commande> chCommandes;
    private TreeMap<String, TreeMap<String, Integer>> chDistances;

    public Scenario() {
        chUtilisateurs = new TreeMap<>();
        chVilles = new TreeMap<>();
        chCommandes = new HashSet<>();
        chDistances = new TreeMap<>();
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

    public void ajouterDistances() {
        for (Ville ville : chVilles.values()) {
            chDistances.put(ville.getChNom(), new TreeMap<>());
            for (Ville v : ville.getChDistances().keySet()) {
                chDistances.get(ville.getChNom()).put(v.getChNom(), ville.getChDistances().get(v));
            }
        }
    }

    public void trouverChemin(String parDepart) {
        ArrayList<String> chemin = new ArrayList<>();
        TreeMap<String, TreeSet<String>> voisinsSortants = new TreeMap<>();
        voisinsSortants.put(parDepart + " - ", new TreeSet<>());
        voisinsSortants.put(parDepart + " + ", new TreeSet<>());
        for (Commande commande : chCommandes) {
            if (!commande.getChVendeur().getChVille().getChNom().equals(parDepart) && !commande.getChAcheteur().getChVille().getChNom().equals(parDepart)) {
                String villeVente = commande.getChVendeur().getChVille().getChNom() + " + ";
                String villeAchat = commande.getChAcheteur().getChVille().getChNom() + " - ";
                if (!voisinsSortants.containsKey(villeVente)) {
                    voisinsSortants.put(villeVente, new TreeSet<>());
                }
                if (!voisinsSortants.containsKey(villeAchat)) {
                    voisinsSortants.put(villeAchat, new TreeSet<>());
                }
                voisinsSortants.get(villeVente).add(villeAchat);
            }
        }
        for (String ville : new HashSet<>(voisinsSortants.keySet())) { // copie de voisinsSortants pour éviter une erreur (on aurait pu faire la boucle avec les indices aussi)
            if (!ville.equals(parDepart + " + ") && !ville.split(" ")[1].equals("-") || ville.equals(parDepart + " - ")) {
                voisinsSortants.get(parDepart + " + ").add(ville);
            }
            if (!ville.split(" ")[1].equals("+") && !ville.equals(parDepart + " - ")) {
                voisinsSortants.get(ville).add(parDepart + " - ");
            }
        }
        Digraphe d = new Digraphe(voisinsSortants, chDistances);
        d.DegresEntrants();
        d.triTopologique(parDepart);
        System.out.println(d);
        System.out.println(d.getChTriTopologique());
    }

    public TreeMap<String,Utilisateur> getChUtilisateurs() {
        return chUtilisateurs;
    }

    public TreeMap<String, Ville> getChVilles() {
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
