package com.adilegngr.myapplication.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adilegngr.myapplication.data.model.NoteModel
import com.adilegngr.myapplication.data.source.NoteDatabase
import com.adilegngr.myapplication.databinding.FragmentSavedScreenBinding
import com.adilegngr.myapplication.ui.adapter.SavedAdapter
import com.adilegngr.myapplication.util.toastMessage



class SavedScreen : Fragment() {

    private lateinit var binding: FragmentSavedScreenBinding
    private val savedAdapter by lazy { SavedAdapter(savedOnItemClick = ::onClickNoteItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()
        checkBoxClick()
    }

    private fun createAdapter() {
        binding.apply {
            val getList = NoteDatabase.getSavedNotes()
            rvSaved.adapter = savedAdapter
            savedAdapter.updateList(getList)
        }
    }

    private fun checkBoxClick() {
        savedAdapter.savedCheckboxClick = { noteModel ->
            NoteDatabase.deleteNoteInSaved(noteModel)
            noteModel.description?.let {
                NoteDatabase.addNoteInHome(
                    noteModel.title,
                    it,
                    noteModel.date,
                    noteModel.saveType
                )
            }
            requireContext().toastMessage("Notlara gönderildi!")
            createAdapter()
        }
    }

    private fun onClickNoteItem(note: NoteModel){
        val action = SavedScreenDirections.actionSavedNotesScreenToDetailNoteScreen(note, fromSaved = true)
        findNavController().navigate(action)
    }
}