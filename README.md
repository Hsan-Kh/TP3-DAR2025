TP3-DAR2025 – Serveur Multi-threads : Sockets en mode connecté TCP

🧾 Informations sur le projet

Auteur :  Hsan Khecharem

Filière : Licence en Sciences de l’Informatique

Spécialité : Génie Logiciel et Systèmes d’Information

Faculté : Faculté des Sciences de Sfax

Projet : Client/Serveur – Gestion simultanée de plusieurs connexions via Threads

🎯 Objectif du TP

Ce TP a pour but de concevoir un serveur multi-thread en Java capable de gérer plusieurs clients connectés simultanément à l’aide de sockets TCP.
L’activité met en pratique les notions de concurrence, de communication réseau, et de gestion des threads dans le cadre d’applications réparties.

L’objectif principal est d’étendre le modèle Client/Serveur développé dans les TP précédents pour permettre à plusieurs clients d’échanger des messages en parallèle avec un même serveur.

📂 Structure du repository

tp3/

│

├── MultiThreadServer.java     → Classe principale du serveur

├── ClientProcess.java         → Thread dédié à la gestion d’un client

├── Client.java                → Application cliente (connexion locale ou distante)

└── README.md                  → Documentation du TP

💻 Environnement de développement

Langage : Java

JDK : 1.8 

IDE : IntelliJ IDEA 

Outil réseau (test) : Telnet

🧠 Description de l’activité

Étape 1 – Préparation du projet

Création du projet TP3 et du dossier de travail personnel.
Mise en place des classes de base pour le serveur et le client à partir du squelette du TP2.

Étape 2 – Serveur Multi-threads

Développement de la classe MultiThreadServer :

Le serveur écoute sur le port 1234.

Chaque nouvelle connexion client est acceptée via ServerSocket.accept().

Un nouveau thread (ClientProcess) est créé pour gérer chaque client de manière indépendante.

Le serveur affiche l’adresse IP du client et son numéro d’ordre de connexion.

La classe ClientProcess :

Hérite de Thread.

Gère la communication avec un client donné.

Envoie un message de bienvenue et traite les messages reçus.

Ferme proprement la connexion lorsque le client envoie quit.

Étape 3 – Tests en réseau

Les tests ont été effectués :

En local (localhost) avec plusieurs instances de client Java et avec Telnet.

En réseau local (LAN) entre plusieurs machines :

La machine serveur a été configurée en réseau privé.

Le pare-feu Windows a été ajusté pour autoriser les connexions entrantes sur le port 1234.

Les clients ont utilisé l’adresse IPv4 du serveur (ex. 192.168.1.10) via InetAddress et InetSocketAddress.

Les connexions multiples ont été correctement gérées : chaque client a reçu son numéro d’ordre, et les échanges étaient indépendants.

🛠️ Fonctionnalités clés

Serveur multi-threads gérant plusieurs clients simultanément.

Communication bidirectionnelle basée sur TCP.

Identification et numérotation automatique des clients connectés.

Messages de bienvenue et d’écho personnalisés pour chaque client.

Gestion correcte de la fermeture des connexions et des flux.

Compatibilité avec les clients Telnet et Java.

📡 Tests réalisés


Local: Connexion de plusieurs clients sur la même machine (localhost)	✅ Réussi

Telnet:	Test de connexion manuelle au serveur (port 1234)	✅ Réussi

Réseau local (LAN):	Connexion entre deux machines via IP du serveur	✅ Réussi après configuration du pare-feu

Déconnexion client:	Fermeture propre après commande quit	✅ Réussi

📈 Résultats attendus

Le serveur affiche la liste des clients connectés (adresse + numéro).

Chaque client reçoit un message personnalisé à sa connexion.

Les échanges s’effectuent sans blocage, même avec plusieurs clients connectés simultanément.

Les connexions et déconnexions sont correctement gérées.

🧩 Conclusion

Ce TP a permis de comprendre et d’implémenter un serveur multi-thread robuste capable de gérer plusieurs connexions réseau simultanées.
L’activité a illustré les mécanismes de concurrence, la gestion des sockets TCP, la synchronisation des échanges et les paramètres réseau nécessaires à une communication entre machines réelles.
Ce travail constitue une base solide pour le développement d’applications réparties plus avancées, intégrant des traitements parallèles et des échanges d’objets complexes.
