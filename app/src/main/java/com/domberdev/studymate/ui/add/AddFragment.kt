package com.domberdev.studymate.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.domberdev.studymate.R
import com.domberdev.studymate.databinding.FragmentAddBinding
import com.domberdev.studymate.domain.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * Fragmento del Add una Tarea de la aplicación
 */
@AndroidEntryPoint
class AddFragment : Fragment() {

    private val addViewModel: AddViewModel by viewModels<AddViewModel>()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val selectedDate = MutableLiveData<String?>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        selectedDate.observe(viewLifecycleOwner) { date ->
            binding.btnDate.text = parseDate(date) ?: getString(R.string.date_message)
        }

        // DatePicker
        binding.btnDate.setOnClickListener {
            initDatePicker()
        }

        binding.btnSave.setOnClickListener {
            if (checkForm()) {
                saveTask(getTask())
                findNavController().popBackStack()
            }
        }
    }

    private fun checkForm(): Boolean {
        binding.etTitle.isErrorEnabled = false
        binding.etSubject.isErrorEnabled = false
        binding.btnDate.error = null
        var isValid = true
        if (binding.etTitle.editText?.text?.isEmpty() == true) {
            binding.etTitle.error = "La tarea debe de tener un título"
            isValid = false
        }
        if (binding.etSubject.editText?.text?.isEmpty() == true) {
            binding.etSubject.error = "La tarea debe de tener una materia"
            isValid = false
        }
        if (selectedDate.value == null) {
            binding.btnDate.error = "La tarea debe de tener una fecha de entrega"
            isValid = false
        }
        if (!binding.chipIndividual.isChecked && !binding.chipTeam.isChecked) {
            Toast.makeText(context, "Debes de elegir un tipo de tarea", Toast.LENGTH_SHORT).show()
            isValid = false
        }
        return isValid
    }

    private fun getTask(): Task {
        val title = binding.etTitle.editText?.text.toString().trim()
        val subject = binding.etSubject.editText?.text.toString().trim()
        val color = getRandomColor()
        val deadline = selectedDate.value!!
        val type = when (binding.chipGroup.checkedChipId) {
            R.id.chipIndividual -> 0
            R.id.chipTeam-> 1
            else -> 0
        }
        val description = binding.etDescription.editText?.text.toString().trim()

        return Task(0, title, subject, color, description, deadline, type, false)
    }

    // Metodo para guardar una tarea
    private fun saveTask(task: Task) {
        addViewModel.saveData(task)
    }

    private fun getRandomColor(): Int {
        val colorList = listOf(
            0xFFC9F44F,
            0xFF4FC9F4,
            0xFFFFD54F,
            0xFF9B4FF4,
            0xFFF44FA9,
            0xFF6EF44F,
            0xFFF4E24F,
            0xFFF44F4F,
            0xFF4FF4DD,
            0xFFA64FF4
        )

        val randomColor = (colorList.indices).random()
        return colorList[randomColor].toInt()
    }

    // Función para la logica del DatePicker y nos retorna la fecha
    private fun initDatePicker() {
        // Construimos el Date Picker
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha limite")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTextInputFormat(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()))
                .build()

        // Mostramos el DatePicker
        datePicker.show(parentFragmentManager, "DatePicker")

        datePicker.addOnPositiveButtonClickListener { date ->
            selectedDate.postValue(getDate(date))
        }
    }

    // Función para convertir un Long (milisegundos) en una Fecha
    private fun getDate(date: Long): String {
        val d = Date(date)
        val format = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        return format.format(d)
    }

    private fun parseDate(date: String?): String? {
        if (date == null) {
            return null
        }
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