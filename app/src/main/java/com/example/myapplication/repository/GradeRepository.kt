package com.example.myapplication.repository

import com.example.myapplication.model.Grade
import com.example.myapplication.model.Student
import com.example.myapplication.network.RetrofitInstance

class GradeRepository {
    /** Отправка или обновление оценки */
    suspend fun send(grade: Grade): Grade? =
        RetrofitInstance.apiService.sendGrade(grade).body()

    /** Список студентов для защиты */
    suspend fun getStudents(defenseId: Int): List<Student> =
        RetrofitInstance.apiService.getStudents(defenseId).body() ?: emptyList()

    /** Список оценок для студента */
    suspend fun getGrades(studentId: Int): List<Grade> =
        RetrofitInstance.apiService.getGrades(studentId).body() ?: emptyList()

    /** Все оценки (для экспорта) */
    suspend fun getAllGrades(): List<Grade> =
        RetrofitInstance.apiService.getAllGrades().body() ?: emptyList()
}
