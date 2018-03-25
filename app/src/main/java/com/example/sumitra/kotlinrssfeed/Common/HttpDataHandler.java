package com.example.sumitra.kotlinrssfeed.Common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static java.lang.System.in;

/**
 * Created by sumitra on 3/25/2018.
 */

public class HttpDataHandler {

    static String stream="";

    public HttpDataHandler(){}

    public String getHttpDataHandler(String urlString)
    {
        try{
            URL url=new URL(urlString);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();

            if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream=new BufferedInputStream(urlConnection.getInputStream());


                BufferedReader r=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer sb = new StringBuffer();
                String line=" ";
                while ((line =r.readLine())!=null)
                          sb.append(line);
                          stream=sb.toString();

                urlConnection.disconnect();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return stream;
    }
}
