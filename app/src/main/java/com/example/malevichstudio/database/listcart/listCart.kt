package com.example.malevichstudio.database.listcart

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
data class listCart(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "product")
    val product:String,
    @ColumnInfo(name = "price")
    val price:String,
    //@ColumnInfo(name =)
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val photo: Bitmap
)
