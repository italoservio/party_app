package br.com.cotemig.italo.party.services

import br.com.cotemig.italo.party.models.Cost
import br.com.cotemig.italo.party.models.Decision
import br.com.cotemig.italo.party.models.Invite
import br.com.cotemig.italo.party.models.Party
import retrofit2.Call
import retrofit2.http.*

interface ServiceParty {

  @GET("party/manager")
  fun listParty(@Header("Authorization") token: String?): Call<List<Party>>

  @GET("party/invited")
  fun listInvited(@Header("Authorization") token: String?): Call<List<Party>>

  @GET("party/{id}")
  fun getById(@Path("id") id: Int?, @Header("Authorization") token: String?): Call<Party>

  @POST("party")
  fun createParty(@Body party: Party?, @Header("Authorization") token: String?): Call<Void>

  @POST("party/invite")
  fun createInvite(@Body invite: Invite?, @Header("Authorization") token: String?): Call<Void>

  @POST("party/decision")
  fun decision(@Body decision: Decision?, @Header("Authorization") token: String?): Call<Void>

  @POST("party/cost")
  fun cost(@Body cost: Cost?, @Header("Authorization") token: String?): Call<Void>
}