package com.adilegngr.myapplication.ui.note

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.R
import com.adilegngr.myapplication.data.model.NoteModel
import com.adilegngr.myapplication.data.source.NoteDatabase
import com.adilegngr.myapplication.databinding.AlertDialogBinding
import com.adilegngr.myapplication.databinding.FragmentNoteScreenBinding
import com.adilegngr.myapplication.ui.adapter.NoteAdapter
import com.adilegngr.myapplication.util.click
import com.adilegngr.myapplication.util.showDatePicker
import com.adilegngr.myapplication.util.showTimePicker
import com.adilegngr.myapplication.util.toastMessage
import java.util.Calendar


class NoteScreen : Fragment() {

    private lateinit var binding: FragmentNoteScreenBinding
    private val noteAdapter by lazy { NoteAdapter(onItemClick = ::onClickNoteItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteScreenBinding.inflate(inflater, container, false)

        with(binding) {
            floatingBtn.click {
                showDialog()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAdapter()
        checkBoxClick()
    }

    private fun createAdapter() {
        binding.apply {
            val getList = NoteDatabase.getHomeNotes()
            rvHome.adapter = noteAdapter
            noteAdapter.updateList(getList)
        }
    }

    private fun checkBoxClick() {
        noteAdapter.checkboxClick = { noteModel ->
            NoteDatabase.deleteNoteInHome(noteModel)
            noteModel.description?.let {
                NoteDatabase.addNoteInSaved(
                    noteModel.title,
                    it,
                    noteModel.date,
                    noteModel.saveType
                )
            }
            requireContext().toastMessage("Kaydedilenlere gönderildi!")
            createAdapter()
        }
    }

    private fun onClickNoteItem(note: NoteModel) {
        val action = NoteScreenDirections.actionNotesScreenToDetailNoteScreen(note, true)
        findNavController().navigate(action)
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = AlertDialogBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()

        val saveTypeList = listOf("Notlar", "Kaydedilenler")

        var selectedSaveType = ""
        var selectedDate = ""

        val saveTypeAdapter = ArrayAdapter(
            requireContext(), R.layout.support_simple_spinner_dropdown_item, saveTypeList
        )

        with(binding) {

            val calendar = Calendar.getInstance()
            actSaveType.setAdapter(saveTypeAdapter)

            actSaveType.setOnItemClickListener { _, _, position, _ ->
                selectedSaveType = saveTypeList[position]
            }

            switchDate.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    showDatePicker(calendar) { year, month, day ->
                        showTimePicker(calendar) { hour, minute ->
                            selectedDate = "$day.$month.$year - $hour:$minute"
                            tvDate.text = "$day.$month.$year - $hour:$minute"
                            tvDate.visibility = View.VISIBLE
                        }
                    }
                }
            }

            btnAdd.setOnClickListener {
                val title = etTitle.text.toString()
                val desc = etDesc.text.toString()

                if (title.isNotEmpty() && desc.isNotEmpty() && selectedSaveType.isNotEmpty() && selectedSaveType == "Notes") {
                    NoteDatabase.addNoteInHome(title, desc, selectedDate, selectedSaveType)
                    requireContext().toastMessage("Notlara eklendi!")
                    createAdapter()
                    dialog.dismiss()
                } else {
                    NoteDatabase.addNoteInSaved(title, desc, selectedDate, selectedSaveType)
                    requireContext().toastMessage("Kaydedilenlere eklendi!")
                    dialog.dismiss()
                }
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }
}