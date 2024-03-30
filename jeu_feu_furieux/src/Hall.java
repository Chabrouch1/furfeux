// La classe Hall représente une case traversable spécifique, un hall, dans un jeu.

public class Hall extends CaseTraversable {
    public boolean keys;     // Indique la présence de clés dans le hall
    public int chaleur;      // Niveau de chaleur associé au hall

    // Constructeur pour un hall sans clés et avec une chaleur initiale de 0
    public Hall(int lig, int col) {
        super(lig, col);
        this.keys = false;
        this.chaleur = 0;
    }

    // Constructeur pour un hall avec une indication de la présence de clés et une chaleur initiale de 0
    public Hall(int lig, int col, boolean k) {
        super(lig, col);
        this.keys = k;
        this.chaleur = 0;
    }

    // Constructeur pour un hall avec une chaleur initiale spécifiée
    public Hall(int lig, int col, int chaleur) {
        super(lig, col);
        this.chaleur = chaleur;
    }

    // Méthode pour faire entrer un joueur dans le hall
    public void entre(Joueur j) {
        j.setC(this);  // Met à jour la position du joueur pour refléter sa nouvelle position dans le hall
        if (this.keys) {
            j.recupcles();  // Si des clés sont présentes dans le hall, le joueur les récupère
            this.keys = false;  // Les clés sont ensuite retirées du hall
        }
    }

    // Méthode indiquant que le hall est traversable
    public boolean estTraversable() {
        return true;
    }

    // Méthode pour propager une certaine quantité de chaleur dans le hall
    public void propage(int chaleur) {
        this.chaleur += chaleur;
    }

    // Méthode pour obtenir le niveau de chaleur associé au hall
    public int getChaleur() {
        return this.chaleur;
    }

    // Méthode pour obtenir l'indication de la présence de clés dans le hall
    public boolean getkeys() {
        return this.keys;
    }
}
