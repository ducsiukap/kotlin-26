package kt002_basics.decision_marking

import kotlin.math.abs
import kotlin.random.Random

// In Java, `if-else` is a statement
// In Kotlin, `if-else` is a statement, and an expression -> may return a value

fun main(args: Array<String>) {

    val a = abs(Random.nextInt() % 100);
    val b = abs(Random.nextInt() % 100);
    val c = abs(Random.nextInt() % 100);
    val msg = "max of ($a, $b, $c) is ";
    println("\na=$a, b=$b, c=$c")
    println("\n=====================")

    // 1. if-else control the execution flow
    /*
        if (condition1) {
            // block1
        } else if (condition2) {
            // block2
        } else if (condition3) {
            // block3
        } else {
            // default-block
        }
    */
    // - else-if block / else block may not exist
    var maxNumber: Int?
    if (a > b) {
        if (a > c) maxNumber = a;
        else maxNumber = c;
    } else {
        if (b > c) maxNumber = b;
        else maxNumber = c;
    }
    println("$msg $maxNumber");

    // 2. using if-else as an expression
    // >>> LAST-LINE rule:
    //      if `if/else` block using {} to define its scope,
    //      the last line of each block will become return value
    // - if-else expression must have both `if` and `else` block
    // 2.1. Ternary operator
    // syntax: if (condition) value1 else value2
    // 2.2. if-else expression using if-else statement syntax:
    /*
    if (condition) {
        // block-1
        // return value
    } else if (condition2) {
        // block-2
        // return value
    } ...
    else {
        // block-n
        // return value
    }
     */
    maxNumber = if (a > b) {
        if (a > c) a else c;
    } else {
        if (b > c) b else c
    };
    println("$msg $maxNumber");
    // => if-else expression can be used to:
    //      - assign value for variable
    //      - return value of method, ...

    println("\n--------------------")
    println("#vduczz")
}