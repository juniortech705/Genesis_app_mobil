package cd.zgeniuscoders.genesis.remote_controle.data.services

import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotDao
import cd.zgeniuscoders.genesis.remote_controle.data.local.ChatBotEntity
import cd.zgeniuscoders.genesis.remote_controle.data.mappers.toChatBotModel
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatBot
import cd.zgeniuscoders.genesis.remote_controle.domain.models.ChatSendMessage
import cd.zgeniuscoders.genesis.remote_controle.domain.services.ChatBotService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChatBotServiceImpl(
    private val chatBotDao: ChatBotDao
) : ChatBotService {

    private val responseRules = listOf(
        // 1. Accueil & Salutations
        listOf(
            "bonjour",
            "bonjour athena",
            "Bonjour athena",
            "Bonjour athena",
            "salut athena",
            "athena",
            "Athena",
            "genesis",
            "Genesis",
            "salut",
            "hey",
            "coucou",
            "bonsoir",
            "hello",
            "yo",
            "wesh",
            "slt",
            "bjr",
            "cc",
            "yop",
            "heya",
            "salut toi",
            "eh bonjour",
            "yo poto",
            "kikoo",
            "holla",
            "hé mec",
            "ça dit quoi",
            "ça va ?",
            "allo",
            "salu",
            "bonjor",
            "bonsoire",
            "slut",
            "hellow",
            "slt le bot",
            "he coucou",
            "j’ai une question",
            "ptit coucou",
            "bonjour le robot",
            "yo le chef",
            "salutation",
            "salut machine",
            "bien ou quoi",
            "hé le cuisto numérique",
            "hello l’IA",
            "wsh bot",
            "salut je veux cuisiner",
            "bonjour je peux poser une question ?",
            "yo, ça marche comment ton truc ?",
            "hé t’es là ?",
            "cc, je t'entends ?",
            "bonjour réchaud",
            "je parle au robot ?",
            "coucou, t’es là ?",
            "bonjour, comment tu fonctionnes ?",
            "hé répondez svp",
            "je veux parler",
            "yo bro, comment",
            "slt slt",
            "Mbote",
            "ndenge nini",
            "mbote"
        ) to "Salut ! Comment puis-je vous aider avec votre réchaud à induction aujourd’hui ?",

                listOf("merci", "merci beaucoup", "super merci", "cool") to
                "De rien ! N'hésitez pas si vous avez d'autres questions.",
        listOf("Lingala", "lingala") to
                "Mbote ! Olingi koloba na Lingala ? Tala ba motuna oyo okoki kotuna nga :\n\n" +
                "- « Ndenge nini nakoki ko pelisa rechaud ou 1? »\n" +
                "- « Ndenge nini nakoki ko éteindre plaque ou 2 ? »\n" +
                "- « Nakoki kokitisa température ndenge nini ou 3 ? »\n" +
                "- « Température ya ko tokisa biloko ezali boni ou 4 ? »\n" +
                "- « Nini esalaka soki plaque epesi erreur ou 5 ? »\n" +
                "- « recette ya omelette ou 7 ? »\n" +
                "- « Pesa recette ya pondu ou 6 ? »\n\n" +
                "Tuna kaka, nazali awa mpo na kosalisa yo 😊",
        // Lancer le réchaud
        listOf("Ndenge nini nakoki ko pelisa rechaud ?", "ndenge nini nakoki ko pelisa rechaud", "1", "kotiela plaque en marche", "kobeta plaque") to
                "Pona kotia plaque en marche :\n1. Ouvre application.\n2. Appuye na bouton ya Power.\n3. To lobela micro ya application ( genesis allume toi ).",

// Éteindre le réchaud
        listOf("Ndenge nini nakoki ko éteindre plaque ?", "ndenge nini nakoki ko éteindre plaque", "2", "stop plaque", "kokanga plaque") to
                "Pona kokanga plaque :\n1. Kende na application\n2. Appuye na 'Éteindre'. To lobela micro ya application (genesis éteint toi )",

// Régler la température
        listOf("Nakoki kokitisa température ndenge nini ?", "nakoki kokitisa température ndenge nini", "3", "kotombola température", "régler chaleur") to
                "Oko regler température na bouton ya app 'Puissance' (kobanda na 0 tii place o lingi).",

// Bouillir ou mijoter
        listOf("Température ya ko tokisa biloko ezali boni ?", "température ya ko tokisa biloko ezali boni", "4", "kobwaka", "mijoter") to
                "Soki olingi biloko e toka, tia température na 100° to +. Pona ko mijoter, tika yango na 60-80. Okoki kosala yango na bouton ya application 'Puissance'.",

// Résoudre un problème
        listOf("Nini esalaka soki plaque epesi erreur ?", "nini esalaka soki plaque epesi erreur", "5", "erreur", "plaque eboyi") to
                "Soki plaque epesi erreur, Tika yango 10 minutes, puis pesa courant lisusu. Soki ezali kaka problème, reinitialiser plaque na Paramètres.",

// Recette de pondu
        listOf("Pesa recette ya pondu ?", "recette ya pondu", "pondu ndenge nini", "6") to
                "Tia feuilles ya manioc na liboka, Tia ba épices ( matungulu, poivron, aie, ndembi, piment vert ), Tuta yango. Tia mayi na mungwa tokisa pendant 30 minutes. Tia mafuta ya mbila, poisson fumé, piment. Zela 45 min lisusu na moto ya moke.",
        listOf("Ndenge nini nasala omelette ?", "recette ya omelette", "salela omelette", "7") to
                "Pona kosalela omelette :\n1. Beta ba maki na mungwa na poivre.\n2. tia ndembi ya oignon na tomate moko na mafuta ya huile na kikalungu.\n3. Sopa ba maki.\n4. Tika yango na moto ya mukie.",


// Mode économie d’énergie
        listOf("Ndenge nini nakoki kosalela mode éco ?", "kobatela courant", "mode éco") to
                "Salela casseroles ya fond plat, couvre mboka, puis tia température moyenne. App ezali na mode Éco mpo na kosalela courant moke.",
        listOf("français", "Français", "francais", "françai", "Françai", "Francais", "Francai", "francai") to
                "Bienvenue ! Voici quelques questions que vous pouvez me poser :\n\n" +
                "- « Comment allumer le réchaud ? ou un»\n" +
                "- « Comment éteindre le réchaud ? ou deux»\n" +
                "- « Comment régler la température ? ou trois»\n" +
                "- « Quelle est la température pour faire bouillir ? ou quatre»\n" +
                "- « Que faire si la plaque affiche une erreur ? ou cinque »\n" +
                "- « Quelle est la recette du pondu ? ou six »\n" +
                "- « Comment faire une omelette ? ou sept »\n" +
                "- « Comment utiliser le mode éco ? ou huite »\n\n" +
                "Posez votre question quand vous êtes prêt, je suis là pour vous aider 😊",
        // Allumer le réchaud
        listOf("Comment allumer le réchaud ?", "comment allumer le réchaud ?", "Comment allumer le réchaud ", "Comment allumer le rechaud ?", "comment allumer le rechaud ?",
            "comment allumer le réchaud ", "un", "Un") to
                "Pour allumer le réchaud :\n1. Ouvrez l’application.\n2. Appuyez sur le bouton Power.\n3. Ou utilisez le micro (genesis allume toi ).",

// Éteindre le réchaud
        listOf("Comment éteindre le réchaud ?","comment éteindre le réchaud ?", "Comment éteindre le réchaud ", "comment éteindre le réchaud ", "Comment eteindre le rechaud ?",
            "comment eteindre le rechaud ?", "Comment eteindre le rechaud ", "comment eteindre le rechaud ", "Comment eteindre le réchaud ?", "Deux", "deux") to
                "Pour éteindre la plaque :\n1. Ouvrez l’application\n2. Cliquez sur le bouton Éteindre. Ou utlisez le micro ( genesis éteint toi )",


        listOf("Quelle est la recette de l’omelette ?", "Comment faire une omelette ?", "Sept", "sept") to
                "Voici une recette simple d’omelette :\n1. Battez 2 œufs avec du sel et du poivre.\n2. Faites revenir 1/2 oignon et 1 tomate dans un peu d’huile.\n3. " +
                "Ajoutez les œufs battus.\n4. Laissez cuire à feu doux jusqu’à ce que ce soit pris.",


// Régler la température
        listOf("Comment régler la température ?", "comment régler la température ?", "Comment régler la température ", "comment régler la température ",
            "Comment régler la temperature ?", "comment régler la temperature ?", "Comment régler la temperature ", "comment régler la temperature ",
            "Comment regler la temperature ", "comment regler la temperature ", "Trois", "trois") to
                "Vous pouvez régler la température dans l'application (de 0 à 120). Il existe aussi des modes prédéfinis comme Faire bouillir ou Mijoter.",

// Bouillir ou mijoter
        listOf("Quelle est la température pour faire bouillir ?", "Quatre", "quatre") to
                "Pour faire bouillir : utilisez une température au-dessus de 100. Pour mijoter : restez entre 60 et 80. Vous pouvez régler ça dans l’app.",

// Résolution d'erreurs
        listOf("Que faire si la plaque affiche une erreur ?", "Cinque", "cinque") to
                "Si le réchaud affiche une erreur, éteignez-le pendant 10 minutes, puis rallumez-le. S’il persiste, essayez de le réinitialiser dans Paramètres.",

// Recette : pondu
        listOf("Quelle est la recette du pondu ?", "quelle est la recette du pondu ?", "Quelle est la recette du pondu ", "quelle est la recette du pondu ", "Six", "six") to
                "Mets les feuilles de manioc dans une casserole. Ajoute les épices (tomates, poivron, ail, oignons, piment vert), puis pile le tout. Mets dans de l’eau avec du sel et laisse au feu spendant 30 minutes. Ajoute de l’huile de palme, du poisson fumé et du piment. Laisse encore cuire 45 minutes à feu doux.",

// Mode économie d’énergie
        listOf("Comment utiliser le mode éco ?", "Huite", "huite") to
                "Utilisez des casseroles à fond plat, couvrez-les, et utilisez une puissance moyenne. L’application propose aussi un mode Éco pour optimiser l’énergie.",





        listOf(
            "au revoir",
            "bye",
            "à plus",
            "ciao",
            "à bientôt",
            "salut",
            "je me casse",
            "à la prochaine",
            "je pars",
            "on se reparle",
            "bonne journée",
            "merci et à bientôt",
            "à plus tard",
            "je vous laisse",
            "on se capte",
            "on se revoit",
            "je quitte",
            "à tout à l’heure",
            "bonne soirée",
            "à la revoyure",
            "adieu",
            "aurevoir",
            "bye bye",
            "a plu",
            "ciao ciao",
            "jte laisse",
            "jmen vais",
            "a la proch",
            "a tt",
            "bn journé",
            "je deco",
            "a pluche",
            "a la prochaine fois",
            "bonne continuation",
            "à une prochaine",
            "a+",
            "chao",
            "a la plus",
            "à toute",
            "a tout",
            "bne soirée",
            "je vous quitte",
            "bnne journé",
            "bey",
            "alé tchao",
            "jdois y aller",
            "merci et bye",
            "jme barre",
            "merci bcp à +",
            "je coupe ici"
        ) to "Au revoir ! Passez une excellente journée. N'hésitez pas à revenir si besoin !",

                // 2. Fonctionnement de Base
        listOf(
            "comment allumer le réchaud ?",
            "génésis allume toi",
            "genesis allume toi",
            "comment demarrer mon réchaud à distance",
            "comment demarrer mon rechaud à distance",
            "comment demarrer mon rechaud à distance",
            "je veux allumer le rechaud",
            "allumer le réchaud",
            "allume toi",
            "allume",
            "allumage",
            "comment se passe l'allumage",
            "je veux démarrer la cuisson",
            "allumer la plaque",
            "mettre en marche le réchaud",
            "comment démarrer la plaque",
            "je veux lancer la cuisson",
            "activer le feu",
            "comment utiliser le réchaud",
            "lancer la cuisson maintenant",
            "je veux faire chauffer",
            "allumage du feu",
            "comment activer la plaque",
            "marche du réchaud",
            "lancer la plaque de cuisson",
            "coment alumer le rechaud",
            "je veu alumer le recho",
            "alumé le rechaud",
            "alume toi",
            "allumaje",
            "coment ce pase lallumage",
            "je veu demarer la cuison",
            "alumer la plaque",
            "alumé la plaq",
            "je veu fair chofé",
            "je veux activer la plaque et régler la température",
            "comment allumer et ajuster la cuisson",
            "démarrer le feu et cuire rapidement",
            "mettre en marche et choisir le niveau de chaleur",
            "allume la plaque et règle à feu moyen",
            "je veux commencer à cuisiner avec le réchaud",
            "démarre le réchaud et chauffe à 100",
            "démarre et règle la température à mijoter",
            "lance la plaque et commence la cuisson",
            "je souhaite allumer et cuire à température douce",
            "activer et chauffer à feu doux",
            "lance la cuisson et règle à feu fort",
            "mets en marche puis ajuste le niveau de chaleur",
            "je commence la cuisson, allume la plaque",
            "démarre la plaque avec puissance moyenne",
            "réchauffer et cuire en même temps",
            "comment lancer et régler la plaque",
            "mettre le réchaud en marche et faire mijoter",
            "je veux chauffer et commencer à cuire"
        ) to "Pour allumer le réchaud :\n1. Ouvrez l’application.\n2. Appuyez sur le bouton Power.\n3. Ajustez la température si nécessaire.",
                listOf("comment éteindre le réchaud ?", "arrêter la cuisson", "éteindre la plaque") to
                "Pour éteindre :\n1. Allez dans l’app\n2. Cliquez sur Éteindre.",
        listOf(
            "comment éteindre le réchaud ?",
            "je veux éteindre le réchaud",
            "éteindre la plaque",
            "genesis éteint toi",
            "génésis éteint toi",
            "génésis eteint toi",
            "genesis eteint toi",
            "comment couper le réchaud",
            "comment arrêter le réchaud",
            "arrêter la cuisson",
            "éteindre le feu",
            "fermer le réchaud",
            "stopper la plaque",
            "désactiver le réchaud",
            "arrêter la plaque de cuisson",
            "mettre le réchaud hors tension",
            "comment désactiver le réchaud",
            "éteindre la plaque maintenant",
            "je veux couper la plaque",
            "comment couper la chaleur",
            "je veux éteindre la plaque",
            "éteindre le feu maintenant",
            "couper la plaque",
            "je veux arrêter la cuisson",
            "je coupe le réchaud",
            "éteint la plaque",
            "je veux arrêter",
            "comment éteindre le feu",
            "comment stopper le réchaud",
            "arrêter la cuisson maintenant",
            "comment couper le feu",
            "mettre la plaque hors tension",
            "je veux stopper la cuisson",
            "comment couper l'alimentation",
            "fermer le feu",
            "coupé le réchaud",
            "arretez la plaque",
            "comment arreter la cuisson",
            "éteindre la plaque de cuisson",
            "je veux mettre fin à la cuisson",
            "fermer le feu",
            "je veux couper la chaleur",
            "stopper le réchaud",
            "éteint le réchaud",
            "désactiver la plaque",
            "éteindre l'appareil"
        ) to "Pour éteindre le réchaud :\n1. Ouvrez l’application.\n2. Appuyez sur le bouton Power.\n3. Ajustez la température à 0 pour éteindre la plaque.",


                listOf(
            // Intentions d'origine
            "comment régler la température ?",
            "augmenter la chaleur",
            "baisser la température",
            "modifier la température",
            "régler la chaleur",
            "diminuer la température",
            "monter la chaleur",
            "changer le niveau de cuisson",
            "ajuster la température",
            "mettre plus chaud",
            "régler la teperature",
            "régler la plaque",
            "comen regler la temperature",
            "augmenter la chaleure",
            "baiser la temperature",
            "regler la temperature",
            "modifier la temperateur",
            "comment regle la temperatur",
            "changer nivo cuisson",
            "ajustr la chaleur",
            "mete moin chaud", "comment regler la temperature",
            "refrodir la plaqu",

            // Nouvelles intentions ajoutées
            "comment augmenter la température de cuisson",
            "réglage de température du réchaud",
            "baisser un peu la température",
            "est-ce qu’on peut régler la température ?",
            "mettre la température à 100 degrés",
            "je veux que ça chauffe plus",
            "il fait trop chaud, baisse un peu",
            "réduis la puissance de chauffe",
            "j’aimerais cuire à feu doux",
            "augmente la chaleur de la plaque",

            "reglage temperatur du rechaud",
            "comen baisser temprature cuisson",
            "je veu que sa chaufe plus",
            "baise la challeur svp",
            "augmante la temperateur",
            "feu tro for diminue un peu",
            "besse la plaque",
            "fai chauffé plus",
            "diminue la chauleur",
            "met le feu moin for",

            "peux-tu chauffer plus fort et allumer la plaque",
            "baisse la température et active le mode mijoter",
            "mets à température moyenne et démarre la cuisson",
            "je veux commencer à cuire à feu doux",
            "réchauffe un peu et garde la température constante",
            "lance la cuisson et ajuste la chaleur en même temps",
            "mets à 60 degrés et active le minuteur",
            "chauffe à fond puis réduis après 5 minutes",
            "je veux faire bouillir rapidement, mets température au max",
            "cuisson douce pendant 10 minutes, règle bien la chaleur",

            "température trop basse, augmente",
            "je trouve que ça ne chauffe pas assez",
            "chauffe plus stp",
            "peux-tu baisser un peu le feu ?",
            "il faut chauffer davantage",
            "je veux mijoter doucement",
            "température optimale pour cuire ?",
            "ajuste pour cuisson moyenne",
            "chauffe légèrement",
            "je veux que ça reste chaud",
            "plaque pas assez chaude, fais quelque chose",

            "chaufer plis fort",
            "bais la temprature",
            "plaque pas assé chaufe",
            "regle temp a 90degrés",
            "met feu fort svvp",
            "comment modif tempratur",
            "chauf tro, baisse",
            "faible chauleur, augmente"
        ) to "Ajustez la température via le bouton dans l’app (de 0 à ≈120). Vous pouvez aussi utiliser des préréglages comme Faire bouillir ou Mijoter.",

                // 3. Connexion & Appairage
        listOf(
            "comment connecter le réchaud ?",
            "comment connecter le rechaud au bluetooth?",
            "comment connecter le réchaud au bleutooth?",
            "comment connecter le rechaud au bleutooth ?",
            "je veux connecter le rechaud au bleutooth ?",
            "je veux connecter le rechaud au bluetooth ?",
            "comment connecter le réchaud au bluetooth?",
            "je veux connecter le telephone au bleutooth ?",
            "je veux connecter le phone au rechaud par bleutooth ?",
            "pairing bluetooth",
            "problème de connexion",
            "connexion du réchaud",
            "connecter le réchaud à mon téléphone",
            "appairer le réchaud",
            "connexion bluetooth réchaud",
            "réchaud non connecté",
            "bluetooth réchaud",
            "comment jumeler le réchaud",
            "lancer la connexion",
            "réchaud introuvable",
            "coment conecter le rechaud",
            "paring bluetuth",
            "problèm de conection",
            "conecter le rechaud a mon telefone",
            "apaire le rechaud",
            "conexon bluetuth rechaud",
            "bluetuth rechaud",
            "jumelèr le recho",
            "lancé la conection",
            "rechaud introvabl"
        ) to "1. Activez le Bluetooth de votre smartphone.\n2. Lancez l’app et allez dans Paramètres > Appairage.\n3. Sélectionnez votre réchaud Genesis.\n4. Confirmez le code à 4 chiffres si demandé.",

                listOf("le réchaud ne se connecte pas", "bluetooth ne marche pas", "erreur de connexion") to
                "Essayez ceci :\n- Redémarrez l’app et le Bluetooth.\n- Vérifiez que le réchaud est sous tension.\n- Si ça persiste, réinitialisez le réchaud (Paramètres > Réinitialiser).",

        // 4. Sécurité
        listOf("comment activer le verrou enfant ?", "mode sécurité", "bloquer les touches") to
                "Allez dans Sécurité > Verrouillage enfant dans l’app. Un code à 4 chiffres sera demandé pour toute modification.",

        listOf(
            "quelle casserole utiliser ?",
            "compatibilité ustensiles",
            "pourquoi ça ne chauffe pas ?"
        ) to
                "Seules les casseroles ferromagnétiques (acier, fonte) fonctionnent. Vérifiez le logo compatible induction sous la casserole.",

        // 5. Dépannage
        listOf("le réchaud affiche e2", "erreur e2", "problème surchauffe") to
                "E2 = Surchauffe. Éteignez le réchaud 10 min, puis rallumez. Vérifiez aussi la ventilation autour de l’appareil.",

        listOf("comment réinitialiser le réchaud ?", "reset usine") to
                "Allez dans Paramètres > Réinitialisation usine dans l’app. Confirmez avec votre mot de passe.",

        // 6. Astuces & Optimisation
        listOf(
            "comment économiser l'énergie ?",
            "réduire consommation",
            "economie d'énergie",
            "réduire ma facture d'électricité",
            "astuces pour consommer moins d'énergie",
            "baisser la consommation électrique",
            "économiser l'électricité à la maison",
            "conseils pour dépenser moins en énergie",
            "faire des économies d'énergie",
            "comment utiliser moins d'électricité",
            "réduction consommation énergie",
            "idées pour économiser l'énergie",
            "techniques pour consommer moins d'électricité",
            "econimiser l'energie",
            "réduir consommation",
            "ekomomie d'energie",
            "economiser lenergi",
            "réduction conso denergi",
            "astuce pour conso moin",
            "consomé moin d'énérgie",
            "réduie ma facture electricité",
            "econome enérgie",
            "énergir economi"
        ) to "Utilisez des casseroles à fond plat, couvrez-les, et privilégiez les puissances moyennes. L’app propose aussi un mode Éco.",

        listOf("et si le rechaud est couvert d'eau je fais comment") to "Si votre rechaud est couvet d'eau contentez vous de l'essuyer avec un tissus sec, puis rallumer après 15 min",


                listOf("nettoyer la plaque", "entretien induction", "comment laver du rechaud", "comment nettoyer du rechaud") to
                "Éteignez le réchaud, puis utilisez un chiffon doux et un produit vitrocéramique. Évitez les abrasifs !",

        // 7. Support & Feedback
        listOf("contacter le support", "aide urgente", "problème grave") to
                "Notre équipe est joignable au +243 823132960 ou juniorangambun2@gmail.com. Décrivez votre problème avec une photo si possible.",

        listOf("où laisser un avis ?", "je veux suggérer une fonction") to
                "Rendez-vous sur Notre App > Menu > Feedback, ou notez-nous sur l’App Store/Play Store ! Merci pour votre contribution.",

        // 8. Commandes vocales
        listOf("allume la plaque 2", "puissance maximale", "mets sur mijoter") to
                "Bien reçu ! La plaque 2 est allumée en mode mijotage (niveau 3). Bonne cuisine !",
        listOf("c'est quoi la température maximale du rechaud?", "temperature maximale") to " la temperature maximale est de 200 degrés",
        listOf(
            "faire du pondu",
            "cuire feuilles de manioc",
            "recette pondu",
            "préparer du pondu",
            "feuilles de manioc recette",
            "comment faire le pondu",
            "cuisson pondu",
            "cuisiner les feuilles de manioc",
            "pondu congolais",
            "pondu au poisson fumé",
            "comment cuire le pondu",
            "fèr du pondu",
            "feuille maniok",
            "recete de pondu",
            "preparer pondu",
            "pondu congole",
            "cuisiner les feille de manio",
            "pondu o poisson fumée",
            "komon fé le pondu",
            "pondu au poissin fume",
            "pondu congola"
        ) to "Lave bien 500g de feuilles de manioc pilées. Fais-les cuire avec un peu d’eau 30 min. Ajoute 2 tomates, 1 oignon, huile de palme, poisson fumé, sel et piment. Laisse mijoter encore 45 min à feu doux.",

                listOf("préparer la bouillie", "bouillie de maïs", "bouillie matin") to
                "Mélange 4 c. à soupe de farine de maïs dans un peu d’eau froide. Ajoute dans 2 verres d’eau bouillante, remue et cuis 10 min. Sucre et ajoute du lait si souhaité.",

        listOf(
            "omelette simple",
            "oeufs matin",
            "omelette rapide",
            "faire une omelette",
            "cuisson œufs matin",
            "recette omelette facile",
            "préparer une omelette vite",
            "omelette express",
            "cuire des œufs rapidement",
            "omelette à la tomate",
            "œufs brouillés maison",
            "omelette maison rapide",
            "omelet simpl",
            "oeuf matin",
            "omelet rapde",
            "fer une omelet",
            "cuson oeuf matin",
            "recete omelet facille",
            "preparer une omelete vit",
            "omelette exprès",
            "cuires des oeux rapideman",
            "omelett a la tomat",
            "comment faire une omelette",
            "comment cuire une omelette"
        ) to "Bats 2 œufs avec sel et poivre. Fais revenir 1/2 oignon et 1 tomate dans l’huile, ajoute les œufs, et cuis à feu doux jusqu’à ce que ce soit pris.",


                listOf("faire des beignets banane", "recette de mikate ya mbila", "beignets doux") to
                "Écrase 3 bananes mûres. Ajoute 250g de farine, 1 c. à soupe de sucre et 1/2 sachet de levure. Laisse reposer 20 min. Fais frire en petites boules dorées dans de l’huile chaude.",

        listOf(
            "petit déjeuner rapide",
            "toast sucré",
            "pain confiture",
            "petit déj",
            "répas du matin",
            "déjeuner express",
            "recette petit dej",
            "idée petit déjeuner",
            "préparer un petit déj rapide",
            "petit-déjeuner énergétique",
            "tartine du matin",
            "breakfast rapide",
            "peti dejener rapide",
            "tost sucré",
            "pain confiutur",
            "petit dege",
            "répa du matin",
            "dejener expres",
            "recet ptit déj",
            "ideé ptit dejeuner",
            "preparé un ptit dej",
            "ptit-déj energétique"
        ) to "Toaste deux tranches de pain. Étale du miel ou de la confiture. Ajoute des rondelles de banane ou mangue pour un petit-déj express et énergétique."

    )

    override suspend fun getMessages(): Flow<List<ChatBot>> = callbackFlow {

        chatBotDao
            .all()
            .onEach { chatBotEntities ->

                val notes = chatBotEntities.map { chatEntity ->
                    chatEntity.toChatBotModel()
                }

                trySend(
                    notes
                )

            }.launchIn(CoroutineScope(Dispatchers.IO))

        awaitClose()

    }

    override suspend fun sendMessage(message: ChatSendMessage) {
        val cleanedMessage = message.message.lowercase().trim()

        var isVoicedMessage = false
        if (message.isVoiceMessage) {
            isVoicedMessage = true
        }

        chatBotDao.add(ChatBotEntity.fromModel(message.copy(message = cleanedMessage)))

        for ((keywords, response) in responseRules) {
            if (keywords.any { cleanedMessage.contains(it) }) {
                val chat = ChatSendMessage(
                    message = response,
                    isRobot = true,
                    isVoiceMessage = isVoicedMessage
                )
                chatBotDao.add(ChatBotEntity.fromModel(chat))
                return
            }
        }

        chatBotDao.add(
            ChatBotEntity.fromModel(
                ChatSendMessage(
                    message = "Désolé, je n’ai pas compris votre demande. Veuillez reformuler ou utiliser un autre ortographe svp. Pour plus d'informations, vous pouvez aller sur notre site web: https://www.Specialgenesis.com",
                    isRobot = true
                )
            )
        )
    }

    override suspend fun startChat() {
        chatBotDao.add(
            ChatBotEntity.fromModel(
                ChatSendMessage(
                    "Salut, Je suis Athena, votre assistant pour l'utilisation de votre réchaud à Induction Genesis.\nCommencer par choisir une langue en écrivant:\n- Français \n  OU \n- Lingala ?",
                    isRobot = true
                )
            )
        )
    }

    override suspend fun clearMessages() {
        chatBotDao.deleteMessages()
    }

}