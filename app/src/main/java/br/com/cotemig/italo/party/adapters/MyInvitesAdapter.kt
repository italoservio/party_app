package br.com.cotemig.italo.party.adapters

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.Members
import br.com.cotemig.italo.party.models.Party

class MyInvitesAdapter(
  var context: Context,
  var list: List<Party>,
  var onClickAccept: (Party) -> Unit,
  var onClickDeny: (Party) -> Unit
) : RecyclerView.Adapter<MyInvitesAdapter.invitesHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): MyInvitesAdapter.invitesHolder {
    var view =
      LayoutInflater.from(context).inflate(R.layout.activity_my_invites_list_item, parent, false)
    return invitesHolder(view, context)
  }

  override fun onBindViewHolder(holder: MyInvitesAdapter.invitesHolder, position: Int) {
    holder.bind(list[position], onClickAccept, onClickDeny)
  }

  override fun getItemCount(): Int {
    return list.size
  }

  class invitesHolder(var view: View, var context: Context) : RecyclerView.ViewHolder(view) {
    fun bind(party: Party, onClickAccept: (Party) -> Unit, onClickDeny: (Party) -> Unit) {

      val button_accept = view.findViewById<Button>(R.id.button_accept)
      val button_deny = view.findViewById<Button>(R.id.button_deny)
      val text_status = view.findViewById<TextView>(R.id.text_status)
      val card = view.findViewById<RelativeLayout>(R.id.card)
      val partyName = view.findViewById<TextView>(R.id.party_name)
      partyName.text = party.name

      button_accept.setOnClickListener { onClickAccept(party) }
      button_deny.setOnClickListener { onClickDeny(party) }

      if (party.accepted == 1) {
        text_status.text = context.getString(R.string.my_invites_adapter_accepted);
        text_status.visibility = View.VISIBLE;
        button_accept.setVisibility(View.GONE)
        button_deny.setVisibility(View.GONE)
      }

      if (party.accepted == -1) {
        card.setVisibility(View.GONE)
      }
    }
  }


}