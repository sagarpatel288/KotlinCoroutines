package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.*

class CancellationEffectOnSiblings {

    /**
     * In this example, we see that canceling a sibling, does not cancel the other siblings.
     * Both the siblings are children of the `outermostJob` and each sibling has its own coroutine.
     *
     * However, we might see a different behavior if we launch the `outermostJob` using a separate scope.
     * E.g., using either CoroutineScope or GlobalScope.
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
            delay(1000L)
            innerJob1.cancel()
            println("Cancelled the innerJob1")
            // Cancelling the `innerJob1` may not cancel the `innerJob2` as they are siblings.
        }
        delay(1000L)
    }
}

fun main() {
    CancellationEffectOnSiblings().nestedScopesAndCoroutines()
}