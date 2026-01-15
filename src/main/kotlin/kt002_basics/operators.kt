package kt002_basics

import kotlin.math.abs
import kotlin.math.max
import kotlin.random.Random

fun main(args: Array<String>) {
    println()
    val a = abs(Random.nextInt())
    val b = abs(Random.nextInt()) * -1
    println("a=$a, b=$b")

    // Arithmetic Operators
    //  |   op  | using method  |
    //  |   +   | a.plus(b)     |
    //  |   -   | a.minus(b)    |
    //  |   *   | a.times(b)    |
    //  |   /   | a.div(b)      |
    //  |   %   | a.rem(b)      |
    println()
    println("a + b : ${a + b}")
    println("a.rem(b) : ${a.rem(b)}")

    // Relational ops
    //  |   op  |       using method    |
    //  |   >   | a.compareTo(b) > 0    |
    //  |   >=  | a.compareTo(b) >= 0   |
    //  |   <   | a.compareTo(b) < 0    |
    //  |   <=  | a.compareTo(b) <= 0   |
    //  |   ==  | a.equals(b)           |
    //  |   !=  | !a.equals(b)          |
    val d = abs(Random.nextInt());
    println("\nd=$d")
    println("a.compareTo(d): ${a.compareTo(d)}")

    // Assignment ops
    // =, +=, -=, *=, /=, %=

    // Unary ops
    // ++, --
    // similar to: += 1, -=1
    // note:
    // - a++, a--: use first, increase/decrease later
    // - ++a, --a: increase/decrease first, use later

    // Logical ops
    // && for AND
    // || for OR
    // ! for NOT

    // Bitwise ops
    // shr for >>
    // shl for <<
    // ushr for >>>
    // and for &
    // or for |
    // not for ~
    println("\na: ${a.toString(2)} ($a), b: ${b.toString(2)} ($b)") // toString(radix)
    println("a and b: ${a and b}");
    println("a or b: ${a or b}");
    // note:
    // - shr: do not shift the sign bit.
    // - ushr: shift anybit, including sign bit
    println("b.shr(1): ${b.shr(1)}"); // .div(2)
    println("b.ushr(1): ${b.ushr(1)}"); // negative number always be converted to positive number

    // Ternary op
    // if (condition) valueIfTrue else valueIfFalse
    val c = abs(Random.nextInt());
    val maxNum = if (a > c) {
        if (a > d) a else d
    } else {
        if (c > d) c else d
    } //max(a, max(c, d)) :))
    print("\nmax($a, $c, $d) is $maxNum");

    println("\n--------------------")
    println("#vduczz")
}