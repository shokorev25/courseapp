package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.model.Grade
import com.example.myapplication.model.Student
import com.example.myapplication.repository.GradeRepository
import kotlinx.coroutines.launch

class GradeViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = GradeRepository()

    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> = _students

    /** Загружает студентов для защиты (используется в CommissionActivity) */
    fun loadStudents(defenseId: Int) = viewModelScope.launch {
        _students.value = repo.getStudents(defenseId)
    }

    /** Отправка или обновление оценки (используется в CommissionActivity) */
    fun sendGrade(grade: Grade): LiveData<Grade?> = liveData {
        val g = repo.send(grade)
        emit(g)
    }

    /** Получение оценок конкретного студента */
    fun getGrades(studentId: Int): LiveData<List<Grade>> = liveData {
        val list = repo.getGrades(studentId)
        emit(list)
    }

    /** Получение всех оценок (для AdminActivity) */
    fun getAllGrades(): LiveData<List<Grade>> = liveData {
        val list = repo.getAllGrades()
        emit(list)
    }
}
