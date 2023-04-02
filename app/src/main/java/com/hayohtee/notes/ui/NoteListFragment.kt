package com.hayohtee.notes.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hayohtee.notes.R
import com.hayohtee.notes.databinding.FragmentNoteListBinding
import com.hayohtee.notes.ui.adapter.NoteListAdapter
import com.hayohtee.notes.ui.viewmodel.NoteListViewModel
import kotlinx.coroutines.launch

class NoteListFragment : Fragment() {
    private val viewModel: NoteListViewModel by viewModels()

    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding
        get() = checkNotNull(_binding) {
            "FragmentNoteListBinding not inflated"
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)

        setupActionBar()
        setupMenu()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteListAdapter {noteId ->
            val action = NoteListFragmentDirections.showNoteDetail(noteId)
            findNavController().navigate(action)
        }

        binding.apply {
            recyclerView.adapter = adapter
            fab.setOnClickListener {
                findNavController().navigate(R.id.showAddNote)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collect { values ->
                    adapter.submitList(values)
                }
            }
        }

    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.note_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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