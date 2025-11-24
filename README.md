#  TP3-DAR2025 – Serveur Multi-threads : Sockets TCP

> Implémentation d'un serveur multi-threads Java avec gestion de connexions concurrentes et service de calculatrice distribué

---

##  Informations du projet

| Élément | Détail |
|---------|--------|
| **Auteur** | Hsan Khecharem |
| **Filière** | Licence en Sciences de l'Informatique |
| **Spécialité** | Génie Logiciel et Systèmes d'Information |
| **Institution** | Faculté des Sciences de Sfax |
| **Thématique** | Architecture Client/Serveur avec gestion concurrente |

---

##  Objectifs pédagogiques

Ce projet vise à maîtriser les concepts avancés de programmation réseau et concurrente en Java :

- **Programmation multi-threads** : Gestion simultanée de plusieurs clients
- **Sockets TCP** : Communication fiable en mode connecté
- **Synchronisation** : Protection des ressources partagées contre les accès concurrents
- **Sérialisation Java** : Échange d'objets entre processus distants
- **Architecture répartie** : Conception d'applications distribuées robustes

### Extension du modèle Client/Serveur

-  Support de connexions multiples en parallèle
-  Service de calculatrice avec objets sérialisés
-  Gestion d'accès concurrent avec synchronisation

---

##  Environnement technique

```
Langage       : Java
Version JDK   : 1.8+
IDE           : IntelliJ IDEA
Protocole     : TCP/IP (Sockets)
Test réseau   : Telnet (Activité 3-1)
```

---

##  Structure du projet

### Activité 3-1 : Serveur Multi-threads de base

**Composants principaux :**

**`MultiThreadServer`**
- Écoute sur le port `1234`
- Accepte les connexions via `ServerSocket.accept()`
- Crée un thread dédié par client
- Affiche l'adresse IP et le numéro d'ordre de chaque connexion

**`ClientProcess extends Thread`**
- Gère la communication avec un client spécifique
- Envoie un message de bienvenue personnalisé
- Traite les messages en mode écho
- Ferme proprement la connexion sur commande `quit`

---

### Activité 3-2 : Calculatrice distribuée avec synchronisation

**Architecture du service :**

**`MultiThreadCalculatorServer`**
- Port d'écoute : `6000`
- Gestion de connexions multiples simultanées
- Création d'un `CalculatorClientHandler` par client
- Compteur global synchronisé des opérations

**`CalculatorClientProcess extends Thread`**
- Communication par objets sérialisés (`ObjectInputStream`/`ObjectOutputStream`)
- Opérations supportées : `+`, `-`, `*`, `/`
- Gestion des erreurs (division par zéro, opérateur invalide)
- Sessions multi-opérations par client
- Incrémentation synchronisée du compteur global

**`Operation implements Serializable`**
```java
class Operation {
    private double op1;
    private double op2;
    private String operator;
    // Getters...
}
```

**`CalculatorClient`**
- Interface interactive en console
- Création et envoi d'objets `Operation`
- Réception et affichage des résultats
- Support de sessions multi-requêtes

---

##  Gestion de la synchronisation

### Problématique
Plusieurs threads accèdent simultanément à une ressource partagée (`totalOperations`), créant un risque de **race condition**.

### Solution implémentée
```java
private static int totalOperations = 0;

private synchronized void incrementOperationCount() {
    totalOperations++;
    System.out.println("Total operations: " + totalOperations);
}
```

### Tests de validation
-  5 clients simultanés effectuant plusieurs opérations
-  Cohérence parfaite du compteur (aucune perte)
-  Tests en réseau local multi-machines

---

##  Tests et validation

### Configuration réseau locale (LAN)

**Serveur**
- Configuration en réseau privé
- Pare-feu Windows ajusté (port `1234` et `6000` autorisés)
- Adresse IPv4 exemple : `192.168.1.10`

