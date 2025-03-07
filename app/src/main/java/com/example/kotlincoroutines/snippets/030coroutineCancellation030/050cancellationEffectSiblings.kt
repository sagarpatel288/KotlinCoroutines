package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.*

class CancellationEffectOnSiblings {

    /**
     * In this example, we see that canceling a sibling, does not cancel the other siblings.
     * Both the siblings are children of the `outermostJob` and each sibling has its own coroutine.
     */
    fun nestedScopesAndCoroutines() = runBlocking {
        val outermostJob = CoroutineScope(Dispatchers.Default).launch {
            val innerJob1 = launch {
                suspendFunctionOne()
            }
            val innerJob2 = launch {
                suspendFunctionTwo()
            }
            delay(1000L)
            innerJob1.cancel()
            println("Cancelled the innerJob1")
            // Cancelling the `innerJob1` may not cancel the `innerJob2` as they are siblings.
        }
        outermostJob.join()
    }

    private suspend fun suspendFunctionOne() {
        return withContext(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000L)
            }
            withContext(Dispatchers.Default) {
                repeat(100) {
                    ensureActive()
                    println("Fn1: Inside the default: Repeating: $it times")
                    (1..10).map {
                        it * it
                    }
                }
            }
        }
    }

    private suspend fun suspendFunctionTwo() {
        return withContext(Dispatchers.Default) {
            withContext(Dispatchers.IO) {
                Thread.sleep(2000L)
            }
            withContext(Dispatchers.Default) {
                repeat(100) {
                    ensureActive()
                    println("Fn2: Inside the default: Repeating: $it times")
                    (1..10).map {
                        it * it
                    }
                }
            }
        }
    }
}

fun main() {
    CancellationEffectOnSiblings().nestedScopesAndCoroutines()
}