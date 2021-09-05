package com.example.rickandmorty.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rickandmorty.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val feedFragment = CharactersFeedFragment(R.layout.fragment_characters_feed)
            supportFragmentManager.beginTransaction().replace(R.id.container, feedFragment).commit()
        }
    }
}