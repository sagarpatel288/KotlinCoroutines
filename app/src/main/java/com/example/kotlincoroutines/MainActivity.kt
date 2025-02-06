package com.example.kotlincoroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlincoroutines.ui.theme.KotlinCoroutinesTheme
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        concurrency()
    }

    /**
     * This is an example of the concurrency.
     *
     * # What is concurrency?
     *
     * [Reference](https://softwareengineering.stackexchange.com/a/366778/318803)
     * [Reference2](https://medium.com/@itIsMadhavan/concurrency-vs-parallelism-a-brief-review-b337c8dac350)
     *
     * An ability to run and deal with multiple tasks at the same time,
     * not necessarily simultaneously (not necessarily at exact same time).
     * So, concurrency is not necessarily parallelism.
     *
     * It means, the structure, or architecture can manage the multiple tasks out-of-order,
     * without affecting or changing the expected outcome.
     *
     * The structure or architecture does not necessarily pick up the tasks in order.
     *
     * For example, the structure or architecture can pick up the second task first,
     * pause the execution of the second task in middle, start the execution of the first task,
     * finish the execution of the first task, and then resume the execution of the second task.
     *
     * ## Analogy:
     *
     * When we, as a single resource, take a bite of a cake, and then sing for a while, again take another bite,
     * and sing for a while, we are doing concurrency.
     *
     * # What is parallelism?
     *
     * [Reference](https://softwareengineering.stackexchange.com/a/366778/318803)
     * [Reference2](https://medium.com/@itIsMadhavan/concurrency-vs-parallelism-a-brief-review-b337c8dac350)
     *
     * An ability to run and deal with multiple tasks at the exact same time, simultaneously.
     *
     * ## Analogy:
     *
     * When we get a partner, who sings and we eat the cake. We are doing parallelism.
     *
     * # Kotlin Coroutines
     *
     * Kotlin coroutines is a concurrency design pattern.
     *
     * If we run the below code (forget about the best practices for a while to understand the concept),
     * it will not have pre-defined and fixed order of execution.
     * So, run it multiple times and see the output.
     *
     *
     */
    private fun concurrency() {
        println("Started")
        GlobalScope.launch {
            repeat(30) {
                println("First Scope $it")
            }
        }
        GlobalScope.launch {
            repeat(30) {
                println("Second Scope $it")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinCoroutinesTheme {
        Greeting("Android")
    }
}