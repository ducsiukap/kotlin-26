package kt005_oops.interfaces

import kotlin.random.Random;

// Interface in Kotlin:
// ---------------
// > components:
// - functions:
//      + abstract function: no body, subclasses have to override
//      + default implementation // normal function
// - properties:
//      + abstract properties
//      + computed properties
// - companion object -> static
// ---------------
// > access modifier // applied for all functions and props
// - public / internal / default
// ---------------
// keyword:
// - abstract class: abstract class, abstract
// - interface: interface
// `:` for all extends and implements

interface MyInterface {
    // properties
    // > abstract prop
    var abstractProp: String

    // > computed prop
    val computedProp: String
        get() = "computed property!"

    // companion object // static
    companion object {
        // static props
        const val CONST: Int = 100
        // static methods
    }

    // function
    // > default function
    fun randomInt() = Random.nextInt(CONST)

    // > abstract function
    fun abstractFun()
    // props, functions can be public, internal and private
}

interface OtherInterface {
    fun randomInt() = Random.nextInt()
}

// can:
// - extends only one super class
// - implements multiple interfaces
class MyClass(
    // - override abstract prop
    override var abstractProp: String
    // or override its get/set function
) : MyInterface, OtherInterface {
    // have to override all abstract member
    // - override abstract method
    override fun abstractFun() {
        println("overriden abstractFun() is called")
    }

    // khi implement nhiều interfaces, mà các interface có cùng hàm
    // khi đó, phải chỉ định hàm cha sẽ được gọi bằng cách override
    override fun randomInt() = super<MyInterface>.randomInt()
    // có thể gọi cả 2 super nếu hợp lí.
}

fun main(args: Array<String>) {
    println()

    val obj = MyClass("abc");

    // method
    obj.abstractFun() // overriden function in subclass
    println(obj.randomInt()) // default function of interface
    // props
    println(obj.abstractProp)
    println(obj.computedProp)

    //statc
    println(MyInterface.CONST);

    println("\n--------------------")
    println("#vduczz")
}

// note:
// - interface có thể extend interface khác, sử dụng toán tử `:`
// ex: A : B, B : C
// khi này, nếu B đã implement (override) abstract method của C,
// A có thể không override lại nữa mà sử dụng lại implementation của B
// => Delegation / ủy quyền

// `sealed interface`: interface chỉ cho phép implement trong cùng file
// => rất phù hợp khi kết hợp với when