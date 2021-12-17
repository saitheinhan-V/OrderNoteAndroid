package com.note.order.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.note.order.R
import com.note.order.adapter.SetupAdapter
import com.note.order.entity.SetupItems
import com.note.order.ui.setting.SettingActivity
import com.note.order.ui.setup.SetupActivity
import com.note.order.utils.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_setup.*

class SetupFragment : Fragment() , SetupAdapter.ItemClickListener {

    private lateinit var setupItemList: ArrayList<SetupItems>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return  inflater.inflate(R.layout.fragment_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupItemList = ArrayList()
        setupItemList.add(SetupItems(0,"Customer","",0))
        setupItemList.add(SetupItems(1,"Color","",1))
        setupItemList.add(SetupItems(2,"City","",0))
        setupItemList.add(SetupItems(3,"Payment","",5))
        setupItemList.add(SetupItems(4,"Gender","",0))
        setupItemList.add(SetupItems(5,"Size","",0))
        setupItemList.add(SetupItems(6,"Brand","",0))
        setupItemList.add(SetupItems(7,"Image","",0))

        rvSetup.adapter = SetupAdapter(setupItemList,this)
        rvSetup.setHasFixedSize(true)
        rvSetup.layoutManager = GridLayoutManager(activity,2)

        userCardView.setSafeOnClickListener {
            val intent = Intent(requireContext(),SettingActivity::class.java)
            intent.putExtra("route",1)
            startActivity(intent)
        }
    }


    override fun onSetupItemClick(position: Int, id: Int) {
        val intent = Intent(context,SetupActivity::class.java)
        intent.putExtra("id",id)
        startActivity(intent)
    }

}