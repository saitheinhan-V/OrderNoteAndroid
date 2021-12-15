package com.note.order.ui.setup

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.note.order.GlobalConstant
import com.note.order.R
import com.note.order.adapter.BrandItemAdapter
import com.note.order.entity.BrandItem
import com.note.order.repository.DataRepository
import com.note.order.roomDatabase.AppDatabase
import com.note.order.viewModels.SetupViewModel
import com.note.order.viewModels.SetupViewModelFactory
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.android.synthetic.main.dialog_brand_setup.*
import kotlinx.android.synthetic.main.fragment_brand_setup.*
import kotlinx.android.synthetic.main.layout_back.*
import androidx.activity.result.ActivityResultLauncher
import android.graphics.Bitmap
import android.util.Log

import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import com.note.order.utils.BitmapUtil
import com.note.order.utils.hideKeyboard
import com.note.order.viewModels.SetupAPIViewModel
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.*
import okhttp3.*


class BrandSetupFragment : Fragment(), BrandItemAdapter.ItemClickListener {

    private lateinit var brandList: MutableList<BrandItem>

    private var newAdded = true
    private var brandName = ""
    private var newImage = ""
    private var type = 0
    private lateinit var url: URL
    private lateinit var conn: HttpURLConnection
    private lateinit var jsonObject: JSONObject
    private lateinit var okHttpClient: OkHttpClient

    private var encodedString = ""
    private var imageByte: ByteArray? = null
    private lateinit var currentBrandItem: BrandItem

    private var activityResultLauncher: ActivityResultLauncher<Intent>? = null

    private val setupViewModel: SetupViewModel by activityViewModels {
        SetupViewModelFactory(
            DataRepository(
                AppDatabase.getDatabase(
                    activity?.applicationContext!!, CoroutineScope(
                        SupervisorJob()
                    )
                ).roomDao()
            )
        )
    }

