package kt004_functions

import kotlin.random.Random

fun sum(a: Int, b: Int) = a + b

fun absMinus(a: Int, b: Int) = if (a > b) a - b else b - a

// Default arguments:
// - arg được định nghĩa giá trị default
// - khi gọi có thể không cần truyền arg đó
fun calculate(method: String = "sum", a: Int, b: Int): Int {
    // method là default arguments => có thể truyền hoặc không
    // a, b là required arguments => phải truyền

    return when (method) {
        "sum" -> sum(a, b);
        "absMinus" -> absMinus(a, b);
        else -> 0;
    }
}

// Named arguments:
// - khi gọi function, chỉ định tham số bằng tên
// => gọi function mà không cần đúng thứ tự

// Varargs
// - tương tự vararg trong java, nhưng không ép vararg phải đứng cuối
// - sử dụng `vararg` keyword
fun sum(vararg numbers: Int): Int {
    var total = 0;
    for (num in numbers) {
        total = sum(total, num);
    }
    return total;
}

//*note: nếu vararg/default argument không đứng cuối,
// các tham số còn lại bắt buộc phải truyền theo named argument.

fun main(args: Array<String>) {
    println();

    // default + named arg arg
    println(calculate(a = 3, b = 5)); // sum(3, 5)
    println(calculate("absMinus", 3, 5)); // normal

    // vararg
    val numbers = intArrayOf(
        Random.nextInt(100),
        Random.nextInt(100),
        Random.nextInt(100),
        Random.nextInt(100),
        Random.nextInt(100)
    );
    // truyền dạng liệt kê
    println(sum(3));
    // truyền mảng => phải sử dụng `*` operator
    // * là spread operator => tách mảng thành các phần tử rời rạc
    println(sum(*numbers));

    println("\n--------------------")
    println("#vduczz")
}
