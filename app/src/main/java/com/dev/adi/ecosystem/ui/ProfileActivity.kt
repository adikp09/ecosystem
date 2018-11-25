package com.dev.adi.ecosystem.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import com.dev.adi.ecosystem.Helper
import com.dev.adi.ecosystem.R
import com.dev.adi.ecosystem.adapter.FeedsProfileAdapter
import com.dev.adi.ecosystem.model.DataProfile
import com.dev.adi.ecosystem.presenter.PresenterProfile
import com.dev.adi.ecosystem.presenter.ProfileView
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileView, FeedsProfileAdapter.onClickListener {
    override fun onClickItem(id: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun successGetProfile(list: DataProfile) {
        title = "My Profile"
        textView7.text = Helper(this).upperCaseFirst(list.name)
        feedsProfileAdapter.addData(list.feeds)
        avi.smoothToHide()
    }

    private lateinit var presenterProfile: PresenterProfile
    lateinit var feedsProfileAdapter : FeedsProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(my_toolbar)
        avi.smoothToShow()
        presenterProfile = PresenterProfile(this, this)
        feedsProfileAdapter = FeedsProfileAdapter(this, this)

        rv_feeds_profile.layoutManager = LinearLayoutManager(this)
        rv_feeds_profile.itemAnimator = DefaultItemAnimator()
        rv_feeds_profile.adapter = feedsProfileAdapter

        val extras = intent.extras
        if (extras != null) {
            val idSpeciesExtra = extras.get("idSpecies").toString().toInt()
            presenterProfile.getProfile(idSpeciesExtra)
        } else {
            val idSpecies = Helper(this).getSpeciesId("speciesId")
            presenterProfile.getProfile(idSpecies)
        }

        imageView.setOnClickListener {
            Helper(this).removeIdSpecies("speciesId")
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}
