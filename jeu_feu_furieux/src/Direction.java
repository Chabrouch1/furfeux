import java.util.Random;

// Enumération représentant les différentes directions possibles
public enum Direction {
    nord, // Direction vers le nord
    sud,  // Direction vers le sud
    est,  // Direction vers l'est
    ouest; // Direction vers l'ouest

    // Méthode statique pour obtenir une direction aléatoire
    public static Direction random() {
        Random rnd = new Random();
        int r = rnd.nextInt(4); // Génère un nombre aléatoire entre 0 et 3 inclus
        switch (r) {
            case 0:  return Direction.nord; // Retourne la direction nord si r est 0
            case 1:  return Direction.sud; // Retourne la direction sud si r est 1
            case 2:  return Direction.est; // Retourne la direction est si r est 2
            default: return Direction.ouest; // Retourne la direction ouest par défaut (si r est 3 ou autre)
        }
    }
}
