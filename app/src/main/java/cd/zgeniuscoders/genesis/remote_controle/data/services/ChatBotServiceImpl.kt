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

    val responseRules = listOf(
        // 1. Accueil & Salutations
        listOf("bonjour", "salut", "hey", "coucou", "bonsoir") to
                "Bonjour ! Comment puis-je vous aider avec votre réchaud à induction aujourd’hui ?",

        listOf("merci", "merci beaucoup", "super merci") to
                "De rien ! N'hésitez pas si vous avez d'autres questions.",

        listOf("au revoir", "bye", "à plus") to
                "Au revoir ! Passez une excellente journée. N'hésitez pas à revenir si besoin !",

        // 2. Fonctionnement de Base
        listOf(
            "comment allumer le réchaud ?",
            "je veux démarrer la cuisson",
            "allumer la plaque"
        ) to
                "Pour allumer le réchaud :\n1. Ouvrez l’application.\n2. Appuyez sur le bouton Power.\n3. Ajustez la température si nécessaire.",

        listOf("comment éteindre le réchaud ?", "arrêter la cuisson", "éteindre la plaque") to
                "Pour éteindre :\n1. Allez dans l’app\n2. Cliquez sur Éteindre.",

        listOf(
            "comment régler la température ?",
            "augmenter la chaleur",
            "baisser la température"
        ) to
                "Ajustez la température via le bouton dans l’app (de 0 à ≈120). Vous pouvez aussi utiliser des préréglages comme Faire bouillir ou Mijoter.",

        // 3. Connexion & Appairage
        listOf("comment connecter le réchaud ?", "pairing bluetooth", "problème de connexion") to
                "1. Activez le Bluetooth de votre smartphone.\n2. Lancez l’app et allez dans Paramètres > Appairage.\n3. Sélectionnez votre réchaud Genesis.\n4. Confirmez le code à 4 chiffres si demandé.",

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
        listOf("comment économiser l'énergie ?", "réduire consommation") to
                "Utilisez des casseroles à fond plat, couvrez-les, et privilégiez les puissances moyennes. L’app propose aussi un mode Éco.",

        listOf("nettoyer la plaque", "entretien induction") to
                "Éteignez le réchaud, puis utilisez un chiffon doux et un produit vitrocéramique. Évitez les abrasifs !",

        // 7. Support & Feedback
        listOf("contacter le support", "aide urgente", "problème grave") to
                "Notre équipe est joignable au +243 823132960 ou juniorangambun2@gmail.com. Décrivez votre problème avec une photo si possible.",

        listOf("où laisser un avis ?", "je veux suggérer une fonction") to
                "Rendez-vous sur Notre App > Menu > Feedback, ou notez-nous sur l’App Store/Play Store ! Merci pour votre contribution.",

        // 8. Commandes vocales
        listOf("allume la plaque 2", "puissance maximale", "mets sur mijoter") to
                "Bien reçu ! La plaque 2 est allumée en mode mijotage (niveau 3). Bonne cuisine !",
        listOf("faire du pondu", "cuire feuilles de manioc", "recette pondu") to
                "Lave bien 500g de feuilles de manioc pilées. Fais-les cuire avec un peu d’eau 30 min. Ajoute 2 tomates, 1 oignon, huile de palme, poisson fumé, sel et piment. Laisse mijoter encore 45 min à feu doux.",

        listOf("préparer la bouillie", "bouillie de maïs", "bouillie matin") to
                "Mélange 4 c. à soupe de farine de maïs dans un peu d’eau froide. Ajoute dans 2 verres d’eau bouillante, remue et cuis 10 min. Sucre et ajoute du lait si souhaité.",

        listOf("omelette simple", "oeufs matin", "omelette rapide") to
                "Bats 2 œufs avec sel et poivre. Fais revenir 1/2 oignon et 1 tomate dans l’huile, ajoute les œufs, et cuis à feu doux jusqu’à ce que ce soit pris.",

        listOf("faire beignets banane", "recette mikate ya mbila", "beignets doux") to
                "Écrase 3 bananes mûres. Ajoute 250g de farine, 1 c. à soupe de sucre et 1/2 sachet de levure. Laisse reposer 20 min. Fais frire en petites boules dorées dans de l’huile chaude.",

        listOf("petit déjeuner rapide", "toast sucré", "pain confiture") to
                "Toaste deux tranches de pain. Étale du miel ou de la confiture. Ajoute des rondelles de banane ou mangue pour un petit-déj express et énergétique.",

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
        chatBotDao.add(ChatBotEntity.fromModel(message.copy(message = cleanedMessage)))

        for ((keywords, response) in responseRules) {
            if (keywords.any { cleanedMessage.contains(it) }) {
                var chat = ChatSendMessage(message = response, isRobot = true)
                chatBotDao.add(ChatBotEntity.fromModel(chat))
                return
            }
        }

        chatBotDao.add(
            ChatBotEntity.fromModel(
                ChatSendMessage(
                    message = "Désolé, je n’ai pas compris votre demande.",
                    isRobot = true
                )
            )
        )
    }

    override suspend fun startChat() {
        chatBotDao.add(
            ChatBotEntity.fromModel(
                ChatSendMessage(
                    "Salut, comment puis-je t'aider ?",
                    isRobot = true
                )
            )
        )
    }

    override suspend fun clearMessages() {
        chatBotDao.deleteMessages()
    }

}