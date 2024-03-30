// La classe Porte représente une case traversable particulière, une porte, dans un jeu.

public class Porte extends CaseTraversable {
    private int l;            // Coordonnée en ligne de la porte (redondante avec lig de la classe parente)
    private int c;            // Coordonnée en colonne de la porte (redondante avec col de la classe parente)
    private boolean ouverte;  // Indique si la porte est ouverte ou fermée
    private Joueur contenu;   // Joueur actuellement contenu dans la porte, null s'il n'y en a pas

    // Constructeur pour une porte avec une position spécifiée (l, c) et un état initial d'ouverture
    public Porte(int l, int c, boolean ouvert) {
        super(l, c);          // Appelle le constructeur de la classe parente (CaseTraversable)
        this.ouverte = ouvert;
        this.contenu = null;  // Initialise le contenu à null, car une porte est initialement vide
    }

    // Méthode pour ouvrir la porte
    public void ouvrePorte() {
        this.ouverte = true;
    }

    // Méthode pour faire entrer un joueur dans la porte, si la porte est ouverte et libre
    public void entre(Joueur e) {
        if (this.contenu == null && this.estTraversable()) {
            this.contenu = e;
            e.setC(this);   // Met à jour la position du joueur pour refléter sa nouvelle position dans la porte
        }
    }

    // Méthode pour vérifier si la porte est traversable (ouverte)
    public boolean estTraversable() {
        return this.ouverte;
    }
}
