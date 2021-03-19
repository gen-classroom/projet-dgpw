# Labo 2, Projet - DGPW 

> Groupe : DGPW
>
> Auteurs : Dössegger Gwendoline, Gaudin Robin, Plancherel Noémie, Wojciechowski Cassandre
>
> Date : 05.03.2021 

**Etat du projet : En cours**

------

Pour générer et unzip le projet :

`````bash
mvn clean install && rm -fr dgpw && unzip target/dgpw.zip
`````

Ajouter le répertoire `bin` à votre `Path`:

````
export PATH=$PATH:`pwd`/dgpw/bin
````

Executer `dgpw`:

````bash
> dgpw

# Affichage
Usage: Main [-v] [COMMAND]
Programme DGPW - Website Generator
  -v, --version   Display the version and exit
Commands:
  new    New .... ???
  clean  Nettoie le répertoire build du site statique.
  build  Build .... ???
  serve  Serve .... ???
  init   Initialise un répertoire de site statique
````
------

Pour afficher la version:

````bash
> dgpw -v

# Affichage
DGPW version 0.1.0-SNAPSHOT
````

Pour initialiser le site statique :

````bash
> dgpw init test_unit

# Affichage
Le répertoire a été créé 
````

Afin d'avoir un projet organisé, le site statique est automatiquement créé dans le répertoire `www/`. Lorsque l'utilisateur souhaite créer un dossier `/mon/site` par exemple, le répertoire se présentera ansi: `www/mon/site`.

Pour clean le site statique (supprimer le répertoire build) :

```bash
Exemple : dgpw clean <cheminDuSite>
dgpw clean www/test_init

# Affiche 
le répertoire build a été supprimé
```

Le chemin complet du site statique doit être renseigné pour supprimer le répertoire build. Sans celui-ci, l'erreur Impossible d'accéder au répertoire <nom>. Celui-ci est inexistant.