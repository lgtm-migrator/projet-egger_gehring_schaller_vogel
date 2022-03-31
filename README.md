#### Date : 3 mars 2022

#### Membre du groupe : Maëlle Vogel, Mélissa Gehring, Joris Schaller, Magali Egger 


# Introduction
Bienvenu-e sur notre projet de semestre, nous avons pour projet d'enrichire ce document tout au long du semestre. 

# Objectif
L'objectif de ce projet de semestre est de pratiquer le développement logicielle en groupe ainsi que mettre en application les concepts théoriques étudiés en cours tel que Agil et Xtrem programming. 

# Fonctionnalité actuelle:
   - initialiser un site
   - nettoyer un site
   - générer un site
   - afficher la version du programme
# Roadmap :
Voici les prochaines étapes du développement : 
 - Tenir à jour ce read-me
 - Se préparer aux sprints suivants
 
# Licensing
Notre projet est sous [Licence MIT](https://github.com/dil-classroom/projet-egger_gehring_schaller_vogel/blob/main/LICENSE)  
# Instalation et lancement :
 - (prérequis) Avoir pandoc installé et le chemin du binaire dans le PATH
 - Télécharger la release du projet sur github.
 - Faire `unzip statique.zip` 
 - Faire `java -cp "statique/lib/*" Statique `

Vous devriez voir l'aide du programme affiché sur votre écran après la dernier commandetar.

    Usage: statique [COMMAND]
    A brand new static site generator.
    Commands:
    init      Initialise un dossier pour le site statique
    clean     Clean a static site
    build     Build a static site
    serve     Serve a static site
    -version  Prints the version of the program


## Exemple d'utilisation
 Nous supposons désormais que vous avez suivis l'étape Instalation et lancement et que vous êtes dans le dossier statique.
### Commande version

    java -cp "lib/*" Statique -version

Cette commande affiche la version du programme et ne prend pas d'argument.
### Commande init

    java -cp "lib/*" Statique -init mon/site/
Cette commande initialise un site statique dans le dossier `mon/site/`.
l'arborescence du site est la suivante:

    mon/Site/
            |- index.md

**Argument** : le chemin du dossier où sera créé le site statique.

Le chemin doit être valide, mais les dossiers parents ne doivent pas forcément exister au préalable. 
Le dossier final ne doit pas exister au préalable, une erreur est affichée si c'est le cas.

### Commande build:  
Pour build un site avec deux fichiers `index.md` et `maPage.md` dans le dossier `mon/site/`
il suffit de faire :

    java -cp "lib/*" Statique build mon/site/
Et l'arborescence résultante est la suivante :
   
    mon/Site/
            | - index.md
            | - maPage.md
            | - build/
                    | - index.html
                    | - maPage.html


Cette commande crée donc un dossier `build` dans le dossier `mon/site/` et y met la version html de tous les fichiers *.md du dossier `mon/site/`.  
**Argument** : un chemin vers un répertoire avec un site statique initialisé.

### Commande clean: 
    java -cp "lib/*" Statique clean mon/site/  
Cette commande supprime le dossier `build` du dossier `mon/site/` ainsi que son contenu.  
**Argument** : un chemin vers un répertoire avec un site statique initialisé.

# Audience
Ce projet est destiné a être évalué dans le cadre du cours DIL (développement d'ingénierie logicielle). 

# Code de conduite
Nous avons établit un code de conduite, qui décrit les comportements souhaités de la part des membres du groupe lors du travail sur le projet. [Code of conduct](https://github.com/dil-classroom/projet-egger_gehring_schaller_vogel/blob/main/code-of-conduct.md).

