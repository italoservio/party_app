package br.com.cotemig.italo.party.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.adapters.MembersAdapter
import br.com.cotemig.italo.party.models.Invite
import br.com.cotemig.italo.party.models.Party
import br.com.cotemig.italo.party.models.User
import br.com.cotemig.italo.party.services.RetrofitParty
import retrofit2.Call
import retrofit2.Response

class MembersActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_members)

    val party = intent.getSerializableExtra("party") as Party
    val user = intent.getSerializableExtra("user") as User

    setupActivity(user, party)
  }

  private fun setupActivity(user: User, party: Party) {
    loadMembersParties(user, party)

    val inviteButton = findViewById<Button>(R.id.button_invite)

    inviteButton.setOnClickListener() {
      inviteMember(user, party)
    }
  }

  private fun inviteMember(user: User, party: Party) {
    var emailText = findViewById<EditText>(R.id.input_member)
    var invite = Invite()
    invite.email = emailText.text.toString()
    invite.party_id = party.id;

    val service = RetrofitParty().serviceParty()
    val call = service.createInvite(invite, user.token)
    call.enqueue(object : retrofit2.Callback<Void> {
      override fun onResponse(call: Call<Void>, response: Response<Void>) {
        if (response.code() == 201) {
          Toast.makeText(
            this@MembersActivity,
            R.string.members_activity_invite_success,
            Toast.LENGTH_LONG
          ).show()
          val intent = Intent(this@MembersActivity, MainActivity::class.java)
          intent.putExtra("user", user)
          startActivity(intent)
          finish()
        } else {
          Toast.makeText(
            this@MembersActivity,
            R.string.members_activity_invite_error,
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<Void>, t: Throwable) {
        Toast.makeText(
          this@MembersActivity,
          R.string.generic_unexpected_error,
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }

  private fun loadMembersParties(user: User, party: Party) {
    val service = RetrofitParty().serviceParty()
    val call = service.getById(party.id, user.token)

    call.enqueue(object : retrofit2.Callback<Party> {
      override fun onResponse(call: Call<Party>, response: Response<Party>) {
        if (response.code() == 200) {
          response.body()?.let { body ->
            fillMembers(body)
          }
        } else {
          Toast.makeText(
            this@MembersActivity,
            R.string.members_activity_list_error,
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<Party>, t: Throwable) {
        Toast.makeText(
          this@MembersActivity,
          R.string.generic_unexpected_error,
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }

  private fun fillMembers(party: Party) {
    val list = findViewById<RecyclerView>(R.id.list)
    list.adapter = MembersAdapter(this, party.members!!)

    list.layoutManager = LinearLayoutManager(
      this,
      LinearLayoutManager.VERTICAL,
      false
    )
  }


}