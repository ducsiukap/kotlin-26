package kt002_basics

import kotlin.random.Random

// Java hỗ trợ Array dạng type[]
// Kotlin không hỗ trợ trực tiếp kiểu Type[], mà wrap nó trong class Array<T>
// sau đó biên dịch xuống Java array

fun main(args: Array<String>) {
    println()
    // 1. Create array
    // ===============
    // 1.1. Boxed Array
    // 1.1.1 Tạo mảng biết trước các phần tử
    //      arrayOf(vararg elements) // or arrayOf<T>(vararg elements)
    //  hoặc
    //      arrayOfNulls<T>(size) // Array<T?>
    // 1.1.2. Sử dụng array constructor
    //      Array(size, { index -> returnValue })
    // 1.1.3. empty array
    //      emptyArray<Type>()
    // 1.1.4. convert from collection
    //      list.toTypedArray()
    val boxedArray = arrayOf(1, 2, 3, 4, 5) // similar to: Array(5, {i -> i+1})
    println("boxedArray: ${boxedArray.contentToString()}")
    // note: BoxedArray, giả sử Array<Int> đưược dịch thành Integer[] trong Java
    //      => hiệu năng chậm!
    // ---------------
    // 1.2. Primitive Array
    // - Primitive Array, giả sử IntArray sẽ đưược dịch thẳng thành int[] trong Java
    //  => hiệu năng cao hơn!
    // - Các primitive có dạng TypeArray, eg. IntArray, DoubleArray, CharArray, ...
    // - cú pháp khởi tạo primive array
    // 1.2.1. Biết trước các giá trị:
    //      typeArrayOf(vararg elements)
    // 1.2.2. sử dụng constructor
    // - khởi tạo n giá trị mặc định
    //      TypeArray(size) // size=0 for empty array
    // - khởi tạo và gán giá trị:
    //      TypeArray(size, {index -> returnValue})
    // 1.2.3. convert from collection:
    //      list.toIntArray() // .toTypeArray
    val primitiveArray = IntArray(5) { it * it }
    println("primitiveArray: ${primitiveArray.contentToString()}")
    // ---------------
    // 1.3. Multi-dimension Array (nD array)
    // multi-dimension simply is an array of array.
    // note: chỉ lõi cuối cùng mới có thể tạo bằng Primitive Array,
    //       các mảng chứa bên ngoài chỉ có thể là Array (Boxed Array)
    val nDArray = Array(3) {
        IntArray(3) { index -> (it) * 3 + index + 1 }
    };
    println("nDArray: ${nDArray.contentDeepToString()}")
    // note:
    // - array.contentToString() to print 1D array
    // - array.contentDeepToString() to print nD array


    println()
    // 2. Accessing element
    // 2.1. using get(index) / set(index, newValue) to get / set the value at position index
    boxedArray.set(2, -1);
    println(boxedArray.get(2));
    // 2.2. using indexing operator
    // recommended (specially for nDarray)
    // to set value at index
    nDArray[2][2] = 100;
    // to get value at index
    println(nDArray[2][2]);


    println()
    // 3. Traversing
    // 3.1. using forloop
    //      for (item in array)
    //      for (index in array.indices) // or: for (item in 0..array.size-1)
    //      for ((item, index) in array.withIndex())
    for ((index, subArray) in nDArray.withIndex()) {
        println("sub-array at row $index: ${subArray.contentToString()}")
    }
    // 3.2. using for each
    //      array.forEach({ item -> process(item) })
    //      array.forEachIndexed({ index, item -> // processing })
    // note:
    // - cannot using break, continue in for-each loop.
    // - nếu muốn bỏ qua loop-step hiện tại (tương tự continue),
    //   => sử dụng labeled return // return@forEach
    print("primitiveArray (for-each + labeled return print):")
    primitiveArray.forEach {
        val p = Random.nextFloat();
        if (p > 0.80) return@forEach; // 20% chance to skip loop-step
        print("\t$it")
    }

    println("\n--------------------")
    println("#vduczz")

}