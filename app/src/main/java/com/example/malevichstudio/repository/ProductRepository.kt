package com.example.malevichstudio.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.malevichstudio.database.product.ProductDao
import com.example.malevichstudio.database.product.TableProduct
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.flow.Flow

class ProductRepository (private val productDao: ProductDao){
    val allProduct:LiveData<List<TableProduct>> = productDao.getAllProduct()

   fun addProduct (product: TableProduct){
        productDao.insert(product)
    }
    fun deletProduct(product: TableProduct){
        productDao.deleteItem(product)
    }
    fun updateProduct(product: TableProduct){
        productDao.updateProduct(product)
    }
}

