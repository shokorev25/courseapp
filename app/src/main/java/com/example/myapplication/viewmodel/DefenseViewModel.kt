// DefenseViewModel.kt
package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.model.Defense
import com.example.myapplication.repository.DefenseRepository
import kotlinx.coroutines.launch

class DefenseViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = DefenseRepository()

    private val _defenses = MutableLiveData<List<Defense>>()
    val defenses: LiveData<List<Defense>> = _defenses

    init { loadDefenses() }

    fun loadDefenses() = viewModelScope.launch {
        _defenses.value = repo.getAll()
    }

    fun addDefense(defense: Defense) = viewModelScope.launch {
        if (repo.create(defense) != null) loadDefenses()
    }

    fun updateDefense(defense: Defense) = viewModelScope.launch {
        if (repo.update(defense) != null) loadDefenses()
    }

    fun deleteDefense(id: Int) = liveData<Boolean> {
        val success = repo.delete(id)
        emit(success)
        if (success) loadDefenses()
    }
}
