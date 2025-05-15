// UserViewModel.kt
package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.model.User
import com.example.myapplication.model.UserResponse
import com.example.myapplication.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = UserRepository()

    fun login(user: User) = liveData<UserResponse?> {
        val res = repo.login(user)
        emit(res)
    }
}
