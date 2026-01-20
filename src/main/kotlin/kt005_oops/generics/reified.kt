package kt005_oops.generics

//import com.google.gson.Gson

// `reified` - giải quyết vấn đề Type Ensure của Java

// Type Erasure là khi chạy (Runtime), JVM sẽ XÓA SẠCH mọi thông tin về Generics T.
// => runtime: List<String> -> List (không biết bên trong chứa String hay Int)
// => không thể check : if (object is T) ...

// => solution: inline function + reified
// *note: `reified` chỉ đùng được với `inline function`
inline fun <reified T> checkType(obj: Any): Boolean {
    // khi này T trở thành class cụ thể
    // cho phép dùng : obj is T
    return obj is T;
}

// Kotlin style với reified:
//inline fun <reified T> Gson.fromJson(json: String): T {
//    return this.fromJson(json, T::class.java)
//}

fun main(args: Array<String>) {
    val arr = IntArray(5) { i -> i }

    println(checkType<Iterable<*>>(arr));
    println(checkType<Array<*>>(arr));
    println(checkType<IntArray>(arr));

    println(checkType<Int>(arr.size));
    println(checkType<String>(arr));

    println("\n--------------------")
    println("#vduczz")
}