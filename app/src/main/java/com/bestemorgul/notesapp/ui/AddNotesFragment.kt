package com.bestemorgul.notesapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bestemorgul.notesapp.NotesApplication
import com.bestemorgul.notesapp.R
import com.bestemorgul.notesapp.databinding.FragmentAddNotesBinding
import com.bestemorgul.notesapp.model.Notes
import com.bestemorgul.notesapp.ui.viewmodel.NotesViewModel
import com.bestemorgul.notesapp.ui.viewmodel.NotesViewModelFactory

class AddNotesFragment : Fragment() {
    private val navigationArgs: AddNotesFragmentArgs by navArgs()

    private val viewModel: NotesViewModel by activityViewModels {
        NotesViewModelFactory(
            (activity?.application as NotesApplication).database.notesDao()
        )
    }

    private lateinit var notes: Notes

    private var _binding: FragmentAddNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun updateNotes() {
        if (isEntryValid()) {
            viewModel.updateNotes(
                id = id,
                noteTitle = binding.notesTitle.text.toString(),
                noteBody = binding.notesBody.text.toString()
            )
            findNavController().navigate(
                R.id.action_addNotesFragment_to_notesListFragment
            )
        }
    }

    private fun bindNotes(notes: Notes) {

        binding.apply{
            notesTitle.setText(notes.noteTitle, TextView.BufferType.SPANNABLE)
            notesBody.setText(notes.noteBody, TextView.BufferType.SPANNABLE)

            actionSave.setOnClickListener {
                updateNotes()
            }
        }

    }

    private fun isEntryValid(): Boolean {
        return viewModel.isValidEntry(
            binding.notesBody.text.toString(),
            binding.notesTitle.text.toString()
        )
    }

    private fun addNotes() {
        if (isEntryValid()) {
            viewModel.addNotes(
                binding.notesTitle.text.toString(),
                binding.notesBody.text.toString(),
            )
            findNavController().navigate(
                R.id.action_addNotesFragment_to_notesListFragment
            )
        }
    }

    private fun deleteNotes(notes: Notes) {
        viewModel.deleteNotes(notes)
        findNavController().navigate(
            R.id.action_addNotesFragment_to_notesListFragment
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        if (id > 0) {
            viewModel.retrieveNotes(id).observe(this.viewLifecycleOwner) { selectedNotes ->
                notes= selectedNotes
                bindNotes(notes)
            }

            binding.actionDelete.visibility = View.VISIBLE
            binding.actionDelete.setOnClickListener {
                deleteNotes(notes)
            }
        } else {
            binding.actionSave.setOnClickListener {
                addNotes()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}