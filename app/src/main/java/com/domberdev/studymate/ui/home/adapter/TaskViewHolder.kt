package com.domberdev.studymate.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.domberdev.studymate.R
import com.domberdev.studymate.databinding.ItemTaskBinding
import com.domberdev.studymate.domain.model.Task

class TaskViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemTaskBinding.bind(view)

    fun render(task: Task, onClickListener: (Long) -> Unit) {
        binding.cvTask.setCardBackgroundColor(task.color)
        binding.tvName.text = task.titulo
        binding.tvSubject.text = task.materia
        binding.tvDeadline.text = task.deadline

        when (task.tipo) {
            0 -> {
                binding.tvType.text = binding.tvType.context.getString(R.string.individual)
                binding.cvTypeLayout.setCardBackgroundColor((0xFFF4BF4F).toInt())
            }

            1 -> {
                binding.tvType.text = binding.tvType.context.getString(R.string.team)
                binding.cvTypeLayout.setCardBackgroundColor((0xFFC9F44F).toInt())
            }
        }

        itemView.setOnClickListener { onClickListener(task.id) }
    }
}