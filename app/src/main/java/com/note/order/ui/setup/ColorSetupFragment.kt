package com.note.order.ui.setup

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.note.order.R
import com.note.order.DAO.RoomDao
import com.note.order.adapter.ColorItemAdapter
import com.note.order.entity.ColorItem
import com.note.order.repository.DataRepository
import com.note.order.roomDatabase.AppDatabase
import com.note.order.viewModels.SetupViewModel
import com.note.order.viewModels.SetupViewModelFactory
import kotlinx.android.synthetic.main.dialog_color_catalog.view.*
import kotlinx.android.synthetic.main.fragment_color_setup.*
import kotlinx.android.synthetic.main.layout_back.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob


class ColorSetupFragment : Fragment(), ColorItemAdapter.ItemClickListener {

    private var newColor = 0
    private lateinit var db : AppDatabase
    private lateinit var dao : RoomDao

    private lateinit var colorList: MutableList<ColorItem>
    private lateinit var colorItemAdapter: ColorItemAdapter


    private val setupViewModel: SetupViewModel by activityViewModels {
        SetupViewModelFactory(DataRepository(AppDatabase.getDatabase(activity?.applicationContext!!, CoroutineScope(SupervisorJob())).roomDao()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_color_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//          db = Room.databaseBuilder(
//            view.context,
//            AppDatabase::class.java, "main.db"
//        ).build()
////        db = AppDatabase(view.context)
//        dao = db.roomDao()


        tvTitle.text = "Color Setup"
        colorList = ArrayList()

        ivBack.setOnClickListener {
            findNavController().navigateUp()
            activity?.finish()
        }

        //setUpRvColor()
        getAllColorList()

        fabColor.setOnClickListener {
            showColorPickerDialog("add",0)
        }

        ivAdd.setOnClickListener {
            showColorPickerDialog("add",0)
        }

    }

    private fun setUpRvColor() {
        colorItemAdapter = ColorItemAdapter(colorList,this)
        rvColor.adapter = colorItemAdapter
        rvColor.layoutManager = GridLayoutManager(context,2)
        //rvColor.setHasFixedSize(true)
        colorItemAdapter.notifyDataSetChanged()

    }

    private fun showColorPickerDialog(type: String,position: Int) {

        val layout = LayoutInflater.from(context).inflate(R.layout.dialog_color_catalog, null)
        val builder = AlertDialog.Builder(requireContext()).setView(layout)

        val dialog = builder.create()
        dialog.show()
        dialog.setCancelable(false)

        var name = ""
        var oldColor = ""
        var oldName = ""

        when(type){
            "add" -> {
                newColor = resources.getColor(R.color.colorPrimary)
            }
            "edit" -> {
                oldColor = colorList[position].color
                oldName = colorList[position].name
                name = oldName

                newColor = oldColor.toInt()
                layout.edtColorName.setText(oldName)
            }
        }

        layout.edtColorName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                name = if(!s.isNullOrEmpty()){
                    s.toString()
                }else ""
            }
        })

        layout.color_picker_view.setInitialColor(newColor,true)
        layout.viewColorChosen.setBackgroundColor(newColor)

        layout.color_picker_view.addOnColorSelectedListener {
            newColor = it
            layout.viewColorChosen.setBackgroundColor(newColor)
        }

        layout.tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        layout.tvOK.setOnClickListener {
            when(type){
                "add" -> {
                    if(newColor == 0)
                        Toast.makeText(context, "No color chosen!", Toast.LENGTH_LONG).show()
                    else {

                        if(name.isNullOrEmpty()){
                            layout.edtColorName.error = "name required"
                        }else{
                            var index = 1
                            if(!colorList.isNullOrEmpty()){
                                index = colorList[colorList.size-1].id + 1
                            }
                            val item = ColorItem(index,name,newColor.toString())
                            setupViewModel.insertColor(item)

                            dialog.dismiss()

                            getAllColorList()

                        }
                    }
                }
                "edit" -> {
                    var changed = false
                    if(oldColor != newColor.toString() || oldName != name){
                        changed = true
                    }

                    if(changed){
                        if(newColor == 0){
                            Toast.makeText(context, "No color chosen!", Toast.LENGTH_LONG).show()
                        }else {
                            if (name.isNullOrEmpty()) {
                                layout.edtColorName.error = "name required"
                            } else {
                                val updateItem = ColorItem(colorList[position].id, name, newColor.toString())
                                setupViewModel.updateColor(updateItem)
                                dialog.dismiss()
                                getAllColorList()
                            }
                        }
                    }else{
                        Toast.makeText(context,"No changes found!",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }

    private fun getAllColorList() {
        //GlobalScope.launch {
            //colorList = dao.getAllColorList()
            //colorItemAdapter.notifyDataSetChanged()
        //}
        colorList = ArrayList()
        setupViewModel.allColors.observe(viewLifecycleOwner) { words ->
            // Update the cached copy of the words in the adapter.
            words.let {
                colorList = it as ArrayList<ColorItem>
                Log.i("color",colorList.toString())
                setUpRvColor()
                //colorItemAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun saveToColorList(name: String, color: Int) {
//        db = Room.databaseBuilder(
//            activity?.applicationContext!!,
//            AppDatabase::class.java, "main.db"
//        ).build()
////        db = AppDatabase(view.context)
//        dao = db.roomDao()
        //GlobalScope.launch {
            //dao.saveNewColor(name,color.toString())
            //dao.saveNewColor(ColorItem(name,color.toString()))
        //}
    }

    override fun onColorItemClick(adapterPosition: Int, id: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_popup_color_setup,null)

        view.findViewById<TextView>(R.id.tvDelete).setOnClickListener {
            setupViewModel.deleteColor(colorList[adapterPosition])
            getAllColorList()
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.tvEdit).setOnClickListener {
            dialog.dismiss()
            showColorPickerDialog("edit",adapterPosition)
        }

        view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun onColorItemLongClick(adapterPosition: Int, id: Int) {


    }


}