package com.note.order.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "color_item")
data class ColorItem(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "color") val color: String,
)

@Entity(tableName = "size_item")
data class SizeItem(
    @PrimaryKey(autoGenerate = true)  val id: Int=0,
    @ColumnInfo(name = "name") val name: String
)

//@Entity(tableName = "brand_item")
//data class BrandItem(
//    @PrimaryKey(autoGenerate = true) val id: Int=0,
//    @ColumnInfo(name = "name") val name: String,
//    @ColumnInfo(typeAffinity = ColumnInfo.BLOB,name = "image") val image: ByteArray ?= null
//)

@Entity(tableName = "brand_item")
data class BrandItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 1,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "image") var image: String = ""
    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB) var data: ByteArray ?= null
)
