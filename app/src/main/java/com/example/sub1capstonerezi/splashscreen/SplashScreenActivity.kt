package com.example.sub1capstonerezi.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.sub1capstonerezi.R
import com.example.sub1capstonerezi.home.HomeActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val test: ImageView = findViewById(R.id.photo_splash)
        test.alpha = 0F
        test.animate().setDuration(1000).alpha(1f).withEndAction {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }
    }
}