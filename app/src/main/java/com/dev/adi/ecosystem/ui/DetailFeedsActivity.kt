package com.dev.adi.ecosystem.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.dev.adi.ecosystem.Helper
import com.dev.adi.ecosystem.R
import com.dev.adi.ecosystem.adapter.ReplyAdapter
import com.dev.adi.ecosystem.model.DataFeeds
import com.dev.adi.ecosystem.presenter.FeedsView
import com.dev.adi.ecosystem.presenter.PresenterFeeds
import kotlinx.android.synthetic.main.activity_detail_feeds.*

class DetailFeedsActivity : AppCompatActivity(), FeedsView, ReplyAdapter.onClickListener {
    override fun onClickReply(idFeeds: Int) {
        val i = Intent(this, TweatActivity::class.java)
        i.putExtra("idFeeds", idFeeds)
        i.putExtra("type", "reply")
        startActivity(i)
    }

    override fun onClickAva(id: Int) {
        val i = Intent(this, ProfileActivity::class.java)
        i.putExtra("idSpecies", id)
        startActivity(i)
    }

    override fun onClickItem(id: Int) {
        val i = Intent(this, DetailFeedsActivity::class.java)
        i.putExtra("idFeeds", id)
        startActivity(i)
    }

    override fun successGetDetailFeeds(list: DataFeeds) {
        avi.smoothToHide()
        Glide.with(this).load(R.drawable.ic_baseline_account_circle_24px).into(imageView2)
        textView.text = Helper(this).getSpeciesName(presenterFeeds.getDataSpecies(), list.species_id)
        textView2.text = if (list.content != null ) Helper(this).replaceIdContent(presenterFeeds.getDataSpecies(), list.content) else "-Empty Content-"
        textView3.text = Helper(this).getDateTime(list.time.toString())
        if (list.replies.isNotEmpty()) textView5.visibility = View.VISIBLE
        imageView6.setOnClickListener {
            val idFeeds = intent.extras.get("idFeeds").toString().toInt()
            val i = Intent(this, TweatActivity::class.java)
            i.putExtra("idFeeds", idFeeds)
            i.putExtra("type", "reply")
            startActivity(i)
        }
        replyAdapter.addData(list.replies)
    }

    private lateinit var presenterFeeds: PresenterFeeds
    lateinit var replyAdapter: ReplyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_feeds)
        setSupportActionBar(my_toolbar)
        title = "Detail Feeds"
        presenterFeeds = PresenterFeeds(this, this)

        val extras = intent.extras
        if (extras != null) {
            val id = extras.get("idFeeds").toString().toInt()
            presenterFeeds.getDetailFeed(id)
            avi.smoothToShow()
        }

        replyAdapter = ReplyAdapter(this, this)
        rv_reply.layoutManager = LinearLayoutManager(this)
        rv_reply.itemAnimator = DefaultItemAnimator()
        rv_reply.adapter = replyAdapter
    }
}
