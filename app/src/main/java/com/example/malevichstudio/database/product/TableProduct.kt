package com.example.malevichstudio.database.product

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize


@Entity(tableName = "product_table")
data class TableProduct(
    @PrimaryKey(autoGenerate = true)
     val id:Int,
    @ColumnInfo(name = "product")
     val product:String,
    @ColumnInfo(name = "price")
     val price:String,
    @ColumnInfo(name ="frame")//ldc
    val frame: String,
    @ColumnInfo(name = "edge")//cromca
    val edge:Int,
    @ColumnInfo(name="framemetr")
    val framemetr:Int,
    @ColumnInfo(name = "facade")
    val facade: String,
    @ColumnInfo(name = "facadepoc")
    val facadepoc:Int,
    @ColumnInfo(name="facadeedge")
    val facadeedge:Int,
    @ColumnInfo(name = "fittings")
    val fittings:String,
    @ColumnInfo(name = "fittingssize")
    val fittingssize:Int,
    @ColumnInfo(name = "fitting")
    val fitting:String,
    @ColumnInfo(name = "fittinssize")
    val fittinssize:Int,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
     val photo:Bitmap
)
