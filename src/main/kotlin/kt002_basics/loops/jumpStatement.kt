package kt002_basics.loops

import kotlin.random.Random

fun main() {
    // 1. `break`
    // using to terminate nearest related loop
    for (i in 1..10) {
        print("$i ")
        if (i == 5) break;
    }
    println()
    // labeled break
    outer@ for (i in 1..5) {
        print("outer $i: ")
        for (j in 1..5) {
            print("\t$j")
            val p = Random.nextFloat()
            if (p > 0.9) break@outer; // 10% chance to break
        }
        println()
    }
    // mọi lệnh bên trong block chứa break và phía sau break sẽ không được thực thi

    println("\n=======\n")
    // 2. `continue`
    // skip current loop step
    for (i in 1..10) {
        if ((i and 1) == 1) continue;
        print("$i ")
    }
    println()
    // labeled skip
    loop_step@
    for (i in 1..5) {
        println("loop step $i:")
        for (j in 1..3) {
            val p = Random.nextFloat()
            println("\tp=$p")
            if (p > 0.9) {
                println("\tskip current loop step!")
                continue@loop_step;
            }
        }
        println("end of loop step $i!")
    }

    // 3. `return`
    // thoát khỏi hàm hiện tại và chuyển về hàm gọi nó

}