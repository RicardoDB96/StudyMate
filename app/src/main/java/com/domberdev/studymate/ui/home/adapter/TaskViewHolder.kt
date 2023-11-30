package com.domberdev.studymate.ui.home.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.domberdev.studymate.R
import com.domberdev.studymate.databinding.ItemTaskBinding
import com.domberdev.studymate.domain.model.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemTaskBinding.bind(view)

    fun render(task: Task, onClickListener: (Long) -> Unit) {
        binding.cvTask.setCardBackgroundColor(task.color)
        binding.tvName.text = task.titulo
        binding.tvSubject.text = task.materia
        binding.tvDeadline.text = parseDate(task.deadline)

        when (task.tipo) {
            0 -> {
                binding.tvType.text = binding.tvType.context.getString(R.string.individual)
                binding.cvTypeLayout.setCardBackgroundColor(binding.tvType.context.getColor(R.color.orange))
            }

            1 -> {
                binding.tvType.text = binding.tvType.context.getString(R.string.team)
                binding.cvTypeLayout.setCardBackgroundColor(binding.tvType.context.getColor(R.color.green))
            }
        }

        itemView.setOnClickListener { onClickListener(task.id) }
    }

    private fun parseDate(date: String): String {
        val d = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(date)
        val day =
            SimpleDateFormat("d 'de' MMMM, yyyy", Locale.forLanguageTag("es-MX")).format(d!!)
        return day.capitalizeWords()
    }

    private fun String.capitalizeWords(): String = split(" ").joinToString(" ") {
        if (it.equals("de", ignoreCase = true)) {
            it // Si la palabra es "de", no la capitalizamos
        } else {
            it.replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString()
            }
        }
    }
}