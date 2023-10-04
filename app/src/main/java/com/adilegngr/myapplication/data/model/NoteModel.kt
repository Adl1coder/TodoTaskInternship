package com.adilegngr.myapplication.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NoteModel(
    val id: Int,
    val title: String,
    val description: String?,
    val date: String,
    val saveType: String,
): Parcelable
