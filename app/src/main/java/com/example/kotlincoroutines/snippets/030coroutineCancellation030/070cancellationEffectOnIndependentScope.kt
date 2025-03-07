package com.example.kotlincoroutines.snippets.`030coroutineCancellation030`

import kotlinx.coroutines.*

class CancellationEffectOnIndependentScope {

    /**
     * In this example, we see that even after canceling the parent coroutine,
     * we still get the print statements from both the children.
     *
     * Hints:
     * 1. CoroutineScope is an independent scope and it is not tied with the parent runBlocking.
     * 2. If we use `ensureActive()` before we launch the children `innerJob1` and `innerJob2`,
     * we don't get any print statements.
     *
     * The answer is:
     * When we launch a different and an independent coroutineScope inside the runBlocking,
     * it does not have any idea about the cancellation.
     * The cancellation that happens inside the `runBlocking` does not automatically propagate
     * to the independent scope.
     * When we launch a different and an independent scope, we need to use `ensureActive()` to cooperate with
     * cancellation, that might happen inside the parent scope.
     *
     * When we simply use `launch` instead of `CoroutineScope` inside the `runBlocking`,
     * we are not launching an independent scope. The `launch` becomes a child of the `runBlocking`.
     * The parent scope and children cooperate with cancellation.
     * So, in this case, when the cancellation happens inside the `runBlocking`,
     * it properly propagates to the child `launch`, and its children `innerJob1` and `innerJob2`.
     *
     */
    fun nestedScopesAndCoroutines() = runBlocking {

        val outermostJob = CoroutineScope(Dispatchers.Default).launch {
            // We need to check for the cancellation when we launch an independent scope.
            ensureActive() // throws cancellationException when the coroutine is cancelled.
            val innerJob1 = launch {
                delay(1000L)
                println("innerJob1 finished!")
            }
            // The best practice is to check for the cancellation before every coroutine.
            ensureActive() // throws cancellationException when the coroutine is cancelled.
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
    CancellationEffectOnIndependentScope().nestedScopesAndCoroutines()
}