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

    /**
     * This is an interesting example.
     * The below suspend function does not handle or check for the coroutine cancellation.
     *
     * Now, at any point, one of the coroutine block, let us assume,
     * the `withContext(Dispatchers.Default)` block has started its execution,
     * and before it completes, we cancel the coroutine in which this suspend function has been called,
     * it will not stop the ongoing execution of the `withContext(Dispatchers.Default)`,
     * because there is no checkpoint inside the `withContext(Dispatchers.Default)` block,
     * that checks whether the coroutine is cancelled or not.
     *
     * In this case, we are wasting our resources.
     *
     * However, if we cancel the coroutine inside which this suspend function has been called,
     * before any of the coroutine inside the suspend block starts execution,
     * let us assume, before the `withContext(Dispatchers.Default)`,
     * then in this case, the `withContext(Dispatchers.Default)` will not start its execution.
     *
     * It means that, by default, a coroutine can check it by default that whether the coroutine is active or not,
     * only when it starts a new coroutine.
     *
     * In other words, a coroutine can check it by default that whether the coroutine is active or not,
     * only when it hits a suspension point.
     *
     * However, we can explicitly put check-points inside any coroutine using `ensureActive()` (recommended),
     * `coroutineContext.isActive`, or by any inbuilt default suspend functions such as `yield()`, `delay()`, etc.
     *
     * Yes, kotlin in-built (pre-defined) suspend functions such as `yield()`, `delay()`, etc., explicitly check
     * whether a coroutine is active or not.
     *
     * However, the straight-forward out-of-the-box, pre-defined, and recommended way to put this check-point is to use
     * the `ensureActive()` function, which is an in-built, pre-defined function.
     *
     * The lesson here is:
     * Whenever we use a suspend function, it is a good practice to use `ensureActive()`,
     * especially during a background-work, CPU intensive work, or UI-update,
     * because if the parent has already been cancelled, we don't want to waste our resources doing unnecessary
     * computation and execution.
     *
     * Whether to use `ensureActive()` inside Android's `viewModelScope` depends on the situation.
     *
     * In most cases, it is safe to use `ensureActive()` inside the `viewModelScope`.
     *
     * For example, we don't stop the ongoing API call, file read/write, or database read/write, etc. when the user
     * changes the configuration. E.g., rotating the screen, changing the theme or language, etc.
     * But in this case, the `viewModelScope` survives anyway. So, we still have the active `viewModelScope`.
     *
     * However, when a user changes the screen, or to be precise, when we get `onCleared()` call of the
     * `viewModelScope`, we want to make sure that any ongoing coroutine work inside the `viewModelScope` stops.
     *
     * Hence, it is safe to use `ensureActive()` inside a `viewModelScope` coroutines / suspend functions.
     *
     * We will see the effect of `ensureActive()` in the next example.
     *
     */
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

