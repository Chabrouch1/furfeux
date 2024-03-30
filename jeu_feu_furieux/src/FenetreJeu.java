import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//La classe principale du jeu (fenetre graphique)
public class FenetreJeu extends JPanel implements KeyListener{
    private Terrain terrain;
    private int tailleCase = 36;
    private int hauteur, largeur;
    private JFrame frame;
    private Joueur joueur;
    private BufferedImage imageSoldat;
    private BufferedImage imageKey;

    private BufferedImage imageBricks;
    private boolean choixSoldat;

    private String choixMap;
    private BufferedImage imageBackground;
    public FenetreJeu(Terrain t) {
        this.hauteur = t.getHauteur();
        this.largeur = t.getLargeur();
        this.terrain = t;
        this.joueur=t.getJoueur();
        this.choixSoldat=false;
        this.joueur.setColor(Color.CYAN);
        this.choixMap= "manoir.txt";
        //setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(9 * tailleCase, 9 * tailleCase));

        JFrame frame = new JFrame("Furfeux");
        this.frame = frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        frame.addKeyListener(this);
        //chargement des images
        try  {
            imageSoldat = ImageIO.read(new File("soldat.png"));
            imageKey = ImageIO.read(new File("key.png"));
            imageBackground=ImageIO.read(new File("grass.png"));
            imageBricks=ImageIO.read(new File("bricks.png"));
        } catch (IOException e) { //on fait un catch en cas de soucis lies au load des images
            e.printStackTrace();
        }

    }

