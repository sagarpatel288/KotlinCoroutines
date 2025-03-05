package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay

class CoroutineCancellation {

    fun simpleCancellation() = runBlocking {
        val outerJob = CoroutineScope(Dispatchers.IO).launch {
            val innerJob = launch {
                delay(2000L)
                // This will not be printed, because we cancel this job before it gets the chance for execution.
                // The print statement is set to be executed after 2000ms, but we cancel the job after 1000ms.
                println("simpleCancellation: innerJob")
            }
            delay(1000L)
            innerJob.cancel()
            println("simpleCancellation: outerJob: cancelled the innerJob")
        }
        // Without `outerJob.join()`, the `runBlocking` will finish and we will not get any print statement,
        // unless the caller function (in our case, the `main` function) is busy doing something else and is incomplete.
        outerJob.join()
    }
}

fun main() {
    val cancellation = CoroutineCancellation()
    cancellation.simpleCancellation()
}

