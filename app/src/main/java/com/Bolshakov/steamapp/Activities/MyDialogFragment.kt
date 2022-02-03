package com.Bolshakov.steamapp.Activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.Bolshakov.steamapp.DataBase.DBConfigUsers
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import com.Bolshakov.steamapp.Models.Person

class MyDialogFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            Log.d("tag", "person login: ${Person.login}")
            DBManagerUsers.deleteDbData(Person.login)

            Log.d("tag", "deleting...: ${Person.login}")
            builder.setTitle("Вы действительно хотите удалить аккаунт?")
                .setCancelable(true)
                .setPositiveButton("Да") { dialog, id ->
                    Toast.makeText(activity, "Ваш аккаунт удален",
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                        Toast.makeText(activity, "Отмена удаления",
                            Toast.LENGTH_LONG).show()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}