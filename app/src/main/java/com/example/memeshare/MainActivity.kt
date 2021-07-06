package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

           var currentImageUrl: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadMeme()

    }


    fun shareClick(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plane"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Checkout this cool meme I get from Reddit  $currentImageUrl")
        val chooser =Intent.createChooser(intent,"Share this Meme using...")
        startActivity(chooser)
    }


    fun nextClick(view: View) {
        loadMeme()
    }





   private  fun loadMeme(){
           findViewById<ProgressBar>(R.id.pBar).visibility=View.VISIBLE

       
        val textView = findViewById<TextView>(R.id.text)


        // Instantiate the RequestQueue.
        //val queue = Volley.newRequestQueue(this)
        val url =  "https://meme-api.herokuapp.com/gimme"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
                Response.Listener{ response ->
                     //val url =response.getString("url")
                    currentImageUrl=response.getString("url")
                    //Glide.with(this).load(url).into(findViewById<ImageView>(R.id.memeImage))

                    Glide.with(this).load(currentImageUrl).listener(object :RequestListener<Drawable>  {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            findViewById<ProgressBar>(R.id.pBar).visibility=View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            //TODO("Not yet implemented")
                            findViewById<ProgressBar>(R.id.pBar).visibility=View.GONE
                            return false
                        }
                    }).into(findViewById<ImageView>(R.id.memeImage))
                    
                    
                
                },
                Response.ErrorListener {
                   Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_LONG).show()
                })

// Add the request to the RequestQueue.
        //queue.add(jsonObjectRequest)

           MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }





}