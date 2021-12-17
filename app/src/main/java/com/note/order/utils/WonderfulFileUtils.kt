package com.note.order.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.note.order.utils.WonderfulFileUtils
import kotlin.Throws
import android.os.Build
import androidx.core.content.FileProvider
import com.note.order.BuildConfig
import java.io.*

/**
 * Created by wonderful on 2017/7/17.
 */
class WonderfulFileUtils {
    fun hasSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED || !Environment.isExternalStorageRemovable()
    }

    fun getLongSaveFile(context: Context, dirname: String?, filename: String?): File? {
        val file: File =
        if (hasSDCard()) {
            File(context.getExternalFilesDir(dirname), filename!!)
        } else {
            File(context.filesDir, filename!!)
        }
        if (!file.parentFile!!.exists()) {
            file.mkdirs()
        }
        if (file.exists()) {
            file.delete()
        }
        try {
            file.createNewFile()
            return file
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    fun getLongSaveDir(): File? {

        var file: File ?= null

        try {
            val myDir: String = Environment.getDataDirectory().toString() +  "/orderNote"
            // create a File object for the parent directory
            val myFolder = File(myDir)
            val userFile = File(myDir,"user.txt")

            if(!userFile.parentFile!!.exists()){

            }else{

            }

            file = if(!myFolder.exists()){
                myFolder.createNewFile()
                val success = userFile.createNewFile()
                if(success)
                    userFile
                else null

            }else{
                if(!userFile.exists()){
                    userFile.createNewFile()
                    userFile
                }else{
                    userFile
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return file

//        val path = Environment.getExternalStorageDirectory().absolutePath
//        val folder = File(path,"/order/")
//        val userFile = File(folder,"/user.txt")
//
////        var file = File(Environment.getExternalStorageDirectory(), "/user")
//        try {
//            file = if(folder.exists()){
//                if(!userFile.exists()){
//                    userFile.createNewFile()
//                }
//                userFile
//            }else{
//                folder.mkdirs()
//                userFile.createNewFile()
//                userFile
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }

        //val file: File? = File(Environment.getExternalStorageDirectory(),"/user/user.txt")
//            if (hasSDCard()) {
//            context.getExternalFilesDir(dirname)
//        } else {
//            context.filesDir
//        }
//        if (!file?.parentFile!!.exists()) {
//            file.mkdirs()
//        }
//        if (!file.exists()) file.createNewFile()
    }

    fun getCacheSaveFile(context: Context, filename: String?): File? {
        val file: File = if (hasSDCard()) {
            File(context.externalCacheDir, filename)
        } else {
            File(context.cacheDir, filename)
        }
        if (!file.parentFile.exists()) {
            file.mkdirs()
        }
        if (file.exists()) {
            file.delete()
        }
        try {
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return file
    }

    @Throws(IOException::class)
    fun getCacheSaveDir(context: Context): File? {
        val file: File? = if (hasSDCard()) {
            context.externalCacheDir
        } else {
            context.cacheDir
        }
        if (!file!!.parentFile.exists()) {
            file.mkdirs()
        }
        if (!file.exists()) file.createNewFile()
        return file
    }

    fun getCommonPathFile(context: Context?, filename: String): File? {
        val file = File(Environment.getExternalStorageDirectory().toString() + filename)
        if (file.isDirectory) return null
        if (file.exists()) file.delete()
        if (!file.parentFile.exists()) file.mkdirs()
        return try {
            file.createNewFile()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun getUriForFile(context: Context?, file: File?): Uri {
        return if (Build.VERSION.SDK_INT >= 24) getUriForFile24(context, file) else Uri.fromFile(
            file
        )
    }

    fun getUriForFile24(context: Context?, file: File?): Uri {
        return FileProvider.getUriForFile(
            context!!,
            BuildConfig.APPLICATION_ID + ".fileprovider",
            file!!
        )
    }



}