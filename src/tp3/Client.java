package tp3;

import java.io.*;
import java.net.*;


public class Client {
    public static void main(String[] args) {
        String host = "192.168.x.x"; // Remplacez par 192.168.x.x l'adresse IP du serveur
        int port = 1234;
        int timeout = 5000;

        try (
                Socket socket = new Socket();
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))
        ) {
            socket.connect(new InetSocketAddress(InetAddress.getByName(host), port), timeout);
            System.out.println("✓ Connecté au serveur " + host + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Thread pour recevoir les messages du serveur
            Thread receiver = new Thread(() -> {
                try {
                    String msg;
                    while ((msg = in.readLine()) != null) {
                        System.out.println("[Serveur] " + msg);
                    }
                } catch (IOException ignored) {}
            });
            receiver.setDaemon(true);
            receiver.start();

            String input;
            while ((input = userInput.readLine()) != null) {
                if (input.trim().isEmpty()) continue;
                out.println(input);
                if ("quit".equalsIgnoreCase(input.trim())) break;
            }

        } catch (UnknownHostException e) {
            System.err.println("Erreur : hôte inconnu '" + host + "'");
        } catch (SocketTimeoutException e) {
            System.err.println("Erreur : Timeout de connexion dépassé");
        } catch (ConnectException e) {
            System.err.println("Erreur : impossible de se connecter au serveur");
        } catch (IOException e) {
            System.err.println("Erreur I/O : " + e.getMessage());
        }

        System.out.println("✓ Client fermé");
    }
}
