package com.example.ethan.fnppokedex

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.content.LocalBroadcastManager
import android.view.MenuItem
import com.example.ethan.fnppokedex.Common.Common
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private val showPokemonType = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent !!.action !!.toString() == Common.KEY_POKEMON_TYPE) {



                //Replace Fragment
                val pokemonType = PokemonType.getInstance()
                val type = intent.getStringExtra("type")
                val bundle = Bundle()
                bundle.putString("type",type)
                pokemonType!!.arguments = bundle

                supportFragmentManager.popBackStack(0, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.list_pokemon_fragment, pokemonType)
                fragmentTransaction.addToBackStack("type")
                fragmentTransaction.commit()

                toolbar.title = type.toUpperCase() + " TYPE POKEMON"
            }
        }

    }

    private val showDetail = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, intent: Intent?) {
                if (intent !!.action !!.toString() == Common.KEY_ENABLE_HOME) {

                    supportActionBar !!.setDisplayHomeAsUpEnabled(true)
                    supportActionBar !!.setDisplayShowHomeEnabled(true)

                    //Replace Fragment
                    val detailFragment = PokemonDetail.getInstance()
                    val num = intent.getStringExtra("num")
                    val bundle = Bundle()
                    bundle.putString("num",num)
                    detailFragment.arguments = bundle

                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment)
                    fragmentTransaction.addToBackStack("detail")
                    fragmentTransaction.commit()

                    val pokemon = Common.findPokemonByNum(num)
                    toolbar.title = pokemon!!.name
                }
            }

        }

    private val showEvolution = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            if (intent !!.action !!.toString() == Common.KEY_NUM_EVOLUTION) {



                //Replace Fragment
                val detailFragment = PokemonDetail.getInstance()
                val bundle = Bundle()
                val num = intent.getStringExtra("num")
                bundle.putString("num",num)
                detailFragment.arguments = bundle

                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.remove(detailFragment)
                fragmentTransaction.replace(R.id.list_pokemon_fragment, detailFragment)
                fragmentTransaction.addToBackStack("detail")
                fragmentTransaction.commit()

                val pokemon = Common.findPokemonByNum(num)
                toolbar.title = pokemon!!.name
            }
        }

    }


        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            toolbar.setTitle("POKEDEX")
            setSupportActionBar(toolbar)


            LocalBroadcastManager.getInstance(this)
                .registerReceiver(showDetail, IntentFilter(Common.KEY_ENABLE_HOME))
            LocalBroadcastManager.getInstance(this)
                .registerReceiver(showEvolution, IntentFilter(Common.KEY_NUM_EVOLUTION))
            LocalBroadcastManager.getInstance(this)
                .registerReceiver(showPokemonType, IntentFilter(Common.KEY_POKEMON_TYPE))
        }


        override fun onOptionsItemSelected(item: MenuItem?): Boolean {
            when (item !!.itemId) {
                android.R.id.home -> {
                    toolbar.title = "POKEDEX"


                    supportFragmentManager.popBackStack("detail", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    supportFragmentManager.popBackStack("type", FragmentManager.POP_BACK_STACK_INCLUSIVE)

                    val pokemonList = PokemonList.getInstance()
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    fragmentTransaction.remove(pokemonList)
                    fragmentTransaction.replace(R.id.list_pokemon_fragment,pokemonList)
                    fragmentTransaction.commit()

                    supportActionBar !!.setDisplayHomeAsUpEnabled(false)
                    supportActionBar !!.setDisplayShowHomeEnabled(false)

                }
            }

            return true
        }


    }
