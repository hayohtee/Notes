package com.hayohtee.notes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hayohtee.notes.R
import com.hayohtee.notes.databinding.FragmentEditNoteBinding
import com.hayohtee.notes.ui.viewmodel.EditNoteViewModel
import com.hayohtee.notes.ui.viewmodel.EditNoteViewModelFactory

class EditNoteFragment : Fragment() {
    private val args: EditNoteFragmentArgs by navArgs()
    private val viewModel: EditNoteViewModel by viewModels {
        EditNoteViewModelFactory(args.noteId)
    }

    private var _binding: FragmentEditNoteBinding? = null
    private val binding: FragmentEditNoteBinding
        get() = checkNotNull(_binding) {
            "FragmentEditNote not inflated"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)

        setupActionBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
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