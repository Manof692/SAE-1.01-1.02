import extensions.File;
class leCheminDuSavoir extends Program {

    final String[] plateau = new String[]{"DEPART", "BOUTIQUE", "MATHEMATIQUE", "FRANCAIS", "ANGLAIS", 
                                        "SVT", "GEOGRAPHIE", "BOUTIQUE", "HISTOIRE", "EMC", 
                                        "PHYSIQUE-CHIMIE", "MATHEMATIQUE", "BOUTIQUE", "ANGLAIS", "FRANCAIS", 
                                        "GEOGRAPHIE", "SVT", "MATHEMATIQUE", "BOUTIQUE", "HISTOIRE", 
                                        "EMC", "PHYSIQUE-CHIMIE", "ANGLAIS", "FRANCAIS", "BOUTIQUE", 
                                        "MATHEMATIQUE", "GEOGRAPHIE", "SVT", "HISTOIRE", "EMC", 
                                        "PHYSIQUE-CHIMIE", "BOUTIQUE", "MATHEMATIQUE", "ANGLAIS", "FRANCAIS", 
                                        "GEOGRAPHIE", "SVT", "HISTOIRE", "EMC", "PHYSIQUE-CHIMIE", 
                                        "BOUTIQUE", "MATHEMATIQUE", "ANGLAIS", "FRANCAIS", "GEOGRAPHIE", 
                                        "SVT", "HISTOIRE", "EMC", "PHYSIQUE-CHIMIE", "BOUTIQUE"};

    Joueur newJoueur(String nom){
        Joueur j = new Joueur();
        j.nom = nom;
        j.inventaire = newInventaire();
        j.position = 0;
        return j;
    }

    void testNewJoueur(){
        Joueur j = newJoueur("toto");
        assertEquals("toto",j.nom);
        assertEquals(0,j.position);
    }

    Inventaire newInventaire(){
        Inventaire inv = new Inventaire();
        inv.argent = 20;
        inv.bombe = false;
        inv.livre = 0;
        return inv;
    }

    void testNewInventaire(){
        Inventaire inv = newInventaire();
        assertEquals(20,inv.argent);
        assertFalse(inv.bombe);
        assertEquals(0,inv.livre);
    }

