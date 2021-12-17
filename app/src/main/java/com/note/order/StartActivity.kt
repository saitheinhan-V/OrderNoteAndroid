package com.note.order

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.note.order.application.MyApplication
import com.note.order.entity.User
import com.note.order.ui.account.LoginActivity
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import java.util.*
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_start.*
import com.hanks.passcodeview.PasscodeView
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import com.note.order.utils.AppSharedPreference


class StartActivity : AppCompatActivity() {

    companion object{
        const val RESULT_CODE = 100
    }

    private val REQUEST = arrayOf(
        Permission.READ_EXTERNAL_STORAGE,
        Permission.WRITE_EXTERNAL_STORAGE,
    )

    private var currentUser: User?= null
    private lateinit var sharedPreference: AppSharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)



        val ivSplash: LinearLayout = findViewById(R.id.mainLayout)
        val animation = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        ivSplash.startAnimation(animation)

        sharedPreference = AppSharedPreference(this)

        /*object : CountDownTimer(2000, 1500) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                checkAllPermission()
            }
        }.start()*/

        // to set length of password as here
        // we have set the length as 5 digits
        // to set length of password as here
        // we have set the length as 5 digits
        val passCode = sharedPreference.getPassCode("pass_code")
        if(!passCode.isNullOrEmpty()) {

            passcodeView.localPasscode = "$passCode"
            passcodeView.setPasscodeLength(4) // to set pincode or passcode
                .listener = object : PasscodeViewListener {
                override fun onFail() {
                    // to show message when Password is incorrect
                    Toast.makeText(this@StartActivity, "Password is wrong!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onSuccess(number: String) {
                    // here is used so that when password
                    // is correct user will be
                    // directly navigated to next activity


                    if (passCode == number) {
                        checkAllPermission()
                    } else {
                        Toast.makeText(this@StartActivity, "Password is wrong!", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            }
        }else{
            startActivity(Intent(this@StartActivity,MainActivity::class.java))
            finish()
        }


    }

    fun checkAllPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){ //Android 11

            if(!Environment.isExternalStorageManager()){ //
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE //replace for WRITE_EXTERNAL_STORAGE
                    ), RESULT_CODE
                )
            }else{
                checkLoginStatus()
            }



        }else{ //below Android 11
//            AndPermission.with(this@StartActivity)
//                .runtime()
//                .permission(Permission.Group.STORAGE)
//                .onGranted { permission ->
//                    //currentUser = MyApplication().getCurrentUserFromFile()
//                    checkLoginStatus()
//
//                }
//                .onDenied { permission ->
//                    Toast.makeText(this@StartActivity, "Permission Denied!", Toast.LENGTH_SHORT).show()
//                }
//                .start()
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), RESULT_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RESULT_CODE) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { //Android 11
                if (Environment.isExternalStorageManager()) { //perform action when permission allowed
                    checkLoginStatus()
                } else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
            else{ //below Android 11
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    checkLoginStatus()
                } else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkLoginStatus(){
        if (MyApplication().isLogin(this)) {
            startActivity(Intent(this@StartActivity,MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@StartActivity,LoginActivity::class.java))
            finish()
        }
    }

}