package kt002_basics

// Về cơ bản vẫn tương tự Java String và cơ chế StringPool
// Điểm mới:
// - concat / formatted string (đơn giản) => String template (embedded string)
// - sử dụng buildString gọn hơn StringBuilder mà vẫn là 1 cái.
// - raw string
// - cho phép truy cập trực tiếp qua [index] / get(index) thay vì charAt(index)

// note:
// - nối chuỗi nhiều (trong loops, ...) => dùng StringBuilder / buildString {}
// - chỉnh sửa chuỗi => StringBuilder // sb.apply{}
// - nối đơn giản => String template

fun main(args: Array<String>) {
    println()

    // 1. Create String
    // ===============
    // - sử dụng ""
    var myStr = "vduczz";
    println(myStr)
    // - sử dụng buildString + append // StringBuilder
    myStr = buildString {
        append("{");
        append("\n\tname: vduczz,");
        append("\n\tage: 22;");
        append("\n}");
    }
    println(myStr)


    println()
    // 2. accessing element
    // ===============
    println("char at index=13: ${myStr[13]}")
    println("char at index=20: ${myStr.get(20)}")


    println()
    // 3. String template
    val name = "vduczz";
    val age = 20;
    myStr = "{ name: $name, age: $age }";
    // nhúng biến / giá trị đơn giản => $variable
    // nhúng biểu thức phức tạp => ${expression}
    println(myStr)


    println()
    // 4. raw string
    myStr = """
        {
            name: $name,
            age: $age
        }
    """.trimIndent()
    println(myStr)
    // trimIndent() // không cần thêm ký tự lạ vào chuỗi, cắt phần trắng chung nhỏ nhất bên trái của các dòng
    // trimMargin(delimiter) // default delim = "|"
    //                       // có thể set delim tùy ý
    myStr = """
        |{
        |    name: $name,
        |    age: $age
        |}
    """.trimMargin()
    println(myStr)
    myStr = """
        java{
        java    name: $name,
        java    age: $age
        java}
    """.trimMargin("java")
    println(myStr)

    // Various string method...

    println("\n--------------------")
    println("#vduczz")
}