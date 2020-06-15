package data

import android.util.Log
import java.net.URL

class Request (private val url: String){
    fun run(){
        val currecy = URL(url).readText()
        Log.d(javaClass.simpleName, currecy)
    }
}
