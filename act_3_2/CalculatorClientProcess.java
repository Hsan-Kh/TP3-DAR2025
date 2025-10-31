package tp3.act_3_2;

import java.io.*;
import java.net.Socket;

/**
 * Thread gérant la communication avec un client spécifique
 * Chaque client a son propre thread pour un traitement parallèle
 */
public class CalculatorClientProcess extends Thread {
    private Socket clientSocket;
    private int clientNumber;

    public CalculatorClientProcess(Socket socket, int clientNumber) {
        this.clientSocket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        System.out.println("[Client " + clientNumber + "] Thread démarré");

        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            while (true) {
                try {
                    Object receivedObject = in.readObject();
                    if (receivedObject instanceof String && "QUIT".equals(((String) receivedObject).toUpperCase())) {
                        System.out.println("[Client " + clientNumber + "] Demande de déconnexion");
                        out.writeObject("Au revoir !");
                        break;
                    }

                    if (receivedObject instanceof Operation) {
                        Operation operation = (Operation) receivedObject;

                        System.out.println("[Client " + clientNumber + "] Opération reçue : " + operation.toString());
                        Object resultat = calculateOperation(operation);
                        out.writeObject(resultat);
                        System.out.println("[Client " + clientNumber + "] Résultat envoyé : " + resultat);

                        MultiThreadCalculatorServer.incrementOperationCount();
                    } else {
                        out.writeObject("Erreur : format d'opération invalide");
                    }

                } catch (EOFException e) {
                    System.out.println("[Client " + clientNumber + "] Connexion fermée par le client");
                    break;
                } catch (ClassNotFoundException e) {
                    System.err.println("[Client " + clientNumber + "] Erreur de classe : " + e.getMessage());
                    out.writeObject("Erreur serveur : classe non trouvée");
                }
            }

        } catch (IOException e) {
            System.err.println("[Client " + clientNumber + "] Erreur I/O : " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
                System.out.println("✓ [Client " + clientNumber + "] Déconnecté\n");
            } catch (IOException e) {
                System.err.println("[Client " + clientNumber + "] Erreur fermeture : " + e.getMessage());
            }
        }
    }

    private Object calculateOperation(Operation op) {
        double op1 = op.getOp1();
        double op2 = op.getOp2();
        char operateur = op.getOperateur();

        if (!(operateur == '+' || operateur == '-' || operateur == '*' || operateur == '/')) {
            return "Erreur : Opérateur non reconnu !";
        }

        // Effectuer le calcul selon l'opérateur
        switch (operateur) {
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
            case '*':
                return op1 * op2;
            case '/':
                if (op2 == 0) {
                    return "Erreur : Division par zéro impossible !";
                }
                return op1 / op2;
            default:
                return "Erreur : Opérateur non reconnu !";
        }
    }
}