package br.com.cotemig.italo.party.models

import java.io.Serializable

class Party: Serializable {
    var id: Number? = null
    var name: String? = null
    var location: String? = null
    var max_members: Int? = null
    var max_cost: Float? = null
    var total_members: Int? = null
    var total_cost: Float? = null

}