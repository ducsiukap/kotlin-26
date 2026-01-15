package kt002_basics

import kotlin.random.Random;

fun main(args: Array<String>) {
    println();

    // 1. Primitive data types
    // >> Dù mọi kiểu dữ liệu trong Kotlin đều là Object,
    // nhưng khi biên dịch và chạy trên JVM, các kiêu Number, Boolean, Char
    // được dịch về các kiểu nguyên thủy tương ứng của Java

    // 1.1. Numeric types
    // 1.1.1. Integer
    // > Byte   : 8-bits [-128, 127]
    // > Short  : 16-bits [-32768, 32767] (2-bytes int)
    // > Int    : 32-bits [-2147483648, 2147483647] (4-bytes, |2.14e9|)
    // > Long   : 64-bits [-9223372036854775808, 9223372036854775807] (8-bytes, |9.22e18|, 'L' suffix)
    val randInt = Random.nextInt();
    println("randInt: $randInt");
    // 1.1.2. Floating Point
    // > Float  : 32-bits, 'F' suffix (optional), 6 decimal precision
    // > Double : 64-bits, 15 decimal precision
    val randFloat = Random.nextFloat();
    println("randFloat: $randFloat");

    // 1.2. Boolean type
    // > Boolean    : 1-bit -> true / false
    val randInt2 = Random.nextInt();
    val isRandEqual = (randInt == randInt2);
    println("$randInt == $randInt2 is $isRandEqual");

    // 1.3. Character type
    // > Char   : 16-bits, '\u0000' (0) to '\uFFFF' (65535)
    val randChar = (Random.nextInt() % 26 + 97).toChar();
    println("randChar: $randChar");


    // 2. Reference types
    // usually is an Object.
    // hold address of an object, not the object's data.
    // e.g., String, Array, Class, Interface, Object.


    // 3. Type conversion
    // Java cho phép cast ngầm giữa kiểu nhỏ hơn sang kiểu lớn hơn
    // eg. int -> long
    // Nhưng Kotlin không cho phép chuyển đổi ngầm định giữa 2 kiểu dữ liệu khác nhau
    // phải dùng .toType() để chuyển sang type tương ứng.
    println()
    val randLong = Random.nextInt().toLong();
    val randFloatToDouble = randFloat.toDouble();
    println("randLong: $randLong, type: ${randLong.javaClass}");
    println("randFloat: $randFloat, type: ${randFloat.javaClass}");
    println("randFloatToDouble: $randFloatToDouble, type: ${randFloatToDouble.javaClass}");


    // 4. Nullable
    // add suffix '?' to type's name to make variable nullable
    // eg: Int? -> nullable Int variable

    println("\n--------------------")
    println("#vduczz")
}