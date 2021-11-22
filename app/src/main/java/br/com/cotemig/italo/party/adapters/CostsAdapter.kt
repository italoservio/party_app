package br.com.cotemig.italo.party.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.cotemig.italo.party.R
import br.com.cotemig.italo.party.models.Cost

class CostsAdapter (var context: Context, var list : List<Cost>?) : RecyclerView.Adapter<CostsAdapter.CostsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostsAdapter.CostsHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.activity_costs_list_item, parent, false)
        return CostsHolder(view)
    }

    override fun onBindViewHolder(holder: CostsAdapter.CostsHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class CostsHolder(var view : View) : RecyclerView.ViewHolder(view){
        fun bind (cost : Cost){
            var price = view.findViewById<TextView>(R.id.cost_price)
            price.text = cost.price.toString()
        }
    }
}