package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * This small practical example demonstrates that how a coroutine can be suspended and resumed.
 * It demonstrates how it works, when and how a coroutine pauses the execution, and how it resumes the execution.
 */
fun main() = runBlocking {
    GlobalScope.launch {
        repeat(10) {
            println("runBlocking --> GlobalScope.launch --> before delay $it")
            // `delay` is a suspend function and when a coroutine hits a suspension point,
            // it pauses the execution, waits until the suspend function completes, and then resumes the execution.
            delay(1000)
            println("runBlocking --> GlobalScope.launch --> after delay $it")
        }
    }
    delay(5000)
}