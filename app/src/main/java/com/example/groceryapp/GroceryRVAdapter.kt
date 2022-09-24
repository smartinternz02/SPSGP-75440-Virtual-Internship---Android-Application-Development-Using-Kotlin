package com.example.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryRVAdapter(
    var list:List<GroceryItems>,
    val groceryItemClickInterface:
    GroceryItemClickInterface ): RecyclerView.Adapter<GroceryRVAdapter.GroceryViewHolder>() {

    inner class GroceryViewHolder(iteamView: View):RecyclerView.ViewHolder(iteamView){
        val nameTv=iteamView.findViewById<TextView>(R.id.idRVItemName)
        val quantityTv=iteamView.findViewById<TextView>(R.id.idTVQuantity)
        val rateTv=iteamView.findViewById<TextView>(R.id.idTVRate)
        val amountTv=iteamView.findViewById<TextView>(R.id.idTVTotalAmt)
        val deleteIv=iteamView.findViewById<ImageView>(R.id.idIVDelete)


    }



    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_item,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.nameTv.text=list.get(position).itemName
        holder.quantityTv.text= list.get(position).itemQuality.toString()
        holder.rateTv.text="Rs."+list.get(position).itemPrice.toString()
        val itemTotal:Int =list.get(position).itemPrice*list.get(position).itemQuality
        holder.amountTv.text="Rs."+itemTotal.toString()
        holder.deleteIv.setOnClickListener{
            groceryItemClickInterface.onItemClick(list.get(position))
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }
}