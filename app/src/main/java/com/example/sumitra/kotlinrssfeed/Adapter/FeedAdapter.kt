package com.example.sumitra.kotlinrssfeed.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sumitra.kotlinrssfeed.Interface.ItemClickListener
import com.example.sumitra.kotlinrssfeed.Model.RSSObject
import com.example.sumitra.kotlinrssfeed.R
import kotlinx.android.synthetic.main.row.view.*

/**
 * Created by sumitra on 3/25/2018.
 */



class FeedViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener,View.OnLongClickListener
{

    var txtTitle:TextView
    var txtpubDate: TextView
    var txtContent:TextView

    private var itemClickListener:ItemClickListener?=null

    init {
        txtTitle= itemView.findViewById(R.id.textTitle) as TextView
        txtpubDate= itemView.findViewById(R.id.textpubDate) as TextView
        txtContent= itemView.findViewById(R.id.textContent) as TextView

        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

    override fun onClick(v: View?) {
        itemClickListener!!.OnClick(v,adapterPosition,false)  //symbol !! this will return not null value of the variable
    }

    override fun onLongClick(v: View?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        itemClickListener!!.OnClick(v,adapterPosition,true)

    }

}

class FeedAdapter(private val rssObject:RSSObject, private val mContext:Context):RecyclerView.Adapter<FeedViewHolder>()
{

    private val inflate:LayoutInflater

    init {
        inflate= LayoutInflater.from(mContext)
    }
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {

        holder.txtTitle.text = rssObject.items[position].title
        holder.txtContent.text = rssObject.items[position].content
        holder.txtpubDate.text = rssObject.items[position].pubDate

        holder.setItemClickListener(ItemClickListener { view, position, isLongClick ->

            if (!isLongClick)
            {
                val browserIntent= Intent(Intent.ACTION_VIEW, Uri.parse(rssObject.items[position].link))
                mContext.startActivity(browserIntent)    //calling the browser with the rss link
            }
        })


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedViewHolder {
        val itemView= inflate.inflate(R.layout.row,parent,false)   //data taken from the row.xml file
        return FeedViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rssObject.items.size
    }

}