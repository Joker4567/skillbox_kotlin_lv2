package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recyclerview.databinding.MainActivityBinding
import com.example.recyclerview.paging.PaginationFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding:MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
                .replace(R.id.container, PaginationFragment())
                .commitNow()
    }
}