package br.com.cotemig.italo.party.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.Members

class MembersAdapter(var context: Context, var list : List<Members>) : RecyclerView.Adapter<MembersAdapter.MembersHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MembersAdapter.MembersHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.activity_members_list_item, parent, false)
        return MembersHolder(view)
    }

    override fun onBindViewHolder(holder: MembersAdapter.MembersHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MembersHolder(var view : View) : RecyclerView.ViewHolder(view){
        fun bind (member : Members ){

            var name = view.findViewById<TextView>(R.id.member_name)
            name.text = member.name

        }
    }

}