package kt005_oops.generics

import kotlin.random.Random

// Generic: định nghĩa class/method hoạt động được với các kiểu dữ liệu khác nhau

// generic class
// bổ sung <T>, <K, V> (tùy ý) sau tên class
class Box<T>(var value: T) {
    //body
    override fun toString() = value.toString()
}

// generic method
// bổ sung <T>, <K, V> (tùy ý) trước tên method
fun <T> logAnyThing(item: T) {
    println("item: $item");
}

// constrants -> giới hạn T
// <T : SuperClass> -> T bắt buộc phải là con của SuperClass
// - Any là cha của mọi class
// - Nothing là con của mọi class
// Gia phả :)
//Any?
//├── Any
//│   ├── Unit
//│   ├── Number → Int, Long, Double...
//│   ├── String → CharSequence
//│   ├── Boolean
//│   ├── Array<T>
//│   ├── FunctionN
//│   ├── Iterable → Collection → List / Set ..
//│   └── YourClass
//└── Nothing?
//└── Nothing


class BoxOfNumber<T : Number>(val number: T) {
    override fun toString() = number.toString()
}

fun main(args: Array<String>) {
    println()

    // class generic
    val pythonList = ArrayList<Box<Any>>();
    pythonList.add(Box(1))
    pythonList.add(Box("vduczz"))
    pythonList.add(Box(Array(5) { _ -> Random.nextInt(100) }))
    pythonList.add(Box(-0.4f))
    println("pythonList: ${pythonList.joinToString()}")

    val mixedNumberList = ArrayList<BoxOfNumber<*>>();
    mixedNumberList.add(BoxOfNumber(5));
    mixedNumberList.add(BoxOfNumber(10f));
    println("mixedNumberList: ${mixedNumberList.joinToString()}")

    // method generic
    println()
    logAnyThing("hello") // ok
    logAnyThing<String>("world") // ok
    logAnyThing<Box<Any>>(Box(99f)); // ok
    //    logAnyThing<BoxOfNumber<Double>>(BoxOfNumber(100)); // error
    logAnyThing<BoxOfNumber<Double>>(BoxOfNumber(100.0)); // ok

    println("\n--------------------")
    println("#vduczz")
}