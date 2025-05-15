// UserRepository.kt
package com.example.myapplication.repository

import com.example.myapplication.model.User
import com.example.myapplication.model.UserResponse
import com.example.myapplication.network.RetrofitInstance

class UserRepository {
    suspend fun login(user: User): UserResponse? =
        RetrofitInstance.apiService.login(user).body()
}
