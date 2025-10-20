package com.example.pokeshoptcg_.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PokemonCardDao {

    @Query("SELECT * FROM pokemon_cards")
    fun getAllCards(): LiveData<List<PokemonCardEntity>>

    @Query("SELECT * FROM pokemon_cards WHERE isFavorite = 1")
    fun getFavoriteCards(): LiveData<List<PokemonCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cards: List<PokemonCardEntity>)

    @Update
    suspend fun update(card: PokemonCardEntity)

    @Query("DELETE FROM pokemon_cards")
    suspend fun clearAll()
}
