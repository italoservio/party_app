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

  @GET("party/manager")
  fun list(@Header("Authorization") token: String?): Call<List<Party>>

  @POST("party")
  fun createParty(@Body party: Party?, @Header("Authorization") token: String): Call<Void>
}