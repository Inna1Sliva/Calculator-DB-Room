package com.example.malevichstudio.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.malevichstudio.database.appDatabase
import com.example.malevichstudio.database.product.TableProduct
import com.example.malevichstudio.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ProductViewModel(application: Application): AndroidViewModel(application){
     val allProduct:LiveData<List<TableProduct>>
    private val repository:ProductRepository


    init {
        val productDao = appDatabase.getDatabase(application).productDuo()
        repository = ProductRepository(productDao)
        allProduct =repository.allProduct
    }
    fun addProduct(product: TableProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProduct(product)
        }
    }
        fun deletItem(product: TableProduct){
            viewModelScope.launch (Dispatchers.IO){
                repository.deletProduct(product)

            }
        }
    fun updateItem(product: TableProduct){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    }

