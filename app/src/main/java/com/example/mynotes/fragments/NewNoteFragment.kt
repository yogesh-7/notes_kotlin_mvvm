package com.example.mynotes.fragments

import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bersyte.noteapp.toast
import com.example.mynotes.MainActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentNewNoteBinding
import com.example.mynotes.models.Note
import com.example.mynotes.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding: FragmentNewNoteBinding?=null
    private val binding get() = _binding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentNewNoteBinding.inflate(
            inflater,
            container,
            false
        )


        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_note,menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    private fun saveNote(view: View){
        val noteTittle = binding?.etNoteTitle?.text.toString().trim()
        val noteBody = binding?.etNoteBody?.text.toString().trim()


        if(noteTittle.isNotEmpty() ){
            if(noteBody.isNotEmpty() ){
                val note = Note(0,noteTittle,noteBody)
                noteViewModel.addNote(note)
                Snackbar.make(view,"Saved Successfully",Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigate(
                    R.id.action_newNoteFragment_to_homeFragment
                )
            }else{
                activity?.toast("Please enter note body")
            }

        }else{
            activity?.toast("Please enter note title")
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }
}