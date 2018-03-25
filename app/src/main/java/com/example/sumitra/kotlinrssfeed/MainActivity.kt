package com.example.sumitra.kotlinrssfeed

import android.app.Dialog
import android.app.ProgressDialog
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.sumitra.kotlinrssfeed.Adapter.FeedAdapter
import com.example.sumitra.kotlinrssfeed.Common.HttpDataHandler
import com.example.sumitra.kotlinrssfeed.Model.RSSObject
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val RSS_link="http://rss.nytimes.com/services/xml/rss/nyt/Science.xml"
    private val RSS_to_JSON_API="https://api.rss2json.com/v1/api.json?rss_url="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title="NEWS"
        setSupportActionBar(toolbar)

        val linearLayoutManager=LinearLayoutManager(baseContext,LinearLayoutManager.VERTICAL,false)
        recycleView.layoutManager = linearLayoutManager

        loadRSS()



    }

    private fun loadRSS()
    {
        val loadRSSAsync=object:AsyncTask<String,String,String>(){
            internal  var mDialog = ProgressDialog(this@MainActivity)

            override fun onPreExecute() {
               mDialog.setMessage("Please wait...")
                mDialog.show()
            }

            override fun doInBackground(vararg params: String): String {
              val result:String
                val http = HttpDataHandler()
                result=http.getHttpDataHandler(params[0])
                return result
            }

            override fun onPostExecute(result: String?) {
                mDialog.dismiss()
                var rssObject:RSSObject
                rssObject= Gson().fromJson<RSSObject>(result,RSSObject::class.java!!)
                val adapter=FeedAdapter(rssObject,baseContext)
                recycleView.adapter=adapter
                adapter.notifyDataSetChanged()
            }
        }

        val url_get_data=StringBuilder(RSS_to_JSON_API)  //call async to get data
        url_get_data.append(RSS_link)
        loadRSSAsync.execute(url_get_data.toString())

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_refresh)
            loadRSS()
        return true
    }
}
