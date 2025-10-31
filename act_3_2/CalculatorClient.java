package tp3.act_3_2;


import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Client pour le service de calculatrice multi-thread
 * Permet d'envoyer plusieurs opérations au serveur
 */
public class CalculatorClient {
    public static void main(String[] args) {
        String host = "localhost"; // Changez en adresse IP du serveur pour test réseau
        int port = 6000;

        System.out.println("--> CLIENT CALCULATRICE :");

        try (Socket socket = new Socket(host, port)) {
            System.out.println("+ Connecté au serveur " + host + ":" + port);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);

            boolean continuer = true;

            while (continuer) {
                System.out.println("\n--- Nouvelle opération ---");

                System.out.print("Premier nombre (ou 'q' pour quitter) : ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
                    out.writeObject("QUIT");
                    Object response = in.readObject();
                    System.out.println("Serveur : " + response);
                    continuer = false;
                    break;
                }

                double a;
                try {
                    a = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    System.out.println("- Erreur : nombre invalide !");
                    continue;
                }

                System.out.print("Opérateur (+, -, *, /) : ");
                String opStr = scanner.nextLine().trim();

                if (opStr.length() != 1 || "+-*/".indexOf(opStr.charAt(0)) == -1) {
                    System.out.println("- Erreur : opérateur invalide !");
                    continue;
                }
                char operateur = opStr.charAt(0);

                System.out.print("Deuxième nombre : ");
                double b;
                try {
                    b = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("- Erreur : nombre invalide !");
                    continue;
                }

                Operation operation = new Operation(a, b, operateur);
                out.writeObject(operation);
                System.out.println("+ Opération envoyée : " + operation.toString());

                Object resultat = in.readObject();
                System.out.println("Résultat : " + resultat);

                System.out.print("\nAutre opération ? (oui/non) : ");
                String reponse = scanner.nextLine().trim();
                if (!reponse.equalsIgnoreCase("o") && !reponse.equalsIgnoreCase("oui")) {
                    out.writeObject("QUIT");
                    Object response = in.readObject();
                    System.out.println("Serveur : " + response);
                    continuer = false;
                }
            }

            scanner.close();
            System.out.println("\n+ Déconnexion du serveur");

        } catch (UnknownHostException e) {
            System.err.println("- Erreur : hôte inconnu '" + host + "'");
        } catch (ConnectException e) {
            System.err.println("- Erreur : impossible de se connecter au serveur");
            System.err.println("   Vérifiez que le serveur est démarré sur " + host + ":" + port);
        } catch (IOException e) {
            System.err.println("- Erreur I/O : " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("- Erreur : classe non trouvée - " + e.getMessage());
        }
    }
}
