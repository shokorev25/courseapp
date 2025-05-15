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
import com.example.myapplication.databinding.ActivityStudentBinding
import com.example.myapplication.view.adapter.GradeAdapter
import com.example.myapplication.viewmodel.GradeViewModel

class StudentActivity : AppCompatActivity() {

    private lateinit var b: ActivityStudentBinding
    private lateinit var gradeVm: GradeViewModel
    private lateinit var adapter: GradeAdapter

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbarStudent)

        val studentId = intent.getIntExtra("STUDENT_ID", -1)
        if (studentId == -1) {
            Toast.makeText(this,"No STUDENT_ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        gradeVm = ViewModelProvider(this).get(GradeViewModel::class.java)
        adapter = GradeAdapter(emptyList())

        b.rvGrades.layoutManager = LinearLayoutManager(this)
        b.rvGrades.adapter = adapter

        gradeVm.getGrades(studentId).observe(this) { grades ->
            if (grades.isEmpty()) {
                Toast.makeText(this,"Оценок пока нет", Toast.LENGTH_SHORT).show()
            }
            adapter.updateData(grades)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_logout) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish(); true
        } else super.onOptionsItemSelected(item)
    }
}
