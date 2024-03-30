import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// La classe Terrain représente le terrain de jeu dans le jeu Furfeux.

public class Terrain {
    private int hauteur, largeur;  // Dimensions du terrain
    private Case[][] carte;        // Carte du terrain
    private Joueur joueur;         // Joueur sur le terrain

    // Constructeur de la classe Terrain, initialisant le terrain à partir d'un fichier de configuration
    public Terrain(String file) {
        try {
            Scanner sc = new Scanner(new FileInputStream(file));
            this.hauteur = sc.nextInt();    // Lecture de la hauteur du terrain depuis le fichier
            this.largeur = sc.nextInt();    // Lecture de la largeur du terrain depuis le fichier
            sc.nextLine();                  // Passage à la ligne suivante dans le fichier

            // Lecture des valeurs initiales du joueur depuis le fichier
            int resistanceJoueur = sc.nextInt();
            int cles = sc.nextInt();
            sc.nextLine();                  // Passage à la ligne suivante dans le fichier

            this.carte = new Case[hauteur][largeur];  // Initialisation de la carte avec les dimensions spécifiées

            // Initialisation de la carte à partir du fichier
            for (int l = 0; l < hauteur; l++) {
                String line = sc.nextLine();
                for (int c = 0; c < largeur; c++) {
                    Case cc;
                    Character ch = line.charAt(c);

                    // Création d'une case en fonction du caractère lu depuis le fichier
                    switch (ch) {
                        case '#': cc = new Mur(l, c); break;
                        case ' ': cc = new Hall(l, c); break;
                        case '+': cc = new Hall(l, c, true); break;
                        case '1': case '2': case '3': case '4':
                            cc = new Hall(l, c, (int) ch - (int) '0'); break;
                        case 'O': cc = new Sortie(l, c, 0); break;
                        case '@': cc = new Porte(l, c, false); break;
                        case '.': cc = new Porte(l, c, true); break;
                        case 'H':
                            // Vérification s'il y a déjà un joueur sur la carte
                            if (this.joueur != null)
                                throw new IllegalArgumentException("carte avec deux joueurs");

                            cc = new Hall(l, c);
                            // Création du joueur et placement sur la case Hall
                            this.joueur = new Joueur((CaseTraversable) cc, resistanceJoueur, cles);
                            ((Hall) cc).entre(joueur);
                            break;
                        default: cc = null; break;
                    }

                    carte[l][c] = cc;  // Ajout de la case à la carte
                }
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Méthode pour obtenir le joueur du terrain
    public Joueur getJoueur() {
        return this.joueur;
    }

    // Méthode pour obtenir la largeur du terrain
    public int getLargeur() {
        return this.largeur;
    }

    // Méthode pour obtenir la hauteur du terrain
    public int getHauteur() {
        return this.hauteur;
    }

    // Méthode pour obtenir la carte du terrain
    public Case[][] getCarte() {
        return this.carte;
    }

    // Méthode pour obtenir la case à une position spécifique
    public Case SetCase(int lig, int col) {
        return this.carte[lig][col];
    }

    // Méthode pour obtenir les cases traversables voisines d'une position spécifique
    public ArrayList<CaseTraversable> getVoisinesTraversables(int lig, int col) {
        ArrayList caselibres = new ArrayList<>();
        for (int i = lig - 1; i <= lig + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                Case newcase = this.SetCase(i, j);
                // Vérification si la case existe et si elle est traversable
                if (newcase != null && newcase.estTraversable()) {
                    caselibres.add(newcase); // ajout à la liste la case
                }
            }
        }
        return caselibres;
    }

    // Méthode pour propager le feu sur le terrain
    public void propagateFire() {
        Case[][] carte = this.carte; // Récupération de la carte du terrain

        // Parcours de chaque case du terrain
        for (int i = 0; i < this.hauteur; i++) {
            for (int j = 0; j < this.largeur; j++) {
                Case currentCase = carte[i][j];// Récupération de la case actuelle

                // Vérification si la case actuelle est un Hall
                if (currentCase instanceof Hall) {

                    int totalChaleur = ((Hall) currentCase).getChaleur();// Récupération de la chaleur de la case actuelle

                    // Récupération des cases traversables voisines
                    ArrayList<CaseTraversable> neighbors = this.getVoisinesTraversables(i, j);

                    // Calcul de la somme totale de la chaleur des cases voisines
                    for (CaseTraversable neighbor : neighbors) {
                        if(neighbor instanceof Hall){
                            totalChaleur += ((Hall) neighbor).getChaleur();
                        }
                    }

                    // Génération d'un nombre aléatoire
                    int randomNumber = (int) (Math.random() * 200);

                    // Si le nombre aléatoire est inférieur à la chaleur totale, propager le feu
                    if (randomNumber < totalChaleur) {
                        // Propagation du feu avec une intensité limitée par la chaleur actuelle de la case
                        ((Hall) currentCase).propage(Math.min(10 - ((Hall) currentCase).getChaleur(), 1));
                    }
                }
            }
        }
    }

    // Méthode pour ouvrir une porte à une position spécifique si la case est une porte et le joueur a au moin une clé
    public void ouvrePorte(Joueur j,Case cible) {
        // Vérification si la cible est une porte
        if (cible instanceof Porte) {
            // Vérification si la porte n'est pas traversable et si le joueur a des clés
            if (!cible.estTraversable() && j.getcles() > 0) {
            // Ouvre la porte en diminuant le nombre de clés du joueur
                j.ouvreUnePorte();
                // Remplace la porte par une case Hall dans la carte
                carte[cible.getLig()][cible.getCol()]=new Hall(cible.getLig(),cible.getCol());
            }


        }
    }

    // Méthode pour effectuer un mouvement du joueur dans une direction spécifique
    public void mouvement(Direction d) {

        //récuperation de la ligne et de la colone de la case acutelle
        int currLig=this.joueur.getCase().getLig();
        int currCol=this.joueur.getCase().getCol();

        //movement vers la direction en paramètre
        //ouvrir une porte à une position spécifique si la case est une porte et le joueur a au moin une clé
        //on vérifie l'instance de la case pour faire le mouvement
        switch (d) {
            case est:
                this.ouvrePorte(joueur,carte[currLig][currCol+1]);
                if (carte[currLig][currCol+1] instanceof Porte){
                    ((Porte) carte[currLig][currCol+1]).entre(this.joueur);
                }
                else if (carte[currLig][currCol+1] instanceof Sortie){
                    ((Sortie) carte[currLig][currCol+1]).entre(this.joueur);
                }
                else if (carte[currLig][currCol+1] instanceof Hall){
                    ((Hall) carte[currLig][currCol+1]).entre(this.joueur);
                }

                break;
            case sud:
                this.ouvrePorte(joueur,carte[currLig+1][currCol]);

                if (carte[currLig+1][currCol] instanceof Porte){
                    ((Porte) carte[currLig+1][currCol]).entre(this.joueur);
                }
                else if (carte[currLig+1][currCol] instanceof Sortie){
                    ((Sortie) carte[currLig+1][currCol]).entre(this.joueur);
                }
                else if (carte[currLig+1][currCol] instanceof Hall){
                    ((Hall) carte[currLig+1][currCol]).entre(this.joueur);
                }
                break;
            case nord:
                this.ouvrePorte(joueur,carte[currLig-1][currCol]);

                if (carte[currLig-1][currCol] instanceof Porte){
                    ((Porte) carte[currLig-1][currCol]).entre(this.joueur);
                }
                else if (carte[currLig-1][currCol] instanceof Sortie){
                    ((Sortie) carte[currLig-1][currCol]).entre(this.joueur);
                }
                else if (carte[currLig-1][currCol] instanceof Hall){
                    ((Hall) carte[currLig-1][currCol]).entre(this.joueur);
                }
                break;

            case ouest:
                this.ouvrePorte(joueur,carte[currLig][currCol-1]);

                if (carte[currLig][currCol-1] instanceof Porte){
                    ((Porte) carte[currLig][currCol-1]).entre(this.joueur);
                }
                else if (carte[currLig][currCol-1] instanceof Sortie){
                    ((Sortie) carte[currLig][currCol-1]).entre(this.joueur);
                }
                else if (carte[currLig][currCol-1] instanceof Hall){
                    ((Hall) carte[currLig][currCol-1]).entre(this.joueur);
                }
                break;


        }
    }

}
