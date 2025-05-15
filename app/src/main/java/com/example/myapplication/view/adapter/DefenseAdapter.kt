// DefenseAdapter.kt
package com.example.myapplication.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemDefenseBinding
import com.example.myapplication.model.Defense

class DefenseAdapter(
    private var list: List<Defense>,
    private val onEdit: (Defense) -> Unit,
    private val onDelete: (Defense) -> Unit
) : RecyclerView.Adapter<DefenseAdapter.VH>() {

    inner class VH(val b: ItemDefenseBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(d: Defense) {
            b.tvDate.text       = d.date
            b.tvTime.text       = d.time
            b.tvTheme.text      = d.theme
            b.tvCommission.text = d.commission
            b.btnEdit.setOnClickListener   { onEdit(d) }
            b.btnDelete.setOnClickListener { onDelete(d) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, i: Int) =
        VH(ItemDefenseBinding.inflate(LayoutInflater.from(p.context), p, false))

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(list[pos])
    override fun getItemCount() = list.size
    fun updateData(new: List<Defense>) { list = new; notifyDataSetChanged() }
}
