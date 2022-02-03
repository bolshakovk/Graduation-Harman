package com.Bolshakov.steamapp.Activities

import android.app.Dialog
import android.content.ContentValues
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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Bolshakov.steamapp.DataBase.DBConfigUsers
import com.Bolshakov.steamapp.DataBase.DBManagerGames
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.Models.Person
import com.Bolshakov.steamapp.R
import com.Bolshakov.steamapp.adapters.GamesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList

import com.Bolshakov.steamapp.Callback.SwipeGesture
import androidx.lifecycle.LiveData

import android.R.string.no
import java.util.Calendar.getInstance


class LoggedActivity : AppCompatActivity(){
    private lateinit var profileTextView : TextView
    private lateinit var deleteActionButton: FloatingActionButton
    var dataTextView: TextView? = null

    private lateinit var customViewModel: CustomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_activity)
        val profileName = intent.getStringExtra("login")
        Person.login = profileName.toString()
        val textViewOnGame: TextView = findViewById(R.id.multyGameTextView)
        // в шапке имя профиля
        profileTextView = findViewById(R.id.profileTextView)
        profileTextView.text = profileName

        Log.d("view", profileTextView.text as String)
        val arrayListAllGames: ArrayList<String> = ArrayList<String>()
        for (item in DBManagerGames.readDbData()) {
            arrayListAllGames.add(item.name)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.idRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        DBManagerUsers.getForGamesListDbDataByPerson(Person.login)

        var arr: ArrayList<String> = ArrayList<String>()
        var games: ArrayList<Game> = ArrayList()

        recyclerView.adapter = GamesAdapter(games)
        customViewModel = ViewModelProvider(this)[CustomViewModel::class.java]
        customViewModel.readableValue.observe(this,{
            recyclerView.adapter = GamesAdapter(it)
        })








        val swipeGesture = object : SwipeGesture(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT ->{
                        GamesAdapter(games).removeAt(viewHolder.adapterPosition)
                        Log.d("tag", "swiped left $games")
                    }
                    ItemTouchHelper.RIGHT ->{
                        GamesAdapter(games).removeAt(viewHolder.adapterPosition)
                        Log.d("tag", "swiped right")
                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)

        //нажатие на выбор игр
        textViewOnGame.setOnClickListener(View.OnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_searchable_spinner)
            dialog.window?.setLayout(900, 2000)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            val editTextS: EditText = dialog.findViewById(R.id.edit_textS)
            val listGamesView: ListView = dialog.findViewById(R.id.list_viewS)

            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListAllGames)
            listGamesView.adapter = adapter

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
            //нажатие на игру
            listGamesView.onItemClickListener =
                AdapterView.OnItemClickListener { parent, view, position, id -> // when item selected from list
                    // set selected item on textView
                    textViewOnGame.text = adapter.getItem(position).toString()
                    Person.login = profileName.toString()
                    recyclerView.adapter = GamesAdapter(games)
                    GamesAdapter(games).update()
                    Log.d("tag", "chose game: id =  $id ${textViewOnGame.text}")
                    Log.d("tag", "chose game to person =  $id ${Person.login}")
                    arr.add(textViewOnGame.text.toString())
                    var v = ContentValues()
                    v.put(DBConfigUsers.COLUMN_NAME_GAMES, arr.toString())
                    Log.d("tag", "HERE IS CONTENT VALUES: $v")


                    DBManagerUsers.db?.update(
                        DBConfigUsers.TABLE_NAME,
                        v,
                        "${DBConfigUsers.COLUMN_NAME_LOGIN} = ?",
                        arrayOf(Person.login)
                    )

                    for (item in arr) {
                        games.add(Game(item))
                        Log.d("tag", "items: $item")
                    }
                    DBManagerUsers.testRawQuery(profileTextView.text as String)
                    // Dismiss dialog
                    dialog.dismiss()
                }
        })

        var friendArray: Array<String> = arrayOf()
        for (name in DBManagerUsers.readForFriendListDbData()) {
            friendArray += name
        }


        var friendList : ArrayList<String> = ArrayList()
        for (friend in DBManagerUsers.readForFriendListDbData()){
            friendList.add(Person.login)
            DBManagerUsers.getForGamesListDbDataByPerson(friend)
        }





        var allGamesList :ArrayList<Game> = ArrayList()
        for (item in DBManagerUsers.readForGamesListDbData()){
            allGamesList.add(Game(item.name))
        }

        var selectedFriendsGames : ArrayList<Game> = ArrayList()


        var editActionButton: FloatingActionButton = findViewById(R.id.editActionButton)
        editActionButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        })
        parse(DBManagerUsers.readGamesFromPerson("misha").toString().drop(10))

        intent.putExtra("login", profileName)
        deleteActionButton = findViewById(R.id.deleteActionButton)
        deleteActionButton.setOnClickListener(View.OnClickListener {
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "MyDialog")
        })

        var positionList = ArrayList<Int>()
        val selectedFriend = BooleanArray(friendArray.size)
        val multyFriendTextView: TextView = findViewById(R.id.multyFriendTextView)
        multyFriendTextView.setOnClickListener(View.OnClickListener { // Initialize alert dialog

            val builder = AlertDialog.Builder(this@LoggedActivity)
            builder.setTitle("Выбор друга")

            // set dialog non cancelable
            builder.setCancelable(false)
            builder.setMultiChoiceItems(friendArray, selectedFriend) {
                    _, i, b ->
                // check condition
                if (b) {
                    // when checkbox selected
                    // Add position  in lang list
                    positionList.add(i)
                    Log.d("tag", "i = $i position = $positionList")
                    // Sort array list
                    positionList.sort()
                } else {
                    // when checkbox unselected
                    // Remove position from langList
                    positionList.removeAt(i)
                }
            }
            builder.setPositiveButton("OK") { _, i -> // Initialize string builder
                val stringBuilder = StringBuilder()
                // use for loop

                for (j in positionList.indices) {
                    recyclerView.adapter = GamesAdapter(games)
                    GamesAdapter(games).update()
                    // concat array value
                    stringBuilder.append(friendArray[positionList[j]])

                    recyclerView.adapter = GamesAdapter(games)
                    GamesAdapter(games).update()
                    selectedFriendsGames.add(Game(DBManagerUsers.getForGamesListDbDataByPerson(friendArray[positionList[j]]).toString()))

                    // check condition
                    if (j != positionList.size - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ")
                    }

                }

                // set text on textView
                multyFriendTextView.text = stringBuilder.toString()
                dataTextView?.text = stringBuilder.toString()
            }
            builder.setNegativeButton(
                "Отмена"
            ) { dialogInterface, i -> // dismiss dialog
                dialogInterface.dismiss()
            }
            builder.setNeutralButton(
                "Отчистить всех"
            ) { _, i ->
                // use for loop
                for (j in selectedFriend.indices) {
                    // remove all selection
                    selectedFriend[j] = false
                    // clear language list
                    positionList.clear()
                    // clear text view value
                    multyFriendTextView.text = ""
                }
            }
            // show dialog
            builder.show()
        })
        for (item in selectedFriendsGames){
            Log.d("selectedFriendsGames:", item.name)
        }
    }

    fun parse(array : String){

        var i = 0
        var m : Array<String> = arrayOf("")
        var subString = ""
        while (i<array.length - 1){
            i++
            if(array[i]==',' || array[i] ==']' || array[i] == '(' || array[i] == ')' || array[i] == '['){
                m + subString
                //m.plus(subString)
                Log.d("ttt", subString)
                subString = ""
                continue
            }
            subString += array[i]
        }
        Log.d("ttt", subString)
    }
}
