package com.example.kotlincoroutines.snippets.coroutineContexts

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WithContext() {

    suspend fun changeContext() {
        println("Parent Thread 1: ${Thread.currentThread().name}")
        withContext(Dispatchers.IO) {
            println("IO Thread 2: Background work like network operation: ${Thread.currentThread().name}")
            withContext(Dispatchers.Default) {
                println("Default Thread 3: CPU intensive work like sorting: ${Thread.currentThread().name}")
                withContext(Dispatchers.Main) {
                    println("Main Thread 4: update UI, UI related work: ${Thread.currentThread().name}")
                }
            }
        }
    }
}

suspend fun main() {
    val withContext = WithContext()
    withContext.changeContext()
}