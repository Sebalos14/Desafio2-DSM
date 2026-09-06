package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Verificar si hay sesión activa
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Redirigir según el estado
        val intent = if (currentUser != null) {
            Intent(this, WelcomeActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}