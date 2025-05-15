// StudentGradeAdapter.kt
package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemStudentGradeBinding
import com.example.myapplication.model.Student

class StudentGradeAdapter(
    private var list: List<Student>,
    private val onGrade: (Student, Int) -> Unit
) : RecyclerView.Adapter<StudentGradeAdapter.VH>() {

    inner class VH(val b: ItemStudentGradeBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(s: Student) {
            b.tvName.text = s.name
            b.btnSubmit.setOnClickListener {
                val g = b.etGrade.text.toString().toIntOrNull() ?: return@setOnClickListener
                onGrade(s, g)
            }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, i: Int) =
        VH(ItemStudentGradeBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(list[pos])
    override fun getItemCount() = list.size
    fun updateData(new: List<Student>) { list = new; notifyDataSetChanged() }
}
