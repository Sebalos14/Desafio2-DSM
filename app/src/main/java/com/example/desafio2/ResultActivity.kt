package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var tvCategory: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var llReview: LinearLayout
    private lateinit var btnRetry: Button
    private lateinit var btnHome: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvCategory = findViewById(R.id.tvCategory)
        tvScore = findViewById(R.id.tvScore)
        tvFeedback = findViewById(R.id.tvFeedback)
        llReview = findViewById(R.id.llReview)
        btnRetry = findViewById(R.id.btnRetry)
        btnHome = findViewById(R.id.btnHome)

        // Recibir datos simples
        val category = intent.getStringExtra("CATEGORY") ?: "Historia"
        val difficulty = intent.getStringExtra("DIFFICULTY") ?: "Fácil"
        val total = intent.getIntExtra("TOTAL", 5)
        val correct = intent.getIntExtra("CORRECT", 0)
        val selectedAnswers = intent.getIntArrayExtra("SELECTED_ANSWERS") ?: intArrayOf()
        val correctAnswers = intent.getIntArrayExtra("CORRECT_ANSWERS") ?: intArrayOf()
        val questionTexts = intent.getStringArrayExtra("QUESTION_TEXTS") ?: arrayOf()
        val optionLists = intent.getStringArrayExtra("OPTION_LISTS") ?: arrayOf()

        tvCategory.text = " $category -  $difficulty"
        tvScore.text = "Obtuviste $correct de $total"

        // Retroalimentación
        val feedback = when {
            correct == 0 || correct == 1 -> " Mejor me dedico a otra cosa..."
            correct in 2..3 -> " Más o menos OK"
            correct == 4 -> " Me merezco un churro"
            correct == 5 && difficulty == "Difícil" -> " Como pegarle a un bolo"
            correct == 5 -> " ¡Excelente! Eres un verdadero Culé"
            else -> "¡Sigue practicando!"
        }
        tvFeedback.text = feedback

        // Revisión de respuestas
        for (i in questionTexts.indices) {
            val question = questionTexts[i]
            val selected = selectedAnswers.getOrElse(i) { -1 }
            val correctIndex = correctAnswers.getOrElse(i) { 0 }
            val options = optionLists.getOrElse(i) { "" }.split("|")
            val isCorrect = selected == correctIndex

            val container = LinearLayout(this)
            container.orientation = LinearLayout.VERTICAL
            container.setPadding(16, 16, 16, 16)
            container.setBackgroundResource(android.R.drawable.btn_default)
            container.elevation = 2f
            container.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 8 }

            val tvQuestion = TextView(this)
            tvQuestion.text = "${i + 1}. $question"
            tvQuestion.textSize = 14f
            tvQuestion.setPadding(0, 0, 0, 8)
            container.addView(tvQuestion)

            val tvAnswer = TextView(this)
            val selectedText = if (selected != -1 && selected < options.size) options[selected] else "No respondida"
            val correctText = if (correctIndex < options.size) options[correctIndex] else "N/A"

            tvAnswer.text = if (isCorrect) {
                " Correcta: $selectedText"
            } else {
                " Tu respuesta: $selectedText\n Correcta: $correctText"
            }
            tvAnswer.textSize = 12f
            container.addView(tvAnswer)

            if (isCorrect) {
                container.setBackgroundColor(resources.getColor(android.R.color.holo_green_light))
            } else {
                container.setBackgroundColor(resources.getColor(android.R.color.holo_red_light))
            }

            llReview.addView(container)
        }

        btnRetry.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("CATEGORY", category)
            intent.putExtra("DIFFICULTY", difficulty)
            startActivity(intent)
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}