package coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random
import kotlin.system.measureTimeMillis

// launch {} // không trả về giá trị cụ thể -> fire and forget :)
// - return: 1 cái job: Job quản lý nó
//      val job = launch {}
//          + job.cancel() để hủy khối run đó
//          + job.join() để chờ launch chạy xong
// - error handling: error propagation lên parent scope

// async {} // chạy task để tính toán và TRẢ VỀ KẾT QUẢ
// - return a instance of Deferred (*note: Deferred extends Job)
//      val fetchData = await {}
//          + fetchData.await() để lấy kết quả
//              // note: .await() suspend scope hiện tại cho tới khi có kết quả trả về
// - error handling:
//      + nếu job=Job() -> error propagation
//      + nếu job=SupervisorJob() -> chỉ error propagation khi gọi .await()
//          => phần sau async{} vẫn chạy ngon lành nếu không gọi .await() :P
// - lazy start:
//      val doSmth = async(start = CoroutineStart.LAZY) { // do smth}
//          => khi này, doSmth.start() thì async mới chạy

suspend fun callAPI(): Boolean {
    print("Fetching...")
    for (i in 1..10) {
        print(".")
        delay(300)
    }
    val isOK = Random.nextBoolean();
    println(if (isOK) "[OK]" else "[FAILED]");
    return isOK
}

suspend fun main(args: Array<String>) {

    withContext(Dispatchers.Default) {
        // tuần tự
        var time = measureTimeMillis {
            withContext(Dispatchers.IO) {
                val r1 = callAPI()
                val r2 = callAPI()
                println("r1: $r1, r2: $r2")
            }
        };
        println("${time}ms") //6220ms

        // launch
        time = measureTimeMillis {
            withContext(Dispatchers.IO) {
                var r1: Boolean? = null
                var r2: Boolean? = null
                launch { r1 = callAPI() }
                launch { r2 = callAPI() }
                println("r1: $r1, r2: $r2") // luon in truoc :)
                /*
                Fetching....Fetching....r1: null, r2: null
                ..................[OK]
                [FAILED]
                 */ // +1 ly do vi sao async ra doi
            }
        }
        println("${time}ms") //3120ms

        // async
        time = measureTimeMillis {
            withContext(Dispatchers.IO) {
                val promise1 = async { callAPI() }
                val promise2 = async { callAPI() }

                println("r1: ${promise1.await()}, r2: ${promise2.await()}")
            }
        }
        println("${time}ms") // 3106ms
    }

    println("\n--------------------")
    println("#vduczz")

}