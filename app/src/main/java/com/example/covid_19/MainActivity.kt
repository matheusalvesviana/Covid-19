package com.example.covid_19

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.covid_19.objects.People
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var infectedPeople: MutableList<People> = mutableListOf()
    private var recoveredPeople: MutableList<People> = mutableListOf()
    private var healthyPeople: MutableList<People> = mutableListOf()
    private var matrix = Array(10) { IntArray(10) }
    private var counter = 0
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
        button_reset.setOnClickListener {
            infectedPeople = mutableListOf()
            recoveredPeople = mutableListOf()
            healthyPeople = mutableListOf()
            matrix = Array(10) { IntArray(10) }
            counter = 0
            randomValue = Random().nextInt(3)
            createMatrix()
        }
        button_status.setOnClickListener {
            Toast.makeText(
                this,
                "Pessoas saudaveis: ${healthyPeople.size} \nPessoas infectadas: ${infectedPeople.size}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun createMatrix() {

        while (counter < matrix.size) {
            val people: People
            val randRow = Random().nextInt(matrix.size)
            val randColumn = Random().nextInt(matrix.size)
            if (matrix[randRow][randColumn] == 0) {
                if (randomValue == 2) {
                    matrix[randRow][randColumn] = 2
                    people = People(randRow, randColumn, true, 0)
                    infectedPeople.add(people)
                } else if (randomValue == 1) {
                    people = People(randRow, randColumn, false, 0)
                    healthyPeople.add(people)
                    matrix[randRow][randColumn] = 1
                }
                randomValue = Random().nextInt(3)
                counter++
            }
        }
        refreshMatrix()
        pb_circular.visibility = View.GONE
    }

    private fun applyPeoplePosition(peoples: List<People>) {
        peoples.map { matrix[it.row][it.column] = if (it.isInfected) 2 else 1 }
    }

    private fun applyContamination() {
        for (i in 0 until healthyPeople.size) {
            infectedPeople.map {
                if (healthyPeople[i].row == it.row && healthyPeople[i].column == it.column - 1 ||
                    healthyPeople[i].row == it.row && healthyPeople[i].column == it.column + 1 ||
                    healthyPeople[i].column == it.column && healthyPeople[i].row == it.row - 1 ||
                    healthyPeople[i].column == it.column && healthyPeople[i].row == it.row + 1 ||
                    healthyPeople[i].column == it.column - 1 && healthyPeople[i].row == it.row - 1 ||
                    healthyPeople[i].column == it.column - 1 && healthyPeople[i].row == it.row + 1 ||
                    healthyPeople[i].column == it.column + 1 && healthyPeople[i].row == it.row - 1 ||
                    healthyPeople[i].column == it.column + 1 && healthyPeople[i].row == it.row + 1
                ) {
                    healthyPeople[i].isInfected = true
                }
            }
        }
        val newInfecteds = healthyPeople.filter { it.isInfected }
        val newHealthys = infectedPeople.filter { !it.isInfected }
        infectedPeople.addAll(newInfecteds)
        healthyPeople.removeAll(newInfecteds)
        healthyPeople.addAll(newHealthys)
        infectedPeople.removeAll(newHealthys)
    }

    private fun refreshMatrix() {
        tv_matrix.text = ""
        for (i in matrix.indices) {
            for (element in matrix[i]) {
                if (element != 0) tv_matrix.text = tv_matrix.text.toString() + " $element "
                else tv_matrix.text = tv_matrix.text.toString() + " X "

            }
            tv_matrix.text = tv_matrix.text.toString() + "\n"
        }
    }

    private fun runCycle(cycles: Int) {
        matrix = Array(10) { IntArray(10) }
        for (i in 0 until cycles) {
            infectedPeople.map {
                it.infectedTime++
                it.column = Random().nextInt(10)
                it.row = Random().nextInt(10)
                if (it.infectedTime > 4) {
                    it.isInfected = false
                    it.infectedTime = 0
                }
            }

            healthyPeople.map {
                it.column = Random().nextInt(10)
                it.row = Random().nextInt(10)
            }
            applyContamination()
            applyPeoplePosition(infectedPeople)
            applyPeoplePosition(healthyPeople)
            refreshMatrix()
        }

    }
}
