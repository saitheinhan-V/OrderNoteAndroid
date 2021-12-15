package com.note.order.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.note.order.entity.BrandItem
import com.note.order.entity.ColorItem

@Dao
interface RoomDao {

    @Query("SELECT * FROM color_item")
    fun getAllColor(): LiveData<List<ColorItem>>

    @Query("SELECT * FROM color_item WHERE name LIKE :name")
    fun findByName(name: String): ColorItem

    @Query("SELECT * FROM color_item WHERE id = :id")
    fun getColorByID(id: Int): ColorItem

//    @Query("INSERT INTO color_item(name,color) VALUES(:name,:color)")
//    fun saveNewColor(name: String,color: String)

    @Insert
    fun saveNewColor(item: ColorItem): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertColor(item: ColorItem)

    @Delete
    suspend fun deleteColor(item: ColorItem)

    @Update
    suspend fun updateColor(item: ColorItem)

    @Insert
    fun insertAll(vararg todo: ColorItem)

    @Delete
    fun delete(todo: ColorItem)

    @Update
    fun updateTodo(vararg todos: ColorItem)

    //Brand
    @Query("SELECT * FROM brand_item")
    fun getAllBrand(): LiveData<List<BrandItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBrand(item: BrandItem)

    @Delete
    suspend fun deleteBrand(item: BrandItem)

    @Update
    suspend fun updateBrand(item: BrandItem)


}