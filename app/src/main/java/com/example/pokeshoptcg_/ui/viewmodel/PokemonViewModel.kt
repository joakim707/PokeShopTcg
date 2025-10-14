package com.example.pokeshoptcg_.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.pokeshoptcg_.data.db.PokemonCardEntity
import com.example.pokeshoptcg_.data.db.PokemonDatabase
import com.example.pokeshoptcg_.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(application: Application) : AndroidViewModel(application) {

    private val repo: PokemonRepository
    val cards: LiveData<List<PokemonCardEntity>>
    val favorites: LiveData<List<PokemonCardEntity>>
    val isLoading = MutableLiveData<Boolean>()

    init {
        val dao = PokemonDatabase.getDatabase(application).pokemonCardDao()
        repo = PokemonRepository(dao)
        cards = repo.allCards
        favorites = repo.favoriteCards
        refreshData()
    }

    fun refreshData() = viewModelScope.launch {
        isLoading.postValue(true)
        repo.refreshCards()
        isLoading.postValue(false)
    }

    fun toggleFavorite(card: PokemonCardEntity) = viewModelScope.launch {
        val updated = card.copy(isFavorite = !card.isFavorite)
        repo.updateCard(updated)
    }
}
