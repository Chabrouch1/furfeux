// La classe Sortie représente une case traversable particulière, la sortie, dans un jeu.

public class Sortie extends CaseTraversable {
    private Joueur contenu; // Joueur actuellement contenu dans la sortie, null s'il n'y en a pas
    private int chaleur;    // Valeur représentant la chaleur associée à la sortie

    // Constructeur pour une sortie avec une position spécifiée (lig, col) et une chaleur spécifiée
    public Sortie(int lig, int col, int chaleur) {
        super(lig, col);    // Appelle le constructeur de la classe parente (CaseTraversable)
        this.chaleur = chaleur;
        this.contenu = null; // Initialise le contenu à null, car une sortie est initialement vide
    }

    // Méthode pour obtenir la valeur de la chaleur associée à la sortie
    public int getChaleur() {
        return this.chaleur;
    }
}
