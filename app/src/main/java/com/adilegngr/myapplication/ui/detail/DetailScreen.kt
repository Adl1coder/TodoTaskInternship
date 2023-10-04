package com.adilegngr.myapplication.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.adilegngr.myapplication.data.source.NoteDatabase
import com.adilegngr.myapplication.databinding.FragmentDetailScreenBinding
import com.adilegngr.myapplication.util.click
import com.adilegngr.myapplication.util.gone
import com.adilegngr.myapplication.util.toastMessage

class DetailScreen : Fragment() {

    private lateinit var binding: FragmentDetailScreenBinding
    private val args:DetailScreenArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailScreenBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            tvDetailTitle.text = args.note.title
            tvDetailDescription.text = args.note.description
            tvDetailDate.text = args.note.date
        }
        clickDeleteButton()
        clickBackButton()
    }

    private fun clickDeleteButton(){
        binding.btnDetailDelete.click {
            AlertDialog.Builder(requireContext())
                .setTitle("Notu sil")
                .setMessage("Silmek istiyor musunuz?")
                .setPositiveButton("Evet"){_,_ ->
                    if (args.fromHome){
                        with(binding){
                            tvDetailTitle.text = ""
                            tvDetailDescription.text = ""
                            tvDetailDate.text = ""
                            btnDetailDelete.gone()
                        }
                        NoteDatabase.deleteNoteInHome(args.note)
                        requireContext().toastMessage("Silindi!")
                    }
                    else{
                        with(binding){
                            tvDetailTitle.text = ""
                            tvDetailDescription.text = ""
                            tvDetailDate.text = ""
                            btnDetailDelete.gone()
                        }
                        NoteDatabase.deleteNoteInSaved(args.note)
                        requireContext().toastMessage("Silindi!")
                    }
                }
                .setNegativeButton("No",null)
                .show()
        }
    }

    private fun clickBackButton(){
        binding.btnDetailBack.click {
            val action = DetailScreenDirections.actionDetailNoteScreenToNotesScreen()
            findNavController().navigate(action)
        }
    }
}