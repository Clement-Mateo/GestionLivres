# GestionLivres

Petite appli android qui fait du CRUD (Create, read, update and delete) sur des données stockées sur une BD à distance (firebase).  
Gestion assez poussée des images chargées via un url (si ce dernier est absent ou ne fonctionne pas, une image par défaut en local sera mise à la place), retaillées et recentrées tout en gardant leur ratio d'origine.

Lorsque vous lancez l'application, celle ci vous demande votre email afin de vous connectez :  
![connexion_1](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/connexion_1.png?raw=true)

![connexion_2](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/connexion_2.png?raw=true)

Si vous entrez l'email d'un compte qui n'existe pas alors l'application vous proposera automatiquement de créer un compte :  
![inscription](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/inscription.png?raw=true)

Le compte va alors automatiquement être ajouté dans la liste des utilisateurs connus de l'application et les prochaines fois que vous essaiez de vous connecter avec cette adresse email votre mot de passe vous sera demandé :  
![firebase_auth_1](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/firebase_auth_1.png?raw=true)

![firebase_auth_2](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/firebase_auth_2.png?raw=true)

Une fois que vous êtes connectés, si c'est la première fois que vous vous connectez, alors par défaut votre compte ne sera associé àn aucun livre:  
![accueil_vide](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/accueil_vide.png?raw=true)

Libre a vous alors d'ajouter les livres que vous souhaitez lire :  
![ajout_livre_1](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/ajout_livre_1.png?raw=true)

![ajout_livre_2](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/ajout_livre_2.png?raw=true)

![livre_ajouté](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/livre_ajouté.png?raw=true)

![detail_livre_1](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/detail_livre_1.png?raw=true)

![detail_livre_2](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/detail_livre_2.png?raw=true)

![edition_livre](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/edition_livre.png?raw=true)

![firestore](https://github.com/clementor5/GestionLivres/blob/master/IMG_README/firestore.png?raw=true)
