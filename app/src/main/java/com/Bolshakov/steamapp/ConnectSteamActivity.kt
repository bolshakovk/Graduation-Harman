package com.Bolshakov.steamapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder
import java.util.*


class ConnectSteamActivity : AppCompatActivity() {
    // initialize variables
    var textView: TextView? = null
    lateinit var selectedFriend: BooleanArray
    var langList = ArrayList<Int>()
    var langArray = arrayOf("Русланка", "Плющик", "Славка", "Димасик", "Костька", "Колинс")

    var dataTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_activity)

        // assign variable
        textView = findViewById(R.id.multyTextView)
        dataTextView = findViewById(R.id.dataTextView)

        // initialize selected friends array
        selectedFriend = BooleanArray(langArray.size)
        textView?.setOnClickListener(View.OnClickListener { // Initialize alert dialog
            val builder = AlertDialog.Builder(this@ConnectSteamActivity)

            // set title
            builder.setTitle("Выбор друга")

            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                langArray, selectedFriend
            ) { dialogInterface, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position  in lang list
                    langList.add(i)
                    // Sort array list
                    Collections.sort(langList)
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    langList.removeAt(i)
                }
            }
            builder.setPositiveButton(
                "OK"
            ) { dialogInterface, i -> // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop
                for (j in langList.indices) {
                    // concat array value
                    stringBuilder.append(langArray[langList[j]])
                    // check condition
                    if (j != langList.size - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }
                }
                // set text on textView
                textView?.setText(stringBuilder.toString())
                dataTextView?.setText(stringBuilder.toString())
            }
            builder.setNegativeButton(
                "Отмена"
            ) { dialogInterface, i -> // dismiss dialog
                dialogInterface.dismiss()
            }
            builder.setNeutralButton(
                "Отчистить всех"
            ) { dialogInterface, i ->
                // use for loop
                for (j in selectedFriend.indices) {
                    // remove all selection
                    selectedFriend[j] = false
                    // clear language list
                    langList.clear()
                    // clear text view value
                    textView?.setText("")
                }
            }
            // show dialog
            builder.show()
        })
    }
}