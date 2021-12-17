package com.note.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.note.order.R
import com.note.order.entity.OrderItems
import kotlinx.android.synthetic.main.row_order_history_list.view.*

class OrderHistoryAdapter(private val itemList: List<OrderItems>,private val itemClickListener: ItemClickListener): RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_order_history_list,parent,false))
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bindView(itemList[position])
    }

    inner class OrderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val cxt = itemView.context
        fun bindView(item: OrderItems){

            itemView.tvCustomerName.text = item.customerName
            Glide.with(cxt).load(cxt.getDrawable(R.drawable.ic_user_acc)).into(itemView.ivCustomer)

            itemView.tvTotalAmount.text = if(item.totalQty > 1){
                "${item.totalQty} Items, ${item.totalPrice} Ks"
            }else{
                "${item.totalQty} Item, ${item.totalPrice} Ks"
            }

            Glide.with(cxt).load(cxt.getDrawable(R.drawable.ic_dollar)).into(itemView.ivPayment)
            itemView.tvPaymentStatus.text = item.paymentStatus

            Glide.with(cxt).load(cxt.getDrawable(R.drawable.ic_landing_item_background)).into(itemView.ivProductImage)

            itemView.setOnClickListener {
                itemClickListener.onOrderItemClick(adapterPosition,itemList[adapterPosition])
            }

        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    interface ItemClickListener{
        fun onOrderItemClick(position: Int,item: OrderItems)
    }
}