package com.bestemorgul.notesapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bestemorgul.notesapp.NotesApplication
import com.bestemorgul.notesapp.R
import com.bestemorgul.notesapp.databinding.FragmentNotesDetailBinding
import com.bestemorgul.notesapp.model.Notes
import com.bestemorgul.notesapp.ui.viewmodel.NotesViewModel
import com.bestemorgul.notesapp.ui.viewmodel.NotesViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotesDetailFragment : Fragment() {

    private val navigationArgs: NotesDetailFragmentArgs by navArgs()

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory(
            (activity?.application as NotesApplication).database.notesDao()
        )
    }

    private lateinit var notes: Notes

    private var _binding: FragmentNotesDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (id > 0) {

        val id = navigationArgs.id
        viewModel.retrieveNotes(id).observe(this.viewLifecycleOwner) { selectedNotes ->
            notes = selectedNotes
            bindNotes()

        }
            binding.deleteNotes.visibility = View.VISIBLE
            binding.deleteNotes.setOnClickListener {
                deleteNotes()
            }
    }
    }

        private fun bindNotes() {
            binding.apply {
                notesTitle.text = notes.noteTitle
                notesBody.text = notes.noteBody
                deleteNotes.setOnClickListener { showConfirmationDialog() }

                editFloatingActionButton.setOnClickListener {
                    val action = NotesDetailFragmentDirections
                        .actionNotesDetailFragmentToAddNotesFragment(notes.id)
                    findNavController().navigate(action)
                }
            }
        }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteNotes()
            }
            .show()
    }

    private fun deleteNotes() {
        viewModel.deleteNotes(notes)
        findNavController().navigateUp()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

