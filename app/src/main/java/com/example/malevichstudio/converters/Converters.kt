package com.example.malevichstudio.converters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

 open class Converters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap):ByteArray{
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
        return stream.toByteArray()
    }

    @TypeConverter
    fun getImage(image:ByteArray):Bitmap{
        return  BitmapFactory.decodeByteArray(image,0,image.size)
    }
}