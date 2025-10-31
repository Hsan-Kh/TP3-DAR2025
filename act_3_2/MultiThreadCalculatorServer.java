package tp3.act_3_2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Serveur multi-thread pour le service de calculatrice
 * Ce serveur accepte plusieurs connexions simultanées et traite les opérations de chaque client en parallèle
 */
public class MultiThreadCalculatorServer {
    private static int totalOperations = 0;

    public static void main(String[] args) {
        int port = 6000;
        int clientCount = 0;

        try {
            InetAddress localAddress = InetAddress.getLocalHost();
            System.out.println("--> SERVEUR CALCULATRICE MULTI-THREAD : ");
            System.out.println("Adresse IP : " + localAddress.getHostAddress());
            System.out.println("Port       : " + port);

            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("+ Serveur démarré et en attente de connexions...\n");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    clientCount++;

                    System.out.println(" Client n°" + clientCount + " connecté depuis " + clientSocket.getRemoteSocketAddress());

                    CalculatorClientProcess clientp = new CalculatorClientProcess(
                            clientSocket,
                            clientCount
                    );
                    clientp.start();
                }
            }
        } catch (IOException e) {
            System.err.println("- Erreur du serveur : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * La synchronisation évite les conflits d'accès concurrent
     */
    public static synchronized void incrementOperationCount() {
        totalOperations++;
        System.out.println(" COMPTEUR GLOBAL : " + totalOperations + " opération(s) ");
    }

    public static synchronized int getTotalOperations() {
        return totalOperations;
    }
}