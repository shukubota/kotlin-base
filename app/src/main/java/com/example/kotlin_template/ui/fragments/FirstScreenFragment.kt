package com.example.kotlin_template.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.kotlin_template.R
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

val client:OkHttpClient = OkHttpClient()


data class Greeting(val message: String)

data class SendData(val name: String, val id: Int)

class FirstScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("mainmain", "aaddddwwwwwwwddeeaaaa")
        view.findViewById<Button>(R.id.goSecondScreen)
            .setOnClickListener { v ->
                Log.d("mainmain", "aaddeeaaaa")
                Navigation.findNavController(v) // (1)
                    .navigate(R.id.SecondScreenFragment) // (2)
            }

        view.findViewById<Button>(R.id.callapi)
                .setOnClickListener {
                    Log.d("mainmain", "callapi")
//                    onParallelGetButtonClick();
                    onPost();

                }
    }

    fun onParallelGetButtonClick() = runBlocking {
        GlobalScope.launch() {
//            val client:OkHttpClient = OkHttpClient()
            val url:String = "http://10.0.2.2:3009/greet"
//            val url:String = "http://127.0.0.1:3009/greet"
//            val url:String = "https://connpass.com/api/v1/event/"

            val request = Request.Builder().url(url).get().build()

            client.newCall(request).execute().use { response ->
                var gson = Gson()
                val json_data = gson.fromJson<Greeting>(response.body?.string(), Greeting::class.java)
                
                Log.d("mainmain", "globっっっdalscope in")
                Log.d("mainmain", json_data.javaClass.name)
                Log.d("mainmain", json_data.toString())
            }
            Log.d("mainmain", "globalscope in")
        }.join()
    }

    fun onPost() = runBlocking {
        GlobalScope.launch() {
            val url:String = "http://10.0.2.2:3009/greet"
//            val url:String = "http://127.0.0.1:3009/greet"
//            val url:String = "https://connpass.com/api/v1/event/"

            val sendData = SendData("hoge", 1)
            Log.d("mainmain", sendData.toString())
            var gson = Gson()
            val jsonData = gson.toJson(sendData)

            Log.d("mainmain", jsonData)

            val mediaTypeProt = "application/json; charset=utf-8"
            val mediaType = mediaTypeProt.toMediaTypeOrNull()

            val postBody = jsonData.toString().toRequestBody(mediaType)
            Log.d("mainmain", postBody.toString())
            val request = Request.Builder().url(url).put(postBody).build()

            client.newCall(request).execute().use { response ->
                Log.d("mainmain", response.body?.string())
            }
            Log.d("mainmain", "globalscope in")
        }.join()
    }
}

