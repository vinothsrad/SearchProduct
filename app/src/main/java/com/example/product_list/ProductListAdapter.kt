package com.example.product_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.product_list.model.productlistItem

class ProductListAdapter(val con: Context, var list: ArrayList<productlistItem>):RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

      private val fullList=ArrayList<productlistItem>()
        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            var img= itemView.findViewById<ImageView>(R.id.profile_image)
            var tvName = itemView.findViewById<TextView>(R.id.tv_name)
            var tvPrice = itemView.findViewById<TextView>(R.id.tv_price)
            var fulclik=itemView.findViewById<RelativeLayout>(R.id.fullclick)
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view= LayoutInflater.from(con).inflate(R.layout.list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(con).load(list[position].image).into(holder.img)
        holder.tvName.text=list[position].title
        holder.tvPrice.text= list[position].price.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getFilter(): Filter? {
        return object : Filter() {
             override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val searchString = charSequence.toString()
                if (searchString.isEmpty()) {
                    list = list
                } else {
                    val tempFilteredList: ArrayList<productlistItem> = ArrayList()
                    for (user in list) {

                        // search for user title
                        if (user.title.toLowerCase().contains(searchString)) {
                            tempFilteredList.add(user)
                        }
                    }
                    list = tempFilteredList
                }
                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }

             override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults
            ) {
                list = filterResults.values as ArrayList<productlistItem>
                notifyDataSetChanged()
            }
        }
    }


}


