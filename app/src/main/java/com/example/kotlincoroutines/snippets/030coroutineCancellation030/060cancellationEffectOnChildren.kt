package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.*

class CancellationEffectOnChildren {

    /**
     * In this example, we see that canceling a parent coroutine, can also cancel its children.
     * The `innerJob1` and the `innerJob2` are children of the parent `outermostJob`.
     * When we cancel the `outermostJob`, both the children are cancelled.
     */
    fun nestedScopesAndCoroutines() = runBlocking {
        val outermostJob = launch {
            val innerJob1 = launch {
                delay(1000L)
                println("innerJob1 finished!")
            }
            val innerJob2 = launch {
                delay(1000L)
                println("innerJob2 finished!")
            }
        }
        delay(1000L)
        outermostJob.cancel()
        println("Cancelled the outermost job!")
    }
}

fun main() {
    CancellationEffectOnChildren().nestedScopesAndCoroutines()
}