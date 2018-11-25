package com.dev.adi.ecosystem.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import com.dev.adi.ecosystem.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash)


        val handler = Handler()
        handler.postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000L)
    }
}