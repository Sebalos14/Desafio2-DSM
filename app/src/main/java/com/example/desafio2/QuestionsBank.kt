package com.example.desafio2

object QuestionsBank {

    fun getQuestions(category: String, difficulty: String): List<Question> {
        return when (category) {
            "Historia" -> getHistoryQuestions(difficulty)
            "Jugadores" -> getPlayersQuestions(difficulty)
            "Partidos" -> getMatchesQuestions(difficulty)
            "Trofeos" -> getTrophiesQuestions(difficulty)
            else -> getHistoryQuestions(difficulty)
        }
    }

    // ========== HISTORIA ==========
    private fun getHistoryQuestions(difficulty: String): List<Question> {
        val base = listOf(
            Question(1, "¿En qué año se fundó el FC Barcelona?",
                listOf("1899", "1900", "1901"), 0, "Historia", "Fácil"),
            Question(2, "¿Quién fue el fundador del FC Barcelona?",
                listOf("Joan Gamper", "Josep Sunyol", "Agustí Montal"), 0, "Historia", "Fácil"),
            Question(3, "¿Cuál fue el primer estadio del Barça?",
                listOf("Camp de la Indústria", "Les Corts", "Camp Nou"), 0, "Historia", "Fácil"),
            Question(4, "¿En qué año se inauguró el Camp Nou?",
                listOf("1957", "1960", "1955"), 0, "Historia", "Fácil"),
            Question(5, "¿Cuántas Champions League tiene el Barça?",
                listOf("5", "6", "4"), 0, "Historia", "Fácil")
        )
        return if (difficulty == "Difícil") {
            base.mapIndexed { index, q ->
                q.copy(
                    options = when (index) {
                        0 -> listOf("1899", "1898", "1900")
                        1 -> listOf("Joan Gamper", "Hans Gamper", "Josep Sunyol")
                        2 -> listOf("Camp de la Indústria", "Les Corts", "Camp Nou")
                        3 -> listOf("1957", "1958", "1956")
                        4 -> listOf("5", "6", "4")
                        else -> q.options
                    }
                )
            }
        } else base
    }

    // ========== JUGADORES ==========
    private fun getPlayersQuestions(difficulty: String): List<Question> {
        val base = listOf(
            Question(10, "¿Qué jugador es conocido como 'La Pulga'?",
                listOf("Lionel Messi", "Ronaldinho", "Neymar"), 0, "Jugadores", "Fácil"),
            Question(11, "¿Quién es el máximo goleador histórico del Barça?",
                listOf("Lionel Messi", "César Rodríguez", "Luis Suárez"), 0, "Jugadores", "Fácil"),
            Question(12, "¿Qué jugador holandés es leyenda del Barça?",
                listOf("Johan Cruyff", "Frank Rijkaard", "Ronald Koeman"), 0, "Jugadores", "Fácil"),
            Question(13, "¿Qué jugador ganó 6 Balones de Oro en el Barça?",
                listOf("Lionel Messi", "Ronaldinho", "Rivaldo"), 0, "Jugadores", "Fácil"),
            Question(14, "¿Quién fue el entrenador del Barça en el sextete de 2009?",
                listOf("Pep Guardiola", "Frank Rijkaard", "Luis Enrique"), 0, "Jugadores", "Fácil")
        )
        return if (difficulty == "Difícil") {
            base.map { q ->
                q.copy(options = q.options.shuffled())
            }
        } else base
    }

    // ========== PARTIDOS ==========
    private fun getMatchesQuestions(difficulty: String): List<Question> {
        return listOf(
            Question(20, "¿Contra qué equipo fue la remontada de 6-1 en Champions 2017?",
                listOf("PSG", "Bayern Munich", "Juventus"), 0, "Partidos", "Fácil"),
            Question(21, "¿Cuál fue el marcador del Barça vs Real Madrid en el 5-0 de 2010?",
                listOf("5-0", "5-1", "4-0"), 0, "Partidos", "Fácil"),
            Question(22, "¿En qué año el Barça ganó su primera Champions?",
                listOf("1992", "1991", "1993"), 0, "Partidos", "Fácil"),
            Question(23, "¿Quién anotó el gol del 2-2 en la final de Wembley 1992?",
                listOf("Koeman", "Stoichkov", "Laudrup"), 0, "Partidos", "Fácil"),
            Question(24, "¿Contra qué equipo perdió el Barça la final de Champions 1994?",
                listOf("AC Milan", "Juventus", "Ajax"), 0, "Partidos", "Fácil")
        )
    }

    // ========== TROFEOS ==========
    private fun getTrophiesQuestions(difficulty: String): List<Question> {
        return listOf(
            Question(30, "¿Cuántas Ligas tiene el Barça (aprox)?",
                listOf("26", "27", "25"), 0, "Trofeos", "Fácil"),
            Question(31, "¿Cuántas Copas del Rey tiene el Barça?",
                listOf("31", "30", "32"), 0, "Trofeos", "Fácil"),
            Question(32, "¿Qué año fue el sextete del Barça?",
                listOf("2009", "2010", "2011"), 0, "Trofeos", "Fácil"),
            Question(33, "¿Cuántas Champions League ganó el Barça en la era Messi?",
                listOf("4", "3", "5"), 0, "Trofeos", "Fácil"),
            Question(34, "¿Qué título ganó el Barça en 2023?",
                listOf("La Liga", "Copa del Rey", "Supercopa"), 0, "Trofeos", "Fácil")
        )
    }
}