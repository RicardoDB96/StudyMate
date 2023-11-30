package com.domberdev.studymate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

        taskViewModel.incompleteTaskList.observe(viewLifecycleOwner) { taskList ->
            if (taskList.isEmpty()) {
                binding.tvNoTask.isVisible = true
                binding.rvIncompleteTask.isVisible = false
            } else {
                binding.tvNoTask.isVisible = false
                binding.rvIncompleteTask.isVisible = true
                initIncompleteRecyclerView(taskList)
            }
        }

        taskViewModel.completeTaskList.observe(viewLifecycleOwner) { taskList ->
            if (taskList.isEmpty()) {
                binding.tvComplete.isVisible = false
                binding.rvCompleteTask.isVisible = false
            } else {
                binding.tvComplete.isVisible = true
                binding.rvCompleteTask.isVisible = true
                initCompleteRecyclerView(taskList)
            }
        }

        taskViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progress.isVisible = it
        }

        // FAB acción
        binding.fabAddTask.setOnClickListener { findNavController().navigate(R.id.action_home_to_add) }
    }

    private fun initIncompleteRecyclerView(taskList: List<Task>) {
        binding.rvIncompleteTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvIncompleteTask.adapter = TaskAdapter(taskList) { id -> navigateToTaskInfo(id) }
    }

    private fun initCompleteRecyclerView(taskList: List<Task>) {
        binding.rvCompleteTask.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCompleteTask.adapter = TaskAdapter(taskList) { id -> navigateToTaskInfo(id) }
    }

    private fun navigateToTaskInfo(id: Long) {
        val bundle = Bundle()
        bundle.putLong("ID", id)
        findNavController().navigate(R.id.action_home_to_detail, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}