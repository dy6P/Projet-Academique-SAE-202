package modele;

import java.util.*;

public class Scenario {
    private TreeMap<String, Utilisateur> chUtilisateurs;
    private TreeMap<String, Ville> chVilles;
    private HashSet<Commande> chCommandes;
    private TreeMap<String, TreeMap<String, Integer>> chDistances;
    private TreeMap<Integer, ArrayList<String>> cheminsBrut;
    private TreeMap<Integer, ArrayList<String>> cheminsNet;
    private Digraphe chDigraphe;
    private String chDepart;

    public Scenario(String parDepart) {
        chUtilisateurs = new TreeMap<>();
        chVilles = new TreeMap<>();
        chCommandes = new HashSet<>();
        chDistances = new TreeMap<>();
        cheminsBrut = new TreeMap<>();
        cheminsNet = new TreeMap<>();
        chDepart = parDepart;
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

    public void trouverChemins() {
        ArrayList<String> chemin = new ArrayList<>();
        TreeMap<String, TreeSet<String>> voisinsSortants = new TreeMap<>();
        voisinsSortants.put(chDepart + " - ", new TreeSet<>());
        voisinsSortants.put(chDepart + " + ", new TreeSet<>());
        for (Commande commande : chCommandes) {
            if (!commande.getChVendeur().getChVille().getChNom().equals(chDepart) && !commande.getChAcheteur().getChVille().getChNom().equals(chDepart)) {
                String villeVente = commande.getChVendeur().getChVille().getChNom() + " + ";
                String villeAchat = commande.getChAcheteur().getChVille().getChNom() + " - ";
                if (!voisinsSortants.containsKey(villeVente)) {
                    voisinsSortants.put(villeVente, new TreeSet<>());
                }
                if (!voisinsSortants.containsKey(villeAchat)) {
                    voisinsSortants.put(villeAchat, new TreeSet<>());
                }
                voisinsSortants.get(villeVente).add(villeAchat);
                voisinsSortants.get(villeAchat).add(chDepart + " - ");
                voisinsSortants.get(chDepart + " + ").add(villeVente);
            }
        }
        chDigraphe = new Digraphe(voisinsSortants, chDistances, chDepart);
        cheminsBrut = chDigraphe.solutions();
    }

    public void cheminsNet() {
        for (int distance : cheminsBrut.keySet()) {
            cheminsNet.put(distance, new ArrayList<>());
            for (int ville = 0; ville < cheminsBrut.get(distance).size(); ville++) {
                if (ville > 0 && !cheminsBrut.get(distance).get(ville).split(" ")[0].equals(cheminsBrut.get(distance).get(ville - 1).split(" ")[0])) {
                    cheminsNet.get(distance).add(cheminsBrut.get(distance).get(ville).split(" ")[0]);
                }
            }
            cheminsNet.get(distance).addFirst(chDepart);
        }
    }

    public String toString() {
        String resultat = "\n";
        resultat += "Commandes :\n";
        for (Commande commande : chCommandes) {
            resultat += "- " + commande.toString() + "\n";
        }
        resultat += "\n" + chDigraphe.toString();
        int i = 1;
        for (int distance : cheminsBrut.keySet()) {
            resultat += "\n" + "SOLUTION " + i + " :\n" + "CHEMIN_BRUT =";
            for (String ville : cheminsBrut.get(distance)) {
                resultat += " -> " + ville;
            }
            resultat += "\nCHEMIN_NET =";
            for (String ville : cheminsNet.get(distance)) {
                resultat += " -> " + ville;
            }
            resultat += "\nDISTANCE = " + distance + " KM\n";
            i ++;
        }
        return resultat;
    }
}