    boolean saisieIntCorrecte(String saisie){
        char c;
        if (length(saisie)>0){ 
            for(int i=0; i<length(saisie);i++){
                c = charAt(saisie,i);
                if (c<48 || c>57){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    void testSaisieIntCorrecte(){
        assertTrue(saisieIntCorrecte("2"));
        assertFalse(saisieIntCorrecte("a"));
        assertTrue(saisieIntCorrecte("15"));
    }

    boolean choixNombreJoueur(String saisieNbJoueurs){
        if (saisieIntCorrecte(saisieNbJoueurs)){
            int nbJoueurs = stringToInt(saisieNbJoueurs);
            if (nbJoueurs < 1){
                println("Depuis quand, peut-on ne pas exister ?");
                return false;
            } if(nbJoueurs < 2 && nbJoueurs > 0){
                println("Non ! On ne joue pas seul à un jeu où le savoir est maître, le savoir se partage !");
                return false;
            } if(nbJoueurs>4) {
                println("Malheureusement cela serait trop chaotique pour vous d'avoir plus de 4 joueurs !");
                return false;
            }
            return true;
        } else {
            println("Attention ! Ceci n'est pas un nombre !");
            return false;
        }
    }

    void testChoixNbjoueur(){
        assertTrue(choixNombreJoueur("3"));
        assertFalse(choixNombreJoueur("5"));
        assertFalse(choixNombreJoueur("a"));
        assertFalse(choixNombreJoueur(""));
    }

    boolean nomJoueurValide(String nom, int numJoueur, Joueur[] listeJoueurs){
        if (equals(nom,"")){
            println("Attention ! Votre nom ne peux pas être vide !");
            return false;
        } if (numJoueur>1){
            for (int joueur=0; joueur<numJoueur-1; joueur++){
                if(equals(listeJoueurs[joueur].nom,nom)){
                    println("Attention ! Vous ne pouvez pas prendre le même nom qu'un autre joueur !");
                    return false;
                }
            }
        }
        return true;
    }

    void testNomJoueurValide(){
        Joueur[] listeJoueurs = new Joueur[]{newJoueur(""),newJoueur("toto")};
        assertFalse(nomJoueurValide(listeJoueurs[0].nom,1,listeJoueurs));
        listeJoueurs[0] = newJoueur("toto");
        assertTrue(nomJoueurValide(listeJoueurs[0].nom,1,listeJoueurs));
        assertFalse(nomJoueurValide(listeJoueurs[1].nom,2,listeJoueurs));
        listeJoueurs[1] = newJoueur("tata");
        assertTrue(nomJoueurValide(listeJoueurs[1].nom,2,listeJoueurs));
    }

    Joueur[] listerJoueur(int nbJoueurs){
        Joueur[] listeJoueurs = new Joueur [nbJoueurs];
        for(int cpt=1; cpt<=nbJoueurs; cpt++){
            String nomJoueur = "";
            do{
                print("Joueur " + cpt + " : ");
                nomJoueur = readString();
            } while(!nomJoueurValide(nomJoueur,cpt,listeJoueurs));
            listeJoueurs[cpt-1] = newJoueur(nomJoueur);
        }
        return listeJoueurs;
    }

    int lancerDe(){
        println("Lancement du dé !");
        int de = ((int) (random(1,6)));
        println("Vous avez fait un "+de+" !");
        return de;
    }

    void testLancerDe(){
        int de = lancerDe();
        assertTrue(de>0 && de<7);
    }

    void afficherObjet(Inventaire inv){
        println("Objet(s) ; ");
        if(inv.bombe){
            println("- Bombe");
        }
    }

    void afficherFichier(String nomFichier) {
        File fichier = newFile(nomFichier);
        while (ready(fichier)) {
            println(readLine(fichier));
            sleep(20);
        }
    }
    void afficherChoixPossible(){
        afficherFichier("../ressources/choixJoueur.txt");
    }

    void afficherBoutique(){
        afficherFichier("../ressources/boutique.txt");
    }

    Joueur[] choixJoueur(String choix, Inventaire inv,Joueur[] listeJoueurs, int numJoueur){
        if (equals(choix,"1")){
            return listeJoueurs;
        }
        if (equals(choix,"2")){
            return utiliserObjet(inv,listeJoueurs,numJoueur);
        }
        if (equals(choix,"3")){
            println("Vous avez actuellement : "+inv.argent+" Pièce(s) et "+inv.livre+" Livre(s).");
            return listeJoueurs;
        }
        println("Attention ! Tu ne peux pas choisir cette option !");
        return listeJoueurs;
    }

    Inventaire choixBoutique(Inventaire inv){
        Inventaire inventaireJoueur = inv;
        boolean actionValide = false;
        boolean achatEffectué = false;
        do {
            afficherBoutique();
            println("Que choisissez-vous ?");
            String reponse = "";
            int numChoix = saisieChoix();
            if (numChoix==1){
                achatEffectué = true;
                actionValide = true;
                inventaireJoueur = achatLivre(inventaireJoueur);
            } else if (numChoix==2){
                if (achatPossible(inventaireJoueur)){
                    achatEffectué = true;
                    actionValide = true;
                    inventaireJoueur = achatBombe(inventaireJoueur);
                } else {
                    actionValide = false;
                    println("Attention ! Vous possedez déja une bombe !");
                }
            } else {
                println("Très bien, je vous souhaite une bonne journée !");
                actionValide = true;
            }
            if (achatEffectué){
                println("Merci pour votre achat !");
            }
        } while(!actionValide);
        return inventaireJoueur;
    }

    int saisieChoix(){
        String reponse = "";
        int numChoix = 0;
        boolean ChoixValide = false;
        do {
            reponse = readString();
            if (saisieIntCorrecte(reponse)){
                numChoix = stringToInt(reponse);
                ChoixValide = (numChoix>0 && numChoix<5);
            }
            if (!ChoixValide){
                println("Attention ! Vous ne pouvez pas choisir cela !");
            }
        } while(!ChoixValide);
        return numChoix;
    }

    boolean achatPossible(Inventaire inv){
        return !inv.bombe;
    }

    Inventaire achatLivre(Inventaire inv){
        Inventaire inventaireJoueur = inv;
        inventaireJoueur.argent -= 20;
        inventaireJoueur.livre ++;
        return inventaireJoueur;
    }

    Inventaire achatBombe(Inventaire inv){
        Inventaire inventaireJoueur = inv;
        inventaireJoueur.argent -= 3;
        inventaireJoueur.bombe = true;
        return inventaireJoueur;
    }

    Joueur[] utiliserObjet(Inventaire inv, Joueur[] l, int numJoueurActuel){
        Joueur[] listeJoueurs = l;
        Inventaire inventaireJoueur = inv;
        boolean saisieCorrecte = true;
        if (!inventaireJoueur.bombe){
            println("Vous n'avez pas d'objet disponible.");
            return listeJoueurs;
        } else {
            do {
                println("Souhaitez-vous utiliser votre bombe ? (Y/N)");
                String choix = readString();
                if (equals(choix, "Y")){
                    return lancerBombe(inventaireJoueur, listeJoueurs,numJoueurActuel);
                } else if (equals(choix, "N")){
                    return listeJoueurs;
                } else {
                    println("Attention ! Vous vous êtes trompé !");
                    saisieCorrecte = false;
                }
            } while (!saisieCorrecte);
        }
        return listeJoueurs;
    }

    Joueur initialiserInventaire(Joueur j, Inventaire inv){
        Joueur joueur = j;
        joueur.inventaire = inv;
        return joueur;
    }

    Joueur[] lancerBombe(Inventaire inv, Joueur[] l,int numJoueurActuel){
        Joueur[] listeJoueurs = l;
        Inventaire inventaireJoueur = inv;
        String saisieJoueurVisé = "";
        boolean saisieCorrecte = true;
        do {
            println("Sur qui souhaitez vous l'utiliser ? (Entrer son numéro)");
            saisieJoueurVisé = readString();
            saisieCorrecte = joueurExistant(listeJoueurs,saisieJoueurVisé,numJoueurActuel);
        } while(!saisieCorrecte);
        int numJoueurVisé = stringToInt(saisieJoueurVisé);
        Joueur joueurVisé = listeJoueurs[numJoueurVisé-1];
        Inventaire inventaireJoueurVisé = joueurVisé.inventaire;
        inventaireJoueurVisé.argent -= 5;
        listeJoueurs[numJoueurVisé-1] = initialiserInventaire(joueurVisé,inventaireJoueurVisé);
        inventaireJoueur.bombe = false;
        listeJoueurs[numJoueurActuel] = initialiserInventaire(listeJoueurs[numJoueurActuel],inventaireJoueur);
        println("Ce joueur a perdu 5 Pièces");
        return listeJoueurs;
    }

    boolean joueurExistant(Joueur[] listeJoueurs, String joueurVisé, int joueurActuel){
        if (saisieIntCorrecte(joueurVisé)){
            int numJoueur = stringToInt(joueurVisé);
            if (numJoueur>0 && numJoueur<=length(listeJoueurs)){
                if (numJoueur == joueurActuel+1){
                    println("Vous ne pouvez pas vous choisir vous même !");
                    return false;
                }
                return true;
            } else {
                println("Il n'y a pas de joueur qui porte ce numéro !");
                return false;
            }
        } else {
            println("Ce n'est pas un numéro de joueur !");
            return false;
        }
    }

    void testJoueurExistant(){
        Joueur[] listeJoueurs = new Joueur[]{newJoueur("toto"),newJoueur("tata")};
        assertTrue(joueurExistant(listeJoueurs,"2",0));
        assertFalse(joueurExistant(listeJoueurs,"1",0));
        assertFalse(joueurExistant(listeJoueurs,"1",0));
    }

    Inventaire actionCase(Inventaire inv, int position){
        String themeCase = plateau[position];
        if (equals(themeCase,"BOUTIQUE")){
            println("Case Boutique ! \n");
            return choixBoutique(inv);
        }
        if (equals(themeCase,"DEPART")){
            println("Case Départ ! \nVous gagner 10 pièces !");
            return caseDepart(inv);
        }
        println("Question "+themeCase+" ! \n");
        return poserQuestion(inv,themeCase);
    }

    Inventaire caseDepart(Inventaire inv){
        Inventaire inventaireJoueur = inv;
        inventaireJoueur.argent += 10;
        return inventaireJoueur;
    }

    int nbLignes(String nomFichier){
        int nbr = 0;
        File fichier = newFile(nomFichier);
        while(ready(fichier)){
            readLine(fichier);
            nbr ++;
        }
        return nbr;
    }

    void testNbLignes(){
        assertEquals(50,nbLignes("../ressources/question.txt"));
    }

    String questionAleatoire(String nomFichier){
        File fichier = newFile(nomFichier);
        int nbQuestion = nbLignes(nomFichier)-1;
        int numQuestion = (int)random(1,nbQuestion);
        int cpt = 0;
        while (cpt!=numQuestion){
            readLine(fichier);
            cpt++;
        }
        return readLine(fichier);
    }

    Question newQuestion(){
        Question question = new Question();
        question.question = "";
        question.reponses = new String[]{"","",""};
        return question;
    }

    void testNewQuestion(){
        Question question = newQuestion();
        assertEquals("",question.question);
        String[] reponses = question.reponses;
        assertEquals("",reponses[0]);
        assertEquals("",reponses[1]);
        assertEquals("",reponses[2]);
    }

    Question recupererQuesion(String ligne){
        char c;
        int cpt=0; int debut = 0;
        Question question = newQuestion();
        String[] reponses = new String[]{"","",""};
        for (int i=0; i<length(ligne); i++){
            c= charAt(ligne,i);
            if (c=='/'){
                cpt ++;
                if (cpt==1){
                    question.question = substring(ligne,debut,i);
                    debut = i+1;
                } else if (cpt==2){
                    reponses[0] = substring(ligne,debut,i);
                    debut = i+1;
                } else if (cpt==3){
                    reponses[1] = substring(ligne,debut,i);
                    debut = i+1;
                }
            }
            if (!equals(reponses[1],"")){
                reponses[2] = substring(ligne,debut,length(ligne));
            }
        }
        question.reponses = reponses;
        return question;
    }

    String[] melangerReponses(String[] reponses){
        String[] melange = new String[3];
        int idx1 = random(0,2);
        int idx2 = random(0,2);
        int idx3 = random(0,2);
        while (idx1==idx2){
            idx2 = random(0,2);
        }
        while (idx3==idx2 || idx3==idx1){
            idx3 = random(0,2);
        }
        melange[0] = reponses[idx1];
        melange[1] = reponses[idx2];
        melange[2] = reponses[idx3];
        return melange;
    }

    void afficherQuestion(Question questionReponse, String[] listeReponse){
        String[] reponses = listeReponse;
        println(questionReponse.question+"\n");
        println("1. "+reponses[0]);
        println("2. "+reponses[1]);
        println("3. "+reponses[2]);
    }

    String bonneReponse(Question questionReponse){
        String[] reponses = questionReponse.reponses;
        return reponses[0];
    }

    boolean saisieReponseCorrect(String choix){
        if (saisieIntCorrecte(choix)){
            int numChoix = stringToInt(choix);
            if (numChoix>0 && numChoix<4){
                return true;
            } else {
                println("Attention ! Tu ne peux pas choisir ce numéro !");
                return false;
            }
        } else {
            println("Attention ! Ceci n'est pas un nombre !");
            return false;
        }
    }

    boolean correctionReponse(String[] listeReponse, String bonneReponse, String choix){
        String reponse = listeReponse[stringToInt(choix)-1];
        if (equals(reponse,bonneReponse)){
            println("Bravo ! Tu as trouvé la bonne réponse !");
            return true;
        }
        println("Oh non... C'est la mauvaise réponse...");
        return false;
    }

    Inventaire recompenserJoueur(Inventaire inv, boolean reussite){
        Inventaire inventaireJoueur = inv;
        if (reussite){
            println("Vous avez gagné 2 Pièces !");
            inventaireJoueur.argent += 3;
        }
        return inventaireJoueur;
    }

    Inventaire poserQuestion(Inventaire inv, String nomTheme){
        Inventaire inventaireJoueur = inv;
        Question questionReponse = recupererQuesion(questionAleatoire("../ressources/"+nomTheme+".txt"));
        String bonneReponse = bonneReponse(questionReponse);
        String[] listeReponse = melangerReponses(questionReponse.reponses);
        String choix;
        do {
            afficherQuestion(questionReponse, listeReponse);
            choix = readString();
        } while(!saisieReponseCorrect(choix));
        inventaireJoueur = recompenserJoueur(inventaireJoueur, correctionReponse(listeReponse, bonneReponse, choix));
        return inventaireJoueur;
    }

    boolean victoire(Inventaire inv){
        return (inv.livre >= 3);
    }

    void testVictoire(){
        Inventaire inv = newInventaire();
        assertFalse(victoire(inv));
        inv.livre = 3;
        assertTrue(victoire(inv));
    }

    int changementJoueur(int joueurActuel, int nbJoueurs){
        return (joueurActuel+1) %nbJoueurs;
    }

    void testChangementJoueur(){
        assertEquals(1,changementJoueur(0,2));
        assertEquals(0,changementJoueur(2,3));
    }

    void algorithm(){
        println("Bienvenue, choisissez le nombre de joueurs qui participeront au jeu : 2, 3 ou 4 ?");
        String saisieNbJoueurs = "";
        do {
            saisieNbJoueurs = readString();
        } while (!choixNombreJoueur(saisieNbJoueurs));
        int nbJoueurs = stringToInt(saisieNbJoueurs);
        println("Bonjour à vous nouveaux joueurs ! \nMoi, vous pouvez m’appeler.. hummm .. le présentateur ! \nJe vous convie à jouer à notre jeu, notre jeu est présenté par notre collaborateur.. \nJe n’ai plus son nom, m’enfin, il est temps de vous présenter chers joueurs ! \nVeuillez entrer vos noms ;");
        Joueur[] listeJoueurs = listerJoueur(nbJoueurs);
        println("Nous allons dès à présent commencer le jeu !");
        int numJoueurActuel = 0;
        Joueur joueurActuel = listeJoueurs[0];
        do {
            println("====================================================================================================================================================");
            joueurActuel = listeJoueurs[numJoueurActuel];
            println("C'est à "+joueurActuel.nom+" de jouer !");
            boolean relancer;
            String choix = "";
            do{
                afficherChoixPossible();
                choix = readString();
                listeJoueurs = choixJoueur(choix,joueurActuel.inventaire, listeJoueurs, numJoueurActuel);
                joueurActuel = listeJoueurs[numJoueurActuel];
            } while(!equals(choix,"1"));
            int resultatDe = lancerDe();
            joueurActuel.position = joueurActuel.position + resultatDe % length(plateau);
            joueurActuel.inventaire = actionCase(joueurActuel.inventaire, joueurActuel.position);
            listeJoueurs[numJoueurActuel] = joueurActuel;
            numJoueurActuel = changementJoueur(numJoueurActuel,nbJoueurs);
        } while (!victoire(joueurActuel.inventaire));
        println("Felicitation ! Vous avez gagné !");
    }
}