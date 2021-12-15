package com.note.order.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.text.format.DateFormat
import android.util.Base64
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.note.order.R
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

//Override function of setOnclickListener for handling multiple click
fun View.setSafeOnClickListener(block: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        block(it)
    }
    setOnClickListener(safeClickListener)
}

//fun Context.showToast() {
//    Toast.makeText(this, this.getString(R.string.connection_time_out), Toast.LENGTH_SHORT).show()
//}

fun CharSequence?.isValidEmail() =
    Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun CharSequence?.isValidPhoneNumber() =
    Patterns.PHONE.matcher(this).matches()

//fun TextView.checkValidation(context: Context, isValidate: Boolean) {
//    if (isValidate) {
//        this.setTextColor(ContextCompat.getColor(context, R.color.colorGreenLight))
//        this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password_fit, 0, 0, 0)
//    } else {
//        this.setTextColor(ContextCompat.getColor(context, R.color.colorGray))
//        this.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_asterisk, 0, 0, 0)
//    }
//}

fun View.showSnackBar(height: Int, message: String) {
    val snack: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snack.setBackgroundTint(ContextCompat.getColor(this.context,R.color.colorPrimary))
    val view: View = snack.view
    view.setPadding(5, 5, 5, 5)
    val textView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.maxLines = 10
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    params.setMargins(0,height,0,0)
    view.layoutParams = params
    snack.show()
}

fun View.showSnackBar(message: String) {
    val snack: Snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    snack.setBackgroundTint(Color.parseColor("#ED2F93"))
    val view: View = snack.view
    view.setPadding(5, 5, 5, 5)
    val textView = view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.maxLines = 10
    val params = view.layoutParams as FrameLayout.LayoutParams
    params.gravity = Gravity.TOP
    view.layoutParams = params
    snack.show()
}

//fun View.setMarginInsetsTop() {
//    val parameter = this.layoutParams as RelativeLayout.LayoutParams
//    parameter.setMargins(0, StatusHeight.getStatusBarHeight(this.context), 0, 0)
//}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

@SuppressLint("SimpleDateFormat")
fun convertDate(date : String) : String{  //22 May 2020
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") //Mon Jun 01 00:00:00 GMT+06:30 2020
    val mDate = sdf.parse(date).toString()
    val dateString = mDate.split(" ")[2]+" "+mDate.split(" ")[1]+" "+mDate.split(" ")[5]
    return dateString
}

@SuppressLint("SimpleDateFormat")
fun getTheNextDay(dateStr : String) : String{ //2020-06-02T00:00:00
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date = sdf.parse(dateStr)
    val calendar = Calendar.getInstance()
    calendar.time = date!!
    calendar.add(Calendar.DAY_OF_YEAR, 1)
    return sdf.format(calendar.time)
}

@SuppressLint("SimpleDateFormat")
fun getDayFromDate(date : String) : String{
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date = sdf.parse(date)
    val day  = DateFormat.format("dd", date) as String // 20

    return day
}

@SuppressLint("SimpleDateFormat")
fun getMonthFromDate(date : String) : String{
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val date  = sdf.parse(date)
    val month = DateFormat.format("MMM", date) as String // Jun
    return month
}

fun decimalSeparator(value : Long) : String{
    return DecimalFormat().format(value)
}

fun Bitmap.getImageUri(context: Context): Uri? {
    val bytes = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path =
        MediaStore.Images.Media.insertImage(
            context.contentResolver,
            this,
            "Title",
            null
        )
    return Uri.parse(path)
}

fun File.convertFileToContentUri(context: Context): Uri? {

    //Uri localImageUri = Uri.fromFile(localImageFile); // Not suitable as it's not a content Uri
    val cr: ContentResolver = context.contentResolver
    val imagePath = this.absolutePath
    val imageName: String? = null
    val imageDescription: String? = null
    val uriString: String = MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription)
    return Uri.parse(uriString)
}

//fun Context.showOTPSuccessful(){
//    Toast.makeText(this,this.getString(R.string.resend_otp_successful),Toast.LENGTH_SHORT).show()
//}

fun bitmapToBase64EncodedStr(b : Bitmap) : String{
    val byteArrayOutputStream = ByteArrayOutputStream()
    b.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun isNumber(string : String) : Boolean{
    return string.trim().matches("^[0-9]*$".toRegex())
}
