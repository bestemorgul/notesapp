package com.bestemorgul.notesapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bestemorgul.notesapp.NotesApplication
import com.bestemorgul.notesapp.R
import com.bestemorgul.notesapp.databinding.FragmentNotesListBinding
import com.bestemorgul.notesapp.ui.adapter.NotesListAdapter
import com.bestemorgul.notesapp.ui.viewmodel.NotesViewModel
import com.bestemorgul.notesapp.ui.viewmodel.NotesViewModelFactory

class NotesListFragment : Fragment() {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory(
            (activity?.application as NotesApplication).database.notesDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NotesListAdapter { notes ->
            val action = NotesListFragmentDirections
                .actionNotesListFragmentToNotesDetailFragment(notes.id)
            findNavController().navigate(action)
        }

        viewModel.allNotes.observe(this.viewLifecycleOwner) {notes ->
            notes.let {
                adapter.submitList(it)
            }
        }

        binding.apply {
            recyclerView.adapter = adapter
            floatingActionButton.setOnClickListener {
                findNavController().navigate(
                    R.id.action_notesListFragment_to_addNotesFragment
                )
            }
        }
    }
}