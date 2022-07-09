package com.example.malevichstudio.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.malevichstudio.R
import com.example.malevichstudio.databinding.ActivityKategoryBinding
import com.example.malevichstudio.databinding.ActivityMainBinding

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityKategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityKategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            openMenuActivity()
        }
    }

    private fun openMenuActivity() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}