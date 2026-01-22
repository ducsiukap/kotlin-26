package kt007_collections

import kotlin.math.abs
import kotlin.math.sqrt
import kotlin.random.Random
import kotlin.system.measureTimeMillis

// > Các collection trong Kotlin được chia vào 2 group:
// ---------------
// >>> Immutable -> read-only
//      - includes: instance của interface List / Set / Map.
//          hoặc instance của các class cụ thể implements các interfaces này!
//      - function supported: get, set, contain, ...
//          // tất cả các function chỉnh sửa collection như
//          .add(), .remove(), .clear() đều không được hỗ trợ
// ---------------
// >>> Mutable -> editable
//      - include: instance của interface MutableList / MutableSet / MutableMap
//          hoặc instance của các class cụ thể implements các interfaces này!
//      - functions: kế thừa mọi hàm có trong các immutable interface tương ứng
//          // bổ sung các hàm chỉnh sửa: .add(), .remove(), .clear()

// ===============
// > Collection Hierarchy
// ---------------
// >>> Diagram
//      [ MutableIterable ]     ->      [ Iterable ]
//              ^️                              ^
//              |                              |
//      [ MutableCollection]    ->      [ Collection]
//              ^                               ^
//              |                       ________|____
//              |                       |           |
//              |                   [ List ]      [ Set ]
//              |
//              |----[ MutableList]             ________________________
//              |                              |*note                   |
//              |----[MutableSet]              |    A -> B is           |
//                                             |    A inherits from B   |
//                                             |________________________|
//      [ Map ] <- [MutableMap]
// ---------------
// >>> Type of Collection
// >>>>>> List
//      - Immutable List -> listOf(vararg elements)
//      - Mutable List  -> mutableListOf()
//                      -> ArrayList, LinkedList, Vector (thread-safe), Stack
//                              // Vector, Stack đã quá cũ và không còn được dùng
// >>>>>> Set
//      - Immutable -> setOf(vararg elems)
//      - Mutable   -> mutableSetOf()
//                  ->HashSet, TreeSet, LinkedHashSet
// >>>>>> Map
//      - Immutable -> mapOf(vararg pairs) // pair: a.to(b) or a to b // entry
//      - MutableMap -> mutableMapOf()
//                   -> HashMap, LinkedHashMap, TreeMap, Hashtable
//                              // tương tự Vector, Stack thì Hashtable cũng là công nghệ cũ
//                              // nên không dùng Hashtable
// >>>>>> Queue/ Deque
//      - ArrayQueue // mảng 2 đầu -> làm được cả LIFO và FIFO
//                   // chuẩn mới thay cho Stack, nhanh hơn Stack và LinkedList
//      - PriorityQueue // heap -> phần tử nhỏ/lớn nhất luôn ontop
//                      // yêu cầu phần tử phải comparable hoặc cung cấp comparator cho pq
// >>>>>> Collection
//      - Array -> Array/IntArray, ...
//          + hiệu năng cao hơn list
//          + mutable nhẹ (cho phép chỉnh sửa giá trị element bên trong)
//      - Sequence -> phục vụ stream
//          + iterable -> eager
//              - stream xử lý tuần tự từng bước -> gọi hàm nào xử lý luôn hàm đó
//              - quy trình xử lý: mỗi bước, đưa toàn bộ phần tử vào xử lý cùng lúc
//          + Sequencer -> lazy
//              - stream không làm gì cho tới khi gọi cac terminate operator .toList(), .count(), ...
//              - quy trình: vẫn tuần tự,
//                  // nhưng thay vì đưa đồng thời toàn bộ phần tử vào cùng 1 bước
//                  // thì sequence đưa từng phần tử vào, xử lý hết chuỗi các operation,
//                      sau đó đưa phần tử tiếp theo
//                  // nếu thỏa mãn điều kiện terminate -> dừng ngay chứ không chạy phần tử tiếp theo
//              - tạo dữ liệu vô tận => sử dụng sequence
//                  generateSequence(seed) {it -> // }
//      => mặc định collection là eager
//      => gọi .asSequence() // trong Java, gọi .stream()
//          để chuyển sang lazy
//         // sequence cho performance tốt hơn khi xử lý dữ liệu lớn / chuỗi xử lý dài
//                                              // hoặc terminate op là hàm dừng sớm được
// ---------------
// >>> empty collection
// có thể tạo các empty immutable collection
//          emptyList(), emptyMap(), emptySet(), emptyArray(), emptySequence()
fun sequencePerformanceTest(list: List<Int>) {
    // take first prime number in list that bigger than n
    // sau đó, trả về bình phương của phần tử đó

    val n = abs(Random.nextInt());

    println("\n______________________________________")
    println("eager vs lazy stream performance testing")
    println("\t> n: $n\n\t> list.size: ${list.size}")

    // check prime
    val isPrime = { number: Int ->
        var prime = true;
        for (i in 2..sqrt(number.toDouble()).toInt()) {
            if (number % i == 0) {
                prime = false;
                break;
            }
        }

        if (prime) number > 1 else false
    }

    // collection -> eager:
    var time = measureTimeMillis {
        list.filter { it > n }
            .filter { isPrime(it) }
            .map { it.toLong() * it }
            .first()
    }
    println("eager time: $time ms")

    // sequence -> lazy
    time = measureTimeMillis {
        list.asSequence() // dù tốn thêm thời gian tạo sequence
            .filter { it > n }
            .filter { isPrime(it) }
            .map { it.toLong() * it }
            .first()
    }
    println("lazy time: $time ms")
    println("______________________________________")
}

