package com.example.compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.compose.databinding.ActivityMainBinding
import com.example.compose.list.ListFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        replaceFragment(ListFragment())
    }

    private fun FragmentActivity.replaceFragment(fragment: Fragment){
        supportFragmentManager.commit {
            replace(R.id.container, fragment)
            addToBackStack(null)
        }
    }
}
