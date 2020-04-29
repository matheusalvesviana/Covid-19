package com.example.covid_19.objects

import kotlin.random.Random

data class People (
    var row:Int,
    var column :Int,
    var isInfected: Boolean,
    var infectedTime: Int,
    var isGroupOfRisk: Boolean = Random.nextBoolean()
)