package com.dev.adi.ecosystem.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.adi.ecosystem.Helper
import com.dev.adi.ecosystem.R
import com.dev.adi.ecosystem.model.DataHome
import kotlinx.android.synthetic.main.item_home.view.*

class ReplyAdapter (val context : Context, private val onClick: onClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listData = arrayListOf<DataHome>()
    private val listSpecies = Helper(context).getArrayList("dataSpecies")

    fun addData(data: ArrayList<DataHome>) {
        listData.addAll(data)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var contentData = listData[position].content
        if (contentData != null) {
            holder?.itemView.textView56.text = Helper(context).replaceIdContent(listSpecies, contentData)
        } else {
            holder?.itemView.textView56.text = "-Empty Content-"
        }
        Helper(context).setGlideImage(R.drawable.ic_baseline_account_circle_24px, holder.itemView.imageView4)
        holder?.itemView.textView55.text = Helper(context).getSpeciesName(listSpecies, listData[position].species_id)
        holder?.itemView.textView4.text = Helper(context).getDateTime(listData[position].time.toString())
        holder?.itemView.setOnClickListener { onClick.onClickItem(listData[position].id) }
        holder?.itemView.imageView5.setOnClickListener { onClick.onClickReply(listData[position].id)  }
        holder?.itemView.imageView4.setOnClickListener { onClick.onClickAva(listData[position].species_id)  }

    }

    interface onClickListener {
        fun onClickItem(id: Int)
        fun onClickAva(species_id: Int)
        fun onClickReply(id: Int)
    }

    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView)

}