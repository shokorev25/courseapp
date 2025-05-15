// DefenseRepository.kt
package com.example.myapplication.repository

import com.example.myapplication.model.Defense
import com.example.myapplication.network.RetrofitInstance

class DefenseRepository {
    suspend fun getAll(): List<Defense> =
        RetrofitInstance.apiService.getDefenses().body() ?: emptyList()

    suspend fun create(defense: Defense): Defense? =
        RetrofitInstance.apiService.addDefense(defense).body()

    suspend fun update(defense: Defense): Defense? =
        RetrofitInstance.apiService.updateDefense(defense.id, defense).body()

    suspend fun delete(id: Int): Boolean =
        RetrofitInstance.apiService.deleteDefense(id).isSuccessful
}
