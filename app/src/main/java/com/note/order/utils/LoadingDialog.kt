package com.note.order.utils

import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.note.order.R

class LoadingDialog(var activity: FragmentActivity) {

    var dialog: Dialog? = null

    fun showDialog() {
        dialog = Dialog(activity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_loading)

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog!!.window?.attributes)

        //dialog!!.window?.setBackgroundDrawableResource(R.drawable.dialog_margin)
        //dialog!!.window?.attributes = lp

        val gifImageView = dialog?.findViewById<ImageView>(R.id.iv_loading)
        val imageViewTarget = DrawableImageViewTarget(gifImageView)

        Glide.with(activity)
            .load(R.drawable.loading_one)
            .placeholder(R.drawable.loading_one)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageViewTarget)

        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

}