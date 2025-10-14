package com.example.pokeshoptcg_.viewmodel

import androidx.lifecycle.*
import com.example.pokeshoptcg_.data.model.PokemonCard
import com.example.pokeshoptcg_.data.remote.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.pokeshoptcg_.util.Result

class HomeViewModel : ViewModel() {

    private val _cards = MutableLiveData<Result<List<PokemonCard>>>()
    val cards: LiveData<Result<List<PokemonCard>>> = _cards

    private val api = RetrofitInstance.api

    fun fetchCardsWithRetry(retries: Int = 3) {
        viewModelScope.launch(Dispatchers.IO) {
            repeat(retries) { attempt ->
                try {
                    val response = api.getCards(20).execute()
                    if (response.isSuccessful) {
                        val cards = response.body()?.data ?: emptyList()
                        _cards.postValue(Result.Success(cards))
                        return@launch
                    }
                } catch (e: Exception) {
                    // Timeout ou autre erreur : on réessaie
                }
                delay(1000) // 1s entre les tentatives
            }
            _cards.postValue(Result.Error("Impossible de récupérer les cartes après plusieurs tentatives"))
        }
    }
}
