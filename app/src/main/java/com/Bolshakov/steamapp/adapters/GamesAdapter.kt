package com.Bolshakov.steamapp.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.R

class GamesAdapter(private val arrayGames : ArrayList<Game> ) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GamesAdapter.ViewHolder, position: Int) {
        holder.name.text = arrayGames[position].name
        holder.description.text = arrayGames[position].descr
        holder.rating.text = arrayGames[position].rating.toString()
    }

    override fun getItemCount(): Int {
        return arrayGames.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.textViewName)
        val rating = itemView.findViewById<TextView>(R.id.textRating)
        val description = itemView.findViewById<TextView>(R.id.textDecription)
    }
}