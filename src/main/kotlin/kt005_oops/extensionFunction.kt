package kt005_oops

// Extension Functions/Properties
// - gắn thêm hàm/biến vào class bên ngoài class
// + thực chất, nó là static function, nằm bên ngoài class
//      nhận tham số đầu tiên là đối tượng gọi (this)
// => vì vậy:
//  - extension function: không thể truy cập private/protected props
//  - extension property: không có backing field
// - shadowing:
//  + function trùng name (+ param list) => function trong class được ưu tiên hơn
//  + function trùng name khác param list => overload

// note: vì nằm ngoài class nên biến null của class này cũng có thể gọi extension function

// syntax
//      fun ClassName.functionName(paramList: Type): ReturnType {}
// note: gắn vào Companion object -> static function // gọi qua ClassName
fun Int.double(): Int {
    return this * 2
}

fun Int.Companion.double(int: Int): Int {
    return int.double()
}

//
fun Any?.safeToString(): Boolean {
    return this != null
}

fun main(args: Array<String>) {
    println()

    val int: Int = 10;
    println(int.double())
    println(int.double().double().double())

    // extension function of companion object
    println()
    println(Int.double(100))
    println(Int.double(int))

    println()
    // safe to string
    val str: String? = null
    println(str)
    println(str.safeToString())

    println("\n--------------------")
    println("#vduczz")
}