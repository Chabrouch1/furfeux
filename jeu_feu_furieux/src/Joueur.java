//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import javax.swing.JComboBox;
//import java.awt.event.ActionListener;
//import java.util.Objects;
import javax.swing.*;
import java.awt.Color;

// La classe Joueur représente un joueur dans le jeu, avec une position dans une case traversable, une résistance, des clés, et une couleur.

public class Joueur {
    private CaseTraversable c; // Case traversable actuelle où se trouve le joueur
    private int resistance;    // Valeur représentant la résistance du joueur
    private int cles;          // Nombre de clés détenues par le joueur
    public Color couleur;      // Couleur associée au joueur

    // Constructeur initialisant la position, la résistance et le nombre de clés du joueur
    public Joueur(CaseTraversable c, int r, int k) {
        this.c = c;
        this.resistance = r;
        this.cles = k;
    }

    // Méthode pour obtenir la couleur associée au joueur
    public Color getCouleur() {
        return this.couleur;
    }

    // Méthode pour définir la couleur du joueur
    public void setColor(Color couleur) {
        this.couleur = couleur;
    }

    // Méthode pour obtenir la résistance du joueur
    public int getResistance() {
        return this.resistance;
    }

    // Méthode pour obtenir la case traversable actuelle où se trouve le joueur
    public Case getCase() {
        return this.c;
    }

    // Méthode pour définir la nouvelle case où se trouve le joueur
    public void setC(Case newCase) {
        if (newCase instanceof CaseTraversable) {
            this.c = (CaseTraversable) newCase;
        } else {
            System.out.println("La case n'est pas traversable.");
        }
    }

    // Méthode pour simuler l'ouverture d'une porte par le joueur
    public void ouvreUnePorte() {
        this.cles -= 1;
    }

    // Méthode pour récupérer une clé si la case actuelle en contient une
    public void recupcles() {
        if (((Hall) c).keys) {
            this.cles += 1;
            ((Hall) c).keys = false;
        }
    }

    // Méthode pour obtenir le nombre de clés détenu par le joueur
    public int getcles() {
        return this.cles;
    }

    // Méthode pour infliger des dégâts au joueur
    public void prendDegat(int deg) {
        this.resistance -= deg;
    }

    // Méthode pour vérifier si la partie est terminée (le joueur est sorti ou a épuisé sa résistance)
    public boolean partieFinie() {
        return this.getCase() instanceof Sortie || this.getResistance() <= 0;
    }
}
