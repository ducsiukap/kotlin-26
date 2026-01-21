package kt006_exception_handling

import kotlin.random.Random

// Nullable type
// val x : T    =>  x có type=T, non-nullable
// val x : T?   =>  x có type=T?, nullable

// *note: khi Kotlin gọi Java, Null Safety bị vô hiệu quá
//  -> các biến trả về có dạng Type! (không có thông tin / không biết null hay không)
//      // T! có thể gán vào T và cả T?

fun main(args: Array<String>) {
    println()

    // NULL SAFETY
    // ===============
    val username: String? = if (Random.nextFloat() > 0.8) null else "vduczz"; // 80% chance to be null
    // nếu biến có kiểu Type?, BUỘC phải HANDLE trước khi sử dụng

    // > save call operator `?.`
    //  -> nếu không null thì có thể truy cập member của object
    //  -> nếu null thì trả về null
    println("username?.length: ${username?.length}")
    // object thường sử dụng let sau op này
    //      object?.let { obj -> //do smth } //

    // > elvis operator `?:`
    //  -> default value if null
    println("username: ${username ?: "default-username"}")
    //      *thường vế phải của ?: sẽ là cái gì đó (hàm, throw, ..) trả về nothing
    //          -> mục đích: tận dụng smart cast

    // > non-null assertion `!!`
    //  -> ý nghĩa: username!! -> (coder) chắc chắn lúc này username không null

    // > safe cast `as?`
    //  -> object as? T // return null nếu object không cast được sang kiểu T

    // SMART CAST
    // ===============
    // - type cast:
    //      -> bên trong block {} của if (obj is T) {},
    //      // hoặc bên dưới block {} của if (obj !is T) {} (else, phần dưới nếu không có else, ...) NẾU if CÓ LỆNH DỪNG CHƯƠNG TRÌNH
    //      // ex: error(msg), throw ...
    //          => obj tự động được mang kiểu T :)
    //          => gọi hàm của T vô tư

    // - check null
    //      -> bên trong block {} của if (obj != null)
    //      // hoặc bên dưới block {} của if (obj == null) {} (else, phần dưới nếu không có else, ...) NẾU if CÓ LỆNH DỪNG CHƯƠNG TRÌNH
    //    //      // ex: error(msg), throw ...
    //          => obj được cast từ T? sang T (chắc chắn không null)
    //          => khi này, không cần sử dụng null safety

    // - logic flow
    //      if (a && b) -> chỉ check b khi a true
    //      if (a || b) -> chỉ check b khi a false

    // *note: smart cast không hoạt động với biến `var` trong class vì có thể bị thay đổi
    // example smart cast null
    if (username != null) {
        // trong đây, user có type là String
        println("len: ${username.length}, uppercase: ${username.uppercase()}")

        // nếu có lệnh dừng chương trình ở đây
        // bên dưới có thể sử dụng object trong điều kiện ngược lại với if
        // error("statement that terminated the program!")
    }

    // nếu if bên trên có lệnh dừng chương trình như: error(msg), throw ...
    // thì ra tới đây, username chắc chắn null
    // val len = username?.length; // warning: Value of 'username?.length' is always null

    println("\n--------------------")
    println("#vduczz")
}