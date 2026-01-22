package kt007_collections

import kotlin.random.Random

// Để thực hiện các thao tác của Kotlin Stream
// nên dùng với sequence (lazy stream) -> list.asSequence()
// thay vì dùng trực tiếp list hay collection (eager stream)
// ==> lý do: xem ở `collections.kt`

class Student(
    var firstName: String,
    var lastName: String,
    var age: Int,
    var gpa: Float,
    val major: String,
) : Comparable<Student> {
    // extends Comparable<Student> để cho phép so sánh trực tiếp 2 instance của Class này

    val fullName: String
        get() = "$firstName $lastName"

    fun show(takeMajor: Boolean = false): String {
        val str = buildString {
            append("fullname: $fullName");
            append(", GPA: ${"%.2f".format(gpa)}");
            append(", age: $age");
            if (takeMajor) append(", major: $major");
        };

        return str;
    }

    // example: compare by score only
    override fun compareTo(other: Student): Int {
        return this.gpa.compareTo(other.gpa)
    }
}

fun genString(length: Int = Random.nextInt(2, 6)): String {
    val str = buildString {
        for (i in 0..length)
            append(
                (Random.nextInt(65, 91) +
                        (if (Random.nextBoolean()) 32 else 0)).toChar()
            )
    }
    return str;
}


fun main(args: Array<String>) {
    println()

    // major list
    val majors = listOf("CNTT", "SPT", "SPV", "SPA", "DDT", "DTVT", "TDH", "ATTT");

    // list of 2000 students
    val students = Array<Student>(2000) {
        Student(
            firstName = "${genString()} ${genString()}", lastName = genString(),
            age = Random.nextInt(18, 24),
            gpa = Random.nextFloat() * 2.5f + 1.5f,
            major = majors[Random.nextInt(8)]
        );
    }
    println("example 3-first students: ")
    students.slice(0..2).forEach { println("\t${it.show()}") }

    // =============== https://kotlinlang.org/docs/collection-ordering.html
    // Ordering (Collections utility class methods)
    // >>> Compare
    // - vì student là comparable -> có thể so sánh trực tiếp
    println("\n> compare the first and second students directly: ${students[0].compareTo(students[1])}") // or using >, >=, <, <=, ...
    // >>> Sort
    //      + sort...: sort directly in base list
    //      + sorted...: create a new list and sort
    // ---------------
    // - vì là comparable có thể sort trực tiếp mà không cân cung cấp comparator
    //      .sort() => sort by natural order
    students.sortDescending() // sort all by gpa
    println("> first-10 student have the highest gpa: ")
    students.slice(0..9).forEach { println("\t${it.show()}") }
    // ---------------
    // - hoặc sử dụng .sortWith() và comparator => multi key (sort theo nhiều tiêu chí)
    //      + sortWith: sort directly in base list
    //      + sortedWith: create a new list and sort
    //  > chained comparator
    //      sortWith(compareBy<T> { // boolean })
    //           // can call
    //           // .thenBy {it.nextKey} // sort theo key tiếp theo
    //           // .then { compareBy }  // bổ sung thêm comparable,
    //                                   // then {compareBy} tương tự sortWith {compareBy} cho phép nối thêm điều kiẹn
    // example:
    // sort by gpa, then by lastname, then firstname and age at the finally
    students.sortWith(
        compareByDescending<Student> { "%.2f".format(it.gpa) }
            .thenBy { it.lastName.lowercase() } // thenByDescending -> sort giảm dần theo tiêu chí này
            .thenBy { it.firstName.lowercase() }
            // .reversed() // reversed the current list
            .thenBy { it.age }
    )
    println("> first-10 student have the highest gpa: ")
    students.slice(0..9).forEach { println("\t${it.show()}") }
    // ---------------
    // .sortBy {} -> cách viết ngắn hơn của sortWith trong trường hợp chỉ 1 key
    students.sortBy { it.firstName } //single key comparator
    println("> first-10 student have the smallest firstname: ")
    students.slice(0..9).forEach { println("\t${it.show()}") }

    println("\n-------------------------\n")

    // ===============
    // Streams
    // *note: dùng sequence để tăng performance khi sử dụng các hàm Streams
    val sequence = students.asSequence()
    // --------------- https://kotlinlang.org/docs/collection-filtering.html
    // .filter() -> lọc // return a new sequence
    val goodGPA = sequence.filter { it.gpa >= 3.2f };
    println("number of students have gpa >= 3.2 is ${goodGPA.count()} in total ${students.size} students!")
    // --------------- https://kotlinlang.org/docs/collection-transformations.html
    // .map(e: T -> R) -> ánh xạ phần tử // return new sequence
    // --------------- https://kotlinlang.org/docs/collection-grouping.html
    //                 https://kotlinlang.org/docs/collection-aggregate.html
    // .groupBy() -> Map<K, List<R>> -> tạo map luôn
    //      *note: dù là sequence nhưng groupBy dùng eager pipeline
    val studentByMajor = sequence.groupBy { it.major };
    println("Number student of each major (with groupBy): ")
    studentByMajor.forEach { (major, students) ->
        println("\tMajor: $major, student amount: ${students.size}");
    }
    // ---------------
    // .groupingBy() -> chỉ là bước gom nhóm trung gian trong chuỗi xử lý
    //      *note:  - là lazy pipeline
    //              - phải đi kèm aggregate function
    // .eachCount() -> Map<group, groupCount>
    println("Number student of each major (with groupingsBy): ")
    sequence.groupingBy { it.major }.eachCount()
        .forEach { println("\tMajor: ${it.key}, student amount: ${it.value}}") }
    // .aggregate(currentKey, accumulator, currentValueInGroupKey, first) // thống kê
    //      - acc : accumulator // giá trị tích lũy ở group hiện tại
    //              // acc phải được định kiểu rõ ràng => acc:Type?
    //      - first: phần tử hiện tại có phải phần tử đầu tiên trong group không ?
    //  => output: Map<K, Type> với Type của acc
    println("Average GPA of each Major: ")
    sequence.groupingBy { it.major }.aggregate { _, acc: Pair<Float, Int>?, student, first ->
        if (first) student.gpa to 1 else (acc!!.first + student.gpa) to (acc.second + 1)
    }.mapValues { (_, pair) -> pair.first / pair.second }.forEach { println("\t${it.key}: ${"%.2f".format(it.value)}") }
    // .fold(initValue: T, operation: (key, acc, elem) -> T)
    // .fold(initValue: T, operation: (acc, elem) -> T) // lambda bắt đầu từ phần tử đầu tiên
    //                                                  // acc lấy 0 làm init value
    println("max GPA of each Major: ")
    sequence.groupingBy { it.major }
        .fold(0f) { accumulator, element ->
            if (element.gpa > accumulator) element.gpa else accumulator
        }.mapValues { println("\t${it.key} : ${"%.2f".format(it.value)}") }
    // .reduce(acc, elem -> ....) // lấy phần tử đầu mỗi group làm acc
    //                              // lambda bắt đầu từ phần tử thứ 2

    println("\n--------------------")
    println("#vduczz")
}