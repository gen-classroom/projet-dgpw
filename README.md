# Labo 2, Projet - DGPW 

> Groupe : DGPW
>
> Auteurs : Dössegger Gwendoline, Gaudin Robin, Plancherel Noémie, Wojciechowski Cassandre
>
> Date : 05.03.2021 

**Etat du projet : En cours**

------
### Générer l'executable 
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
  new    Création d'une nouvelle page
  clean  Nettoie le répertoire build du site statique.
  build  Compile un site statique
  serve  Serve .... ???
  init   Initialise un répertoire de site statique
````
------

### Affichage Version
Pour afficher la version, utiliser le paramètre `-v` ou `--version` :

````bash
Usage: Main [-v]
Programme DGPW - Website Generator
  -v, --version   Display the version and exit
  
> dgpw -v

# Affichage
DGPW version X.X.X-SNAPSHOT
````

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
Usage: Main build [-d=<siteDir>]
Compile un site statique
  -d=<siteDir>    Répertoire du site statique
  
> dgpw build -d test-init

#Affichage
Compilation terminée !
```
> /!\ Si le répertoire du site Web contient deja un répertoire build, celui-ci sera supprimé et recréer d'après les fichiers présents dans le répertoire.g

Pour générer les pages HTML du site, le répertoire doit `OBLIGATOIREMENT` être dans le répertoire `www`. Si ce n'est pas le cas, une erreur sera retournée.

### Nettoyer le site (répertoire build)
Pour clean le site statique (supprimer le répertoire build), utiliser la commande `clean`:

```bash
Usage: Main clean [-d=<dir>]
Nettoie le répertoire build du site statique.
  -d=<dir>    Chemin du répertoire du site statique à nettoyer
  
> dgpw clean -d test_init

# Affiche 
le répertoire build a été supprimé.
```

Pour que le site soit nettoyer, son répertoire doit `OBLIGATOIREMENT` être dans le répertoire `www`. Si ce n'est pas le cas, une erreur sera retournée.
