package com.basics.cft_test

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.basics.cft_test.databinding.ActivityMainBinding
import com.basics.cft_test.model.RowAdapter
import com.basics.cft_test.model.UserActionListener
import com.basics.cft_test.model.Valute
import com.fasterxml.jackson.databind.util.ClassUtil.name
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: RowAdapter
    private lateinit var mainBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val  layoutManager = LinearLayoutManager(this)
        adapter = RowAdapter(object : UserActionListener{
            override fun onClick(valute: Valute) {
                val toValuteChangerIntent = Intent(this@MainActivity,ValuteChangerActivity::class.java)
                    .apply {  }
                toValuteChangerIntent.putExtra("char_code",valute.char_code)
                toValuteChangerIntent.putExtra("value",valute.value)
                toValuteChangerIntent.putExtra("nominal",valute.nominal)
                startActivity(toValuteChangerIntent)
            }
        })
        //todo make async
        if(savedInstanceState !=  null){
            try {
                adapter.valutesList =
                    savedInstanceState.getParcelableArrayList<Valute>("valutes") as ArrayList<Valute>
            }catch (e:Exception){
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            }
        }
        else  adapter.valutesList = readValutesFromURL("https://www.cbr-xml-daily.ru/daily_json.js")
        mainBinding.recyclerView.layoutManager = layoutManager
        mainBinding.recyclerView.adapter = adapter
    }

    private fun readValutesFromURL(url:String) : ArrayList<Valute> {
        var text:String
        val connection = URL(url).openConnection() as HttpURLConnection
        print(connection.responseCode)
        try {
            connection.connect()
            text = connection.inputStream.use { it.reader().use { reader->reader.readText() } }
        }
        finally {
            connection.disconnect()
        }
        val valutesJSON = JSONObject(text).getJSONObject("Valute")
        val valutesKotlin = ArrayList<Valute>()
        val mapper = jacksonObjectMapper()
        valutesJSON.keys().forEach { key ->
            valutesKotlin.add( mapper.readValue<Valute>(valutesJSON[key].toString()))
        }

        return valutesKotlin
    }
    //todo make async



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("valutes",adapter.valutesList)
    }
}


