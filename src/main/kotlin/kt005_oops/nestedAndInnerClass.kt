package kt005_oops

// Inner/Nested class là class được khai báo bên trong 1 class khác.

// Nested classes
// - no-keyword
// - không tham chiếu tới outer => Không truy cập được member của outer class.
// - `public static class` trong java -> không cần instance của outer để tạo nested instance

// Inner classes
// - `inner` keyword
// - giữ tham chiếu ẩn tới outer -> có thể trực tiếp truy cập member của outer thoải mái.
//      // hoặc phải dùng `this@Outer.prop`
// - non-static class trong java -> cần instance của outer trước khi có thể tạo instance của inner

// Note: java ngược lại giữa inner và nested
class Outer(val prop: Int) {
    class Nested() {
        // cannot access to Outer.prop
    }

    inner class Inner() {
        fun showOuterProp() {
            println("Outer.prop: $prop")
            println("Outer.prop: ${this@Outer.prop}")
        }
    }
}

fun main(args: Array<String>) {
    println()

    // nested có thể tạo instance của nó trực tiếp thông qua outer classs
    val nested = Outer.Nested();

    // inner cần instance của outer trước khi tạo instance của nó
    val outer = Outer(13);
    val inner = outer.Inner();

    // inner có thể truy cập outer prop
    inner.showOuterProp();

    println("\n--------------------")
    println("#vduczz")
}