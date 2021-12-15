package com.note.order.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.note.order.DAO.RoomDao
import com.note.order.entity.BrandItem
import com.note.order.entity.ColorItem

class DataRepository(private val roomDao: RoomDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.



    val allColors: LiveData<List<ColorItem>> = roomDao.getAllColor()
    val allBrands: LiveData<List<BrandItem>> = roomDao.getAllBrand()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertColor(item: ColorItem) {
        roomDao.insertColor(item)
    }

    suspend fun deleteColor(item: ColorItem){
        roomDao.deleteColor(item)
    }

    suspend fun updateColor(item: ColorItem){
        roomDao.updateColor(item)
    }

    suspend fun insertBrand(item: BrandItem){
        roomDao.insertBrand(item)
    }

    suspend fun deleteBrand(item: BrandItem){
        roomDao.deleteBrand(item)
    }

    suspend fun updateBrand(item: BrandItem){
        roomDao.updateBrand(item)
    }



}