// ApiService.kt
package com.example.myapplication.network

import com.example.myapplication.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    suspend fun login(@Body user: User): Response<UserResponse>

    @GET("defenses")
    suspend fun getDefenses(): Response<List<Defense>>

    @POST("defenses")
    suspend fun addDefense(@Body defense: Defense): Response<Defense>

    @PUT("defenses/{id}")
    suspend fun updateDefense(@Path("id") id: Int, @Body defense: Defense): Response<Defense>

    @DELETE("defenses/{id}")
    suspend fun deleteDefense(@Path("id") id: Int): Response<Void>

    @GET("defenses/{id}/students")
    suspend fun getStudents(@Path("id") defenseId: Int): Response<List<Student>>

    @POST("grades")
    suspend fun sendGrade(@Body grade: Grade): Response<Grade>

    @GET("students/{id}/grades")
    suspend fun getGrades(@Path("id") studentId: Int): Response<List<Grade>>

    @GET("grades")
    suspend fun getAllGrades(): Response<List<Grade>>
}
