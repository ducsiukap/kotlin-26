package kt002_basics.loops

import kotlin.random.Random

fun main(args: Array<String>) {
    val start = Random.nextInt(10) shr 1 shl 1;
    val end = (Random.nextInt(30) shr 1 shl 1) + 10;
    // 1. `for` loops
    // 1.1. for ranges
    // - range = [start, end], step=1
    //      for (i in start..end) {}
    // - range = [start, end), step=1
    //      for (i in start until end) {}
    // - reverse range: [end, start], step=1:
    //      for (i in end downTo start) {}
    // - specified step: => add step param
    //      for (i in start..end step 3) {} // step=3
    print("even number in range [$start, $end]:")
    for (i in start..end step 2) print(" $i")
    println()

    // 1.2. for in collection
    // - get only value:
    //      for (item in collection) {}
    // - get only index:
    //      for (item in collection.indices) {}
    // - get index + value
    //      for ((index, value) in collection.withIndex()) {}

    // 1.3. for in map
    //      for ((key, value) in map) {}


    //=======


    // 2. `while` and `do-while` loop
    // 2.1. while loop
    //      while (condition) {}
    // 2.2. do-while loop
    //      do {} while (condition)
    print("even number in range [$start, $end]: ")
    var i = start;
    while (i <= end) {
        print(" $i")
        i += 2;
    }
    println()

    println("\n--------------------")
    println("#vduczz")
}