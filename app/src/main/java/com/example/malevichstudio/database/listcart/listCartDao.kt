package com.example.malevichstudio.database.listcart

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.malevichstudio.database.product.TableProduct

@Dao
interface listCartDao {

    @Query("SELECT * FROM product_table ORDER BY id DESC")
    fun getAllCart(): LiveData<List<listCart>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(cart: listCart)

    @Delete
    fun deleteItem(cart: listCart)

}