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

class MyInvitesAdapter (var context: Context, var list : List<Party>, var onClickAccept: (Party) -> Unit, var onClickDeny: (Party) -> Unit) : RecyclerView.Adapter<MyInvitesAdapter.invitesHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyInvitesAdapter.invitesHolder {
            var view = LayoutInflater.from(context).inflate(R.layout.activity_my_invites_list_item, parent, false)
            return invitesHolder(view)
        }

        override fun onBindViewHolder(holder: MyInvitesAdapter.invitesHolder, position: Int) {
            holder.bind(list[position], onClickAccept, onClickDeny )
        }

        override fun getItemCount(): Int {
            return list.size
        }

        class invitesHolder(var view : View)  : RecyclerView.ViewHolder(view){
            fun bind (party : Party, onClickAccept: (Party) -> Unit, onClickDeny: (Party) -> Unit){

                var button_accept = view.findViewById<Button>(R.id.button_accept)
                var button_deny = view.findViewById<Button>(R.id.button_deny)
                var card = view.findViewById<RelativeLayout>(R.id.card)
                var partyName = view.findViewById<TextView>(R.id.party_name)
                partyName.text = party.name

                button_accept.setOnClickListener { onClickAccept(party) }
                button_deny.setOnClickListener { onClickDeny(party) }

                if(party.accepted == 1) {
                    button_accept.setVisibility(View.INVISIBLE)
                    button_deny.setVisibility(View.INVISIBLE)
                }

                if(party.accepted == -1) {
                    card.setVisibility(View.INVISIBLE)
                }
            }
        }


}