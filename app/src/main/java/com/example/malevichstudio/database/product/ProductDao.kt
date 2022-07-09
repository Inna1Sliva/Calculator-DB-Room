package com.example.malevichstudio.database.product

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface  ProductDao {
    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllProduct():LiveData<List<TableProduct>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product: TableProduct)

    @Update
    fun updateProduct(product: TableProduct)

    @Delete
  fun deleteItem(product: TableProduct)
}