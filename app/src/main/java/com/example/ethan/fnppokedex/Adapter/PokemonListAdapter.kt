package com.example.ethan.fnppokedex.Adapter

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.example.ethan.fnppokedex.R
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.ethan.fnppokedex.Common.Common
import com.example.ethan.fnppokedex.Interface.IItemClickListener
import com.example.ethan.fnppokedex.Model.Pokemon


class PokemonListAdapter (internal var context: Context,
                          internal var pokemonlist:List<Pokemon>) :RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.pokemon_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pokemonlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(pokemonlist[position].img).into(holder.img_pokemon)
        holder.txt_pokemon.text = pokemonlist[position].name

            holder.setItemClickListener(object:IItemClickListener{
                override fun onClick(view: View, position: Int) {
                  //Toast.makeText(context, "Clicked at Pokemon: "+pokemonlist[position],Toast.LENGTH_SHORT).show()

                    LocalBroadcastManager.getInstance(context)
                        .sendBroadcast(Intent(Common.KEY_ENABLE_HOME).putExtra("num",pokemonlist[position].num))
                }

            })

    }



    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        internal var img_pokemon: ImageView
        internal var txt_pokemon: TextView

        internal var itemClickListener:IItemClickListener?=null

        fun setItemClickListener(iItemClickListener: IItemClickListener)
        {
            this.itemClickListener = iItemClickListener
        }


        init{
            img_pokemon = itemView.findViewById(R.id.pokemon_image) as ImageView
            txt_pokemon = itemView.findViewById(R.id.pokemon_name) as TextView

            itemView.setOnClickListener { view -> itemClickListener!!.onClick(view,adapterPosition) }

        }

    }
}
