package com.example.kotlincoroutines.snippets.basics

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class Concurrency() {

    suspend fun concurrency() {
        coroutineScope {
            val job1 = launch {
                delay(2000L)
                println("concurreny --> GlobalScope.launch --> job1 launch --> ")
            }
            val job2 = launch {
                delay(2000L)
                println("concurreny --> GlobalScope.launch --> job2 launch --> ")
            }
            val jobTime = measureTimeMillis {
                job1.join()
                job2.join()
            }
            println("concurreny --> GlobalScope.launch --> both the jobs took --> $jobTime ms time")
        }
    }
}

fun main() = runBlocking {
    val concurrency = Concurrency()
    concurrency.concurrency()
}