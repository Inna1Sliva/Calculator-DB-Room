package com.example.malevichstudio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.malevichstudio.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)//R.layout.activity_main

    }
}