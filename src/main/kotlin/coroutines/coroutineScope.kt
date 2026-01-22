package coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
//import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//import kotlinx.coroutines.supervisorScope

// STRUCTURED CONCURRENCY - 3 principles of Coroutines:
// - Parent waits for its child:
//      ->  coroutine cha không được phép finish
//          cho tới khi ALL sub-coroutine bên trong nó finish
// - Cancellation Propagation:
//      ->  coroutine cha bị hủy
//          ->  lệnh hủy được lan truyền
//              xuống tới các sub-coroutine bên trong
// - Error Propagation:
//      -> nếu 1 sub-coroutine nào đó bên trong bị lỗi
//          ->  error được lan truyền lên cha
//              ->  cha hủy toàn bộ sub-coroutines còn lại
//                  sau đó, cha cũng chết
//              // trừ khi có cơ chế bảo vệ riêng - SupervisorJob

// COROUTINE SCOPE
// >>> any coroutines must be initialized inside a coroutine scope
// > Scope định nghĩa 2 thứ:
//      - vòng đời -> Job: quản lý coroutine active/completed/cancelled
//                      + parent job: job cha quản lý toàn bộ coroutine bên trong nó
//                      + child job: job con ứng với mỗi sub-coroutine, quản lý nó + child job của nó
//              + Job (default job) -> duy trùy Error Propagation từ child lên parent
//              + SupervisorJob -> bỏ Error propagation, child chết không ảnh hưởng tới parent và các child khác
//      - context -> - luồng nào -> Dispatcher // mỗi Dispatcher = 1 Thread pool riêng
//                          + Dispatchers.Main      -> UI
//                                  // Thread Pool của Main có 1 JVM Thread duy nhất
//                          + Dispatchers.IO        -> I/O, Database, Netword
//                                  // Thread Pool của IO có rất nhiều JVM Thread ( >> n-core)
//                                      // các thread chủ yếu chờ -> không ăn CPU.
//                          + Dispatchers.Default   -> CPU-Bound
//                                  // máy có N-Core -> N-OS Thread được phép chạy đồng thời tại 1 thời điểm
//                                      // (các OS Thread còn lại phải chờ)
//                                  // Thread Pool của Dispatchers.Default cố gắng tạo N-JVM Thread ứng với N-Core
//                                      // (JVM Thread ~ OS Thread)
//                                      // các thread chạy liên tục và ăn CPU thật.
//                                  // Các coroutines bên trong sẽ chạy trên N-JVM Thread này.
//                          + Dispatchers.Unconfined -> nguy hiểm, không nên dùng
//                   - tên gì -> Name
//                   - xử lý lỗi -> ErrorHandler
//
// _________________________
// =>> CoroutineScope = CoroutineContext = Job + Dispatcher + Name + ErrorHandler
//
// > Quy tắc ghi đè:    Khi `launch` 1 coroutine bên trong scope, nó sẽ kế thừa context của cha
//                      nhưng có thể ghi đè context mới bằng cách truyền vào như params
//                          .launch(newContext) {}
// _________________________
// - CoroutineScope:   có vòng đời, kiểm soát được
// - GlobalScope: không có vòng đời, chỉ chết khi app bị kill -> dễ leak // không dùng


suspend fun main(args: Array<String>) {
    println()

    withContext(Dispatchers.Default) {
        // ===============
        // Các cách tạo CoroutineScope
        // ---------------
        // >>> Sử dụng CoroutineScope constructor -> thường được dùng để tạo parent scope
        // Job() + Dispatcher.Default + <no-name> + <default-hanndler> is the default context
        var scope = CoroutineScope(Dispatchers.Default) // bắt buộc phải truyền context

        // khi này, muốn launch {} / async {} gì đó, gọi hàm
        //    scope.launch {}

        // ---------------
        // >>> sử dụng Scoped Function  -> thường dùng để tạo child scope
        //                              -> hoặc tạo parent scope nhưng không quản lý được sau khi tạo
        //                              -> áp dụng Last-Line rule, trả về giá trị dòng cuối cùng
        //        val s3 = withContext(Dispatchers.Default) { } // use .withContext() để chạy code ở context khác
        //                                                      // parent phải đợi .withContext() xong mới chạy tiếp được
        //        val s2 = supervisorScope {} // job=SupervisorJob()
        val s1 = coroutineScope { // job = Job()

            // khi này, có thể gọi launch{}, async{} trực tiếp trong này
            // giống như gọi append() trong buildString()

            // hoặc viết code bình thường
            // -> chạy trong luồng chính của scope cha,
            // luồng chính của cha chạy song song với các khối launch bên trong

            // launch{}, async{} có thể override context của cha
            launch(Dispatchers.IO) {
                // Run in IO Thread pool
            }

            36 // trả về 36 cho s1 (LLR)
        }
        println("s1: $s1\n")
        // KHÔNG THỂ gọi s1.launch(), s1.async() để thêm task vào scope

        // ---------------
        // runBlocking{} -> chặn luôn Thread hiện tại đợi tới khi nó thực hiện xong :)
        //      *note: chỉ dùng demo/test, không dùng trong code logic của project

        // ---------------
        // GlobalScope.launch {}
        // - không gắn với ai => không ai quản lý
        // - chỉ chết khi app bị kill.
        //      *note: cách cũ, không ai dùng nữa cả

        // ===============
        // Scope management
        // ---------------
        // Cancellation -> scope.cancel()
        scope.launch {
            repeat(10_000_000) {
                print("+")
            }
            println("\nchild coroutine is finished")
        }
        scope.cancel() // terminal parent scope
        // -> dừng scope cha và truyền tín hiệu isActive = false tới các coroutine con bên trong
        // note: scope đã cancel thì không thể goi .launch {}, .async {} để add task
        println("parent scope is cancelled!")
        // but, wait! the result maybe belike:
        /*
         ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
         ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
         ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
         ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++parent scope is cancelled!
         +++++++++++++++++++++++++
         --------------------
         #vduczz
         ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        */

        // parent chỉ truyền isActive=false xuống child
        // Việc hủy ở child là tự nguyện (Cooperative) :)
        // - đa số con cũng hủy theo
        // - một số trường hợp thường không hủy:
        //      + Vòng lặp CPU-bound dài // heavy compute, ...
        //      + Code blocking // ex: stream.read()
        //      + Custom loop không suspend // long loops
        // -> để đảm bảo hủy, cần check biến isActive bên trong child
        //          hoặc gọi các hàm .delay(time), .yeild() // nhường lượt
        //                          .ensureActive()
        //              vì bản thân các hàm đó cũng check
        // *Note: muốn con chắc chắn làm gì đó trước khi hủy, dùng try + finally + withContext(Noncancellable)
        println()
        scope = CoroutineScope(Dispatchers.Default);
        scope.launch {
            // try-finally + withContext(Noncancellable)
            try {
                scope.launch {
                    for (i in 1..1_000_000) {

                        // to ensure kill this coroutine when its parent is killed
                        // if (isActive) break // check is active
                        print("-")
                        delay(1000) // or call .delay(), .yeild() or ensureActive()
                    }
                }
            } finally {
                // muốn làm gì đó (chắc chắn xảy ra) trước khi this scope bị cancel
                withContext(NonCancellable) {
                    println("child coroutine is finished!");
                }
            }
        }
        scope.cancel()
        println("parent scope is cancelled!")
    }
    println("\n--------------------")
    println("#vduczz")
}