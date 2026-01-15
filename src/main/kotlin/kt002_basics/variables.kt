package kt002_basics

import kotlin.random.Random

fun main(args: Array<String>) {
    println()
    // every variable should be declared before it's used

    // the declaration of the variable type also decides:
    // - type of variable
    // - is nullable?
    // - initialized value

    /* SYNTAX to create a variable:
    > mutable variable: // mutable + non-nullable
        var varName: Type = initializedValue;
    > immutable variable: // immutable + non-nullable
        val varName: Type = initializedValue;
    > nullable variable: // nullable
        var varName: Type? = initializedValueOrNull
        val varName: Type? = initializedValueOrNull
    */
    // khi tạo biến trong Kotlin, ít nhất 1 trong 2 thành phần:
    // Type (hoặc Type?) initializedValue phải được chỉ rõ
    val randInt: Int = Random.nextInt();
    val randInt2 = Random.nextInt(); // int
    var randFloat: Float;
    var nullableVariable: Int? = null;

    // > variable must be assigned a value before be used.
    // println("randFloat: $randFloat"); // error
    randFloat = Random.nextFloat();
    println("randInt: $randInt, type: ${randInt.javaClass}");
    println("randInt2: $randInt2, type: ${randInt2.javaClass}");
    println("randFloat: $randFloat, type: ${randFloat.javaClass}");
    // > nullable variable must be checked is null before be used
    // 3 ways:
    // - if u ensure that variable is not null:
    //      nullableVariable!!
    // or   nullableVariable
    // - fallback value if null:
    //      nullableVariable?: defaultValue
    // - if variable is an object, and you are trying to it field:
    //      nullableObject?.field
    // - directly compare with `null`
    //      if (nullableVariable != null) {}
    println("nullableVariable: ${nullableVariable ?: -1} (default value: -1), type: ${nullableVariable?.javaClass}");

    println()
    // Immutable (declared with `val` keyword) cannot be reassigned.
    // randInt = 5; // error
    // Mutable variable (declared with `var` keyword) can be reassigned.
    randFloat = Random.nextFloat();
    println("reassigned randFloat: $randFloat");
    nullableVariable = 5;
    println("reassigned nullableVariable: $nullableVariable (default value: -1), type: ${nullableVariable.javaClass}");

    println("\n--------------------")
    println("#vduczz")
}