package com.note.order.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.ByteArrayOutputStream
import java.lang.Exception
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log


class BitmapUtil {

    fun zoomBitmap(bitmap: Bitmap, w: Int, h: Int): Bitmap? {
        var bitmap = bitmap
        return try {
            val width = bitmap.width
            val height = bitmap.height
            val matrix = Matrix()
            val scaleWidth = w.toFloat() / width
            val scaleHeight = h.toFloat() / height
            matrix.postScale(scaleWidth, scaleHeight)
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    fun bitmapToBase64EncodedStr(b : Bitmap) : String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
    }

    fun convertToByteArray(bitmap: Bitmap): ByteArray{
        var byteArray : ByteArray ?= null
//
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, stream)
//        byteArray = stream.toByteArray()
//        bitmap.recycle()
//
//        return byteArray
        val immagex: Bitmap = bitmap
        val baos = ByteArrayOutputStream()
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        byteArray = baos.toByteArray()

        return byteArray
    }

    fun encode(context: Context, imageUri: Uri) : ByteArray{
        //Convert Image to Bitmap
        var bArray: ByteArray? = null
        val bitmap: Bitmap
        if(Build.VERSION.SDK_INT < 28) {
            bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            bitmap = ImageDecoder.decodeBitmap(source)
        }

        //Convert Bitmap to ByteArray
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        bArray = bos.toByteArray()

        Log.i("bbb","Byteeeeee<<< $bArray")

        return bArray


        //Encode ByteArray to Base64 String
//        val encodedString: String
//        encodedString = Base64.encodeToString(bArray, Base64.DEFAULT)
//
//        return encodedString
    }


}