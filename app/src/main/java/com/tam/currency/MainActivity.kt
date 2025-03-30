package com.tam.currency

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var txtFrom: TextView
    private lateinit var edtFrom: EditText
    private lateinit var spFrom: Spinner

    private lateinit var txtTo1: TextView
    private lateinit var txtTo2: TextView
    private lateinit var spTo: Spinner

    private lateinit var txtRate: TextView

    private val list = arrayOf(
        "Vietnam - Dong",
        "United States - Dollar",
        "European Union - Euro",
        "Korea - Won",
        "China - Yuan",
    )
    private val acronym = arrayOf("VND", "USD", "EUR", "KRW", "CNY")
    private val symbols = arrayOf("₫", "$", "€", "₩", "¥")
    private val rates = arrayOf(1.0, 0.000042, 0.000038, 0.000057, 0.00029)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtFrom = findViewById(R.id.txtFrom)
        edtFrom = findViewById(R.id.edtFrom)
        spFrom = findViewById(R.id.spFrom)
        txtTo1 = findViewById(R.id.txtTo1)
        txtTo2 = findViewById(R.id.txtTo2)
        spTo = findViewById(R.id.spTo)
        txtRate = findViewById(R.id.txtRate)

        spFrom.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        spTo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)

        edtFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isEmpty()) {
                    txtTo2.text = "0"
                } else {
                    calculate()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                onFromChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                onToChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spFrom.setSelection(1)
        spTo.setSelection(0)
    }

    @SuppressLint("SetTextI18n")
    fun onFromChange() {
        val from = spFrom.selectedItemPosition
        val to = spTo.selectedItemPosition
        val rate = rates[to] / rates[from]
        txtRate.text = "1 ${acronym[from]} = ${"%.2f".format(rate)} ${acronym[to]}"
        txtFrom.text = symbols[from]
        edtFrom.setText("")
        txtTo2.text = "0"
    }

    @SuppressLint("SetTextI18n")
    fun onToChange() {
        val from = spFrom.selectedItemPosition
        val to = spTo.selectedItemPosition
        val rate = rates[to] / rates[from]
        txtRate.text = "1 ${acronym[from]} = ${"%.2f".format(rate)} ${acronym[to]}"
        txtTo1.text = symbols[to]
        edtFrom.setText("")
        txtTo2.text = "0"
    }

    @SuppressLint("SetTextI18n")
    fun calculate() {
        val from = spFrom.selectedItemPosition
        val to = spTo.selectedItemPosition
        val rate = rates[to] / rates[from]
        val amount = edtFrom.text.toString().toDouble()
        val result = amount * rate
        txtTo2.text = "%.2f".format(result)
    }
}