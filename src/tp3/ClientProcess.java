package tp3;

import java.io.*;
import java.net.Socket;


public class ClientProcess extends Thread {
    private Socket socket;
    private int clientNumber;

    public ClientProcess(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            out.println("Bienvenue, vous êtes le client n°" + clientNumber);
            out.println("Votre adresse : " + socket.getRemoteSocketAddress());
            out.println("Tapez 'quit' pour quitter.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.printf("[Client %d] %s%n", clientNumber, message);

                if ("quit".equalsIgnoreCase(message.trim())) {
                    out.println("Au revoir !");
                    break;
                }

                out.println("Serveur a reçu : " + message);
            }

            System.out.printf("[Client %d] Déconnexion%n", clientNumber);

        } catch (IOException e) {
            System.err.printf("[Client %d] Erreur : %s%n", clientNumber, e.getMessage());
        } finally {
            try {
                if (socket != null && !socket.isClosed()) socket.close();
                System.out.printf("✓ Connexion fermée pour client n°%d%n", clientNumber);
            } catch (IOException ignored) {}
        }
    }
}
