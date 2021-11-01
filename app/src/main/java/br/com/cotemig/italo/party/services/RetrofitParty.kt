package br.com.cotemig.italo.party.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitParty {

  private val retrofit = Retrofit.Builder()
    .baseUrl("http://18.230.130.214")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  fun serviceUser(): ServiceUser {
    return this.retrofit.create(ServiceUser::class.java)
  }

}