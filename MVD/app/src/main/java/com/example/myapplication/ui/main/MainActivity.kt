package com.example.myapplication.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.R
import com.example.myapplication.di.AppModule

class MainActivity : AppCompatActivity() {

    private  val  binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel: MainViewModel by lazy { AppModule.provideMainViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    private fun setupEventos(){
        binding.button.setOnclickListenner()
        viewModel.
    }
}