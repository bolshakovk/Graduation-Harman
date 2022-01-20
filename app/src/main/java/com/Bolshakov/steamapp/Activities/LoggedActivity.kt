package com.Bolshakov.steamapp.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.Bolshakov.steamapp.DataBase.DBManager
import com.Bolshakov.steamapp.DataBase.Person
import com.Bolshakov.steamapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder
import java.util.*

//TODO кнопка удаления аккаунта, кнопка редактирования аккаунта, вход под конкретного пользователя,
// а не под первого, выбор всех пользователей, а не первого, отображение украсть в виде вьюшек
class LoggedActivity : AppCompatActivity() {
    // initialize variables
    var textView: TextView? = null
    lateinit var selectedFriend: BooleanArray
    var langList = ArrayList<Int>()
    var langArray = arrayOf(Person.name)
    private lateinit var editActionButton : FloatingActionButton
    private lateinit var deleteActionButton : FloatingActionButton
    var dataTextView: TextView? = null
    private lateinit var profileTextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_activity)

        // assign variable
        textView = findViewById(R.id.multyTextView)
        dataTextView = findViewById(R.id.dataTextView)

        // в шапке имя профиля
        profileTextView = findViewById(R.id.profileTextView)
        profileTextView.text = Person.name + " " + Person.login


        editActionButton = findViewById(R.id.editActionButton)
        editActionButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        })

        deleteActionButton = findViewById(R.id.deleteActionButton)
        deleteActionButton.setOnClickListener(View.OnClickListener{
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "MyDialog")
        })


        // initialize selected friends array
        selectedFriend = BooleanArray(langArray.size)
        textView?.setOnClickListener(View.OnClickListener { // Initialize alert dialog
            val builder = AlertDialog.Builder(this@LoggedActivity)

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
