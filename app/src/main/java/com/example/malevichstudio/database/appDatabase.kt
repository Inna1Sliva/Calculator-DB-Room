package com.example.malevichstudio.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.malevichstudio.converters.Converters
import com.example.malevichstudio.database.product.ProductDao
import com.example.malevichstudio.database.product.TableProduct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TableProduct::class], version = 1)
@TypeConverters(Converters::class)
abstract class appDatabase:RoomDatabase() {
    abstract fun productDuo(): ProductDao

    companion object {
        @Volatile
        private var instance: appDatabase? = null
        fun getDatabase(
            context: Context): appDatabase {
            return instance ?: synchronized(this) {
                val inst = Room.databaseBuilder(
                    context.applicationContext,
                    appDatabase::class.java,
                    "app_Database"
                )

                    .build()
                instance = inst
                inst
            }

        }

    }
}


