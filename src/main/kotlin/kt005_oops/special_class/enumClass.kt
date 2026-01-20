package kt005_oops.special_class

// Enum class: định nghĩa 1 danh sách các hằng số cố định, không đổi
// - keyword: `enum class`

// basic enum class: chỉ tên
enum class Direction {
    DONG, TAY, NAM, BAC
}

// enum with constructor + prop
enum class Color(val color: String) {
    RED("FF0000"),
    BLUE("00FF00"),
    GREEN("0000FF"); // thêm `;` nếu có hàm bên dưới

    fun printColor() {
        // $name để lấy tên enum hiện tại
        // $ordinal để lấy thứ tự enum hiện tại
        // RED, GREEN, ...
        println("$name: $color, ordinal: $ordinal")
    }
}

// enum as anonymous class
// tự override abstract class của chính nó :)
enum class Calculator {
    PLUS {
        override fun calculate(a: Int, b: Int): Int = a + b
    },
    MINUS {
        override fun calculate(a: Int, b: Int): Int = a - b
    },
    TIMES {
        override fun calculate(a: Int, b: Int): Int = a * b
    },
    DIV {
        override fun calculate(a: Int, b: Int): Int {
            if (b == 0) return Int.MIN_VALUE;
            return a / b;
        }
    }
    ;

    abstract fun calculate(a: Int, b: Int): Int
}

// enum + when
// 99% dùng enum chỉ có tên
fun enumWithWhen(dir: Direction) {
    val direction = when (dir) {
        Direction.BAC ->
            // logic
            "Bac"

        Direction.NAM ->
            // do smth
            "Nam"

        Direction.DONG ->
            // do smth
            "Dong"

        Direction.TAY ->
            // do smth
            "Tay"
    }

    println("No chay huong $direction roi may!")
}

fun main(args: Array<String>) {
    println()

    enumWithWhen(Direction.BAC)

    // duyet toan bo enum
    // -> .entries
    println()
    for (entry in Color.entries) {
        entry.printColor()
    }

    // enum as anonymous class
    println()
    println(Calculator.PLUS.calculate(3, 5))
    println(Calculator.MINUS.calculate(3, 5))

    println("\n--------------------")
    println("#vduczz")
}