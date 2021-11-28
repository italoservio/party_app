package br.com.cotemig.italo.party.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.adapters.MyInvitesAdapter
import br.com.cotemig.italo.party.models.Decision
import br.com.cotemig.italo.party.models.Party
import br.com.cotemig.italo.party.models.User
import br.com.cotemig.italo.party.services.RetrofitParty
import retrofit2.Call
import retrofit2.Response

class MyInvitesActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_invites)

    val user = intent.getSerializableExtra("user") as User

    setupActivity(user)
  }

  private fun setupActivity(user: User) {
    loadInvitedParties(user)

  }

  private fun loadInvitedParties(user: User) {
    val service = RetrofitParty().serviceParty()
    val call = service.listInvited(user.token)

    call.enqueue(object : retrofit2.Callback<List<Party>> {
      override fun onResponse(call: Call<List<Party>>, response: Response<List<Party>>) {
        if (response.code() == 200) {
          response.body()?.let { body ->
            fillMembers(body, user)
          }
        } else {
          Toast.makeText(
            this@MyInvitesActivity,
            "Não foi possível listar seus convites.",
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<List<Party>>, t: Throwable) {
        Toast.makeText(
          this@MyInvitesActivity,
          "Um erro inesperado ocorreu",
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }

  private fun fillMembers(parties: List<Party>, user: User) {
    val list = findViewById<RecyclerView>(R.id.list)

    list.adapter = MyInvitesAdapter(this, parties, {
      onClickAccept(it, user)
    }, {
        onClickDeny(it, user)
    })

    list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
  }

  private fun onClickAccept(party: Party, user: User) {
    val service = RetrofitParty().serviceParty()
    var decision = Decision()
    decision.decision = 1
    decision.party_id = party.id

    val call = service.decision(decision, user.token)

    call.enqueue(object : retrofit2.Callback<Void> {
      override fun onResponse(call: Call<Void>, response: Response<Void>) {
        if (response.code() == 200) {
          response.body()?.let {
            loadInvitedParties(user)
          }
        } else {
          Toast.makeText(
            this@MyInvitesActivity,
            "Não foi possível aceitar o convite.",
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<Void>, t: Throwable) {
        Toast.makeText(
          this@MyInvitesActivity,
          "Um erro inesperado ocorreu",
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }

  private fun onClickDeny(party: Party, user: User) {
    val service = RetrofitParty().serviceParty()
    var decision = Decision()
    decision.decision = -1
    decision.party_id = party.id

    val call = service.decision(decision, user.token)

    call.enqueue(object : retrofit2.Callback<Void> {
      override fun onResponse(call: Call<Void>, response: Response<Void>) {
        if (response.code() == 200) {
          response.body()?.let {
            loadInvitedParties(user)
          }
        } else {
          Toast.makeText(
            this@MyInvitesActivity,
            "Não foi possível recusar o convite.",
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<Void>, t: Throwable) {
        Toast.makeText(
          this@MyInvitesActivity,
          "Um erro inesperado ocorreu",
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }
}