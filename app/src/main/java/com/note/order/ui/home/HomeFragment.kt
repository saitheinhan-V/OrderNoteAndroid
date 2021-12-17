package com.note.order.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.note.order.R
import com.note.order.adapter.OrderHistoryAdapter
import com.note.order.entity.OrderItems
import com.note.order.ui.order.OrderActivity
import com.note.order.utils.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), OrderHistoryAdapter.ItemClickListener {

    private lateinit var orderHistoryList: ArrayList<OrderItems>
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderHistoryList = ArrayList()
        orderHistoryList.add(OrderItems(1,1,1,"","","","","",
            1,"","","","",0.0,1))

        orderHistoryAPICall()

        ivAdd.setOnClickListener {
            val intent = Intent(requireContext(),OrderActivity::class.java)
            intent.putExtra("route",1)
            startActivity(intent)
        }
    }

    private fun orderHistoryAPICall() {
        orderHistoryAdapter = OrderHistoryAdapter(orderHistoryList,this)
        rvOrder.adapter = orderHistoryAdapter
        rvOrder.setHasFixedSize(true)
        orderHistoryAdapter.notifyDataSetChanged()
    }

    override fun onOrderItemClick(position: Int, item: OrderItems) {
        val intent = Intent(requireContext(),OrderActivity::class.java)
        intent.putExtra("route",2)
        startActivity(intent)
    }
}