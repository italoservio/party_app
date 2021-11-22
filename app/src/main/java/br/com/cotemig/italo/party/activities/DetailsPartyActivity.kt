package br.com.cotemig.italo.party.activities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.Party
import br.com.cotemig.italo.party.models.User
import br.com.cotemig.italo.party.services.RetrofitParty
import retrofit2.Call
import retrofit2.Response

class DetailsPartyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_party)

        val user = intent.getSerializableExtra("user") as User?
        val party = intent.getSerializableExtra("party") as Party?

        if (party != null) {
            completeScreen(party)
        }

        this.setupActivity(user, party)
    }

    private fun setupActivity(user : User?, party : Party?) {
        val addCost = findViewById<Button>(R.id.button_add_cost)

        addCost.setOnClickListener() {

                val intent = Intent(this, CostsActivity::class.java)
                intent.putExtra("party", party)
                intent.putExtra("user", user)
                startActivity(intent)
        }

        val inviteMember = findViewById<Button>(R.id.button_invite_member)
        inviteMember.setOnClickListener() {

                val intent = Intent(this, MembersActivity::class.java)
                intent.putExtra("party", party)
                intent.putExtra("user", user)
                startActivity(intent)

        }

        val submit = findViewById<Button>(R.id.button_submit)
        submit.setOnClickListener() {
            createParty(user)
        }

        val back = findViewById<Button>(R.id.button_back)
        back.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }
    private fun completeScreen(party : Party){
        var addCost = findViewById<Button>(R.id.button_add_cost)
        addCost.visibility = View.VISIBLE
        var inviteMember = findViewById<Button>(R.id.button_invite_member)
        inviteMember.visibility = View.VISIBLE
        var back = findViewById<Button>(R.id.button_back)
        back.visibility = View.VISIBLE
        var submit = findViewById<Button>(R.id.button_submit)
        submit.visibility = View.GONE
        var partyName = findViewById<EditText>(R.id.input_party_name)
        partyName.setText(party.name)
        partyName.setEnabled(false)
        var location = findViewById<EditText>(R.id.input_location)
        location.setText(party.location)
        location.setEnabled(false)
        var maxMembers = findViewById<EditText>(R.id.input_max_members)
        maxMembers.setText(party.max_members.toString())
        maxMembers.setEnabled(false)
        var maxCost= findViewById<EditText>(R.id.input_max_cost)
        maxCost.setText(party.max_cost.toString())
        maxCost.setEnabled(false)
    }

    private fun createParty(user : User?){
        var name = findViewById<EditText>(R.id.input_party_name)
        var location = findViewById<EditText>(R.id.input_location)
        var maxmembers = findViewById<EditText>(R.id.input_max_members)
        var maxcost = findViewById<EditText>(R.id.input_max_cost)

        var party = Party()
        party.name = name.text.toString()
        party.location = location.text.toString()
        party.max_members = maxmembers.text.toString().toInt()
        party.max_cost = maxcost.text.toString().toFloat()

        val service = RetrofitParty().serviceParty()
        Log.i("party", party.max_members.toString())
        Log.i("user", user?.token.toString())
        val call = service.createParty(party, user?.token.toString())

        call.enqueue(object: retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 201) {
                    Toast.makeText(
                        this@DetailsPartyActivity,
                        "Sucesso!",
                        Toast.LENGTH_LONG
                    ).show()
                    val intent = Intent(this@DetailsPartyActivity, MainActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@DetailsPartyActivity,
                        "Não foi possível criar a festa.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@DetailsPartyActivity,
                    "Um erro inesperado ocorreu",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}