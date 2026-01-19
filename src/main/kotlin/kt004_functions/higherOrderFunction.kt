package kt004_functions

// Higher-order function là nền tảng của functional programming.
// đơn giản là:
// - nhận 1 hàm làm đối số
infix fun String.applyPreview(operaton: (String) -> String): Unit {
    val result = operaton(this)

    println("previous: \"$this\"")
    println("after: \"$result\"")
}

// hoặc
// - trả về 1 hàm
fun operation(op: String): (String) -> String {
    return when (op.lowercase()) {
        "capitalize" -> { str: String ->
            str.lowercase().replaceFirstChar { it.uppercase() }
                .split("\\s+".toRegex())
                .joinToString(" ") { it }
        };
        "titlecase" -> { str: String ->
            str.lowercase().split("\\s+".toRegex())
                .joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
        };
        "camelcase" -> { str ->
            str.lowercase().split("\\s+".toRegex())
                .joinToString("") { word -> word.replaceFirstChar { it.uppercase() } }
                .replaceFirstChar { it.lowercase() };
        }

        "pascalcase" -> { str: String ->
            str.lowercase().split("\\s+".toRegex())
                .joinToString("") { word -> word.replaceFirstChar { it.uppercase() } };
        }

        "snakecase" -> { str: String ->
            str.lowercase().split("\\s+".toRegex()).joinToString("_") { it }
        };
        else -> { str: String -> str }
    }
}

fun main(args: Array<String>) {
    println()

    val str = "kotlin is          amazing! :_)))"
    println(str)

    val operations = arrayOf("capitalize", "titlecase", "camelcase", "pascalcase", "snakecase");
    for (op in operations) {
        println("\nApply $op:")
        // nhận về function từ 1 function
        // như biến bình thường
        val OP = operation(op);

        // truyền function vào 1 function
        // cũng như biến bình thường
        str.applyPreview { OP(it) };
    }

    println("\n--------------------")
    println("#vduczz")
}
