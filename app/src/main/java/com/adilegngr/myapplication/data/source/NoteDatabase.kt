package com.adilegngr.myapplication.data.source

import com.adilegngr.myapplication.data.model.NoteModel

/*
* getHomeNotes() ve getSavedNotes():
*  Sırasıyla "homeList" ve "savedList" içindeki notları döndürür.
addNoteInHome() ve addNoteInSaved():
* Yeni notları "homeList" veya "savedList" listesine ekler.
deleteNoteInHome() ve deleteNoteInSaved():
* Belirtilen bir notu "homeList" veya "savedList" listesinden kaldırır.
* */

object NoteDatabase {

    private val notesModel = mutableListOf<NoteModel>()

    var homeList = mutableListOf<NoteModel>()
    var savedList = mutableListOf<NoteModel>()

    fun getHomeNotes() = homeList

    fun getSavedNotes() = savedList

    fun addNoteInHome(title: String, desc: String, date: String, saveType: String){
        homeList.add(
            NoteModel(
                id = (notesModel.lastOrNull()?.id ?: 0) + 1,
                title = title,
                description = desc,
                date = date,
                saveType = saveType
            )
        )
    }

    fun deleteNoteInHome(note:NoteModel) = homeList.remove(note)

    fun addNoteInSaved(title: String, desc: String, date: String, saveType: String){
        savedList.add(
            NoteModel(
                id = (notesModel.lastOrNull()?.id ?: 0) + 1,
                title = title,
                description = desc,
                date = date,
                saveType = saveType
            )
        )
    }

    fun deleteNoteInSaved(note: NoteModel) = savedList.remove(note)
}