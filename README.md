# Blocks

Ce plugin permet de jouer à un jeu, celui de récupérer tous les items et blocs de Minecraft.

! ATTENTION : Le plugin est en bêta-test

## Commandes

Voici les commandes : 
- ``/team`` : permet aux administrateurs de gérer plus finement les équipes (permission: `blocks.team`)
- ``/start`` : permet de commencer le jeu (permission : `blocks.start`)
- ``/t <message>`` : permet d'envoyer un message à son équipe
- ``/coords`` : permet d'envoyer ses coordonnées à son équipe
- ``/join <joueur> <nom équipe>`` : permet d'ajouter un joueur dans une équipe pendant la partie (uniquement accessible par console)
- ``/farm`` : permet de se téléporter au monde "farm"
- ``/base`` : permet de se téléporter à sa base
- ``/pause`` : permet de mettre en pause la partie (permission : `blocks.pause`)
- ``/unpause`` : permet de reprendre une partie en pause (permission : `blocks.unpause`)
- ``/stop`` : permet d'arrêter la partie prémarturement (permission : `blocks.stop`)
- ``/stopserver`` : permet d'arrêter le serveur, car la commande `/stop` remplace la commande de base (permission : `blocks.stopserver`)

## Fichier de configuration 

Voici le fichier `config.yml` de base : 
````yaml
potions:
  normal: true
  extended: true
  upgraded: true
  splash: true
  lingening: true
  tipped_arrow: true
goat_horn:
  specific: true
dimension:
  nether: true
  end: true
enchanting_table: true
ekalia:
  recipes: true
````

``potions`` permet de choisir si on peut déposer les différents types de potions

``goat_horn`` permet de choisir si on doit déposer un type de corne ou les huit

``dimension`` permet de choisir si on active ou désactive les différentes dimensions

``enchanting_table`` permet de choisir si la table d'enchantement niveau 30 est déjà posé ou non 

``ekalia`` permet d'ajouter les crafts personnalisés de CubesHunters Mini 2 d'Ekalia