package com.example.malevichstudio.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.malevichstudio.R
import com.example.malevichstudio.adapter.ListAdapter
import com.example.malevichstudio.databinding.ActivityMenuBinding
import com.example.malevichstudio.fragment.AddFragment
import com.example.malevichstudio.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity(R.layout.activity_menu) {
      //  private lateinit var floatingActionButton:View
       // private lateinit var recyclerView: RecyclerView
        private var adapter:ListAdapter? =null
    companion object {
       lateinit var viewModel: ProductViewModel
   }
lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ListAdapter(this)

        binding.recyclerview.adapter=adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        viewModel.allProduct.observe(this, Observer { product->
          adapter!!.setData(product)
        })

       binding.fab.setOnClickListener { starActivity() }
    }

    private fun starActivity() {
        supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container_view, AddFragment.newInstance())
         .addToBackStack(null)
         .commit()
    }
}