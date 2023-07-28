package com.example.takana

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.takana.data.model.UserLoginData
import com.example.takana.data.util.SessionManager
import com.example.takana.databinding.ActivityMainBinding
import com.example.takana.presentation.home.HomeFragment
import com.example.takana.presentation.money_account.MoneyAccountFragment
import com.example.takana.presentation.profile.ProfileFragment
import com.example.takana.presentation.transaction.TransactionFragment
import com.example.takana.presentation.login.LoginActivity
import com.example.takana.service.JwtDecode
import com.google.gson.Gson
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        isLogin(this, LoginActivity())
        setupContent()
    }

    private fun isLogin(context: Context, activity: Activity) {
        val token = SessionManager.getToken(context)
        if (token.isNullOrBlank()) {
            val intent = Intent(context, activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
            finish()
        }
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