# Labo 2, Projet - DGPW 

> Groupe : DGPW
>
> Auteurs : Dössegger Gwendoline, Gaudin Robin, Plancherel Noémie, Wojciechowski Cassandre
>
> Date : 05.06.2021 

**Etat du projet : Terminé**

------

### Générer l'executable 

Pour générer et décompresser le projet :

`````bash
mvn clean install && rm -fr dgpw && unzip target/dgpw.zip
`````

Ajouter le répertoire `bin` au `Path`:

````
export PATH=$PATH:`pwd`/dgpw/bin
````

Exécuter `dgpw`:

````bash
> dgpw

# Affichage
Usage: Main [-v] [COMMAND]
Programme DGPW - Website Generator
  -v, --version   Display the version and exit
Commands:
  new      Création d'une nouvelle page
  clean    Nettoie le répertoire build du site statique.
  build    Compile un site statique
  serve    Servir un site statique
  init     Initialise un répertoire de site statique
  easyuse  Commande qui facilite l'utilisation du site
````

------

### Affichage de la version

Pour afficher la version, utiliser le paramètre `-v` ou `--version` :

````bash
Usage: Main [-v]
Programme DGPW - Website Generator
  -v, --version   Display the version and exit
  
> dgpw -v

# Affichage
DGPW version X.X.X-SNAPSHOT
````

### EasyUse

Nous avons ajouté une commande qui réunit toutes les commandes afin de faciliter l'utilisation de l'application; les deux commandes appellent la commande `build` pour la compilation du site statique.

````bash
Usage: Main easyuse [-init] [-new] [--watch] [-d=<dirStatic>] [-f=<filePage>]
Commande qui facilite l'utilisation du site
  -d=<dirStatic>    Répertoire du site statique
  -f=<filePage>     Nom de la page
      -init         Création du site statique
      -new          Création d'une nouvelle page
      --watch       Contrôle continu
````

Afin d'initialiser le site et le compiler:

````bash
> dgpw easyuse -init -d test_unit

# Affichage
Le répertoire a été créé
Le fichier de config a été créé : /home/noemie/Documents/GEN/projet-dgpw/projet-dgpw/www/test_unit/config.yaml
Le fichier d'index a été créé : /home/noemie/Documents/GEN/projet-dgpw/projet-dgpw/www/test_unit/index.md
Conversion index.md: Build - Compilation terminée !
````

Afin de créer une page et compiler le site statique:

````bash
> dgpw easyuse -new -d test_unit -f new

# Affichage
La page voulue, new.md, a été créée
www/test_unit
Le répertoire build du site test_unit a été supprimé.
Conversion index.md: Conversion new.md: Build - Compilation terminée !
````

> /!\ Le nom de la page ne doit pas avoir d'extension. 

Pour activer la génération du site à la volée, nous avons ajouté une option pour le faire. Elle va tourner en arrière plan jusqu'à interruption du processus:

````bash
> dgpw easyuse --watch -d mon/site
````

Ainsi pour ajouter une nouvelle page markdown, nous pouvons utiliser la commande `dgpw easyuse -new` ou la commande `dgpw new`. Il est possible de modifier un fichier markdown, cependant il ne faut pas oublier d'effectuer `ctrl+s` pour sauver les modifications. Finalement, on peut aussi supprimer un fichier markdown.

> /!\ Il est seulement possible d'utiliser cette commande avec des fichiers **markdown**

Afin d'interrompre le processus, il faut effectuer la commande `ctrl+c` dans le terminal.

### Servir le site

Afin d'initialiser le serveur pour notre site statique, il est nécessaire de lancer une commande:

````bash
Usage: Main serve [-d=<dirStatic>]
Servir un site statique
  -d=<dirStatic>    Répertoire du site statique

> dgpw serve -d test_init

# Affichage
Vous pouvez vous connectez sur votre site statique : http://localhost:8080/index.html
````

Une fois la commande lancée, le site statique se visite à l'adresse `http://localhost:8080/index.html`.

------

Si vous préférez effectuer les commandes une à une, il est également possible de le faire comme indiqué ci-après:

### Initialisation du site

Pour initialiser le site statique, utiliser la commande `init` :

````bash
Usage: Main init [-d=<file>]
Initialise un répertoire de site statique
  -d=<file>    Répertoire du site statique

> dgpw init -d test_unit

# Affichage
Le répertoire a été créé 
````

Lors de l'initialisation du site statique, celui-ci est automatiquement créé dans le répertoire `www`. Lorsque l'utilisateur souhaite créer un dossier `/mon/site` par exemple, le répertoire se présentera ansi: `www/mon/site`.

### Ajouter une nouvelle page

Pour créer une nouvelle page template, utiliser la commande `new`:

````bash
Usage: Main new [-d=<dirStatic>] [-f=<filePage>]
Création d'une nouvelle page
  -d=<dirStatic>    Répertoire du site statique
  -f=<filePage>     Nom de la page
  
> dgpw new -f mapremierepage -d test_init

# Affichage
La page voulue, mapremierepage.md, a été créée
````

>/!\ Le nom de la page ne doit pas avoir d'extension. 

Pour ajouter une nouvelle page au site, le répertoire doit `OBLIGATOIREMENT` être dans le répertoire `www`. Si ce n'est pas le cas, une erreur sera retournée.

### Build le site Web (générer les pages HTML)

Pour générer les pages HTML, utiliser la commande `build`:

```bash
Usage: Main build [--watch] [-d=<siteDir>]
Compile un site statique
  -d=<siteDir>    Répertoire du site statique
      --watch     Contrôle continu
  
> dgpw build -d test_init

#Affichage
Compilation terminée !
```

> /!\ Si le répertoire du site Web contient deja un répertoire build, celui-ci sera supprimé et recréé d'après les fichiers présents dans le répertoire.

Pour générer les pages HTML du site, le répertoire doit `OBLIGATOIREMENT` être dans le répertoire `www`. Si ce n'est pas le cas, une erreur sera retournée.

Nous avons ajouté une option à la commande afin de générer le site statique à la volée. Il est possible de le faire ainsi:

````bash
> dgpw build -d test_init --watch
````

Ainsi pour ajouter une nouvelle page markdown, nous pouvons utiliser la commande `dgpw easyuse -new` ou la commande `dgpw new`. Il est possible de modifier un fichier markdown, cependant il ne faut pas oublier d'effectuer `ctrl+s` pour sauver les modifications. Finalement, on peut supprimer un fichier markdown.

> /!\ Il est seulement possible d'utiliser cette commande avec des fichiers **markdown**

Afin d'arrêter le processus lancé par cette option, il faut effectuer la commande `ctrl+c` dans le terminal.

### Nettoyer le site (répertoire build)

Pour nettoyer le site statique (supprimer le répertoire `build`), utiliser la commande `clean`:

```bash
Usage: Main clean [-d=<dir>]
Nettoie le répertoire build du site statique.
  -d=<dir>    Chemin du répertoire du site statique à nettoyer
  
> dgpw clean -d test_init

# Affiche 
le répertoire build a été supprimé.
```

Pour que le site soit nettoyé, son répertoire doit `OBLIGATOIREMENT` être dans le répertoire `www`. Si ce n'est pas le cas, une erreur sera retournée.
