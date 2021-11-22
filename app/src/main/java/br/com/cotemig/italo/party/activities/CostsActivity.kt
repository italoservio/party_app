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
import br.com.cotemig.italo.party.adapters.CostsAdapter
import br.com.cotemig.italo.party.adapters.MembersAdapter
import br.com.cotemig.italo.party.models.Cost
import br.com.cotemig.italo.party.models.Invite
import br.com.cotemig.italo.party.models.Party
import br.com.cotemig.italo.party.models.User
import br.com.cotemig.italo.party.services.RetrofitParty
import retrofit2.Call
import retrofit2.Response

class CostsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_costs)

        val party = intent.getSerializableExtra("party") as Party
        val user = intent.getSerializableExtra("user") as User


        setupActivity(user, party)
    }

    private fun setupActivity(user: User, party: Party) {

        getPartyCosts(user, party)


        val costButton = findViewById<Button>(R.id.button_cost)
        costButton.setOnClickListener() {
            addCosts(user, party)
        }
    }

    private fun getPartyCosts(user: User, party: Party) {
        val service = RetrofitParty().serviceParty()
        val call = service.getById(party.id, user.token)
        call.enqueue(object : retrofit2.Callback<Party> {
            override fun onResponse(call: Call<Party>, response: Response<Party>) {
                if (response.code() == 200) {
                    response.body()?.let { fillCosts(it) }

                } else {
                    Toast.makeText(
                        this@CostsActivity,
                        "Não foi possível adicionar o membro a festa.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Party>, t: Throwable) {
                Toast.makeText(
                    this@CostsActivity,
                    "Um erro inesperado ocorreu",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        return
    }


    private fun addCosts(user: User, party: Party) {
        var description = findViewById<EditText>(R.id.input_description)
        var price = findViewById<EditText>(R.id.input_price)
        var cost = Cost()

        cost.description = description.text.toString()
        cost.price = price.text.toString().toDouble()
        cost.party_id = party.id;

        val service = RetrofitParty().serviceParty()
        val call = service.cost(cost, user.token)
        call.enqueue(object : retrofit2.Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.code() == 201) {
                    val intent = Intent(this@CostsActivity, MainActivity::class.java)
                    intent.putExtra("user", user)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@CostsActivity,
                        "Não foi possível adicionar o membro a festa.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@CostsActivity,
                    "Um erro inesperado ocorreu",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun fillCosts(party: Party) {
        val list = findViewById<RecyclerView>(R.id.list)

        if (party.costs != null) {
            list.adapter = CostsAdapter(this, party.costs!!)

            list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }


}