package coroutines.channel_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Flow // tương đối giống Java Stream nhưng xử lý bất đồng bộ

// Stream
//      + đồng bộ
//      + dữ liệu tĩnh
//      + dùng 1 lần
// Flow
//      + bất đồng bộ : asynchronous // xử lý dữ liệu theo thời gian - time based
//      + cold: collect mới chạy :)
//      + reactive: dữ liệu đến lúc nào cũng được, đến là đón
//      + đa năng: chuyển thành hot nhờ StateFlow/SharedFlow cho multicast
// Channel
//      + bất đồng bộ: asynchronous
//      + hot: sender gửi liên tục, không quan tâm có ai nhận không
//      + unicast: 1-1 -> chỉ 1 người lấy, lấy xong hết

// Cold Stream -> mỗi 1 lần gọi collect, block flow {} lại chạy lại :)
// Ngược lại, LiveData cứ chạy dù có ai nhận hay không

// StateFlow = Flow + LiveData
//      + chạy 1 lần
//      + ai collect thì lấy giá trị mới nhất được emit
//          => multicast
//      + ai cũng có thể sửa, thông qua state.update {it -> ...}

// - flow.emit(obj) to send object
// - flow.collect() to receive object

suspend fun main(args: Array<String>) {
    println()

    // 1. Upstream : nguồn dữ liệu
    //      + có thể sử dụng collection.asFlow(), or
    //      + sử dụng Functional interface Flow
    val myFlow: Flow<Int> = flow {
        println("Flow started")
        for (i in 1..10) {
            delay(1000)
            println("Emitting $i")
            emit(i)
        }
    }

    withContext(Dispatchers.IO) {

        // 2. Intermediate function => receive + process
        //  flow.map(), flow.filter() ,...

        // 3. Downstream -> nhận dữ liệu
        // flow.collect()

        // myFlow chạy lần đầu
        launch { //
            myFlow
                .filter { it % 2 == 0 }
                .collect { value -> println("[launch-1] collect: $value") }
        }
        // myFlow chạy lại (chạy cái mới)
        launch {// consumer2
            myFlow.collect { value -> println("[launch-2]Collect: $value") }
        }
        // myFlow chạy lại thêm lần nữa
        launch {// consumer3
            myFlow
                .map { it * it }
                .collect { value -> println("[launch-3]: $value") }
        }
        // cả 3 lần chạy đều không làm ảnh hưởng lẫn nhau
    }

    println("\n--------------------")
    println("#vduczz")
}

// Mô hình chuẩn hiện nay trong Android:
//      - Trong `ViewModel`: Dùng StateFlow (hoặc SharedFlow) để chứa trạng thái UI.
//      - Trong Repository/Data Source: Dùng Flow thường (Cold) để lấy dữ liệu.
//      - Trong Activity/Fragment: Dùng coroutine để collect cái StateFlow kia.