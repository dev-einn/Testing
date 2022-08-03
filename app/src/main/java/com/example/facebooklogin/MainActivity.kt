package com.example.facebooklogin


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.facebooklogin.databinding.ActivityMainBinding
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.lang.Exception
import com.facebook.FacebookSdk;

import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mContentViewBinding: ActivityMainBinding
    lateinit var callbackManager: CallbackManager
    private  val EMAIL = "email"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContentViewBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        FacebookSdk.sdkInitialize(this)
        Helper.printHashKey(this)

        //val loginButton = findViewById<LoginButton>(R.id.loginButton)

      mContentViewBinding.loginButton.setOnClickListener{
          mContentViewBinding.loginButton.setReadPermissions(listOf(EMAIL))
            callbackManager = CallbackManager.Factory.create()

            LoginManager.getInstance().registerCallback(callbackManager, object :FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult?) {
                    val graphRequest = GraphRequest.newMeRequest(result?.accessToken){obj , response->
                        try {
                            if (obj.has("id")){
                                Log.d("FaceBookdata", obj.getString("name"))
                                Log.d("Facebook", obj.getString("email"))

                            }
                        }catch (e:Exception){

                        }

                    }
                    val param =Bundle()
                    param.putString("fields" , "name ,email id")
                    graphRequest.parameters = param
                    graphRequest.executeAsync()
                }

                override fun onCancel() {
                    Toast.makeText(this@MainActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(this@MainActivity, "Login Error", Toast.LENGTH_LONG).show()

                }

            })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode,resultCode,data)
    }

}

