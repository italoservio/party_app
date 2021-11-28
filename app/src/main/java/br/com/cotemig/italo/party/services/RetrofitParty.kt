package br.com.cotemig.italo.party.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitParty {

  private val retrofit = Retrofit.Builder()
    .baseUrl("https://www.italoservio.com/party-api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  fun serviceUser(): ServiceUser {
    return this.retrofit.create(ServiceUser::class.java)
  }

  fun serviceParty(): ServiceParty {
    return this.retrofit.create(ServiceParty::class.java)
  }

}