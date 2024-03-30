// La classe CaseIntraversable étend la classe de base Case, représentant une case intraversable dans un jeu.

public class CaseIntraversable extends Case {
    private Joueur contenu;  // Joueur actuellement contenu dans la case intraversable, null s'il n'y en a pas

    // Constructeur pour une case intraversable avec une position spécifiée (lig, col)
    public CaseIntraversable(int lig, int col) {
        super(lig, col);
        this.contenu = null; // Initialise le contenu à null, car une case intraversable est initialement vide
    }

    // Méthode pour vérifier si la case intraversable est libre (ne contient pas de joueur)
    public boolean estLibre() {
        return this.contenu == null;
    }

    // Méthode pour obtenir le joueur actuellement contenu dans la case intraversable
    public Joueur getContenu() {
        return this.contenu;
    }

    // Méthode pour vider la case intraversable de son contenu (joueur)
    public void vide() {
        this.contenu = null;
    }

    // Méthode pour faire entrer un joueur dans la case intraversable, si la case est libre
    public void entre(Joueur e) {
        if (this.contenu == null) {
            this.contenu = e;
        }
    }

    // Méthode indiquant que la case intraversable n'est pas traversable
    public boolean estTraversable() {
        return false;
    }

    // Méthode pour représenter la case intraversable sous forme de chaîne de caractères
    public String toString() {
        return "###";  // Représentation visuelle d'une case intraversable dans le jeu
    }
}
