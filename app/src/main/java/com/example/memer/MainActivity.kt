package com.example.memer

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //lateinit var imageView: ImageView
    var currentURL: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var imageView = findViewById(R.id.imageView)
        loadMeme()
    }
    private fun loadMeme(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        progressBar.visibility = View.VISIBLE
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                currentURL = response.getString("url")

                Glide.with(this).load(currentURL).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?,
                                              model: Any?,
                                              target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?,
                                                 model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?,
                                                 isFirstResource: Boolean): Boolean {
                        progressBar.visibility = View.GONE
                        return false
                    }
                }).into(imageView)
            },
            { error ->
                Toast.makeText(this, "Something wrong",Toast.LENGTH_LONG).show()
            }
        )

        // Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest)
    }
    fun nextMeme(view: View) {
        loadMeme()

    }
    fun share(view: View) {
       intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey check this out $currentURL")
        val chooser = Intent.createChooser(intent, "Share this msg using..")
        startActivity(chooser)
    }
}