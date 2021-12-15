package com.note.order.application

import android.app.Application
import com.note.order.repository.DataRepository
import com.note.order.roomDatabase.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication : Application(){

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(applicationContext, applicationScope) }
    val repository by lazy { DataRepository(database.roomDao()) }

}