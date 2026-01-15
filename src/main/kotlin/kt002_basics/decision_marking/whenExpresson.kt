package kt002_basics.decision_marking

import kotlin.math.abs
import kotlin.random.Random

// Kotlin `when` expression is same to `swith-case` statement in Java.
// similar to Kotlin `if-else` expression, `when` also has return value.

// the key point that makes Kotlin `when` different to Java `switch-case`:
// - may have return value
// - doesn't need `break` at the end of each case to terminate when expression
// - each case does not need have the same type

// LAST-LINE rules also applied to when expression

fun main(args: Array<String>) {
    // Syntax
    /*
    when (expression) {
        matchableValue1 -> { // single case
            // block 1
            // return value
        }
        matchableValue2, matchableValue3, matchableValueN -> { // combine case
            // do smth
            // return value
        }
        else -> { // default case
            // do smth
            // return value
        }
    }
     */
    // each case have to comparable and return boolean
    // - value -> expression == value
    // - is Type -> expression is Type
    // - in collection -> expression is in collection
    // - expression.booleanMethod()
    // ...

    val score = abs(Random.nextInt() % 11);
    println("score: $score")
    var result = when (score) {
        in 9..10 -> "Gioi"
        7, 8 -> "Kha"
        5, 6 -> "Trung Binh"
        in 0..4 -> "Yeu"
        else -> "Diem khong hop le"
    }
    println(result)


    // when without expression
    // check multi condition / multi expression
    result = when {
        score in 0..4 -> "Yeu"
        score in 9..10 -> "Gioi"
        score in 7..8 -> "Kha"
        score in 5..8 -> "Trung Binh"
        else -> "Diem khong hop le"
    }
    println(result)

    // read more: when + ENUM

    println("\n--------------------")
    println("#vduczz")
}
