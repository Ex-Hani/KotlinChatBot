package com.example.chatbot.utils

import com.example.chatbot.utils.Constans.OPEN_GOOGLE
import com.example.chatbot.utils.Constans.OPEN_SEARCH
import java.lang.Exception

object BotResponse {
    fun basicResponses(_message: String): String {
        val random = (0..2).random()
        val message = _message.toLowerCase()

        return when {
            //Hello
            message.contains("привет") -> {
                when (random) {
                    0 -> "О, привет!"
                    1 -> "Здравствуй"
                    2 -> "Добрый день,человек"
                    else -> "error"
                }
            }
            //Flip
            message.contains("брось")&& message.contains("монетку") -> {
                var r = (0..1).random()
                val result = if (r == 0) "орёл" else "решка"
                "Я кинул монетку - $result"
            }

            //Gets the current time
            message.contains("какое") && message.contains("время?") -> {
                Time.timeStamp()
            }
            //SolveMath
            message.contains("реши")->{
                val equation: String? = message.substringAfter("solve")
                return try {
                    val answer = SolveMath.solveMath(equation ?: "0")
                    answer.toString()
                } catch (e: Exception){
                    "Прости, я не смог решить"
                }
            }

            message.contains("открой") && message.contains("гугл") -> {
                OPEN_GOOGLE
            }
            message.contains("поиск") -> {
                OPEN_SEARCH
            }
            //How are u
            message.contains("как ты") -> {
                when (random) {
                    0 -> "Я в норме"
                    1 -> "В целом хорошо, но я голоден"
                    2 -> "Весьма неплохо, спасибо что спросил, а ты как?"
                    else -> "error"
                }
            }
            else -> {
                when (random) {
                    0 -> "Я не понял, что ты сказал"
                    1 -> "Честно, не знаю"
                    2 -> "Попробуй спросить по-другому"
                    else -> "error"
                }
            }
        }
    }
}