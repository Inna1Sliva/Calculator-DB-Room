package com.example.malevichstudio.application

import android.app.Application
import com.example.malevichstudio.database.appDatabase
import com.example.malevichstudio.repository.ProductRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProductApplication:Application() {
val database by lazy { appDatabase.getDatabase(this) }
  val repository by lazy { ProductRepository(database.productDuo()) }
}