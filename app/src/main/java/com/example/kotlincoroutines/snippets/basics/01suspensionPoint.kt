package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("Before the suspension point")
    // The `delay` function suspends the coroutine for 3 seconds.
    delay(3000) // The `delay` function is a suspendable function, a suspension point.
    // The below line will be executed after the delay of 3 seconds.
    println("After the suspension point")
}