package kt004_functions

// Trong Java, function (method) phải được bọc trong 1 class

// Kotlin là ngôn ngữ hướng hàm - functional friendly
// Trong Kotlin, function là First-class citizen
// có thể:
// - đứng độc lập, bên ngoài class, tương tự funct￿￿n trong C/C++
// - truyền đi như biến hoặc lồng vào nhau
// > Bản chất:
// - với mỗi file .kt, kotlin tạo class dạng FileNameKt
// - với top-level function (đứng ngoài class), kotlin sẽ tạo static method trong class này

// components of a function:
// - `fun` keyword to declare a function
// - function name
// - optional parameters list (enclosed in parentheses)
// - optional return type (Unit is default)
// - function body (enclosed in curly braces or as an expression)
// fun functionName(params:Type):returnType {//body}
fun sayHello(name: String) {
    println("Hello, $name !!!")
}

// Expression body - one line function
// cú pháp khai báo tương tự khai báo biến
fun add(a: Int, b: Int): Int = a + b

fun main(args: Array<String>) {
    println();

    // Unit return-type function
    sayHello("Kotlin");
    sayHello("vduczz");

    // function with return type
    val result = add(2, 3);
    println(result);

    println("\n--------------------");
    println("#vduczz");
}
