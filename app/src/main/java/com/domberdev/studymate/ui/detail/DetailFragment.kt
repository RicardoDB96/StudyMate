package com.domberdev.studymate.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.domberdev.studymate.R
import com.domberdev.studymate.databinding.FragmentDetailBinding
import com.domberdev.studymate.domain.model.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Fragmento del Detail de la Tarea de la aplicación
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel>()

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getLong("ID")!!
        detailViewModel.onCreate(id)
        detailViewModel.task.observe(viewLifecycleOwner) { task ->
            initUI(task)
        }
    }

    // Esta es la función que debes de editar
    private fun initUI(task: Task) {
        // Cambiar color del background segun el color de la materia
        binding.cardContainer.setCardBackgroundColor(task.color)
        activity?.window?.navigationBarColor = task.color

        // Poner la información en los TextView
        binding.tvTitleInfo.text = task.titulo
        binding.tvSubjectInfo.text = task.materia
        binding.tvDeadlineInfo.text = parseDate(task.deadline)
        if (task.descripcion != "") {
            binding.tvDescriptionTitle.isVisible = true
            binding.tvDescriptionInfo.isVisible = true
            binding.tvDescriptionInfo.text = task.descripcion
        }

        when (task.tipo) {
            0 -> {
                binding.tvTypeInfo.text = binding.tvTypeInfo.context.getString(R.string.individual)
                binding.cvTypeLayoutInfo.setCardBackgroundColor(binding.tvTypeInfo.context.getColor(R.color.orange))
            }

            1 -> {
                binding.tvTypeInfo.text = binding.tvTypeInfo.context.getString(R.string.team)
                binding.cvTypeLayoutInfo.setCardBackgroundColor(binding.tvTypeInfo.context.getColor(R.color.green))
            }
        }

        binding.btnDeleteTask.setOnClickListener {
            deleteDialog(task)
        }

        binding.btnTaskComplete.setOnClickListener {
            updateCompleteStatus(!task.estatus, task.id)
        }
    }

    // Metodo para actualizar el estatus de una tarea (completada/no completada)
    private fun updateCompleteStatus(status: Boolean, id: Long) {
        detailViewModel.completeTask(status, id)
        findNavController().popBackStack()
    }

    // Metodo para borrar una tarea, tiene que accionarse al hacer click en el boton de borrar tarea
    private fun deleteDialog(task: Task) {
        val dialog = MaterialAlertDialogBuilder(requireContext())
        dialog.setTitle(R.string.delete_task)
            .setMessage(R.string.delete_message)
            .setPositiveButton(R.string.yes) { d, _ ->
                detailViewModel.deleteTask(task)
                Toast.makeText(requireContext(), getString(R.string.deleting), Toast.LENGTH_SHORT).show()
                d.dismiss()
                findNavController().popBackStack()
            }
            .setNegativeButton(R.string.no) { d, _ ->
                d.cancel()
            }.show()
    }

    private fun parseDate(date: String): String {
        val d = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).parse(date)
        val day =
            SimpleDateFormat("EEEE d 'de' MMMM 'de' yyyy", Locale.forLanguageTag("es-MX")).format(d!!)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}