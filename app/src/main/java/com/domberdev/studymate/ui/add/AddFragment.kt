package com.domberdev.studymate.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.domberdev.studymate.databinding.FragmentAddBinding
import com.domberdev.studymate.domain.model.Task
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragmento del Add una Tarea de la aplicación
 */
@AndroidEntryPoint
class AddFragment : Fragment() {

    private val addViewModel: AddViewModel by viewModels<AddViewModel>()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Dato, la Task te pedira una id, pon de valor de esta 0 y el estatus, es un boolean, por defecto ponle false
        // Ejemplo de como llamar la funcion
        // val tarea = Task(0, ..., false) ... representa los demas elementos de la data class Task
        // saveTask(tarea)
        initUI()
    }

    private fun initUI() {
        // Desde aqui puedes comezar con tu codigo
    }

    // Metodo para guardar una tarea
    private fun saveTask(task: Task) {
        addViewModel.saveData(task)
    }
}