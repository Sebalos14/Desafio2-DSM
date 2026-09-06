package com.example.desafio2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private lateinit var tvCategoryInfo: TextView
    private lateinit var tvProgress: TextView
    private lateinit var llQuestions: LinearLayout
    private lateinit var btnReset: Button
    private lateinit var btnSubmit: Button

    private lateinit var questions: List<Question>
    private val userAnswers = mutableMapOf<Int, Int>()
    private lateinit var category: String
    private lateinit var difficulty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        tvCategoryInfo = findViewById(R.id.tvCategoryInfo)
        tvProgress = findViewById(R.id.tvProgress)
        llQuestions = findViewById(R.id.llQuestions)
        btnReset = findViewById(R.id.btnReset)
        btnSubmit = findViewById(R.id.btnSubmit)

        category = intent.getStringExtra("CATEGORY") ?: "Historia"
        difficulty = intent.getStringExtra("DIFFICULTY") ?: "Fácil"

        tvCategoryInfo.text = " $category -  $difficulty"

        questions = QuestionsBank.getQuestions(category, difficulty)
        displayQuestions()

        btnReset.setOnClickListener {
            resetQuiz()
        }

        btnSubmit.setOnClickListener {
            submitQuiz()
        }
    }

    private fun displayQuestions() {
        llQuestions.removeAllViews()
        userAnswers.clear()

        questions.forEachIndexed { index, question ->
            val questionView = createQuestionView(index, question)
            llQuestions.addView(questionView)
        }

        updateProgress()
    }

    private fun createQuestionView(index: Int, question: Question): LinearLayout {
        val container = LinearLayout(this)
        container.orientation = LinearLayout.VERTICAL
        container.setPadding(16, 16, 16, 16)
        container.setBackgroundResource(android.R.drawable.btn_default)
        container.elevation = 4f
        container.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply { bottomMargin = 16 }

        val tvQuestion = TextView(this)
        tvQuestion.text = "${index + 1}. ${question.question}"
        tvQuestion.textSize = 16f
        tvQuestion.setPadding(0, 0, 0, 12)
        container.addView(tvQuestion)

        val rgOptions = RadioGroup(this)
        rgOptions.id = question.id + 1000

        question.options.forEachIndexed { optIndex, option ->
            val rb = RadioButton(this)
            rb.id = optIndex
            rb.text = option
            rgOptions.addView(rb)
        }

        val savedAnswer = userAnswers[question.id]
        if (savedAnswer != null && savedAnswer in 0..2) {
            val rb = rgOptions.getChildAt(savedAnswer) as RadioButton
            rb.isChecked = true
        }

        rgOptions.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                userAnswers[question.id] = checkedId
                updateProgress()
            }
        }

        container.addView(rgOptions)
        return container
    }

    private fun updateProgress() {
        val answered = userAnswers.size
        val total = questions.size
        tvProgress.text = "$answered/$total"
    }

    private fun resetQuiz() {
        userAnswers.clear()
        displayQuestions()
        Toast.makeText(this, " Quiz reiniciado", Toast.LENGTH_SHORT).show()
    }

    // ============================================
    // ⭐ ENVIO DEL QUIZ - VERSIÓN CORREGIDA
    // ============================================
    private fun submitQuiz() {
        // Verificar que todas las preguntas estén respondidas
        val unanswered = questions.filter { !userAnswers.containsKey(it.id) }

        if (unanswered.isNotEmpty()) {
            val indices = unanswered.map { questions.indexOf(it) + 1 }
            val msg = " Faltan preguntas: ${indices.joinToString(", ")}"
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            return
        }

        // Calcular puntaje
        var correct = 0
        val selectedAnswers = mutableListOf<Int>()
        val correctAnswers = mutableListOf<Int>()
        val questionTexts = mutableListOf<String>()
        val optionLists = mutableListOf<String>()

        questions.forEach { question ->
            val selected = userAnswers[question.id] ?: -1
            if (selected == question.correctAnswer) correct++
            selectedAnswers.add(selected)
            correctAnswers.add(question.correctAnswer)
            questionTexts.add(question.question)
            optionLists.add(question.options.joinToString("|")) // Separar opciones con |
        }

        // Enviar datos simples a ResultActivity
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("CATEGORY", category)
        intent.putExtra("DIFFICULTY", difficulty)
        intent.putExtra("TOTAL", questions.size)
        intent.putExtra("CORRECT", correct)
        intent.putExtra("SELECTED_ANSWERS", selectedAnswers.toIntArray())
        intent.putExtra("CORRECT_ANSWERS", correctAnswers.toIntArray())
        intent.putExtra("QUESTION_TEXTS", questionTexts.toTypedArray())
        intent.putExtra("OPTION_LISTS", optionLists.toTypedArray())

        startActivity(intent)
        finish()
    }
}