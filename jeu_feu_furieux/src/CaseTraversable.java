// La classe CaseTraversable étend la classe de base Case, représentant une case traversable dans un jeu.

public class CaseTraversable extends Case {
    public int chaleur;            // Attribut représentant la chaleur associée à la case
    private Joueur contenu;        // Joueur actuellement contenu dans la case, null s'il n'y en a pas

    // Constructeur pour une case traversable avec une position spécifiée (lig, col)
    public CaseTraversable(int lig, int col) {
        super(lig, col);
    }

    // Constructeur pour une case traversable avec une position spécifiée (lig, col) et une chaleur spécifiée
    public CaseTraversable(int lig, int col, int chaleur) {
        super(lig, col);
        this.chaleur = chaleur;
    }

    // Méthode pour obtenir le joueur actuellement contenu dans la case
    public Joueur getContenu() {
        return this.contenu;
    }

    // Méthode pour vider la case de son contenu (joueur)
    public void vide() {
        this.contenu = null;
    }

    // Méthode pour faire entrer un joueur dans la case, si la case est libre
    public void entre(Joueur e) {
        if (this.contenu == null) {
            this.contenu = e;
            e.setC(this);   // Met à jour la position du joueur pour refléter sa nouvelle position dans la case
        }
    }

    // Méthode pour vérifier si la case est libre (ne contient pas de joueur)
    public boolean estLibre() {
        return this.contenu == null;
    }

    // Méthode indiquant que la case est traversable (par opposition à un mur, par exemple)
    public boolean estTraversable() {
        return true;
    }
}
