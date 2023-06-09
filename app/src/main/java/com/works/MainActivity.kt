package com.works
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.works.configs.ApiClient
import com.works.data.User
import com.works.data.UserData
import com.works.services.DummyService

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var usernametext:EditText
    lateinit var passwordtext:EditText
    lateinit var loginButton:Button
    lateinit var dummyService: DummyService

    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor:Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("users", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        dummyService = ApiClient.getClient().create(DummyService::class.java)

        usernametext = findViewById(R.id.etUsername)
        passwordtext = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)
        loginButton.setOnClickListener(btnOnClickListener)

        val username = sharedPreferences.getString("username", "")
        usernametext.setText(username)

    }
    val btnOnClickListener = View.OnClickListener {
        val email = usernametext.text.toString()
        val pass = passwordtext.text.toString()
        val user = User(email,pass)
        dummyService.login(user).enqueue(object : Callback<UserData> {
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                val User = response.body()
                Log.d("status", response.code().toString())
                if (User!= null) {
                    Util.user = User
                    Log.d("User", User.toString())

                    editor.putString("username", User.username)
                    editor.putString("firstName", User.firstName)
                    editor.commit()

                    val intent = Intent(this@MainActivity, ProductActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.e("login", t.toString())
                Toast.makeText(this@MainActivity, "Internet or Server Fail", Toast.LENGTH_LONG).show()
            }
        })
    }
}