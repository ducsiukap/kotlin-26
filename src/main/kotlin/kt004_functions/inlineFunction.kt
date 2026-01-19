package kt004_functions

import kotlin.system.measureTimeMillis

// Inline function giúp tối ưu hiệu năng cho chường trình
// > Đa số trường hợp inline áp dụng cho function có nhận 1 argument là lambda expression
// Thực chất, mỗi lambda body là 1 object (Anonymous class) // tạo lúc runtime
// => mỗi lần gọi function, 1 object mới được tạo, sau đó gọi .invoke(), cuối dùng là GC dọn
// => lặp càng nhiều -> càng nhiều object được tạo => tài nguyên + thời gian

// Khi hàm có tham số dạng lambda + được khai báo là `inline`, khi gọi:
// - không tạo object
// - không invoke + GC dọn
// - không call stack
// Thay vào đó, mỗi khi lambda được gọi,
// compiler sẽ copy + paste thằng body của lambda vào vị trí đó
// ex:

// Tại sao?
// > Bản chất của inline là copy-paste code:
// - inline function: body của nó được paste vào vị trí nó được gọi
// - params list: (cũng được copy+paste) được bind trực tiếp vào tham số tương ứng
//      + với các tham số thông thuờng: Int, Long, object => không khác biệt so với hàm thông thường
//          => giá trị của biên được truyền vào tham số tương ứng
//      + với tham số là lambda => body của lambda được copy + paste trực tiếp vào vị trí nó được
// gọi
//          => không tạo object, không invoke, không call stack
//          (Với no-inline function, lambda cần được tạo thành object -> invoke() -> GC dọn)

inline fun inlineFunction(action: () -> Unit) {

    // function body
    action() // copy + paste directly lambda at here

    // function body
}

fun noinlineFunction(action: () -> Unit) {
    // function body

    action() // create new lambda object -> call stack

    // function body
}

fun performanceTesting(action: () -> Unit): Long {
    return measureTimeMillis {
        for (i in 1..10000000) { // call 10.000.000 times
            action()
        }
    }
}

// Điểm nổi bật so với Java inline function - Generic
// > Trong java => Khi runtime, Generic bị xóa bỏ // Type ensure
// -> Không thể check T.class hay (object instanceof T)
// > Kotlin: nhờ copy-paste trực tiếp của inline, ta có thể biết chính xác T là gì
// -> kết hợp với `reified` keyword, có thể truy cập class của Generic
// example
inline fun <reified T> checkType(obj: Any) {
    if (obj is T) {
        println("obj is of type ${T::class.java}")
    } else {
        println("obj is NOT of type ${T::class.java}")
    }
}

// inline usecase:
// - generic
// - lambda argument
// - hàm không quá dài

fun main(args: Array<String>) {
    println()

    // inline function performance testing
    var count = 0
    println(
            "inlineFunction execution time: ${
            performanceTesting {
                inlineFunction {
                    // do smth
                    count++
                }
            }
        }"
    )
    println(
            "inlineFunction execution time: ${
            performanceTesting {
                noinlineFunction {
                    // do smth
                    count++
                }
            }
        }"
    )
    println()

    // inline + reified
    checkType<Int>(3)
    checkType<Int>("hello")

    //

    println("\n--------------------")
    println("#vduczz")
}

// Ngoài inline, ta còn có:

// > noinline (dành cho tham số của hàm)
// => giúp tránh trường hợp hàm inline mặc định cũng copy-paste (inline) các đối số của nó,
// lambda không còn là object mà chỉ là khối code đơn giản được copy-paste
//      => không thể truyền lambda đó cho hàm khác / gán cho biến ...
// => noinline được sử dụng cho tham số nào mà muốn giữ nguyên nó là object
inline fun noinlineExample(
        noinline noinlineAction: () -> Unit, // vẫn tạo object + invoke
        inlineAction: () -> Unit, // là khối code được paste, không tạo object
        callback: (() -> Unit) -> Unit
) {
    // call
    inlineAction() // valid
    noinlineAction() // valid

    // assign to variable
    // val lambda = inlineAction // invalid
    val lambda2 = noinlineAction // valid
    lambda2()

    // passing to another function
    // callback(inlineAction); // error
    callback(noinlineAction) // valid
}

// =====================

// > crossinline
// - lambda bình thường không cho phép return
// - lambda trong inline function lại được phép return (vì được copy-paste trực tiếp vào vị trí gọi
// như đoạn code thông thường)
//      *khi này return trong lambda sẽ làm terminate cả inline function!
// => sử dụng crossinline để cấm hoàn toàn việc return từ lambda
// => sử dụng crossinline để cấm hoàn toàn việc return từ lambda
inline fun crossinlineExample(crossinline action: () -> Unit) {
    val runnable = Runnable {
        // action() // invalid if no crossinline
        action() // valid
    }
    runnable.run()
}

// call
fun mainExample() {
    crossinlineExample {
        println("hello")
        //        return // error
    }
}

// =====================

// > value class (inline class)
// Value class là 1 wrapper cho đúng 1 giá trị
// - lúc code, ta biết có class đó.
// - khi runtime, class bị vứt và dùng trực tiếp thứ được wrapped bên trong
// Phải dùng @JvmInline annotation
// - có thể có method
// an toàn vì nó là compile-time, không phải run-time object
@JvmInline value class Password(val value: String)

fun login(password: Password) { // khai báo tham số, biến -> sử dụng className
    // do smth
    println(password.value) // giao tiếp (database, -> ) dùng value
}

// =====================

// > inline property
// - inline cho get/set không được truy cập vào backing field
// trong kotlin, `field` là soft keyword trong get/set đại diện cho chính nó (ô nhớ thật của nó)
// => ý nói: inline get/set không được động vào chính nó (field)
// mà chỉ được tính trên các props khác
