package br.com.cotemig.italo.party.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.Party

class PartyAdapter(
  var context: Context,
  var list: List<Party>,
  var onClickAction: (Party) -> Unit
) : RecyclerView.Adapter<PartyAdapter.PartyHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyAdapter.PartyHolder {
    var view = LayoutInflater.from(context).inflate(R.layout.activity_main_list_item, parent, false)
    return PartyHolder(view)
  }

  override fun onBindViewHolder(holder: PartyAdapter.PartyHolder, position: Int) {
    holder.bind(list[position], onClickAction)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  class PartyHolder(var view: View) : RecyclerView.ViewHolder(view) {
    fun bind(party: Party, onClickAction: (Party) -> Unit) {
      var card = view.findViewById<RelativeLayout>(R.id.card)
      card.setOnClickListener { onClickAction(party) }

      var name = view.findViewById<TextView>(R.id.party_name)
      name.text = party.name
      var location = view.findViewById<TextView>(R.id.party_location)
      location.text = party.location

      var peopleAmount = view.findViewById<TextView>(R.id.party_people_amount)
      peopleAmount.text = party.total_members.toString() + "/" + party.max_members.toString()
      party.max_members?.let { max_members ->
        party.total_members?.let { it ->
          if (compareValues(max_members, it) < 0) {
            peopleAmount.setTextColor(Color.parseColor("#CA6B6B"))
          }
        }
      }

      var costAmount = view.findViewById<TextView>(R.id.party_cost_amount)

      var actualCost = party.total_cost
      if (party.total_cost == null) {
        actualCost = 0.toFloat();
      }
      costAmount.text = actualCost.toString() + "/" + party.max_cost.toString()
      party.max_cost?.let { max_cost ->
        party.total_cost?.let { it ->
          if (compareValues(max_cost, it) < 0) {
            costAmount.setTextColor(Color.parseColor("#CA6B6B"))
          }
        }
      }
    }
  }
}