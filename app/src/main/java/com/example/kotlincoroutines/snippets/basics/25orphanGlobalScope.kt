package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * This will not print anything because the `main` function finishes,
 * before a GlobalScope finishes its independent execution.
 */
fun main() {
    val job1 = GlobalScope.launch {
        println("Inside the global scope 01")
    }
    val job2 = GlobalScope.launch {
        println("Inside the global scope 02")
    }
    GlobalScope.launch {
        job1.join()
        job2.join()
    }
}