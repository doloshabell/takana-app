package com.example.takana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.takana.databinding.ActivityMainBinding
import com.example.takana.presentation.HomeFragment
import com.example.takana.presentation.MoneyAccountFragment
import com.example.takana.presentation.ProfileFragment
import com.example.takana.presentation.TransactionFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupContent()
    }

    private fun setupContent() {
        replaceFragment(HomeFragment())
        binding.apply {
            bnvMenu.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.home -> replaceFragment(HomeFragment())
                    R.id.transactions -> replaceFragment(TransactionFragment())
                    R.id.account -> replaceFragment(MoneyAccountFragment())
                    R.id.profile -> replaceFragment(ProfileFragment())
                }
                true
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_activity, fragment)
        fragmentTransaction.commit()
    }
}