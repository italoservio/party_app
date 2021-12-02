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
import javax.security.auth.callback.Callback

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

    val submitButton = findViewById<Button>(R.id.button_submit)
    submitButton.setOnClickListener() {
      submit()
    }
  }

  private fun submit() {
    var name = findViewById<EditText>(R.id.input_name)
    var email = findViewById<EditText>(R.id.input_email)
    var pass = findViewById<EditText>(R.id.input_password)

    var user = User()
    user.name = name.text.toString()
    user.email = email.text.toString()
    user.pass = pass.text.toString()

    val service = RetrofitParty().serviceUser()
    val call = service.create(user)

    call.enqueue(object : retrofit2.Callback<Void> {
      override fun onResponse(call: Call<Void>, response: Response<Void>) {
        if (response.code() == 201) {
          Toast.makeText(
            this@SignUpActivity,
            R.string.generic_success,
            Toast.LENGTH_LONG
          ).show()
          val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
          startActivity(intent)
          finish()
        } else {
          Toast.makeText(
            this@SignUpActivity,
            R.string.sign_up_activity_error_create_user,
            Toast.LENGTH_LONG
          ).show()
        }
      }

      override fun onFailure(call: Call<Void>, t: Throwable) {
        Toast.makeText(
          this@SignUpActivity,
          R.string.generic_unexpected_error,
          Toast.LENGTH_LONG
        ).show()
      }

    })

  }

}