**Clients**
- Connexion via `InetAddress` et `InetSocketAddress`
- Tests avec clients Java et Telnet (Activité 3-1)
- Multiples machines connectées simultanément

### Scénarios de test

| Test | Description | Résultat |
|------|-------------|----------|
| Connexions multiples | 5+ clients simultanés |  Succès |
| Indépendance des sessions | Échanges parallèles |  Succès |
| Synchronisation | Compteur global cohérent |  Succès |
| Gestion d'erreurs | Division par zéro, opérateur invalide |  Succès |
| Fermeture propre | Commande `quit` |  Succès |

---

##  Fonctionnalités principales

### Activité 3-1 : Serveur de messagerie

- [x] Gestion multi-clients concurrente
- [x] Communication bidirectionnelle TCP
- [x] Identification automatique des clients
- [x] Messages personnalisés (bienvenue, écho)
- [x] Fermeture propre des connexions
- [x] Compatibilité Telnet et Java

### Activité 3-2 : Service de calculatrice

- [x] Architecture multi-threads
- [x] Sérialisation d'objets Java
- [x] 4 opérations arithmétiques (`+`, `-`, `*`, `/`)
- [x] Compteur synchronisé global
- [x] Gestion robuste des erreurs
- [x] Sessions multi-opérations
- [x] Protection contre les race conditions
- [x] Tests réussis en environnement distribué

---

##  Utilisation

### Démarrage du serveur (Activité 3-1)
```bash
java MultiThreadServer
# Serveur en écoute sur le port 1234
```

### Connexion d'un client
```bash
# Avec Telnet
telnet localhost 1234

# Avec le client Java
java Client
```

### Démarrage du serveur calculatrice (Activité 3-2)
```bash
java MultiThreadCalculatorServer
# Serveur en écoute sur le port 6000
```

### Utilisation du client calculatrice
```bash
java CalculatorClient
# Suivre les instructions pour saisir les opérations
```

---

##  Résultats attendus

### Activité 3-1
```
[SERVEUR] Serveur démarré sur le port 1234
[SERVEUR] Client #1 connecté : 192.168.1.15
[SERVEUR] Client #2 connecté : 192.168.1.20
[Client #1] Message reçu : Hello
[Client #2] Message reçu : Bonjour
```

### Activité 3-2
```
[SERVEUR] Calculatrice démarrée sur 192.168.1.10:6000
[SERVEUR] Client #1 connecté : 192.168.1.15
[SERVEUR] Opération reçue : 10.0 + 5.0 = 15.0
[SERVEUR] Total opérations : 1
[SERVEUR] Client #2 connecté : 192.168.1.20
[SERVEUR] Opération reçue : 20.0 / 4.0 = 5.0
[SERVEUR] Total opérations : 2
```

---

##  Concepts clés illustrés

- **Concurrence** : Exécution simultanée de plusieurs threads
- **Synchronisation** : Méthodes `synchronized` pour l'exclusion mutuelle
- **Sockets TCP** : Communication fiable orientée connexion
- **Sérialisation** : Transmission d'objets Java sur le réseau
- **Gestion des ressources** : Fermeture propre des flux et sockets
- **Architecture distribuée** : Communication inter-processus

---

##  Compétences développées

- Programmation réseau en Java
- Gestion de la concurrence et synchronisation
- Debugging d'applications distribuées
- Configuration réseau et pare-feu
- Tests d'intégration multi-machines
- Conception d'architectures Client/Serveur robustes

---

##  Ressources complémentaires

- [Documentation Java Sockets](https://docs.oracle.com/javase/tutorial/networking/sockets/)
- [Java Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- [Serialization Guide](https://docs.oracle.com/javase/tutorial/jndi/objects/serial.html)

---

##  Licence

Projet académique réalisé dans le cadre du cours DAR2025 - Faculté des Sciences de Sfax

---

**Dernière mise à jour** : Novembre 2025
