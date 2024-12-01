package com.jyotsna.storingappsettings

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize SharedPreferences
        val sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Bind views
        val switchDarkMode = findViewById<Switch>(R.id.switch_dark_mode)
        val seekBarTextSize = findViewById<SeekBar>(R.id.seekbar_text_size)
        val editTextUsername = findViewById<EditText>(R.id.edittext_username)
        val settingsLayout = findViewById<ConstraintLayout>(R.id.settings_layout)
        val darkModeLabel = findViewById<TextView>(R.id.dark_mode_label)

        // Load current settings and apply them
        val isDarkMode = sharedPreferences.getBoolean("DarkMode", false)
        applyDarkMode(isDarkMode, settingsLayout, darkModeLabel, editTextUsername) // Apply initial dark/light mode
        switchDarkMode.isChecked = isDarkMode
        seekBarTextSize.progress = sharedPreferences.getInt("TextSize", 14)
        editTextUsername.setText(sharedPreferences.getString("Username", ""))

        // Toggle dark mode dynamically
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            editor.putBoolean("DarkMode", isChecked).apply() // Save dark mode setting
            applyDarkMode(isChecked, settingsLayout, darkModeLabel, editTextUsername) // Apply changes dynamically
        }

        // Save settings when SeekBar value is changed
        seekBarTextSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                editor.putInt("TextSize", progress).apply() // Save text size
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Save username when EditText loses focus
        editTextUsername.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                editor.putString("Username", editTextUsername.text.toString()).apply() // Save username
            }
        }
    }

    // Function to dynamically apply dark/light mode
    private fun applyDarkMode(
        isDarkMode: Boolean,
        layout: ConstraintLayout,
        darkModeLabel: TextView,
        editText: EditText
    ) {
        if (isDarkMode) {
            layout.setBackgroundColor(Color.BLACK) // Set background to dark
            darkModeLabel.setTextColor(Color.WHITE) // Make text visible
            editText.setTextColor(Color.WHITE) // Change text color in EditText
            editText.setHintTextColor(Color.GRAY) // Change hint text color
        } else {
            layout.setBackgroundColor(Color.WHITE) // Set background to light
            darkModeLabel.setTextColor(Color.BLACK) // Change text color back to black
            editText.setTextColor(Color.BLACK) // Change text color back to black
            editText.setHintTextColor(Color.DKGRAY) // Change hint text color back to dark gray
        }
    }
}
