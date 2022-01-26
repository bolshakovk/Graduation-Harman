package com.Bolshakov.steamapp.Activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import com.Bolshakov.steamapp.Models.Person
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.R
import com.Bolshakov.steamapp.adapters.GamesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

//TODO кнопка удаления аккаунта, кнопка редактирования аккаунта, вход под конкретного пользователя,
// а не под первого, выбор всех пользователей, а не первого, отображение украсть в виде вьюшек
class LoggedActivity : AppCompatActivity() {

    val games = arrayListOf<Game>(
        Game(1, "Valheim", "some kind of build", 4.4),
        Game(2,"Phasmophobia", "horror", 3.0),
        Game(3, "Call of Duty", "shouter", 2.0),
        Game(4, "Warcraft", "drochil'nya", 5.5)
    )

    private lateinit var editActionButton : FloatingActionButton
    private lateinit var deleteActionButton : FloatingActionButton
    var dataTextView: TextView? = null
    private lateinit var profileTextView : TextView
    private lateinit var profileTextViewId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_activity)
        val recyclerView = findViewById<RecyclerView>(R.id.idRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = GamesAdapter(games)
        val profileName = intent.getStringExtra("login")

        val textviewS : TextView = findViewById(R.id.multyGameTextView)
        val arrayListS : ArrayList<Game> = ArrayList<Game>()
        arrayListS.add(Game(1, "Valheim", "some kind of build", 4.4))
        arrayListS.add(Game(2,"Phasmophobia", "horror", 3.0))
        arrayListS.add(Game(3, "Call of Duty", "shouter", 2.0))
        arrayListS.add(Game(4, "Warcraft", "drochil'nya", 5.5))


        textviewS.setOnClickListener(View.OnClickListener {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_searchable_spinner)
                dialog.window?.setLayout(650, 800)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()

                val editTextS: EditText = dialog.findViewById(R.id.edit_textS)
                val listViewS: ListView = dialog.findViewById(R.id.list_viewS)

                val adapter: ArrayAdapter<Game> = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListS)
                listViewS.adapter
                editTextS.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                        adapter.filter.filter(s)
                    }

                    override fun afterTextChanged(s: Editable) {}
                })


                listViewS.onItemClickListener =
                    AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                        // set selected item on textView
                        textviewS.setText(adapter.getItem(position).toString())
                        //TODO наверное тут должен отсылаться запрос по айдишнику в бд
                        Log.d("tag", "choised game: id =  $id view = $view position = $position parent = $parent")
                        // Dismiss dialog
                        dialog.dismiss()
                    }
        })


        var friendArray: Array<String> = arrayOf()
        Log.d("tag", "request is: login $profileName")
        for (item in DBManagerUsers.readForFriendListDbData()){
            friendArray += item
        }

        DBManagerUsers.readDbData(profileName.toString())

        // assign variable
        val textView: TextView = findViewById(R.id.multyFriendTextView)

        // в шапке имя профиля
        profileTextView = findViewById(R.id.profileTextViewlogin)
        profileTextView.text = Person.login
        profileTextViewId = findViewById(R.id.profileTextViewId)
        profileTextViewId.text = Person.id.toString()



        editActionButton = findViewById(R.id.editActionButton)
        editActionButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        })



        intent.putExtra("login", profileName)
        deleteActionButton = findViewById(R.id.deleteActionButton)
        deleteActionButton.setOnClickListener(View.OnClickListener{
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "MyDialog")
        })

        var langList = ArrayList<Int>()
        // initialize selected friends array
        val selectedFriend = BooleanArray(friendArray.size)
        textView?.setOnClickListener(View.OnClickListener { // Initialize alert dialog
            val builder = AlertDialog.Builder(this@LoggedActivity)
            // set title
            builder.setTitle("Выбор друга")

            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                friendArray, selectedFriend
            ) { dialogInterface, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position  in lang list
                    langList.add(i)
                    // Sort array list
                    langList.sort()
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
                    stringBuilder.append(friendArray[langList[j]])
                    // check condition
                    if (j != langList.size - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }
                }
                // set text on textView
                textView?.text = stringBuilder.toString()
                dataTextView?.text = stringBuilder.toString()
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
                    textView?.text = ""
                }
            }
            // show dialog
            builder.show()
        })
    }
}
