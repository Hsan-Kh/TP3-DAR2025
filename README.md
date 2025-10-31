TP3-DAR2025 – Serveur Multi-threads : Sockets en mode connecté TCP

🧾 Informations sur le projet

Auteur : Hsan Khecharem

Filière : Licence en Sciences de l'Informatique

Spécialité : Génie Logiciel et Systèmes d'Information

Faculté : Faculté des Sciences de Sfax

Projet : Client/Serveur – Gestion simultanée de plusieurs connexions via Threads et service de calculatrice

🎯 Objectif du TP

Ce TP a pour but de concevoir un serveur multi-thread en Java capable de gérer plusieurs clients connectés simultanément à l'aide de sockets TCP.
L'activité met en pratique les notions de concurrence, de communication réseau, de gestion des threads et de synchronisation dans le cadre d'applications réparties.
L'objectif principal est d'étendre le modèle Client/Serveur développé dans les TP précédents pour :

Permettre à plusieurs clients d'échanger des messages en parallèle avec un même serveur (Activité 3-1)
Implémenter un service de calculatrice avec échange d'objets sérialisés et gestion d'accès concurrent (Activité 3-2)



💻 Environnement de développement

Langage : Java

JDK : 1.8 

IDE : IntelliJ IDEA 

Outil réseau (test) : Telnet (Activité 3-1 uniquement)

🧠 Description des activités

Activité 3-1 : Serveur Multi-thread de base

Étape 1 – Préparation du projet

Création du projet TP3 et du dossier de travail personnel
Mise en place des classes de base pour le serveur et le client à partir du squelette du TP2

Étape 2 – Serveur Multi-threads

Développement de la classe MultiThreadServer :

Le serveur écoute sur le port 1234
Chaque nouvelle connexion client est acceptée via ServerSocket.accept()
Un nouveau thread (ClientProcess) est créé pour gérer chaque client de manière indépendante
Le serveur affiche l'adresse IP du client et son numéro d'ordre de connexion

La classe ClientProcess :

Hérite de Thread
Gère la communication avec un client donné
Envoie un message de bienvenue et traite les messages reçus
Ferme proprement la connexion lorsque le client envoie quit

Étape 3 – Tests en réseau

Les tests ont été effectués :
En local (localhost) avec plusieurs instances de client Java et avec Telnet
En réseau local (LAN) entre plusieurs machines :

La machine serveur a été configurée en réseau privé
Le pare-feu Windows a été ajusté pour autoriser les connexions entrantes sur le port 1234
Les clients ont utilisé l'adresse IPv4 du serveur (ex. 192.168.1.10) via InetAddress et InetSocketAddress
Les connexions multiples ont été correctement gérées : chaque client a reçu son numéro d'ordre, et les échanges étaient indépendants


Activité 3-2 : Serveur Calculatrice Multi-thread avec synchronisation

Étape 1 – Préparation de l'espace de travail

Extension du serveur multi-thread de l'Activité 3-1
Intégration du service de calculatrice développé dans le TP2 Activité 2-2

Étape 2 – Création du serveur multi-thread pour le service de calculatrice

Développement de la classe MultiThreadCalculatorServer :

Le serveur écoute sur le port 6000
Accepte plusieurs connexions clientes simultanées
Crée un thread dédié (CalculatorClientHandler) pour chaque client
Affiche l'adresse IP locale et le port au démarrage
Gère un compteur global des opérations traitées

La classe CalculatorClientProcess :

Hérite de Thread
Gère la communication avec un client spécifique via objets sérialisés
Utilise ObjectInputStream et ObjectOutputStream
Traite les 4 opérations arithmétiques : +, -, *, /
Gère les erreurs : division par zéro, opérateur invalide
Permet plusieurs opérations par session client
Incrémente le compteur global de manière synchronisée

La classe Operation :

Implémente Serializable pour la transmission via sockets
Encapsule les deux opérandes (op1, op2) et l'opérateur
Getters pour accéder aux attributs

La classe CalculatorClient :

Se connecte au serveur via socket sur le port 6000
Interface interactive pour saisir des opérations
Crée des objets Operation et les envoie au serveur
Reçoit et affiche les résultats ou messages d'erreur
Permet de faire plusieurs opérations dans la même session

Étape 3 – Gestion d'un accès concurrent

Compteur global synchronisé :

Variable partagée totalOperations indiquant le nombre total d'opérations traitées
Ressource critique partagée entre tous les threads CalculatorClientHandler
Méthode synchronized incrementOperationCount() pour éviter les conflits d'accès concurrent
À chaque opération calculée :

Le compteur global est incrémenté
Sa nouvelle valeur est affichée dans la console du serveur



Tests de synchronisation :

Tests avec 5 clients en parallèle effectuant plusieurs opérations
Vérification de la cohérence du compteur (aucune perte d'incrémentation)
Tests sur plusieurs machines du même réseau

Étape 4 – Tests en réseau

Les tests ont été effectués :
En local (localhost) avec plusieurs clients Java simultanés
En réseau local (LAN) entre plusieurs machines :

Serveur sur une machine (IP notée)
Clients sur d'autres machines du réseau
Communication bidirectionnelle par objets sérialisés réussie
Compteur global synchronisé et cohérent sur toutes les machines


🛠️ Fonctionnalités clés

Activité 3-1

✅ Serveur multi-threads gérant plusieurs clients simultanément
✅ Communication bidirectionnelle basée sur TCP
✅ Identification et numérotation automatique des clients connectés
✅ Messages de bienvenue et d'écho personnalisés pour chaque client
✅ Gestion correcte de la fermeture des connexions et des flux
✅ Compatibilité avec les clients Telnet et Java

Activité 3-2

✅ Serveur multi-threads pour service de calculatrice
✅ Communication par objets sérialisés (Operation)
✅ Traitement des 4 opérations arithmétiques : +, -, *, /
✅ Compteur global synchronisé des opérations
✅ Gestion des erreurs : division par zéro, opérateur invalide
✅ Interface client interactive permettant plusieurs opérations
✅ Synchronisation correcte pour éviter les race conditions
✅ Tests réussis en réseau local avec plusieurs machines

📈 Résultats attendus

Activité 3-1

Le serveur affiche la liste des clients connectés (adresse + numéro)
Chaque client reçoit un message personnalisé à sa connexion
Les échanges s'effectuent sans blocage, même avec plusieurs clients connectés simultanément
Les connexions et déconnexions sont correctement gérées

Activité 3-2

Le serveur affiche son adresse IP et le port 6000 au démarrage
Chaque client connecté est identifié avec son numéro et son adresse
Les opérations reçues et les résultats calculés sont affichés côté serveur
Le compteur global est affiché après chaque opération et reste cohérent
Les 4 opérations arithmétiques fonctionnent correctement
Les erreurs (division par zéro, opérateur invalide) sont gérées avec des messages appropriés
Le client peut effectuer plusieurs opérations dans la même session
Plusieurs clients peuvent travailler simultanément sans conflit
Le compteur global ne perd aucune incrémentation (pas de race condition)
