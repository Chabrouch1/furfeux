// La classe abstraite Case représente une case dans un jeu, avec une position définie par les coordonnées (lig, col).

import java.util.ArrayList;

public abstract class Case {
    public final int lig, col;  // Coordonnées de la case
    // Constructeur initialisant les coordonnées de la case
    public Case(int l, int c) {
        this.lig = l;
        this.col = c;
    }

    // Méthode abstraite pour déterminer si la case est traversable (doit être implémentée dans les classes dérivées)
    public abstract boolean estTraversable();

    // Méthode pour obtenir la coordonnée en ligne (lig) de la case
    public int getLig() {
        return this.lig;
    }

    // Méthode pour obtenir la coordonnée en colonne (col) de la case
    public int getCol() {
        return this.col;
    }
}
