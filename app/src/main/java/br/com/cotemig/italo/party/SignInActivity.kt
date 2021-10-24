package br.com.cotemig.italo.party

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignInActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_in)
    this.setupActivity()
  }

  private fun setupActivity() {
    val signUpLink = findViewById<TextView>(R.id.link_sign_up)
    signUpLink.setOnClickListener() {
      val intent = Intent(this, SignUpActivity::class.java)
      startActivity(intent)
    }
  }

}