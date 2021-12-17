package com.note.order.application

import android.app.Application
import android.content.Context
import android.os.Environment
import android.util.Log
import com.note.order.entity.User
import com.note.order.repository.DataRepository
import com.note.order.roomDatabase.AppDatabase
import com.note.order.utils.WonderfulFileUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.io.*

class MyApplication : Application(){


    private var currentUser: User ?= null
    private var app: MyApplication? = null


    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(applicationContext, applicationScope) }
    val repository by lazy { DataRepository(database.roomDao()) }

    override fun onCreate() {
        super.onCreate()
        app = this
        getCurrentUserFromFile()
    }

    fun getApp(): MyApplication? {
        return app
    }

    @Synchronized
    fun saveCurrentUser() {
        try {
            val file: File = WonderfulFileUtils().getLongSaveFile(this, "User", "user.info")!!

            if (!file.parentFile!!.exists()) {
                file.parentFile!!.mkdirs()
            }
            if (!file.exists()) {
                file.createNewFile()
            }
            val oos = ObjectOutputStream(FileOutputStream(file))
            oos.writeObject(currentUser)
            oos.flush()
            oos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getCurrentUserFromFile(){
        try {
            val file: File? = WonderfulFileUtils().getLongSaveDir()
            if (file != null) {
                if(!file.exists()){
                    this.currentUser = User()
                }else {
                    val ois = ObjectInputStream(FileInputStream(file))
                    this.currentUser = ois.readObject() as User
                    Log.i("User", currentUser.toString())
                    ois.close()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun isLogin(context: Context): Boolean {
        var isLogin = false
        try {
            val file: File? = WonderfulFileUtils().getLongSaveFile(context,"User","user.info")
            if (file != null) {
                if(!file.exists()){
                    this.currentUser = User()
                    isLogin = false
                }else {
                    try {
                        val ois = ObjectInputStream(FileInputStream(file))
                        this.currentUser = ois.readObject() as User
                        Log.i("User", currentUser.toString())
                        ois.close()

                        isLogin = this.currentUser != null
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }else isLogin = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return isLogin
    }

    fun getCurrentUser(): User? {
        return if (currentUser == null) User().also { currentUser = it } else currentUser
    }

}