package kt005_oops

import kotlin.random.Random

// Polymorphism
// - multi-implememnt for one function

// Compile-time polymorphism -> function overloading
// - same name function
// - difference param list
fun sum(a: Int, b: Int): Int = a + b
fun sum(a: Int, b: Int, c: Int): Int = a + b + c
fun sum(vararg a: Int): Int = a.sum()

// Runtime polymorphism -> function overriding
// abstraction/inheritance/interfacea

fun main(args: Array<String>) {
    val numbers = IntArray(10) { _ -> Random.nextInt(100) };

    println(sum(numbers[0], numbers[1]))
    println(sum(numbers[0], numbers[1], numbers[2]))
    println(sum(*numbers))

    println("\n--------------------")
    println("#vduczz")
}