    private void showMainMenu(Furfeux f) { //configuration de notre menu
        removeAll(); // Clear the panel

        setLayout(new GridLayout(3, 1));//on creer une grille de 3 lignes et 1 ligne place de haut en bas

        JButton playButton = new JButton("jouer");//creation du bouton jouer
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {//cette methode permet de lancer le jeu lors de la selection de ce mode avec la souris
                    paintjoueur();
                startGame(f);
            }
        });

        JButton rulesButton = new JButton("comment jouer");//deuxieme ligne de notre menu (guide)
        rulesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRules(f);
            }
        });

        JButton exitButton = new JButton("Quitter");//troisieme ligne de notre menu
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //ajout des boutons
        add(playButton);
        add(rulesButton);
        add(exitButton);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the panel
    }

    private void startGame(Furfeux jeu) {

        removeAll();
        int tempo = 100;
        Timer timer = new Timer(tempo, e -> {
            jeu.tour();
            this.repaint();
            if (jeu.joueur.partieFinie()) {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.start();
    }

    private void showRules(Furfeux f) {
        removeAll(); // Clear the panel

        setLayout(new BorderLayout());//mise en page (Disposition du panneau)
        //on affiche les regles du jeu
        JTextArea rulesText = new JTextArea("\n" +
                "Le but de ce tutoriel est de guider le joueur vers la sortie.\nLe joueur a la possibilité de se déplacer dans les quatre directions, mais il voit seulement une partie de la carte à la fois.\n En déplaçant le soldat, la carte change dynamiquement.\n" +
                "\n" +
                "Les portes fermées sont représentées en vert.\n Une fois que les portes sont ouvertes, le joueur peut les franchir.\n Pour ouvrir les portes, le joueur peut collecter des clés dispersées dans le niveau.\n" +
                "\n" +
                "Les Halls, que le soldat peut traverser, peuvent contenir du feu.\n La couleur du feu correspond à celle du Hall.\n Plus la teinte de rouge est foncée, plus le feu inflige de dégâts au joueur.\n" +
                "\n" +
                "La partie peut se terminer de deux manières.\n Soit le joueur atteint la sortie (marquée en bleu), remportant ainsi la victoire.\n Soit le joueur succombe aux flammes, mettant fin à la partie.\n Bonne chance, soldat!");
        rulesText.setEditable(false);//on le mets non modifiable
        JButton backButton = new JButton("Retour");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMainMenu(f);
            }
        });

        add(rulesText, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        revalidate(); // Refresh the layout
        repaint(); // Repaint the panel
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);//on depeint le rendu de base du composant

        if (joueur.partieFinie()){//si la partie est finie
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());//un ecran gris est dessine sur toute la zone de jeu
            int finalScore=Math.max(0, joueur.getResistance());//on definit le score du joueur

            g.setColor(Color.BLACK);
            g.setFont(new Font("Verdana", Font.BOLD, 30));
            String scoreText = "Score: " + finalScore;
            int textWidth = g.getFontMetrics().stringWidth(scoreText);
            int x = getWidth() / 2 - textWidth / 2;
            int y = getHeight() / 2;
            g.drawString(scoreText, x, y);

        }
        else {//dans le cas ou la partie n'est pas finie on rentre dans ce cas
                int center_Y = terrain.getJoueur().getCase().getLig();//on get la ligne du joueur
                int center_X = terrain.getJoueur().getCase().getCol();//on get la colonne du joueur
                int rayon = 3;//rayon de vision du joueur
                g.drawImage(imageBackground, 0, 0, this);
                for (int i = center_Y - rayon; i <= center_Y + rayon; i++) {//on parcours les cases selon la vision du joueur (sa position moins/plus rayon)
                    for (int j = center_X - rayon; j <= center_X + rayon; j++) {
                        if (i >= 0 && i < terrain.getHauteur() && j >= 0 && j < terrain.getLargeur()) {//on verifie qu'on est bien dans les limites du terrain
                            Case currentCase = terrain.getCarte()[i][j];//On obtient la case actuelle à la position (i, j).
                            Color caseColor = getColor(currentCase);

                            int x = (j - (center_X - rayon)) * tailleCase;
                            int y = (i - (center_Y - rayon)) * tailleCase;

                            g.setColor(caseColor);//On définit la couleur de dessin à celle de la currentCase
                            g.fillRect(x, y, tailleCase, tailleCase);//on dessine un rectangle plein pour représenter la case.
                            if (currentCase instanceof Hall && ((Hall) currentCase).getkeys()) {
                                g.drawImage(imageKey, x, y, tailleCase, tailleCase, this);//on dessine la cles dans le cas ou elle se trouve dans un Hall
                            }
                            if(currentCase instanceof Mur){

                                g.drawImage(imageBricks, x, y, tailleCase, tailleCase, this);//on dessine un Mur
                            }

                        }

                    }

                }

                int joueurX = (center_X - (center_X - rayon)) * tailleCase + tailleCase / 4;// position du joueur sur la carte en fonction de sa position et du rayon de vision, ainsi que de la taille des cases.(lig)
                int joueurY = (center_Y - (center_Y - rayon)) * tailleCase + tailleCase / 4;// position du joueur sur la carte en fonction de sa position et du rayon de vision, ainsi que de la taille des cases.(col)
                int diametre = tailleCase / 2;
                if (choixSoldat) {//si le joueur choisi est un soldat.
                    //on dessine le soldat selon les coordonnes du  joueurs
                    if (imageSoldat != null) {
                        g.drawImage(imageSoldat, joueurX - tailleCase / 3, joueurY - tailleCase / 3, tailleCase, tailleCase, this);
                    }
                    //dans le cas ou le joueur n'est pas un soldat alors il sera par default represente par un cercle
                } else {
                    g.setColor(joueur.getCouleur());
                    g.fillOval(joueurX, joueurY, diametre, diametre);
                }
            }
        }

    public void paintjoueur() {
        //au choix on a trois couleur pour le cercle ou l'option soldat
        //on utilise un switch pour optimiser notre code
        String[] couleurs = {"Rouge", "Bleu", "Jaune","Soldat"};
        String choix = (String) JOptionPane.showInputDialog(null, "Choisissez une couleur :", "Choix de la couleur", JOptionPane.PLAIN_MESSAGE, null, couleurs, couleurs[0]);
        if (choix != null) {
            switch (choix) {
                case "Rouge":
                    this.joueur.setColor(Color.RED);
                    break;
                case "Bleu":
                    this.joueur.setColor(Color.BLUE);
                    break;
                case "Jaune":
                    this.joueur.setColor(Color.YELLOW);
                    break;
                case "Soldat":
                    this.choixSoldat=true;
                    break;

            }
        }
    }

    public static String choixMap() {
        //deux maps sont proposées
        //on utilise(JOptionPane.showInputDialog) afin d'implementer notre dialogue
        String[] couleurs = {"manoir1", "manoir2"};
        String choixf = (String) JOptionPane.showInputDialog(null, "Choisissez une map :", "Choix de la map", JOptionPane.PLAIN_MESSAGE, null, couleurs, couleurs[0]);
        String choix="";
        if (choixf != null) {
            switch (choixf) {
                case "manoir1":
                     choix= "manoir.txt";
                    break;
                case "manoir2":
                     choix="manoir2.txt";
                    break;
                default:
                     choix= "manoir.txt";
                    break;
            }
        }
        return choix;
    }
    public  Color getColor(Case c){
        if (c instanceof Hall) {
            return new Color(255, 0, 0, ((Hall) c).getChaleur() * 25);//la couleur du Hall depend de l'intensite de la chaleur
        }
        else if (c instanceof Porte) {

            return Color.GREEN;
        }
        else if ( c instanceof Sortie) {
            return Color.BLUE;
        }
        else if (c instanceof Mur) {
            return Color.BLACK;
        }
        else {
            return null;
        }
    }

    public void keyTyped(KeyEvent event){
    }
    public void keyReleased(KeyEvent event) {
    }
    public void keyPressed(KeyEvent event) {
        //on implemente la fontion qui nous peremt d'utiliser notre clavier
        //les KeyEvent.VK_"notre direction" a ete preferer au case 37,36... pour une meilleur lecture du code

        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_LEFT:
                terrain.mouvement(Direction.ouest);
                break;
            case KeyEvent.VK_UP:
                terrain.mouvement(Direction.nord);
                break;
            case KeyEvent.VK_RIGHT:
                terrain.mouvement(Direction.est);
                break;
            case KeyEvent.VK_DOWN:
                terrain.mouvement(Direction.sud);
                break;
        }

        repaint();
    }
    public static void main(String[] args) {
        Furfeux jeu = new Furfeux(choixMap());
        FenetreJeu graphic = new FenetreJeu(jeu.terrain);
        graphic.showMainMenu(jeu);
    }
}

