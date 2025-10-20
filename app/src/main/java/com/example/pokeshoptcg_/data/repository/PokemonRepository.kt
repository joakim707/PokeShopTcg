package com.example.pokeshoptcg_.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.pokeshoptcg_.data.db.PokemonCardDao
import com.example.pokeshoptcg_.data.db.PokemonCardEntity
import com.example.pokeshoptcg_.data.model.PokemonCard
import com.example.pokeshoptcg_.data.remote.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class PokemonRepository(private val dao: PokemonCardDao) {

    // LiveData depuis Room
    val allCards = dao.getAllCards()
    val favoriteCards = dao.getFavoriteCards()

    /**
     * Rafraîchit la base locale depuis l'API.
     * Si l'API échoue (timeout, réseau, etc.), conserve les données locales.
     */
    suspend fun refreshCards(pageSize: Int = 60) {
        withContext(Dispatchers.IO) {
            try {
                // Ici on limite le nombre de cartes récupérées à 30
                val response = ApiClient.api.getPokemonCards(pageSize = 30)

                if (response.isSuccessful) {
                    response.body()?.data?.let { list: List<PokemonCard> ->
                        val entities = list.map { card: PokemonCard ->
                            PokemonCardEntity(
                                id = card.id,
                                name = card.name,
                                imageUrl = card.images.large,
                                rarity = card.rarity,
                                type = card.types?.joinToString(", "),
                                price = card.cardmarket?.prices?.averageSellPrice
                            )
                        }

                        // Vide la table avant d’insérer les nouvelles cartes
                        dao.clearAll()
                        dao.insertAll(entities)

                        Log.d("Repository", "Cartes mises à jour (${entities.size}) depuis l'API")
                    }
                } else {
                    Log.e("Repository", "Erreur API : ${response.code()}")
                }

            } catch (e: SocketTimeoutException) {
                Log.e("Repository", "⏱️ Timeout : ${e.message}")
                // On garde simplement les cartes locales
            } catch (e: UnknownHostException) {
                Log.e("Repository", "🌐 Pas de connexion internet")
                // On garde aussi les cartes locales
            } catch (e: Exception) {
                Log.e("Repository", "Erreur inconnue : ${e.message}")
            }
        }
    }

    /**
     * Met à jour une carte (utile pour les favoris)
     */
    suspend fun updateCard(card: PokemonCardEntity) {
        withContext(Dispatchers.IO) {
            dao.update(card)
        }
    }

    fun searchCards(query: String): LiveData<List<PokemonCardEntity>> {
        return dao.searchCards(query)
    }

}
