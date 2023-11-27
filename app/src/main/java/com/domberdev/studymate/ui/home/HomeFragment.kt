package com.domberdev.studymate.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.domberdev.studymate.R
import com.domberdev.studymate.databinding.FragmentHomeBinding
import com.domberdev.studymate.domain.model.Task
import com.domberdev.studymate.ui.home.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragmento del Home de la aplicación
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val taskViewModel: HomeViewModel by viewModels<HomeViewModel>()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel.onCreate()

        taskViewModel.taskList.observe(viewLifecycleOwner) { taskList ->
            if (taskList.isEmpty()) {
                for (task in testList) {
                    taskViewModel.saveData(task)
                }
            }
            initRecyclerView(taskList)
        }

        // FAB acción
        binding.fabAddTask.setOnClickListener { findNavController().navigate(R.id.action_home_to_add) }
    }

    private fun initRecyclerView(taskList: List<Task>) {
        binding.rvTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTask.adapter = TaskAdapter(taskList) { id -> navigateToTaskInfo(id) }
    }

    private fun navigateToTaskInfo(id: Long) {
        val bundle = Bundle()
        bundle.putLong("ID", id)
        findNavController().navigate(R.id.action_home_to_detail, bundle)
    }

    // No se debe de tomar el valor de esta, se borrara al final
    private val testList =
        listOf(
            Task(
                0,
                "Evidencia 1",
                "Modelacion",
                Color.MAGENTA,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. In aliquam sem fringilla ut morbi tincidunt augue interdum velit. Orci porta non pulvinar neque laoreet suspendisse interdum consectetur. Accumsan in nisl nisi scelerisque eu ultrices. Vel fringilla est ullamcorper eget nulla. Sodales ut eu sem integer vitae justo. In dictum non consectetur a erat nam at lectus urna. At risus viverra adipiscing at in tellus integer feugiat scelerisque. Diam phasellus vestibulum lorem sed. Senectus et netus et malesuada. Habitasse platea dictumst quisque sagittis purus. Pulvinar elementum integer enim neque volutpat ac tincidunt vitae.",
                "29/Nov",
                0,
                false
            ),
            Task(
                1,
                "Evidencia 2",
                "Android",
                Color.GREEN,
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. In aliquam sem fringilla ut morbi tincidunt augue interdum velit. Orci porta non pulvinar neque laoreet suspendisse interdum consectetur. Accumsan in nisl nisi scelerisque eu ultrices. Vel fringilla est ullamcorper eget nulla. Sodales ut eu sem integer vitae justo. In dictum non consectetur a erat nam at lectus urna. At risus viverra adipiscing at in tellus integer feugiat scelerisque. Diam phasellus vestibulum lorem sed. Senectus et netus et malesuada. Habitasse platea dictumst quisque sagittis purus. Pulvinar elementum integer enim neque volutpat ac tincidunt vitae.",
                "30/Nov",
                1,
                false
            ),
        )
}