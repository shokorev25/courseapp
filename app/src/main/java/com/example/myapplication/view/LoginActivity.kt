// LoginActivity.kt
package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.model.User
import com.example.myapplication.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var b: ActivityLoginBinding
    private lateinit var vm: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        vm = ViewModelProvider(this).get(UserViewModel::class.java)

        b.btnLogin.setOnClickListener {
            val username = b.etUsername.text.toString().trim()
            val password = b.etPassword.text.toString().trim()
            if (username.isEmpty() || password.isEmpty()) {
                b.tvError.text = "Enter login/password"
                return@setOnClickListener
            }

            // Отправляем логин и пароль на сервер
            vm.login(User(username, password)).observe(this) { resp ->
                if (resp != null) {
                    when (resp.role) {
                        "admin" -> {
                            startActivity(Intent(this, AdminActivity::class.java))
                        }
                        "commission" -> {
                            Intent(this, CommissionActivity::class.java).also {
                                it.putExtra("USERNAME", username)
                                startActivity(it)
                            }
                        }
                        "student" -> {
                            // Используем именно resp.studentId, а не resp.id
                            val studentId = resp.studentId ?: -1
                            Intent(this, StudentActivity::class.java).also {
                                it.putExtra("STUDENT_ID", studentId)
                                startActivity(it)
                            }
                        }
                        else -> {
                            // На всякий случай — если роль неизвестна
                            b.tvError.text = "Unknown role: ${resp.role}"
                            return@observe
                        }
                    }
                    finish()
                } else {
                    b.tvError.text = "Invalid credentials"
                }
            }
        }
    }
}
