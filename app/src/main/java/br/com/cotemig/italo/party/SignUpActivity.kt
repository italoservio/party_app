package br.com.cotemig.italo.party

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sign_up)
    this.setupActivity()
  }

  private fun setupActivity() {
    val backToLoginLink = findViewById<TextView>(R.id.link_back)
    backToLoginLink.setOnClickListener() {
      val intent = Intent(this, SignInActivity::class.java)
      startActivity(intent)
    }
  }
}