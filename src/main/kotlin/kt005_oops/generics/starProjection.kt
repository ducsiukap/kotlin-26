package kt005_oops.generics

// `*` operator: đại diện cho chỗ của nó có generic nhưng không quan tâm
// - READ: luôn trả về `Any?`
// - WRITE: CẤM GHI
// `*` generic is equal to `out Any?`
// đơn giản vì Any? không có super.

class MyBox<U, V>(var u: U, var v: V)

// List<*> , Array<*>, ... nói chung dạng mảng<*>
// => cấm thay đổi phần tử: sửa/thêm
// xóa ok
fun processList(list: MutableList<*>) {
    // write => not allowed
    //        list.add("abc")// error
    //        list[0] = 100;  // error

    list.removeAt(1); // ok

    // read => ok
    val smth = list[0]; // local val smth: Any?
    println(smth.toString())
    println(list.toString())
}

// với class<T> không phải dạng mảng
// cấm sửa các properties có generic mà * đại diện
fun processBox(box: MyBox<*, *>) {
    // ghi vào generic prop được * đại diện
    //    box.u = 100// error
    //    box.v = 100// error
    //    box.u = null// error

    // read
    val u = box.u; //local val u: Any?
    val v = box.v; //local val u: Any?
    println("u: ${u.toString()}, v: ${v.toString()}")
}

fun main(args: Array<String>) {
    println()

    val b = MyBox(10, 20); //local val b: MyBox<Int, Int>
    processBox(b)
    println()

    val list = mutableListOf("hello", 2, 4, "world"); //local val list: List<Comparable<*> & Serializable>
    processList(list);

    println("\n--------------------")
    println("#vduczz")
}