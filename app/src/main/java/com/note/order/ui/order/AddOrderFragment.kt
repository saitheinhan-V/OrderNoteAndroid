package com.note.order.ui.order

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_add_order.*
import kotlinx.android.synthetic.main.layout_customer_info.view.*

import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import com.note.order.R
import org.jetbrains.anko.textColor


class AddOrderFragment : Fragment() {

    companion object {

    }

    private var step = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_order, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener {
            activity?.finish()
        }

        if(step == 1){ //initialize value
            tvStepName.text = "Customer Info"
            tvStepOne.textColor = resources.getColor(R.color.colorPrimary)
        }

        tvNextStep.setOnClickListener {
            when(step){
                1 -> {
                    step = 2

                    tvStepName.text = "Delivery Info"
                    tvStepTwo.textColor = resources.getColor(R.color.colorPrimary)
                    ivStepTwo.setImageDrawable(resources.getDrawable(R.drawable.step_two_selected))
                    ivStepLineOneTwo.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.stepper_line_selected,resources.newTheme()))

                    customerInfoLayout.isVisible = false
                    deliveryInfoLayout.isVisible = true
                    productInfoLayout.isVisible = false

                }
                2 -> {
                    step = 3

                    tvStepName.text = "Product Info"
                    tvStepThree.textColor = resources.getColor(R.color.colorPrimary)
                    ivStepThree.setImageDrawable(resources.getDrawable(R.drawable.step_three_selected))
                    ivStepLineTwoThree.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.stepper_line_selected,resources.newTheme()))

                    customerInfoLayout.isVisible = false
                    deliveryInfoLayout.isVisible = false
                    productInfoLayout.isVisible = true
                }
                3 -> {
                    tvStepName.text = "Product Info"
                    //check for upload Order

                    activity?.finish()
                }
                else -> {

                }
            }
        }

        customerInfoLayout.toggleButtonGroup.isSelectionRequired = true
//        val buttonId: Int = materialButtonToggleGroup.getCheckedButtonId()
        customerInfoLayout.toggleButtonGroup.addOnButtonCheckedListener(OnButtonCheckedListener { group, checkedId, isChecked ->
//            if (isChecked) {
                if (group.checkedButtonId == customerInfoLayout.toggleNewCustomer.id) { //new customer
                    //..
                    customerInfoLayout.layoutNewCustomerEntry.isVisible = true
                }else{ //old customer
                    customerInfoLayout.layoutNewCustomerEntry.isVisible = false
                    Log.d("test", "onViewCreated: sss")
                }
//            }
        })
    }

}