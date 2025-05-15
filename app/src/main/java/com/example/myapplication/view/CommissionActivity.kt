package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityCommissionBinding
import com.example.myapplication.model.Grade
import com.example.myapplication.viewmodel.DefenseViewModel
import com.example.myapplication.viewmodel.GradeViewModel
import com.example.myapplication.view.adapter.StudentGradeAdapter

class CommissionActivity : AppCompatActivity() {

    private lateinit var b: ActivityCommissionBinding
    private lateinit var gradeVm: GradeViewModel
    private lateinit var defenseVm: DefenseViewModel
    private lateinit var adapter: StudentGradeAdapter
    private var currentDefenseId: Int = -1

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityCommissionBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbarCommission)

        val username = intent.getStringExtra("USERNAME") ?: ""
        if (username.isEmpty()) {
            Toast.makeText(this,"Не передан USERNAME",Toast.LENGTH_SHORT).show()
            finish(); return
        }

        gradeVm   = ViewModelProvider(this).get(GradeViewModel::class.java)
        defenseVm = ViewModelProvider(this).get(DefenseViewModel::class.java)

        adapter = StudentGradeAdapter(emptyList()) { student, gradeValue ->
            gradeVm.sendGrade(Grade(currentDefenseId, student.id, gradeValue))
                .observe(this) { result ->
                    Toast.makeText(this,
                        if (result!=null) "Оценка отправлена" else "Ошибка при отправке",
                        Toast.LENGTH_SHORT).show()
                }
        }
        b.rvStudents.layoutManager = LinearLayoutManager(this)
        b.rvStudents.adapter = adapter

        defenseVm.defenses.observe(this) { defenses ->
            val mine = defenses.find { d ->
                d.commission.split(",").map{ it.trim() }.contains(username)
            }
            if (mine != null) {
                currentDefenseId = mine.id
                title = "Защита: ${mine.theme}"
                gradeVm.loadStudents(mine.id)
            } else {
                Toast.makeText(this,"Вам не назначена ни одна защита",Toast.LENGTH_LONG).show()
            }
        }

        gradeVm.students.observe(this) { students ->
            adapter.updateData(students)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId==R.id.action_logout) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish(); true
        } else super.onOptionsItemSelected(item)
    }
}
