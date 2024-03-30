// La classe Mur représente une case traversable particulière, un mur, dans un jeu.

public class Mur extends CaseTraversable {

    // Constructeur pour un mur avec une position spécifiée (l, c)
    public Mur(int l, int c) {
        super(l, c);  // Appelle le constructeur de la classe parente (CaseTraversable)
    }

    // Méthode pour vérifier si le mur est traversable (toujours faux pour un mur)
    public boolean estTraversable() {
        return false;
    }
}
