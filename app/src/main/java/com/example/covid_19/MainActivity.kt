package com.example.covid_19

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
        createMatrix()
    }

    private fun bind() {
        pb_circular.visibility = View.VISIBLE
    }

    private fun createMatrix() {
        val defaultLine = intArrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        val matrix = arrayOf(
                defaultLine, defaultLine, defaultLine, defaultLine, defaultLine,
                defaultLine, defaultLine, defaultLine, defaultLine, defaultLine
        )

        for (i in matrix.indices) {
            for (element in matrix[i]) {
                tv_matrix.text = tv_matrix.text.toString() + " $element"
            }
            tv_matrix.text = tv_matrix.text.toString() + "\n"
        }
        pb_circular.visibility = View.GONE
    }
}
