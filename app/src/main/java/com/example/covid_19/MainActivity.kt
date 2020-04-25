package com.example.covid_19

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var infectedPeople: Int = 0
    private var recoveredPeople: Int = 0
    private var healthyPeople: Int = 0
    private val matrix = Array(10) { IntArray(10) }
    private var counter = 0
    private var peopleInfected = false
    private var randomValue = Random().nextInt(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
    }

    private fun bind() {
        pb_circular.visibility = View.VISIBLE
        createMatrix()
        var cycles = et_cycle.text.toString()
        button_run_cycle.isEnabled = cycles.isNotEmpty()
        et_cycle.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(text: Editable?) {}

            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cycles = text.toString()
                button_run_cycle.isEnabled = cycles.isNotEmpty()
            }
        })
        button_run_cycle.setOnClickListener { runCycle(Integer.parseInt(cycles)) }
    }

    private fun createMatrix() {

        while (counter < matrix.size) {
            val randRow = Random().nextInt(matrix.size)
            val randColumn = Random().nextInt(matrix.size)
            if (matrix[randRow][randColumn] == 0) {
                if (!peopleInfected && randomValue == 2) {
                    matrix[randRow][randColumn] = 2
                    peopleInfected = true
                } else if (randomValue == 1) {
                    matrix[randRow][randColumn] = 1
                }
                randomValue = if (peopleInfected) {
                    Random().nextInt(2)
                } else {
                    Random().nextInt(3)
                }
                counter++
            }
        }
        refreshMatrix()
        pb_circular.visibility = View.GONE
    }

    private fun refreshMatrix(){
        for (i in matrix.indices) {
            for (element in matrix[i]) {
                if (element != 0) tv_matrix.text = tv_matrix.text.toString() + " $element "
                else tv_matrix.text = tv_matrix.text.toString() + " X "

            }
            tv_matrix.text = tv_matrix.text.toString() + "\n"
        }
    }

    private fun runCycle(cycles: Int) {

    }
}
