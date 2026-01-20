package kt005_oops.interfaces

// Functional Interface - SAM (single abstract method)
// - interface chỉ tồn tại duy nhất 1 abstract function
// - không tồn tại abstract prop
// - còn lại: computed prop, companion object, default/private function vô tư
// - sử dụng `fun interface` thay vì `interface`

// SAM interface là nền tảng của lambda expression,
// cho phép tạo object bằng lambda

fun interface ClickListener {
    fun onClick(position: Int);
}

// typealias: đặt tên cho type có sẵn
// thường dùng cho type phức tạp
typealias callback = (Int, String) -> Unit

// truyền vào đâu đó sử dụng callback thay vì viết dài (Int, String) -> Unit
fun handler(cb: callback) {
    // do smth
}

fun main(args: Array<String>) {
    // để tạo object của functional interface, có nhiều cách:
    // 1. tạo class tường minh + implement
    // 2. anonymous class
    var listener: ClickListener = object : ClickListener {
        override fun onClick(position: Int) {
            println("Button btn is clicked at position $position")
        }
    }
    listener.onClick(1);
    // 3. lambda expression
    listener = ClickListener {
        println("Button btn is clicked at position $it")
    }
    listener.onClick(2);

    // ===============
    // Các functional interface trong kotlin
    // > Runnable: () -> Unit
    // > Callable
    // > Comparator<T>: (T, T) -> Int
    // > Observer / OnClickListener

    // > Consumer<T>: (T) -> Unit // tiêu thụ
    // > Supplier<T>: () -> T
    // > Predicate<T>: (T) -> boolean
    // > Function<T>: (T) -> R
    // BiFunction, Bi...

    println("\n--------------------")
    println("#vduczz")
}