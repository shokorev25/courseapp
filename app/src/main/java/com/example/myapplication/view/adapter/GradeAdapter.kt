// GradeAdapter.kt
package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGradeBinding
import com.example.myapplication.model.Grade

class GradeAdapter(
    private var list: List<Grade>
) : RecyclerView.Adapter<GradeAdapter.VH>() {

    inner class VH(val b: ItemGradeBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(g: Grade) {
            b.tvDefenseId.text = "Defense: ${g.defenseId}"
            b.tvGrade.text     = "Grade: ${g.grade}"
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, i: Int) =
        VH(ItemGradeBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(list[pos])
    override fun getItemCount() = list.size
    fun updateData(new: List<Grade>) { list = new; notifyDataSetChanged() }
}
