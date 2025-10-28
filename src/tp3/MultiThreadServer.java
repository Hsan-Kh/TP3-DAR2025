package tp3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class MultiThreadServer {
    public static void main(String[] args) {
        int port = 1234;
        int clientConn = 0;

        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println("SERVEUR MULTI-THREAD :");
            System.out.println("Adresse IP locale du serveur : " + localAddress.getHostAddress());
            try (ServerSocket server = new ServerSocket(port)) {
                System.out.println("Serveur démarré sur le port " + port);
                System.out.println("En attente de connexions...\n");

                while (true) {
                    Socket client = server.accept();
                    clientConn++;
                    int clientNumber = clientConn;
                    System.out.printf("➡ Client n°%d connecté depuis %s%n", clientNumber, client.getRemoteSocketAddress());
                    ClientProcess clientp = new ClientProcess(client, clientNumber);
                    clientp.start();
                }
            }

        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }
}