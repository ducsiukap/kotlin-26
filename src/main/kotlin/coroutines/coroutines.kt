package coroutines

// Coroutines = async/await in JS
// Cần thêm thư viện `kotlinx.coroutines` để sử dụng Coroutines
//dependencies {
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
//}
import kotlinx.coroutines.*;
import kotlin.random.Random

// Corountines vs Thread
// - JVM Thread -> là 1 wrapper class bọc `OS Thread` // chỉ song song khi có multi-core
//      + OS quản lý => mỗi thread có stack riêng/context riêng
//      + Chuyển context tốn chi phí
//      + Nhiều Thread -> tốn RAM + CPU
//      // Tạm dừng -> chặn cả Thread
//
// - Coroutines -> `thread` ở mức ngôn ngữ thay vì OS
//      + Do Coroutine runtime quản lí, chạy trên JVM Thread.
//      + Nhẹ, cực kì nhẹ (< ~2kB)
//      + Nhiều coroutine chạy trên 1 JVM Thread.
//      // suspend / resume (tạm dừng/tiếp tục) không chặn OS Thread
// => visualize
//          OS
//          └── OS Thread
//                └── JVM Thread
//                        ├── Coroutine A
//                        ├── Coroutine B
//                        ├── ...
//                        └── Coroutine N

// 1. Suspend function
// - chỉ gọi được từ :
//      + other suspend fun
//      + coroutine block
// - cho phép pause (suspend) / resume mà không block thread
suspend fun fetchData() {
    print("Loading.")

    val delayCount = Random.nextInt(5, 10);
    for (i in 1..delayCount) {
        print('.')

        // delay(time) -> lệnh `suspend` - tạm dừng coroutine hiện tại
        delay(500);
    }
    println("\nFinished !!!")
}

// *note: không phải lúc nào cũng có thể đánh dấu main là    suspend
//          // tích hợp với code trong project, frameworks, ...
suspend fun main() {

    // bản thân suspend function không tự chạy ngầm
    // -> nó phải được đi kèm cùng Dispatchers (thông qua withContext)

    // withContext{} -> Runs the code inside its block on a shared thread pool
    //      -> cụ thể, nó chặn coroutine đang gọi nó, chờ tới khi nó thực thi xong
    //      -> không tạo coroutine mới, không chặn Thread/coroutine khác.
    withContext(Dispatchers.Default) {

        // .launch {} -> tạo coroutine mới,
        //                  sau đó đăng ký với CoroutineScope bọc bên ngoài nó,
        //                  và chạy song song với các coroutine cùng cấp khác (cùng trong scope hiện tại)
        // add a coroutine thread
        this.launch {
            // suspend fun is callable only in other suspen fun or coroutine scope
            fetchData()
        }

        // add another coroutine
        this.launch {
            generateSequence(1) { it + 1 }
                .take(20)
                .forEach {
                    print("\nmain-count: $it")
                    delay(400)
                }
        }

        // => 2 coroutine trên chạy song song
    }

    println("\n--------------------")
    println("#vduczz")

}

// exampel result
//Loading..
//main-count: 1
//main-count: 2.
//main-count: 3.
//main-count: 4.
//main-count: 5
//main-count: 6.
//main-count: 7.
//main-count: 8.
//main-count: 9.
//main-count: 10
//main-count: 11.
//main-count: 12
//Finished !!!
//
//main-count: 13
//main-count: 14
//main-count: 15
//main-count: 16
//main-count: 17
//main-count: 18
//main-count: 19
//main-count: 20
//--------------------
//#vduczz
