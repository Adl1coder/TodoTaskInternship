package com.adilegngr.myapplication.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.Calendar


fun View.gone(){
    visibility = View.GONE
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun Context.toastMessage(message:String) = Toast.makeText(this,message, Toast.LENGTH_SHORT).show()

fun View.click(func: () ->Unit){
    this.setOnClickListener {
        func()
    }
}
fun Fragment.showDatePicker(
    calendar: Calendar,
    onDateSelected: (Int, Int, Int) -> Unit
) {
    DatePickerDialog(
        requireContext(),
        { _, year, month, day ->
            onDateSelected(year, month, day)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

fun Fragment.showTimePicker(
    calendar: Calendar,
    onTimeSelected: (Int, Int) -> Unit
) {
    TimePickerDialog(
        requireContext(),
        { _, hour, minute ->
            onTimeSelected(hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    ).show()
}
