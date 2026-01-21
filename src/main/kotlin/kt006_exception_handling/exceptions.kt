package kt006_exception_handling

import java.io.File

// 1. So với Java, Kotlin loại bỏ Checked exception
// -> try-catch trở thành việc làm tự giác thay vì bắt buộc

// 2. try-catch trở thành expression -> có thể return/gán cho biến :)
fun strToInt(s: String): Int {
    return try {
        s.toInt();
    } catch (e: NumberFormatException) {
        println("parse error: ${e.message}")
        0
    }
}

// 3. thay try with resources bằng .use()
//      *note: object phải implements Closeable hoặc AutoCloseable
fun writeMsgToFile(filename: String, msg: String) {
    // kotlin chưa có cú pháp gộp nhiều resource trong 1 use
    // nên thường lồng use trong use.

    // note: vì là try-catch nên use cũng có thể trả về giá trị :)
    println("write $msg to file $filename: " + File(filename).printWriter().use {
        it.println(msg);
        0
    }); // 0
}

// 4. runCatching(block: () -> R)
// - try-catch là kiểu truyền thống
// - runCatching là kiểu hiện đại
//      // runCatching trả về Result<T>
fun stoi(s: String): Int {
    // do smth
    val result = runCatching {
        s.toInt();
    }

    // handle the result
    // .onSuccess() // runCatching chạy ok
    result.onSuccess { // onSuccess( {value -> ...} )
        println("success: $it");
    }.onFailure { exception -> // onFailure( { exception -> ... } )
        println("exception: $exception");
    }

    // get the value
    // return result.getOrNull() // value or null
    return result.getOrDefault(0)
    // getOrElse { exception -> ... }
    // getOrThrow...
}

// 4. throw Exception trả về Nothing
//      - Nothing: có ý nghĩa là không bao giờ tồn tại
//          *note: T + sub-type-T = T // ex: Long + Int -> Long
//              // Nothing là sub-type của mọi type -> T + Nothing -> T
//          -> throw trả về Nothing -> cho phép dùng trong expression
//              -> đặc biệt là sau `?:`, trong when, if, ...

// - function có kiểu trả về là Nothing mang ý nghĩa "Hàm này không bao giờ trả về giá trị bình thường"
//  + hàm có exception -> try-catch có thể handle
//  + infinite loop -> treo
fun nothingExample(str: String?) {
    val anotherStr = str ?: throw Exception("str is null") // or error("str is null");
    // error():Nothing / throw ... -> Nothing
    // ok, vì Nothing được bỏ qua khi xét type

    // ý nghĩa thứ 2 của Nothing - smart cast
    // -> nếu code chạy được tới đây -> chắc chắn str không null
    // => type của str lúc này sẽ là String thay vì String?
    //      vì vây, type của anotherString cũng sẽ trở thành str
    println("str.length: ${str.length}, anotherStr.uppercase(): ${anotherStr.uppercase()}")
}

// Vì không còn Checked -> hàm không cần khai báo throws ... để có thể throw exception bên trong
// tuy nhiên, khi dự án có code java gọi hàm này,
// để bắt java handle exception (try-catch / throws),
// phải sử dụng @Throws(ExceptionName::class)

fun main(args: Array<String>) {
    println()

    println(strToInt("100d"))
    println(stoi("100d"))
    writeMsgToFile(
        "./src/main/kotlin/kt006_exception_handling/exceptions.txt",
        "hello world from exceptions.kt"
    )

    nothingExample(str = "hello world")
    // nothingExample(null) // will cause an error

    println("\n--------------------")
    println("#vduczz")
}

// còn lại, cơ bản Kotlin exception giống Java exception