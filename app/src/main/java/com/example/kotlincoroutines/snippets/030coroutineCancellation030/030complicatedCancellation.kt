package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class ComplicatedCancellation {

    fun nestedScopesAndCoroutines() = runBlocking {
        val outerJob = CoroutineScope(Dispatchers.Default).launch {
            val innerJob = launch {
                suspendFunction()
            }
            delay(2000L)
            innerJob.cancel()
            println("nestedScopesAndCoroutines: Cancelled the innerJob - suspendFunction")
            // Even after canceling the `innerJob`, we might get some print statements from the `suspendFunction`,
        // which is a part of the `innerJob`!
        }
        outerJob.join()
    }

    private suspend fun suspendFunction() {
        withContext(Dispatchers.IO) {
            println("suspendFunction: Inside IO: Doing some blocking background work! Sleeping thread!")
            Thread.sleep(2000L)
            println("suspendFunction: Inside IO: Finished sleeping!")
        }
        withContext(Dispatchers.Default) {
            repeat(100) {
                println("suspendFunction: Inside Default: repeating: $it times")
                (1..10).map {
                    it * it
                }
            }
            println("suspendFunction: Inside the Default: Finished the CPU Intensive work!")
        }
        println("suspendFunction: At the end! Outside of both the withContext!")
    }
}

fun main() {
    ComplicatedCancellation().nestedScopesAndCoroutines()
}

