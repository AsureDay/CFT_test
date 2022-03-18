package com.basics.cft_test

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.basics.cft_test.databinding.ActivityValuteChangerBinding
import java.lang.Exception

class ValuteChangerActivity: AppCompatActivity() {
    lateinit var thisBinding:ActivityValuteChangerBinding
    var value:Double = 0.0
    var nominal:Double = 0.0
    lateinit var charCode:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val thisBinding = ActivityValuteChangerBinding.inflate( layoutInflater)
        setContentView(thisBinding.root)
        nominal =  intent.getDoubleExtra("nominal",1.0)
        value = intent.getDoubleExtra("value",0.0)
        charCode = intent.getStringExtra("char_code").toString()
        thisBinding.constTextView.text = "RUB to $charCode is "
        thisBinding.resultTextView.text = "0.0 $charCode"
        thisBinding.inputField.setOnEditorActionListener{v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                val rub = thisBinding.inputField.text.toString().toDouble()
                thisBinding.resultTextView.text = "${String.format("%.3f", change(rub))} $charCode"
                true
            } else {
                false
            }
        }
    }
    private fun change(rub: Double): Double {
        return try {
            nominal * rub / value
        } catch (e: Exception) {
            0.0
        }
    }
    private fun changed() {

        // thisBinding.resultTextView.text = "${change(rub).toString()} $charCode"
    }
}