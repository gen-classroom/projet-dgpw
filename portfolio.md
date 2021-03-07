# GEN - Laboratoire 2 - Portfolio

> Auteurs : Gwendoline Dössegger, Robin Gaudin, Noémie Plancherel, Cassandre Wojciechowski

> Date : 05.03.2021

## Processus choisi

Nous avons choisi de procéder selon un processus "piloté", qui est un processus planifié : nos activités sont planifiées en avance et notre progrès est mesuré grâce aux `issues` et au projet `Kanban` qui trace notre avancement. 

En effet, le but de ce laboratoire est d'explorer la fonctionnalité `issues` offerte par Github, de manipuler un projet Maven, de modifier le fichier `pom.xml` et de prendre en main la dépendance `Picocli`. 

Nos activités sont planifiées en avance, en suivant les informations fournies par la donnée de laboratoire. De plus, les instructions de la donnée ne risquent pas vraiment d'évoluer ou de changer. Nous n'avons donc pas de réel besoin de procéder avec un processus incrémental dans le cadre de ce deuxième laboratoire de GEN. 

Cependant, si nous gardons la base créée lors de ce deuxième laboratoire pour évoluer et ajouter des fonctionnalités à notre projet, nous nous orienterons vers un processus "agile". Cela nous permettra d'adapter l'existant selon les exigences communiquées par les professeurs au fur et à mesure du semestre.

## Mode de collaboration

Concernant le mode de collaboration que nous avons adopté pendant le déroulement de ce laboratoire, nous nous sommes appuyés sur la fonctionnalité `Kanban` proposée par Github pour le management de projet. Nous faisons également appel aux `issues`, que nous pouvons déplacer dans le projet `Kanban` pour indiquer aux membres de l'équipe ce qui est à faire, ce qui est en cours ou ce qui est terminé. 

Les `issues` sont assignées aux différents membres du groupe pour plus de clarté sur les rôles endossés par chaque membre. 

Nous avons établi entre nous des règles pour améliorer la collaboration au sein du groupe. Notamment, chaque `pull request` doit être analysée par un autre membre du groupe puis acceptée si elle convient. Sinon, nous utilisons l'interface Github pour commenter et indiquer ce qui doit être modifié. 

Nous avons établi des conventions de nommage pour que nos `commit` et `pull requests` soient relativement uniformes et compréhensibles. Ces conventions sont rédigées à la suite de ce document.

En cas de problème, nous échangeons de vive voix sur Discord pour venir en aide au membre du groupe qui en a besoin. 

### Convention de nommage

#### Issues

Les issues seront nommées de la sorte:

- [Verbe] + sujet

Par exemple:

- [Créer|Ajouter|Modifier|Supprimer|...]

#### Branches

Les branches seront nommées de la sorte:

- issue[numéro issue]-[description brève de l'action]

#### Commit

Les commit seront nommés de la sorte:

- brève description du commit 

### PR

Lors de la demande de PR, l'utilisateur doit préciser dans son message:

- Closes #[numéro de l'issue]

### Méthodologie

#### Kanban

Lorsqu'une personne veut travailler sur le projet, elle doit s'assigner une issue. IL faut également
déplacer l'issue du Kanban vers "In Progress".

#### Revue de code

A chaque PR, voici le schéma:

- La personne qui fait une PR, attend qu'une autre personne du groupe accepte la PR et la merge. 
  Il est aussi important que la personne qui valide la PR, ajoute des commentaires si nécessaire.

#### Branch par issue

Pour chaque issue, il faut créer une branche spécifique. Chaque issue est résolue dans sa branche respective.