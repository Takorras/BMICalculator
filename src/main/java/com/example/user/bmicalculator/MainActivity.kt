package com.example.user.bmicalculator

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    val Tag = "TestLog"

    // Manager of what shows a keyboard
    var inputMethodManager : InputMethodManager? = null
    // background layout
    var mainLayout : LinearLayout? = null

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mainLayout = findViewById(R.id.main_layout) as LinearLayout

        findViewById(R.id.CalcButton)!!.setOnClickListener {
            view -> ClickEvent(view)
        }
    }

    @Override
    override fun onTouchEvent(event: MotionEvent?): Boolean{
        // hide a keyboard
        inputMethodManager!!.hideSoftInputFromWindow(mainLayout!!.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS)
        // focus on mainLayout
        mainLayout!!.requestFocus()

        return true
    }

    fun ClickEvent(view:View){
        val eHeightText = findViewById(R.id.eHeightText) as EditText?
        val eWeightText = findViewById(R.id.eWeightText) as EditText?
        val resultNavigationText = findViewById(R.id.ResultNavigation) as TextView?
        val resultText = findViewById(R.id.ResultText) as TextView?
        val bestWeightNavigationText = findViewById(R.id.BestWeightNavigation) as TextView?
        val bestWeightText = findViewById(R.id.BestWeightText) as TextView?

        if(eHeightText!!.getText().toString() == "" || eWeightText!!.getText().toString() == "") {
            resultNavigationText!!.setText("値を入力して下さい")
            resultText!!.setText("")
            bestWeightNavigationText!!.setVisibility(View.INVISIBLE)
            bestWeightText!!.setText("")
            return
        }

        val height = eHeightText.getText().toString().toDouble()
        val weight = eWeightText.getText().toString().toDouble()

        resultNavigationText!!.setText("あなたのBMIは")
        resultText!!.setText(CalcBMI(height,weight).toString())
        bestWeightNavigationText!!.setVisibility(View.VISIBLE)
        bestWeightText!!.setText(CalcBestWeight(height).toString())
    }

    fun CalcBMI(h:Double,w:Double): Double{
        val bmi = BigDecimal(w / (h/100) / (h/100) )
        return bmi.setScale(1, RoundingMode.CEILING).toDouble()
    }

    fun CalcBestWeight(h:Double): Double{
        val bestWeight = BigDecimal(((h/100) * (h/100)) * 22)
        return bestWeight.setScale(1, RoundingMode.CEILING).toDouble()
    }
}
