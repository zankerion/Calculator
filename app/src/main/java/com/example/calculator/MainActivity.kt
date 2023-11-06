package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt
import android.view.View
class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    var oldValue = ""
    var operation = ""
    var operationPerformed = false
    var memory = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Right place for findViewByIds
        val removeAllButton: Button = findViewById(R.id.removeall)
        val removeButton: Button = findViewById(R.id.remove)
        val removeOneButton: Button = findViewById(R.id.removeone)
        val decimalButton: Button = findViewById(R.id.comma)
        val msButton: Button = findViewById(R.id.MSbutton)
        val mPlusButton: Button = findViewById(R.id.Mplusbutton)
        val mMinusButton: Button = findViewById(R.id.Mminusbutton)
        val mrButton: Button = findViewById(R.id.MRbutton)
        val mcButton: Button = findViewById(R.id.MCbutton)

        removeOneButton.setOnClickListener {
            removeOne()
        }

        decimalButton.setOnClickListener {
            addDecimal()
        }
        msButton.setOnClickListener {
            val currentNumber = textView.text.toString().toDoubleOrNull()
            if (currentNumber != null) {
                memory = currentNumber
                operationPerformed = true
            }
        }

        mPlusButton.setOnClickListener {
            val currentNumber = textView.text.toString().toDoubleOrNull()
            if (currentNumber != null) {
                memory += currentNumber
                operationPerformed = true
            }
        }

        mMinusButton.setOnClickListener {
            val currentNumber = textView.text.toString().toDoubleOrNull()
            if (currentNumber != null) {
                memory -= currentNumber
                operationPerformed = true
            }
        }

        mrButton.setOnClickListener {
            textView.text = memory.toString()
            operationPerformed = true
        }

        mcButton.setOnClickListener {
            memory = 0.0
        }

        textView = findViewById(resources.getIdentifier("textView", "id", packageName))

        val numberButtons = listOf(
            R.id.one,
            R.id.two,
            R.id.three,
            R.id.four,
            R.id.five,
            R.id.six,
            R.id.seven,
            R.id.eight,
            R.id.nine
        )

        val operatorButtons = listOf(
            R.id.minus,
            R.id.plus,
            R.id.multiply,
            R.id.divide,
        )

        val unaryOperatorButtons = listOf(
            R.id.negpos,
            R.id.sqroot,
            R.id.onedividex,
            R.id.percent
        )

        numberButtons.forEach { id ->
            val button: Button = findViewById(id)
            button.setOnClickListener { numberClick(it) }
        }

        operatorButtons.forEach { id ->
            val button: Button = findViewById(id)
            button.setOnClickListener { operationClick(it) }
        }

        unaryOperatorButtons.forEach { id ->
            val button: Button = findViewById(id)
            button.setOnClickListener { unaryOperationClick(it) }
        }

        findViewById<Button>(R.id.equal).setOnClickListener {
            calculate(it)
        }

        removeAllButton.setOnClickListener {
            clearEverything()
        }

        removeButton.setOnClickListener {
            clearEverything()
        }
    }


    private fun numberClick(view: View) {
        if (view is Button) {
            if(operationPerformed) {
                textView.text = ""
                operationPerformed = false
            }
            textView.append(view.text)
        }
    }

    private fun operationClick(view: View) {
        if (view is Button) {
            oldValue = textView.text.toString()
            operation = view.text.toString()
            operationPerformed = true
        }
    }
    private fun clearEverything() {
        textView.text = ""
        oldValue = ""
        operation = ""
        operationPerformed = false
    }
    private fun removeOne() {
        var text = textView.text.toString()
        if (text.isNotEmpty()) {
            text = text.substring(0, text.length - 1)
            textView.text = text
        }
    }

    private fun addDecimal() {
        var text = textView.text.toString()
        if (text.isNotEmpty() && !text.contains(".")) {
            text += "."
            textView.text = text
        }
    }

    private fun unaryOperationClick(view: View) {
        if (view is Button) {
            val value = textView.text.toString().toDoubleOrNull()
            if (value != null) {
                when (view.id) {
                    R.id.negpos -> textView.text = (-value).toString()
                    R.id.sqroot -> textView.text = sqrt(value).toString()
                    R.id.onedividex -> textView.text = (1/value).toString()
                    R.id.percent -> textView.text = (value / 100.0).toString()
                }
                operationPerformed = true
            }
        }
    }

    private fun calculate(view: View) {
        if (textView.text != "" && oldValue != "") {
            val newValue = textView.text.toString().toDouble()
            val oldValueDecimal = oldValue.toDouble()
            val result = when (operation) {
                "-" -> oldValueDecimal - newValue
                "+" -> oldValueDecimal + newValue
                "*" -> oldValueDecimal * newValue
                "/" -> if(newValue != 0.0) oldValueDecimal / newValue else "Cannot divide by zero"
                else -> "Unknown operation"
            }
            textView.text = result.toString()
            operationPerformed = true
        }
    }
}