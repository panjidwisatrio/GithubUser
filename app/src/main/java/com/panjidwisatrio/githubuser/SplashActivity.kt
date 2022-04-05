package com.panjidwisatrio.githubuser

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.lang.Exception

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    startActivity(
                        Intent(this@SplashActivity, MainActivity::class.java)
                    )
                    finish()
                } catch (e : Exception) {
                    Log.d("splash screen", e.message.toString())
                }
            }
        }.start()

        supportActionBar?.hide()
    }
}