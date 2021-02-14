package com.example.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.data.Message
import com.example.chatbot.utils.BotResponse
import com.example.chatbot.utils.Constans.OPEN_GOOGLE
import com.example.chatbot.utils.Constans.OPEN_SEARCH
import com.example.chatbot.utils.Constans.RECEIVE_ID
import com.example.chatbot.utils.Constans.SEND_ID
import com.example.chatbot.utils.Time
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: Message_Adapter
    private val botList = listOf("Сергей", "Игорь", "Александр", "Валерий")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()
        clickEvents()
        val random = (0..3).random()
        customMessage("Привет, с кем я сегодня разговариваю? А это же ${botList[random]}, чем могу быть полезен?")
    }
    private fun clickEvents(){
        btn_send.setOnClickListener{
            sendMessage()
        }
        et_message.setOnClickListener{
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount - 1)
                }
            }
        }
    }
private fun recyclerView(){
    adapter = Message_Adapter()
    rv_messages.adapter = adapter
    rv_messages.layoutManager = LinearLayoutManager(applicationContext)
}
    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()
        if(message.isNotEmpty()){
            et_message.setText("")
            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String){
        val timeStamp = Time.timeStamp()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECEIVE_ID, timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)

                when(response){
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm:String? = message.substringAfter("поиск")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
    private fun customMessage(message: String){
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECEIVE_ID,timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)

            }
        }
    }
}