import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// La classe Furfeux représente le jeu Furfeux et gère son fonctionnement.

public class Furfeux {

    Terrain terrain;  // Instance de la classe Terrain représentant le terrain de jeu
    Joueur joueur;    // Instance de la classe Joueur représentant le joueur du jeu

    // Constructeur de la classe Furfeux, initialisant le terrain et le joueur avec un fichier de configuration spécifié
    public Furfeux(String f) {
        this.terrain = new Terrain(f);
        this.joueur = terrain.getJoueur();  // Initialise le joueur avec la position initiale définie dans le terrain
    }

    // Méthode représentant un tour de jeu
    public void tour() {
        // Si le joueur se trouve dans un hall, il subit des dégâts liés à la chaleur du hall
        if (joueur.getCase() instanceof Hall) {
            joueur.prendDegat(((Hall) joueur.getCase()).getChaleur());
        }

        // Propagation du feu sur le terrain
        terrain.propagateFire();
    }
}
