package com.adilegngr.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilegngr.myapplication.data.model.NoteModel
import com.adilegngr.myapplication.databinding.NoteItemBinding
import com.adilegngr.myapplication.util.click

class NoteAdapter(
    var checkboxClick: (NoteModel) -> Unit = {},
    var onItemClick: (NoteModel) -> Unit
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val noteList = mutableListOf<NoteModel>()

    inner class NoteViewHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(note: NoteModel){
            with(binding){
                tvTitle.text = note.title
                tvDate.text = note.date

                checkbox.click {
                    checkboxClick(note)
                }

                root.setOnClickListener{
                    onItemClick(note)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NoteViewHolder(
        NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun getItemCount(): Int = noteList.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind(noteList[position])

    fun updateList(list: List<NoteModel>) {
        noteList.clear()
        noteList.addAll(list)
        notifyItemRangeChanged(0, list.size)
    }
}