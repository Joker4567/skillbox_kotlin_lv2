package com.example.daggerwork

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.daggerwork.dagger.DaggerFragment
import com.example.daggerwork.databinding.MainActivityBinding
import com.example.daggerwork.koin.KoinFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val fragment = Items.values().first { it.title == item.title }.builder()

            supportFragmentManager.commit {
                replace(R.id.container, fragment)
            }

            return@setOnNavigationItemSelectedListener true
        }

        Items.values().forEach { item ->
            binding.bottomNavigation.menu.add(item.title)
        }

        binding.bottomNavigation.selectedItemId = 0
    }

    enum class Items(val title: String, val builder: () -> Fragment) {
        Dagger(title = "Dagger", builder = ::DaggerFragment),
        Koin(title = "Koin", builder = ::KoinFragment)
    }
}