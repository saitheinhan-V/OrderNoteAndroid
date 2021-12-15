package com.note.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.note.order.R
import com.note.order.entity.SetupItems
import com.note.order.utils.ColorUtil
import kotlinx.android.synthetic.main.rc_adapter_setup_list_item.view.*

class SetupAdapter(private val items: MutableList<SetupItems>,private  val itemClickListener: ItemClickListener) : RecyclerView.Adapter<SetupAdapter.BindViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder{
        return BindViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rc_adapter_setup_list_item,parent,false))
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
         return items.size
    }

    inner class BindViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bind(item: SetupItems){

            itemView.tvName.text = item.name
            ///if(!item.imageLink.isNullOrEmpty()){
//                Glide.with(itemView.context).load(item.imageLink).placeholder(itemView.resources.getDrawable(R.drawable.ic_default_profile)).into(itemView.ivItem)
            //}

//            if(item.count == 0){
//                itemView.tvCount.isVisible = false
//            }else{
                itemView.tvCount.isVisible = true
                itemView.tvCount.text = item.count.toString()
            //itemView.tvCount.setBackgroundColor(ColorUtil().getRandomColor())
            itemView.layoutCount.setBackgroundColor(ColorUtil().getRandomColor())
//            }

            itemView.setOnClickListener{
                itemClickListener.onSetupItemClick(adapterPosition, item.id)
            }
        }
    }

    interface ItemClickListener {
        fun onSetupItemClick(position: Int,id: Int)
    }
}