package coroutines.channel_flow

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext
import kotlin.random.Random

// Channel is unicast
// 1 producer - 1 consumer // lấy xong người sau không lấy đc

// - ReceiveChannel : chỉ nhận
// - SendChannel : chỉ gửi
// - Channel: cả gửi cả nhận
// - ProducerScope
// - ConsumerScope

// .send()
// .receive() -> nguy hiểm nếu channel close
//      => sử dụng : `for (item in channel)`
//      để lấy an toàn và dừng loop tự động khi channel đóng

// ===============
// TYPE
// > Rendezvous Channel
//      Channel<Int>()
//          - capacity = 0 -> không có đệm để chứa
//          => sender đến trước => sender chờ
//          => consumer đến trước => consumer chờ

// > Buffered Channel
//      Channel<Int>(capacity = n)
//          -> có tối đa n phần tử trong đệm
//  => sender chỉ chờ khi đệm đầy
//  => consumer chỉ chờ khi đệm trống

// Conflated Channel
//      Channel<Int>(capacity = Channel.CONFLATED)
//          -> có tối đa 1 phần tử trong đệm
//  => sender sẽ ghi đè nếu chưa ai lấy

// Unlimited Channel
//      Channel<Int>(capacity = Channel.UNLIMITED)
//  => sender không bao giờ treo
//  => dễ tràn bộ nhớ nếu tốc độ lấy nhỏ hơn tốc độ gửi

// Coroutine có hàm mở rộng là produce() giúp tạo 1 coroutine chuyên gửi :)
// có thể dùng với Flow hoặc ReceiveChannel
//      produce -> ProducerScope
@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.takeChannel(): ReceiveChannel<Int> = produce { // ProducerScope
    repeat(5) {
        send(Random.nextInt())
    }
}

// khi này, để đọc, sử dụng:
suspend fun CoroutineScope.launcher() {
    launch {

        // gọi CoroutineScope.takeChannel() định nghĩa bên trên để lấy channel
        val channel = takeChannel() // ConsumerScope

        // gọi channel.consumeEach{} để lấy phần tử channel
        channel.consumeEach { println("[laucher-launch-1] take $it") }
    }

    launch {

        // gọi CoroutineScope.takeChannel() định nghĩa bên trên để lấy channel
        val channel = takeChannel() // ConsumerScope

        // gọi channel.consumeEach{} để lấy phần tử channel
        channel.consumeEach { println("[laucher-launch-2] take $it") }
    }
}


suspend fun main(args: Array<String>) = coroutineScope {

    // CoroutineScope extension function
    launcher()

    // =============================
    // basic channel with
    val channel = Channel<Int>(); //Rendezvous

    withContext(Dispatchers.Default) {
        launch {
            // for (item in channel)
            for (item in channel) println("[launch-1] received $item")
        }
        launch {
            for (item in channel)
                println("[launch-2] received $item")
        }
        launch {
            for (item in channel)
                println("[launch-3] received $item")
        }
        launch {
            repeat(5) { channel.send(Random.nextInt()) }
            channel.close()
        }
    }

    // select + channel
    // select an toan, tranh duoc channel close
    val c1 = Channel<String>(capacity = 1);
    val c2 = Channel<String>(capacity = 1);
    withContext(Dispatchers.Default) {

        launch {
            c1.send("String from channel-1")
            c1.close()
        }
        launch {
            c2.send("String from channel-2")
            c2.close()
        }

        select<Unit> {
            // onReceiveCatching
            c1.onReceiveCatching { result ->
                result
                    .onSuccess { println("[select-channel-1] success, msg: $it") }
                    .onFailure { println("[select-channel-1] channel closed") }
            }

            // onReceive // throw exception neu channnel da close
            try {
                c2.onReceive {
                    println("[select-channel-2] success, msg: $it")
                }
            } catch (_: CancellationException) {
                println("[select-channel-2] channel closed")
            }

            // có thể cả onsend :)
            //            c1.onSend()
        }

    }

    println("\n--------------------")
    println("#vduczz")
}