fun main(args: Array<String>) {
    println();

    // ===============
    // collections
    // ---------------
    // create
    // - immutable -> immutable....Of()
    // - mutable
    // ex: List
    val list = ArrayList<Long>();
    for (i in 1..10) list.add(abs(Random.nextInt().toLong()));
    println("list: $list");
    // ---------------
    // accesing
    // - using get(index) / set(index, newValue)
    // - using [index]
    list[3] = -999;
    println("> newValue at index 3: ${list[3]}") // or list.get(3)
    // ---------------
    // traversing
    // - using iterator
    // .iterator() -> iterator 1 chiều
    // .listIterator() -> iterator 2 chiều
    val iterator = list.iterator()
    println("> traversal using iterator: ")
    print("\t")
    while (iterator.hasNext()) {
        print("${iterator.next()} ")

        // với mutable collection, có thể gọi
        //        iterator.remove()
        // để xóa phần tử hiện tại
        // *note: iterator trỏ cùng tới object bên trong list
        // => mọi thay đổi sẽ ánh xạ lên list
    }
    // - using loop
    // for in list -> element
    // for in list.indices -> index
    // for in list.withIndex() -> (index, value)
    println("\n> traversal using for-loop: ")
    for ((index, value) in list.withIndex()) {
        println("\tindex: $index, value: $value")
    }// ---------------
    // .slice(range: Iterable<Int>): List<T> // slice(range: IntRange) // tạo list mới
    // .subList(from: Int, to: Int) // tham chiếu tới cùng list gốc
    println("> list.slice(3..6): ${list.slice(3..6)}")
    // .take(n), .takeLast(n) -> lấy n phần tử đầu/cuối  // .takeWhile(), takeLastWhile() -> lấy kèm điều kiện
    // .drop(n), .dropLast(n) -> trả về list mới đã xóa n phần tử đầu/cuối so với list gốc
    //                              // thêm While -> kèm điều kiện
    println("> take first 3 elements: ${list.take(3)}")
    println("> drop 3 last elements: ${list.dropLast(3)}")
    // .chunked(n) -> chia list thành các khối n phần tử // [1, 2, 3], [4 , 5, 6]
    // .chunked(n, Function<List<T>, R>)
    println("> sum of each chunk of 3 elems: ${list.chunked(3) { it.sum() }}") // size=10 -> 4 chunk
    // .windowed(n, step=1, partialWindows=true) -> tạo cửa sổ trượt, mỗi lần trượt qua n phần tử liên tiếp
    //                            // step = 1 by default // [1, 2, 3], [2, 3, 4], ..
    //                            // step = k // [1, 2, 3], [1+k, 2+k, 3+k], ...
    //                            // partialWindows = true by defaul -> vẫn lấy cửa sổ cuối dù không đủ n phần tử
    println("> windowed ( window=2, step=3, pW = false): ${list.windowed(2, 3, false)}") // idx: [[1,2], [4, 5], [7,8]]
    println()

    // ===============
    // infinite sequence
    val infSeq = generateSequence(1) { abs(Random.nextInt()) }
    // take first 2000000 elements (2M)
    val collection = infSeq.take(2_000_000).toList();
    sequencePerformanceTest(collection)
    // example result :)))
    //    ______________________________________
    //    eager vs lazy stream performance testing
    //    > n: 1339080033
    //    > list.size: 2000000
    //    eager time: 2368 ms
    //    lazy time: 4 ms
    //    ______________________________________
}