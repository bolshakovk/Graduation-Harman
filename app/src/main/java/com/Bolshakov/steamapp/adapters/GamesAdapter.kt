package com.Bolshakov.steamapp.adapters

import android.content.ContentValues
import android.database.DataSetObserver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Bolshakov.steamapp.DataBase.DBConfigUsers
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.Models.Person
import com.Bolshakov.steamapp.R
import java.lang.RuntimeException

class GamesAdapter(private val arrayGames : ArrayList<Game> ) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesAdapter.ViewHolder, position: Int) {
        holder.name.text = arrayGames[position].name
    }

    override fun getItemCount(): Int {
        return arrayGames.size
    }
    fun update(){
        arrayGames.clear()
    }
    fun updateCrutch(request: String){
        var arr: ArrayList<String> = ArrayList<String>()
        for (item in DBManagerUsers.readGamesFromPerson(request)){
            arr.add(item.name)
        }
    }
    fun removeAt(position: Int) {
        var arr: ArrayList<String> = ArrayList<String>()
        var v = ContentValues()
        Log.d("tag", "------------------------------------------------------")
        arrayGames.removeAt(position)
        for (item in arrayGames){
            arr.add(item.name)
        }
        v.put(DBConfigUsers.COLUMN_NAME_GAMES, arr.toString())


        DBManagerUsers.updateDbDataGame(v)
        Log.d("tag", "HERE IS CONTENT VALUES in delete method: $arrayGames")

        notifyItemRemoved(position)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.textViewName)
    }
}