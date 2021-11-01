package br.com.cotemig.italo.party.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.User

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val user = intent.getSerializableExtra("user") as User

  }
}