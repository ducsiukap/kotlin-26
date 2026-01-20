package kt005_oops.generics

import kt005_oops.safeToString

// invariant: chỉ cho phép làm input/output của method
// in: quản lý các con (gán cho kiểu con) + chỉ làm input
// out: quản lý các cha (gán cho kiểu cha) + chỉ là output

// Variance
// - `Invariant`:
//  => Mặc định của kotlin generic => <T>
//  + cho phép function làm mọi thứ với T.
//  + không thể gán instance của T (cha) cho instance của T (con) và ngược lại
class InvariantBox<T>(var value: T) {
    fun consume(item: T) {
        println("consume $item")
        this.value = item;
    }

    fun produce() = value;
}

fun invariant() {
    var iBox1 = InvariantBox<Int>(3)
    var iBox2 = InvariantBox<Number>(5)

    println("produce ${iBox1.produce()}") // take T as input -> ok
    iBox2.consume(100) // return T as output -> ok

    //    iBox1 = iBox2; // error // InvariantBox<Int> # InvariantBox<Number>
    //    iBox2 = iBox1; // error // InvariantBox<Number> # InvariantBox<Int>
    //    iBox1 = InvarianBox<Short>(10); // error, // InvariantBox<Int> # InvariantBox<Short>
    iBox1 = InvariantBox<Int>(100) // ok, InvariantBox<Int> -> InvariantBox<Int>
    iBox2 = InvariantBox<Number>(200) // ok, InvariantBox<Number> -> InvariantBox<Number>

    val intList = ArrayList<InvariantBox<Int>>();
    intList.add(iBox1); // ok
    //    intList.add(iBox2); // error InvariantBox<Number> # InvariantBox<Int>

    //    val intList2 = ArrayList<InvariantBox<Any>>();
    //    intList2.add(iBox2); // error
    //    intList2.add(iBox1); // error
}

// - `in` : contravariance -> consumer
//  + cho phép gán instance of class cha vào instance of class con, ngược lại thì cút=))
//  + `in T`
//  + T không được là kiểu trả về của hàm, chỉ được làm tham số vào
// => class's properties có type = T => phải private (hoặc private get vì không cho get)
class ContravarianceBox<in T> constructor(private var value: T) {
    // chỉ nhận T làm tham số vao
    fun consume(item: T) {
        println("consume $item")
        this.value = item;
    }

    // không được lấy T làm trả về
    //    fun produce(): T {} // error
    // nhưng dùng T để xử lý vân ok :)
    fun produce(): String? {
        return if (this.value.safeToString()) {
            this.value.toString();
        } else null;
    }
}

fun contravariance() {
    var inBox1 = ContravarianceBox<Int>(3)
    var inBox2 = ContravarianceBox<Number>(5)
    var inBox3 = ContravarianceBox<Long>(6)


    //    inBox1 = inBox3; // error, Int vs Long has no-relation

    // gán con cho cha -> error
    //    inBox2 = inBox3; // error, không gán được con cho cha :)

    // gán cha cho con -> ok
    inBox1 = inBox2; // ok
    inBox3 = inBox2; // ok

    // khi này, gán lại inBox3, inBox1 cho inBox2 lại được =))
    inBox2 = inBox1;

    //    inBox1 = ContravarianceBox<Int>(7)
    //    inBox2 = inBox1; // lại ko đc=)
    // tự giải thích đi nhé. dễ lắm

    inBox1.consume(100);
    inBox2.consume(222.22f);
    inBox3.consume(300);

    // List<Contravariance<T>> chỉ nhận các object có type:
    //  + Contravariance<T>
    //  + Contravariance<super T>
}

// - `out` - covariance // produce, ngược lại với in
//  + cho phép gán instance của con vào cha, ngược lại thì cút=))
//  + `out T`
//  + chỉ được là kiểu trả về của hàm, không được là đầu vào :)
// => class's props có kiểu T chỉ được read-only (val props hoặc private set)
class Covariance<out T>(val value: T) {
    // nhận T làm input -> error
    //    fun consume(item: T) {} // error

    // output ra T -> ok
    fun produce(): T = value;
}

fun covariance() {
    var oBox1 = Covariance<Int>(3);
    var oBox2 = Covariance<Number>(5);

    //    oBox1 = oBox2 // error -> không gán cha cho con
    oBox2 = oBox1 // ok
    oBox1 = Covariance<Int>(100); // cùng T -> ok

    // chỉ tiêu thụ
    println("produce ${oBox1.produce()}")
    println("produce ${oBox2.produce()}")

    // List<Covariance<T>> chỉ nhận các object có type:
    //  + Covariance<T>
    //  + Covariance<U extends T>
}

fun main(args: Array<String>) {
    println()

    invariant()
    println()

    contravariance()
    println()

    covariance()
    println()

    // magic ne:
    val invariantList = ArrayList<InvariantBox<Number>>();

    //    invariantList.add(InvariantBox<Int>(3)); // error
    invariantList.add(InvariantBox(3)); // ok
    // => tại sao
    // khác biệt ở chỗ có chỉ định <T> khi khởi tạo không?
    // - InvariantBox<Int>(3) => đã chỉ định nó là Int
    //      => invariant thì không thể cho vào Number được
    // - InvariantBox(3) => 3 có thể là nhiều loại: Number, Int, ...
    //      => đang cần Number
    //      => có thể trở thành Number
    //          => khi này, InvariantBox(3) (ngầm định) trở thành InvariantBox<Number>(3)
    //              // trường hợp này gọi là Type Inference // tự động nâng kiểu
    //          => add ok!

    // - Tương tự, ta có thể dùng với in/out :)
    // Kotlin lỏ :)
    println(invariantList.toString())

    println("\n--------------------")
    println("#vduczz")
}

// method không làm được in/out ở  fun <T> functionName
// nhưng ở (param: Array<out T>) thì cho phép