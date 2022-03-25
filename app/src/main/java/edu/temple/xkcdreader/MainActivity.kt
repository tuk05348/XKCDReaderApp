package edu.temple.xkcdreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso


class MainActivity : AppCompatActivity() {

    lateinit var comicNumberEditText: EditText
    lateinit var fetchComicButton : Button
    lateinit var titleTextView: TextView
    lateinit var altTextView: TextView
    lateinit var comicImageView: ImageView

    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        comicNumberEditText = findViewById(R.id.comicNumberEditText)
        fetchComicButton = findViewById(R.id.fetchComicButton)
        titleTextView = findViewById(R.id.titleTextView)
        altTextView = findViewById(R.id.altTextView)
        comicImageView = findViewById(R.id.comicImageView)

        requestQueue = Volley.newRequestQueue(this)

        fetchComicButton.setOnClickListener{
            fetchComic(comicNumberEditText.text.toString())
        }


    }

    fun fetchComic(comicId: String) {
        requestQueue.add(
            JsonObjectRequest(Request.Method.GET,
            "https://xkcd.com/$comicId/info.0.json",
            null,
                {jsonObject ->
                    titleTextView.text = jsonObject.getString("safe_title")
                    altTextView.text = jsonObject.getString("alt")
                    comicImageView.contentDescription = jsonObject.getString("transcript")
                    Picasso.get().load(jsonObject.getString("img")).into(comicImageView)
                },
                {
                    Log.e("Server Response Error", it.toString())
                })
        )
    }

}