package br.com.cotemig.italo.party.models

import java.io.Serializable

class User: Serializable {
  var id: Number? = null
  var name: String? = null
  var email: String? = null
  var pass: String? = null
  var token: String? = null
}