package br.com.cotemig.italo.party.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.adapters.PartyAdapter
import br.com.cotemig.italo.party.models.Party
import br.com.cotemig.italo.party.models.User
import br.com.cotemig.italo.party.services.RetrofitParty
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
  private var clicked : Boolean = false


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val user = intent.getSerializableExtra("user") as User
    var mainfab = findViewById<FloatingActionButton>(R.id.main_fab)
    var addfab = findViewById<FloatingActionButton>(R.id.add_fab)
    var bellfab = findViewById<FloatingActionButton>(R.id.bell_fab)

    mainfab.setOnClickListener{
      onMainButtonClick(mainfab, addfab, bellfab)
    }
    addfab.setOnClickListener{
      val intent = Intent(this, DetailsPartyActivity::class.java)
      intent.putExtra("user", user)
      startActivity(intent)
    }

    loadParties(user);
  }
  private fun onMainButtonClick(mainfab: FloatingActionButton, addfab: FloatingActionButton, bellfab: FloatingActionButton){
    setVisibility(clicked,mainfab, addfab, bellfab)
    setAnimation(clicked,mainfab, addfab, bellfab)
    clicked = !clicked
  }

  private fun setAnimation(clicked: Boolean, mainfab: FloatingActionButton, addfab: FloatingActionButton, bellfab: FloatingActionButton) {
    val fabOpen : Animation =  AnimationUtils.loadAnimation(this, R.anim.fab_open)
    val fabClose : Animation =  AnimationUtils.loadAnimation(this, R.anim.fab_close)
    val rotateForward : Animation =  AnimationUtils.loadAnimation(this, R.anim.rotate_forward)
    val rotateBackward : Animation =  AnimationUtils.loadAnimation(this, R.anim.rotate_backward)
    if (!clicked){
      mainfab.startAnimation(rotateForward)
      addfab.startAnimation(fabOpen)
      bellfab.startAnimation(fabOpen)
    }else{
      mainfab.startAnimation(rotateBackward)
      addfab.startAnimation(fabClose)
      bellfab.startAnimation(fabClose)
    }
  }

  private fun setVisibility(clicked: Boolean, mainfab: FloatingActionButton, addfab: FloatingActionButton, bellfab: FloatingActionButton) {
    if (!clicked){
      addfab.visibility = View.VISIBLE
      bellfab.visibility = View.VISIBLE
    }else{
      addfab.visibility = View.GONE
      bellfab.visibility = View.GONE
    }
  }


  private fun loadParties(user : User){
    val service = RetrofitParty().serviceUser()
    val call = service.list(user.token)

    call.enqueue(object: retrofit2.Callback<List<Party>> {
      override fun onResponse(call: Call<List<Party>>, response: Response<List<Party>>) {
        if (response.code() == 200) {
          response.body()?.let { body ->
            fillParties(body, user)
          }
        } else {
          Toast.makeText(
            this@MainActivity,
            "Não foi possível listar as festas.",
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<List<Party>>, t: Throwable) {
        Toast.makeText(
          this@MainActivity,
          "Um erro inesperado ocorreu",
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }

  private fun fillParties(parties : List<Party>, user : User){
    val list = findViewById<RecyclerView>(R.id.list)
    list.adapter = PartyAdapter(this, parties,
      {
          party -> val intent = Intent(this, DetailsPartyActivity::class.java)
          intent.putExtra("party", party)
          intent.putExtra("user", user)
          startActivity(intent)
      })

    list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
  }
}