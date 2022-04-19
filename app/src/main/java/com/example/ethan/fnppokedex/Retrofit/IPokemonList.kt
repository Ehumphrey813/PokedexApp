package com.example.ethan.fnppokedex.Retrofit

import com.example.ethan.fnppokedex.Model.Pokedex
import retrofit2.http.GET
import io.reactivex.Observable

interface IPokemonList {
    @get:GET("pokedex.json")
    val listPokemon:Observable<Pokedex>
}