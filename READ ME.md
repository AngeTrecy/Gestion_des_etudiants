# SYSTEME DE GESTION DES ETUDIANTS

## Description
 ce dispositif est concu pour un établissement dans la gestion de ces étudiants.

 Il permet aux personnels d'enregistrer sur MySQL les informations ,de supprimer, de mettre à jour un étudiant, et afficher la liste de tout les étudiants.


 ## Fonctionnalités
   ** . AJout Des étudiants **

 Elle se fait à partir d'une formulaire qui prend en compte: nom,prenom,date de naissance,matricule,sexe et classe; stoké dans une Base de donnée sur MySQL.

 ** .Suppression **

 Ici il s'agirait de saisir le matricule qu'on souhaite supprimer par la suite toute les informations concernant ce matricule s'afficherons et par la suite une question pour poursuivre la suppressions.

 ** .Modification **
 
 Ici il s'agirait de saisir le matricule qu'on souhaite modifier par la suite on pose la question de savoir sur qu'elle champs la modification doit se faire.
 
** . Affichage **

Ici il s'agirat d'afficher tout les étudiants  enregistré dans la BD.

** .Recherche **

la recherche se fait a partir du matricule

## Prérequis

- Avoir des connaissances en JavaFX
- Avoir des connaissances en DataBase 
- mermaid js
- java JDK
- éditeur de texte (IntelliJ, VS Code.....)

##  Installation et Execution

### Cloner le projet
git clone <url-du-depot>
cd project1

### Compiler
javac gestion.java

### Exécuter
java gestion

### Nettoyer (optionnel)
rm *.class