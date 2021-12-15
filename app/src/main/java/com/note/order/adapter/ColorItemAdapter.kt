package com.note.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.note.order.R
import com.note.order.entity.ColorItem
import kotlinx.android.synthetic.main.rc_adapter_color_item.view.*

class ColorItemAdapter(private val itemList: MutableList<ColorItem>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ColorItemAdapter.BindViewHolder>() {


    interface ItemClickListener{
        fun onColorItemClick(adapterPosition: Int,id: Int)
        fun onColorItemLongClick(adapterPosition: Int,id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rc_adapter_color_item,parent,false))
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.bindView(itemList[position])
    }

    inner class BindViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(item: ColorItem){

            itemView.tvColorName.text = item.name

            itemView.baseLayout.setBackgroundColor(item.color.toInt())

            itemView.setOnClickListener {
                item.id?.let { it1 -> itemClickListener.onColorItemClick(adapterPosition , it1) }
            }

            itemView.setOnLongClickListener {
                itemClickListener.onColorItemLongClick(adapterPosition,item.id)
                return@setOnLongClickListener true
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}