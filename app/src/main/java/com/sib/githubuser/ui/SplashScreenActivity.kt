package com.sib.githubuser.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sib.githubuser.databinding.ActivitySplashscreenBinding
import java.lang.Exception

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashScreen = object : Thread() {
            override fun run() {
                try {
                    sleep(EXTRA_TIME)

                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        splashScreen.start()
    }

    companion object{
        const val EXTRA_TIME = 2000L
    }

}