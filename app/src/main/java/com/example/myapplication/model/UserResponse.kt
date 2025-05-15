// UserResponse.kt
package com.example.myapplication.model

data class UserResponse(
    val id: Int,
    val username: String,
    val role: String,
    val studentId: Int?       // новое поле
)
