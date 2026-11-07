package com.odogwudev.liveinterview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.odogwudev.liveinterview.data.domain.rrom.CharacterRicks
import com.odogwudev.liveinterview.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {
    val characters = repository.character().cachedIn(viewModelScope)

    val favoriteIDS: StateFlow<Set<Int>> = repository.favoriteIds.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptySet()
    )
    val favorites: StateFlow<List<CharacterRicks>> = repository.favorites.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        emptyList()
    )

    private val _selected = MutableStateFlow<Map<Int, CharacterRicks>>(emptyMap())
    val selected: StateFlow<Map<Int, CharacterRicks>> = _selected.asStateFlow()

    fun toggleSelect(characterRicks: CharacterRicks) = _selected.update {
        if (characterRicks.id in it) it - characterRicks.id else it + (characterRicks.id to characterRicks)
    }

    fun clearSelection() {
        _selected.value = emptyMap()
    }

    fun addSelectedToFavorites(){
        val items = _selected.value.values.toList()
        if(items.isEmpty()) return

        viewModelScope.launch {
            repository.addFavorites(items)
            _selected.value = emptyMap()
        }
    }

    fun toggleFavorite(characterRicks: CharacterRicks) = viewModelScope.launch{
        if (characterRicks.id in favoriteIDS.value) repository.removeFavorite(characterRicks.id)
        else repository.addFavorites(listOf(characterRicks))
    }


}