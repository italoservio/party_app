package br.com.cotemig.italo.party.services

import br.com.cotemig.italo.party.models.Party
import br.com.cotemig.italo.party.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ServiceUser {

  @POST("user/authenticate")
  fun authenticate(@Body user: User): Call<User>

  @POST("user")
  fun create(@Body user: User): Call<Void>

}