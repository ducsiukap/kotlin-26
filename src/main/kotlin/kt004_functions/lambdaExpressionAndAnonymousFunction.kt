package kt004_functions

import kotlin.random.Random

// Normal function use-cases:
// - Logic được dùng nhiều nơi
// - Logic dài / phức tạp
// - Cần đệ quy
fun calculate(vararg params: Int, function: (IntArray) -> Int): Int {
    // logic abc...
    return function(params)
}

// Lambda expression use-cases:
// - code ngắn, thường làm tham số (callbacks)
// - trailing lambda syntax
// Đặc điểm lambda expression:
// - hàm không có tên, có thể gán/truyền như biến bình thường
// - no recursion
// - no return keyword, apply Last-line rule
// - trailing lambda: nếu tham số cuối của hàm là 1 function,
// được phép đưa lambda ra ngoài () khi gọi hàm
// - phải được bọc trong {}
// syntax:
//      val variable = {args: type ->
//          // statements
//      }
val sum = { numbers: IntArray ->
    // muốn sử dụng vararg trong lambda => truyền dạng array
    var total = 0
    numbers.forEach { total += it }
    total // likely return total;
};

// Anonymous function
// - tương tự lambda nhưng cho phép chỉ định return type và sử dụng return keyword
// cú pháp:
//      val variable = fun(params: type): returnType {
//          // statements
//      }
val multiply =
    fun(numbers: IntArray): Int {
        var result = 1
        numbers.forEach { result *= it }
        return result
    };

fun main(args: Array<String>) {
    println()

    // Trailing syntax:
    // - nếu tham số cuối của function
    // - khi gọi function, có thể đưa lambda ra ngoài ()
    val numbers = IntArray(5) { Random.nextInt(1, 100); };

    //
    println(calculate(*numbers, function = sum));
    println(calculate(*numbers, function = multiply));

    // định nghĩa lambda/anonymous + trailing khi truyền
    numbers.forEach() { it * it };
    // note: nếu lambda chỉ có 1 tham số => có thể sử dụng `it` keyword
    // nếu trong nó tiếp tục có lambda và tiếp tục dùng it thì it con sẽ che it cha.
    // { execute(it); } is similar to {it -> execute(it); }
    println(numbers.contentToString())

    println("\n--------------------")
    println("#vduczz")
}