package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var rgCategory: RadioGroup
    private lateinit var rgDifficulty: RadioGroup
    private lateinit var btnStartQuiz: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        auth = FirebaseAuth.getInstance()

        rgCategory = findViewById(R.id.rgCategory)
        rgDifficulty = findViewById(R.id.rgDifficulty)
        btnStartQuiz = findViewById(R.id.btnStartQuiz)
        btnLogout = findViewById(R.id.btnLogout)

        btnStartQuiz.setOnClickListener {
            val categoryId = rgCategory.checkedRadioButtonId
            val difficultyId = rgDifficulty.checkedRadioButtonId

            if (categoryId == -1) {
                Toast.makeText(this, "❌ Selecciona una categoría", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val category = findViewById<RadioButton>(categoryId).text.toString()
            val difficulty = findViewById<RadioButton>(difficultyId).text.toString()

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("CATEGORY", category)
            intent.putExtra("DIFFICULTY", difficulty)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}