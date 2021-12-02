package br.com.cotemig.italo.party.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.User
import br.com.cotemig.italo.party.services.RetrofitParty
import retrofit2.Call
import retrofit2.Response

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

    val submitButton = findViewById<Button>(R.id.button_submit)
    submitButton.setOnClickListener() {
      submit()
    }
  }

  private fun submit() {
    var email = findViewById<EditText>(R.id.input_email)
    var pass = findViewById<EditText>(R.id.input_password)

    var user = User()
    user.email = email.text.toString()
    user.pass = pass.text.toString()

    val service = RetrofitParty().serviceUser()
    val call = service.authenticate(user)

    call.enqueue(object : retrofit2.Callback<User> {
      override fun onResponse(call: Call<User>, response: Response<User>) {
        if (response.code() == 200) {
          response.body()?.let { body ->
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            intent.putExtra("user", body)
            startActivity(intent)
            finish()
          }
        } else {
          Toast.makeText(
            this@SignInActivity,
            R.string.sign_in_activity_error_login,
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<User>, t: Throwable) {
        Toast.makeText(
          this@SignInActivity,
          R.string.generic_unexpected_error,
          Toast.LENGTH_LONG
        ).show()
      }
    })
  }

}