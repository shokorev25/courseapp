package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityAdminBinding
import com.example.myapplication.model.Defense
import com.example.myapplication.view.adapter.DefenseAdapter
import com.example.myapplication.viewmodel.DefenseViewModel
import com.example.myapplication.viewmodel.GradeViewModel
import java.io.File

class AdminActivity : AppCompatActivity() {
    private lateinit var b: ActivityAdminBinding
    private lateinit var vm: DefenseViewModel
    private lateinit var gradeVm: GradeViewModel
    private lateinit var adapter: DefenseAdapter

    override fun onCreate(s: Bundle?) {
        super.onCreate(s)
        b = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbarAdmin)

        vm = ViewModelProvider(this).get(DefenseViewModel::class.java)
        gradeVm = ViewModelProvider(this).get(GradeViewModel::class.java)

        adapter = DefenseAdapter(emptyList(),
            onEdit = { openForm(it) },
            onDelete = { d ->
                vm.deleteDefense(d.id).observe(this) { ok ->
                    if (ok) vm.loadDefenses()
                    else Toast.makeText(this,"Delete failed",Toast.LENGTH_SHORT).show()
                }
            })

        b.rvDefenses.layoutManager = LinearLayoutManager(this)
        b.rvDefenses.adapter = adapter
        b.fabAddDefense.setOnClickListener { openForm(null) }
        vm.defenses.observe(this) { adapter.updateData(it) }
    }

    private fun openForm(d: Defense?) {
        // Intent -> DefenseFormActivity
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin, menu)
        menuInflater.inflate(R.menu.menu_logout, menu)
        val sv = (menu?.findItem(R.id.action_search)?.actionView as? SearchView)
        sv?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(q: String): Boolean {
                adapter.updateData(vm.defenses.value?.filter {
                    it.theme.contains(q,true) ||
                            it.date.contains(q,true) ||
                            it.commission.contains(q,true)
                } ?: emptyList())
                return true
            }
            override fun onQueryTextSubmit(q: String)=false
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_export -> {
            val defs = vm.defenses.value ?: emptyList()
            gradeVm.getAllGrades().observe(this) { grades ->
                exportCsv(defs, grades)
            }
            true
        }
        R.id.action_logout -> {
            logout()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun exportCsv(defs: List<Defense>, grades: List<com.example.myapplication.model.Grade>) {
        val header = "DefenseId,Date,Theme,StudentId,Grade\n"
        val sb = StringBuilder(header)
        defs.forEach { d ->
            grades.filter { it.defenseId==d.id }
                .forEach { g -> sb.append("${d.id},${d.date},${d.theme},${g.studentId},${g.grade}\n") }
        }
        val f = File(getExternalFilesDir(null),"grades.csv")
        f.writeText(sb.toString())
        Toast.makeText(this,"Exported to ${f.path}",Toast.LENGTH_LONG).show()
    }

    private fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
