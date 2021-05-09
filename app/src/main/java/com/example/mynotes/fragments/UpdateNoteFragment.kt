package com.example.mynotes.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bersyte.noteapp.toast
import com.example.mynotes.MainActivity
import com.example.mynotes.R
import com.example.mynotes.databinding.FragmentUpdateNoteBinding
import com.example.mynotes.models.Note
import com.example.mynotes.viewModel.NoteViewModel
import com.google.android.material.snackbar.Snackbar

class UpdateNoteFragment : Fragment(R.layout.fragment_update_note) {



    private var _binding: FragmentUpdateNoteBinding?=null
    private val binding get() = _binding
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote : Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view

        currentNote= args.note!!
        binding!!.etNoteTitleUpdate.setText(currentNote.noteTitle)
        binding!!.etNoteBodyUpdate.setText(currentNote.noteBody)


        binding?.fabDone?.setOnClickListener {
            updateNote(view)
        }
    }

    private fun updateNote(view: View){
        val noteTittle = binding?.etNoteTitleUpdate?.text.toString().trim()
        val noteBody = binding?.etNoteBodyUpdate?.text.toString().trim()


        if(noteTittle.isNotEmpty() ){
            if(noteBody.isNotEmpty() ){
                val note = Note(currentNote.id,noteTittle,noteBody)
                noteViewModel.addNote(note)
                Snackbar.make(view,"Saved Successfully", Snackbar.LENGTH_SHORT).show()
                view.findNavController().navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }else{
                activity?.toast("Please enter note body")
            }

        }else{
            activity?.toast("Please enter note title")
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        _binding = FragmentUpdateNoteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_update_note,menu)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding= null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to permanently delete this note?")
            setPositiveButton("DELETE") { _, _ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("CANCEL", null)
        }.create().show()

    }

}