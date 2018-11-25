package com.dev.adi.ecosystem.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import com.dev.adi.ecosystem.Helper
import com.dev.adi.ecosystem.R
import com.dev.adi.ecosystem.adapter.HomeAdapter
import com.dev.adi.ecosystem.model.DataHome
import com.dev.adi.ecosystem.model.DataSpecies
import com.dev.adi.ecosystem.presenter.HomeView
import com.dev.adi.ecosystem.presenter.PresenterHome
import com.readystatesoftware.chuck.Chuck
import kotlinx.android.synthetic.main.activity_main.*


class HomeActivity : AppCompatActivity(), HomeView, HomeAdapter.onClickListener {
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

    private lateinit var presenterHome: PresenterHome
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Chuck.getLaunchIntent(this)
        setSupportActionBar(my_toolbar)
        avi.smoothToShow()
        title = "Feeds"

        presenterHome = PresenterHome(this, this)
        presenterHome.getHomeFeeds()

        homeAdapter = HomeAdapter(this, this)
        rv_feeds.layoutManager = LinearLayoutManager(this)
        rv_feeds.itemAnimator = DefaultItemAnimator()
        rv_feeds.adapter = homeAdapter

        floatingActionButton.setOnClickListener {
            val idSpecies = Helper(this).getSpeciesId("speciesId")
            if (idSpecies == 0) {
                dialogBuilder()
            } else {
                startActivity(Intent(this, TweatActivity::class.java))
            }
        }
    }

    override fun onClickItem(id: Int) {
        val i = Intent(this, DetailFeedsActivity::class.java)
        i.putExtra("idFeeds", id)
        startActivity(i)
    }

    override fun successGetHome(list: ArrayList<DataHome>) {
        homeAdapter.addData(list)
        presenterHome.getSpecies()
    }

    override fun successGetSpecies(list: ArrayList<DataSpecies>) {
        Helper(this).saveArrayList(list, "dataSpecies")
        avi.smoothToHide()
    }

    override fun failedGetHome(message: String) {
        avi.smoothToHide()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun emptyFeeds(message: String) {
        avi.smoothToHide()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val idSpecies = Helper(this).getSpeciesId("speciesId")

        if (id == R.id.action_account) {
            if (idSpecies == 0) {
                dialogBuilder()
            } else {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun dialogBuilder() {
        val listSpecies = Helper(this).getArrayList("dataSpecies")
        val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose an Species")
        val arrayAdapter = ArrayAdapter<DataSpecies>(this, android.R.layout.simple_list_item_1, listSpecies)
        mBuilder.setSingleChoiceItems(arrayAdapter, 0) { dialog, which ->
            Helper(this).saveSpeciesId(which + 1, "speciesId")
            dialog.dismiss()
            startActivity(Intent(this, TweatActivity::class.java))
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
}
