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

#Affichage application
Usage: Main [COMMAND]
New picocli command
Commands:
  new    New .... ???
  clean  Clean .... ???
  build  Build .... ???
  serve  Serve .... ???
````

