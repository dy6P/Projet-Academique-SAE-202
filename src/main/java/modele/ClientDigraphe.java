package modele;

public class ClientDigraphe {
    public static void main(String[] args) {
        int [][] tabVoisinsSortants = {
                {1, 2, 4},
                {2},
                {1, 3, 4},
                {2, 4},
                {2, 3},
        };
        Digraphe g1 = new Digraphe(tabVoisinsSortants);
        System.out.println(g1);
        System.out.println(g1.getDegresEntrants());
        System.out.println(g1.getSources());
    }
}
