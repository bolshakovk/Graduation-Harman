package com.Bolshakov.steamapp.Activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class MyDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Вы действительно хотите удалить аккаунт?")
                .setCancelable(true)
                .setPositiveButton("Да") { dialog, id ->
                    Toast.makeText(activity, "Вы сделали правильный выбор",
                        Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(activity, "Возможно вы правы",
                            Toast.LENGTH_LONG).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}