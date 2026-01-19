package kt004_functions

// Infix function - biến code thành văn xuôi :)
// human readable

// thay vì gọi:
//      objectA.method(objectB)
// ta gọi:
//      objectA method objectB
// :)
// ex:
val map = mapOf(1.to("one"), 2.to("two"))

// => become:
val map2 = mapOf(1 to "one", 2 to "two") // :))

// Để function có thể trở thành infix function:
// - function phải là member function hoặc extension function của class
// - only 1 parameter, no default value / no vararg
// - marked with `infix` keyword
// note: infix có độ ưu tiên thấp nhất trong tất cả các toán tử

// extension function
infix fun Array<Int>.apply(function: (Int) -> Int) {
    for ((index, item) in this.withIndex()) {
        this[index] = function(item)
    }
}

// class member function
//class className {
//    infix fun functionName(arg: type) {
//        //body
//    }
//}

fun main(args: Array<String>) {
    println();

    // base array
    val arrays = Array<Int>(5) { it + 1 };
    println(arrays.contentToString())

    // square
    arrays.apply { it * it }
    println(arrays.contentToString())

    // double
    arrays.apply { it shl 1 }
    println(arrays.contentToString())

    println("\n--------------------")
    println("#vduczz")
}
