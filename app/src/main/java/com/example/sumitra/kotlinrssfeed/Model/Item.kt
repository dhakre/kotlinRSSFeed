package com.example.sumitra.kotlinrssfeed.Model

import java.io.FileDescriptor

/**
 * Created by sumitra on 3/25/2018.
 */
data class Item(val title:String, val pubDate:String,val link:String, val guid:String,val author:String,val thumbnail:String,val description:String, val content:String,val enclosure: Object,val cateories:List<String>)