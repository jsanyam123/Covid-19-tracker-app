package com.example.vCovid.data

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.util.concurrent.Executors

class NetworkHandler {
    @SuppressLint("StaticFieldLeak")
    class FetchFlag(iv: ImageView, url:String) : AsyncTask<Any, Void, Bitmap?>()
    {
        var imageView = iv
        var url = url
        override fun doInBackground(vararg params: Any): Bitmap? {
            var result: String
            var connection: HttpURLConnection? = null
            try {
                val url = URL(url)
                connection = url.openConnection() as HttpURLConnection
                val httpResult: Int = connection.responseCode
                if (httpResult == HttpURLConnection.HTTP_OK)
                {
                    val inputStream = connection.inputStream
                    return BitmapFactory.decodeStream(inputStream)
                } else {
                    result = connection.responseMessage
                    Log.i("Error",result)
                }
            } catch (e: SocketTimeoutException) {
                result = "Connection Timeout"
                Log.i("Error",result)
            } catch (e: Exception) {
                result = "Error : " + e.message
                Log.i("Error",result)
            } finally {
                connection?.disconnect()
            }
            return null
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
            Log.i("JSON Response Result 1", result.toString())
            imageView.setImageBitmap(result)
        }
    }

    fun flagCall (iv: ImageView, url:String) {
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {

            var imageView = iv
            var url = url
            var result: String
            var imageResult : Bitmap? = null
            var connection: HttpURLConnection? = null
            try {
                val url = URL(url)
                connection = url.openConnection() as HttpURLConnection
                val httpResult: Int = connection.responseCode
                if (httpResult == HttpURLConnection.HTTP_OK)
                {
                    val inputStream = connection.inputStream
                    imageResult =  BitmapFactory.decodeStream(inputStream)
                } else {
                    result = connection.responseMessage
                    Log.i("Error",result)
                }
            } catch (e: SocketTimeoutException) {
                result = "Connection Timeout"
                Log.i("Error",result)
            } catch (e: Exception) {
                result = "Error : " + e.message
                Log.i("Error",result)
            } finally {
                connection?.disconnect()
            }
            handler.post {
                imageView.setImageBitmap(imageResult)
            }
        }
    }
}