package com.note.order.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.note.order.R
import com.note.order.entity.BrandItem
import java.nio.charset.StandardCharsets

import de.hdodenhof.circleimageview.CircleImageView


class BrandItemAdapter(private val list: MutableList<BrandItem>,private val itemClickListener: BrandItemAdapter.ItemClickListener) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        //val view = View(parent?.context)
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.rc_adapter_brand_item,parent,false)

        val ivBrand = view.findViewById<CircleImageView>(R.id.ivBrand)
        val tvBrandName = view.findViewById<TextView>(R.id.tvBrandName)

        val item = getItem(position)

        if(!item.name.isNullOrEmpty())
            tvBrandName.text = item.name

        val str = item.image
        val byteArray: ByteArray = str.toByteArray(StandardCharsets.UTF_8)
        //val bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        //val options = BitmapFactory.Options()
        //val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, options)

        //ivBrand.setImageBitmap(bitmap)

//        var outStream: ByteArrayOutputStream? = null
//        try {
//                outStream = ByteArrayOutputStream()
//                val buffer = ByteArray(1024)
//                var len = 0
//                while (str.byteInputStream(StandardCharsets.UTF_8).read(buffer).also { len = it } != -1) {
//                    outStream.write(buffer, 0, len)
//                }
//                val data = outStream.toByteArray()
//                val file = File(Environment.getExternalStorageDirectory().absolutePath + "/ATC/")
//                if (!file.exists()) {
//                    file.mkdirs()
//                }
//                val imageFile = File(Environment.getExternalStorageDirectory().absolutePath + "/ATC/" + "brand.jpg")
//                if (!file.exists()) {
//                    imageFile.createNewFile()
//                }
//                val fileOutStream = FileOutputStream(imageFile)
//                fileOutStream.write(data)
//                fileOutStream.flush()
//        } finally {
//            outStream?.close()
//        }
//
//        val imageFile = File(Environment.getExternalStorageDirectory().absolutePath + "/ATC/" + "brand.jpg")
//
////                File file = WonderfulFileUtils.getFileFromUrl(response);
////                try {
////                    inputStream=new FileInputStream(file);
////                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
////                    ivCode.setImageBitmap(bitmap);
////                } catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                }
//
////                Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
////                ByteArrayOutputStream baos = new ByteArrayOutputStream();
////                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
////
//        if(imageFile.exists()) {
//            val bmp: Bitmap = BitmapUtil().zoomBitmap(
//                BitmapFactory.decodeFile(imageFile.absolutePath),
//                ivBrand.width,
//                ivBrand.height
//            )!!
//
//            ivBrand.setImageBitmap(bmp)
//        }
//        val img = ImageView(parent?.context)
//        img.setImageResource(getItem(position))
//        img.scaleType = ImageView.ScaleType.FIT_XY
//        img.layoutParams = ViewGroup.LayoutParams(350,350)
//
//        img.setOnClickListener {
//            val intent = Intent(parent?.context,Fullscreen::class.java)
//            intent.putExtra("imgID" , getItem(position))
//            parent?.context?.startActivity(intent)
//        }

        view.setOnClickListener {
            itemClickListener.onItemClickListener(position)
        }

        return view
    }

    override fun getItem(position: Int): BrandItem = list[position]

    override fun getItemId(position: Int): Long = 0

    override fun getCount(): Int = list.size

    interface ItemClickListener{
        fun onItemClickListener(position: Int)
    }
}
