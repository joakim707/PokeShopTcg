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

    // Pour la recherche et le tri
    private val searchQuery = MutableLiveData("")
    private val selectedType = MutableLiveData("")
    val filteredCards = MediatorLiveData<List<PokemonCardEntity>>()

    init {
        val dao = PokemonDatabase.getDatabase(application).pokemonCardDao()
        repo = PokemonRepository(dao)
        cards = repo.allCards
        favorites = repo.favoriteCards

        // MediatorLiveData observe cards, searchQuery et selectedType
        filteredCards.addSource(cards) { updateFilteredCards() }
        filteredCards.addSource(searchQuery) { updateFilteredCards() }
        filteredCards.addSource(selectedType) { updateFilteredCards() }

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

    fun setSearchQuery(query: String) {
        searchQuery.postValue(query)
    }

    fun setSelectedType(type: String) {
        selectedType.postValue(type)
    }

    private fun updateFilteredCards() {
        val query = searchQuery.value.orEmpty()
        val typeFilter = selectedType.value.orEmpty()
        val all = cards.value.orEmpty()

        val filtered = all.filter { card ->
            val matchesName = card.name?.contains(query, ignoreCase = true) == true
            val matchesType = typeFilter.isEmpty() || typeFilter == "All" || card.type?.contains(typeFilter, ignoreCase = true) == true
            matchesName && matchesType
        }
        filteredCards.postValue(filtered)
    }
}
