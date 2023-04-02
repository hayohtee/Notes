package com.hayohtee.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hayohtee.notes.R
import com.hayohtee.notes.data.model.Note
import com.hayohtee.notes.databinding.FragmentAddNoteBinding
import com.hayohtee.notes.ui.viewmodel.AddNoteViewModel
import kotlinx.coroutines.launch

class AddNoteFragment : Fragment() {
    private val viewModel: AddNoteViewModel by viewModels()

    private var _binding: FragmentAddNoteBinding? = null
    private val binding: FragmentAddNoteBinding
        get() = checkNotNull(_binding) {
            "FragmentAddNoteBinding not inflated"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        setupActionBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.note.collect { note ->
                    updateUI(note)
                }
            }
        }

        binding.apply {
            noteDetailEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.updateNote { oldNote ->
                    oldNote.copy(note = text.toString().trim())
                }
            }

            noteTitleEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.updateNote { oldNote ->
                    oldNote.copy(title = text.toString().trim())
                }
            }
        }
    }


    private fun updateUI(note: Note) {
        binding.apply {
            if (noteTitleEditText.text.toString() != note.title) {
                noteTitleEditText.setText(note.title)
            }
            if (noteDetailEditText.text.toString() != note.note) {
                noteDetailEditText.setText(note.note)
            }
        }
    }


    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.topAppBar)

        val navHostFragment = (activity as AppCompatActivity).supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}