    private val apiViewModel: SetupAPIViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_brand_setup, container, false)
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        brandList = ArrayList()



        activityResultLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent = result.data!!
                if (type == 0) {
                    ivBrandImage.setImageURI(data.data)

//                    val source = context?.let { ImageDecoder.createSource(it.contentResolver, data.data!!) }
//                    val bitmap = ImageDecoder.decodeBitmap(source!!)
//
//                    ivBrandImage.setImageBitmap(bitmap)
//
//                    imageByte = BitmapUtil().convertToByteArray(bitmap)

                    imageByte = BitmapUtil().encode(requireContext(), data.data!!)

                } else if (type == 1) {
                    val bitmap = data!!.extras!!.get("data") as Bitmap
                    //val compressBitmap = BitmapUtil().zoomBitmap(bitmap,ivBrandImage.width,ivBrandImage.height)
                    ivBrandImage.setImageBitmap(bitmap)

//                    encodedString = BitmapUtil().bitmapToBase64EncodedStr(bitmap)
                    imageByte = BitmapUtil().convertToByteArray(bitmap)
                    Log.i("byte", ">>>>>>Camera $imageByte")
                }
                ivBrandImage.isVisible = true
                ivUpload.isVisible = false
            } else if (result.resultCode == RESULT_CANCELED) {
                ivBrandImage.isVisible = false
                ivUpload.isVisible = true
            }
        }

        tvTitle.text = "Brand Setup"

        ivBack.setOnClickListener {
            findNavController().navigateUp()
            hideKeyboard()
            activity?.finish()
        }


        ivUpload.isVisible = newAdded
        ivBrandImage.isVisible = !newAdded


        ivAdd.setOnClickListener {
            //showDialog("add",0)
            layoutAdd.isVisible = true
            newAdded = true
        }

        getAllBrandList()

        edtBrandName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                brandName = if (!s.isNullOrEmpty())
                    s.toString()
                else ""
            }

        })

        tvCancel.setOnClickListener {
            layoutAdd.isVisible = false
            edtBrandName.clearFocus()
            hideKeyboard()

        }

        tvOK.setOnClickListener {
            if(newAdded) { //save new
                var index = 1
                if (!brandList.isNullOrEmpty()) {
                    index = brandList[brandList.size - 1].id + 1
                }

                if (brandName.isNullOrEmpty()) {
                    edtBrandName.error = "Please input brand name!"
                } else {
                    //val str = String(imageByte!!, StandardCharsets.UTF_8)
                    val item = BrandItem(index, brandName, "")
                    Log.i("new", "$item")

                    setupViewModel.insertBrand(item)

                    layoutAdd.isVisible = false

                    edtBrandName.setText("")
                    edtBrandName.clearFocus()
                    hideKeyboard()


                    getAllBrandList()
                }
            }else{ //update
                if (brandName.isNullOrEmpty()) {
                    edtBrandName.error = "Please input brand name!"
                } else {
                    val item = BrandItem(currentBrandItem.id, brandName, "")

                    setupViewModel.updateBrand(item)

                    layoutAdd.isVisible = false

                    edtBrandName.setText("")
                    edtBrandName.clearFocus()
                    hideKeyboard()

                    getAllBrandList()
                }
            }
        }

        ivUpload.setOnClickListener {
            showImageSelectDialog()
        }

        ivBrandImage.setOnClickListener {
            showImageSelectDialog()
        }

    }

    private fun showImageSelectDialog() {

        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_popup_upload_image, null)

        view.findViewById<TextView>(R.id.tvAlbum).setOnClickListener {
            //setupViewModel.deleteColor(colorList[adapterPosition])
            //getAllColorList()
            dialog.dismiss()

            AndPermission.with(requireActivity())
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onGranted { permission ->
                    chooseAlbum()
                }
                .onDenied { permission ->
                    Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_LONG).show()
                }
                .start()
        }

        view.findViewById<TextView>(R.id.tvCamera).setOnClickListener {
            dialog.dismiss()
            AndPermission.with(requireActivity())
                .runtime()
                .permission(Permission.Group.CAMERA)
                .onGranted { permission ->
                    takePicture()
                }
                .onDenied { permission ->
                    Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_LONG).show()
                }
                .start()
        }

        view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun takePicture() {
        type = 1
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //startActivityForResult(cameraIntent, GlobalConstant().TAKE_PHOTO)
        cameraIntent.putExtra("type", GlobalConstant().TAKE_PHOTO)
        activityResultLauncher?.launch(cameraIntent)
    }

    private fun chooseAlbum() {
        type = 0
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        //startActivityForResult(photoPickerIntent, GlobalConstant().CHOOSE_ALBUM)
        photoPickerIntent.putExtra("type", GlobalConstant().CHOOSE_ALBUM)
        activityResultLauncher?.launch(photoPickerIntent)

    }

    private fun showDialog(type: String, position: Int) {
        val view = layoutInflater.inflate(R.layout.dialog_brand_setup, null)
        val builder = AlertDialog.Builder(requireContext(), R.style.MyDialog)

        builder.setView(view)
        val dialog = builder.create()

        var brandName = ""

        val tvCancel = view.findViewById<TextView>(R.id.tvCancel)
        val tvOK = view.findViewById<TextView>(R.id.tvOK)
        val edtBrandName = view.findViewById<EditText>(R.id.edtBrandName)
        val ivUpload = view.findViewById<ImageView>(R.id.ivUpload)
        val ivImage = view.findViewById<ImageView>(R.id.ivBrandImage)


        tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        tvOK.setOnClickListener {

            dialog.dismiss()
        }

        edtBrandName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                brandName = if (!s.isNullOrEmpty())
                    s.toString()
                else ""
            }

        })

        ivUpload.setOnClickListener {

        }

        dialog.setCancelable(false)
        dialog.show()
    }

    private fun getAllBrandList() {

        brandList = ArrayList()
        setupViewModel.allBrands.observe(viewLifecycleOwner){
            it.let {
                brandList = it as ArrayList<BrandItem>
                Log.i("br","List $brandList")
                setUpGridView()
            }
        }
//        apiViewModel.getAllBrand().observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Resource.Status.LOADING -> {
//                    //show some loading
//                }
//                Resource.Status.ERROR -> {
//                }
//                Resource.Status.SUCCESS -> {
//                    it.let {
//                        val itemType = object : TypeToken<List<BrandItem>>() {}.type
//                        //brandList = Gson().fromJson<List<BrandItem>>(it.data.toString(), itemType) as ArrayList<BrandItem>
//                        brandList = it.data as ArrayList<BrandItem>
//                        Log.i("br", "List ${it.data}")
//                        setUpGridView()
//                    }
//                }
//            }
//        })
    }

    private fun setUpGridView() {

        val adapter = BrandItemAdapter(this.brandList, this)
        gridView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onItemClickListener(position: Int) {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_popup_color_setup, null)

        view.findViewById<TextView>(R.id.tvDelete).setOnClickListener {
            setupViewModel.deleteBrand(brandList[position])
            getAllBrandList()
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.tvEdit).setOnClickListener {
            dialog.dismiss()
            layoutAdd.isVisible = true
            newAdded = false
            currentBrandItem = brandList[position]
        }

        view.findViewById<TextView>(R.id.tvCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun prepareFile(): MultipartBody.Part? {
//        profileUri?.let {
//            val file = FileUtils.getFile(requireContext(), profileUri)
//            val requestFile =
//                file.asRequestBody(
//                    requireActivity().contentResolver.getType(profileUri!!)!!.toMediaTypeOrNull()
//                )
//            return MultipartBody.Part.createFormData("LogoFile", file.name, requestFile)
//        }
        return null
    }

}