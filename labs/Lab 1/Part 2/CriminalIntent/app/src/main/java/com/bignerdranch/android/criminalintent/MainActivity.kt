package com.bignerdranch.android.criminalintent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: add the following so that the UI will not be hidden by the notch of the front facing camera
        // Enable edge-to-edge drawing and adjust for system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { view, insets ->
            val statusBarHeight = insets.systemWindowInsetTop
            view.setPadding(0, statusBarHeight, 0, 0) // Push content down
            insets
        }
    }
}
