package com.jyotsna.storingappsettings

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()

        // Reapply settings when returning to MainActivity
        applySettings()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<Button>(R.id.settings_button)

        // Navigate to settings screen
        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Apply initial settings
        applySettings()
    }

    private fun applySettings() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val welcomeText = findViewById<TextView>(R.id.welcome_text)

        // Retrieve settings
        val darkMode = sharedPreferences.getBoolean("DarkMode", false)
        val textSize = sharedPreferences.getInt("TextSize", 14)
        val username = sharedPreferences.getString("Username", "User")

        // Apply dark mode
        if (darkMode) {
            mainLayout.setBackgroundColor(Color.BLACK)
            welcomeText.setTextColor(Color.WHITE)
        } else {
            mainLayout.setBackgroundColor(Color.WHITE)
            welcomeText.setTextColor(Color.BLACK)
        }

        // Apply text size and username
        welcomeText.textSize = textSize.toFloat()
        welcomeText.text = "Welcome, $username!"
    }
}
