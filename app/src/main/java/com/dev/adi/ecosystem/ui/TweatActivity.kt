package com.dev.adi.ecosystem.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.widget.Toast
import com.bumptech.glide.Glide
import com.dev.adi.ecosystem.Helper
import com.dev.adi.ecosystem.R
import com.dev.adi.ecosystem.presenter.PresenterTweat
import com.dev.adi.ecosystem.presenter.TweatView
import kotlinx.android.synthetic.main.activity_tweat.*

class TweatActivity : AppCompatActivity(), TweatView {

    lateinit var presenterTweat: PresenterTweat
    var type = "tweat"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweat)
        setSupportActionBar(my_toolbar)
        title = "Tweat"
        presenterTweat = PresenterTweat(this, this)
        val extras = intent.extras
        if (extras != null) {
            type = extras.getString("type")
            if (type == "reply") {
                title = "Reply Tweat"
                editText16.hint = "Reply Tweat?"
                editText16.inputType = InputType.TYPE_CLASS_TEXT
                button21.text = "Reply"
                textView54.text = ""
            } else {
                editText16.inputType = InputType.TYPE_CLASS_NUMBER
            }
        } else {
            editText16.inputType = InputType.TYPE_CLASS_NUMBER
        }
        button21.setOnClickListener {
            avi.smoothToShow()
            val idSpecies = Helper(this).getSpeciesId("speciesId")
            if (type == "reply") {
                val idFeeds = extras.get("idFeeds").toString().toInt()
                presenterTweat.replyFeeds(idSpecies, idFeeds, editText16.text.toString())
            } else {
                presenterTweat.tweat(idSpecies, editText16.text.toString().toInt())
            }
        }

        Glide.with(this).load(R.drawable.ic_baseline_account_circle_24px).into(imageView3)
    }

    override fun successTweat(status: Boolean) {
        avi.smoothToHide()
        if (status) {
            finish()
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun successReplyFeed(status: Boolean) {
        avi.smoothToHide()
        if (status) {
            finish()
        } else {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}
