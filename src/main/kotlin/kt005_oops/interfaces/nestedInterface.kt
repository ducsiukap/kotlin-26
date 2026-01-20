package kt005_oops.interfaces

// nested interface
// - luôn là static => chỉ dùng được biến/hàm bên trong companion object
// - visibility: public / protected / internal / private

// ===============
// nested interface inside class
// 90% usecase of nested interface
// > propose: interface đó chỉ có ý nghĩa mật thiết với class đó => gắn chặt với nghiệp vụ của class đó
//  ex: OnClickListener của Button chỉ lắng nghe click trên button, ...
// > case: callback/listener
class YoutubePlayer {
    // listener interface
    interface EventListener {
        abstract var isPlaying: Boolean
        fun onPlay()
        fun onPause()
    }

    // property of nested interface
    private var listener: EventListener? = null

    // setter for listener
    fun setListener(listener: EventListener) {
        this.listener = listener
    }

    // trigger to call nested action
    fun simulateUserClickPlay() {
        // logic
        listener?.onPlay()
    }

    fun simulateUserClickPause() {
        // logic
        listener?.onPause()
    }
}
// > case: Strategy Pattern
// > case: Che giấu thông tin


// ===============
// nested interface inside interface
// - ít gặp, thường xuất hiện trong bộ thư viện lớn / thiết kế API
// > Ý nghĩa:
//  + Gom nhóm các hằng số/kiểu dữ liệu liên quan
//      ex: Entry là nested interface trong Map interface
//          Entry là phần tử của các class extend Map
interface AuthAPI {
    fun login()

    // result
    interface Result {
        fun onSuccess(token: String)
        fun onError(code: Int)
    }

    // error
    interface ErrorCode {
        val isNetworkError: Boolean
    }
}

// Cách dùng: AuthAPI.Result, AuthAPI.ErrorCode
// Nhìn vào là biết ngay Result này là của AuthAPI, không nhầm với PaymentAPI
//  + Phân cấp: Chia nhỏ một Interface khổng lồ thành các nhóm nhỏ hơn cho dễ quản lý.
interface SmartHome {
    // light group
    interface Lighting {
        fun turnOn()
        fun turnOff()
    }

    // audio group
    interface Audio {
        fun playMusic()
        fun stopMusic()
    }
}
// Class con có thể chọn implement từng phần
//class BongDenThongMinh : SmartHome.Lighting { ... }


fun main(args: Array<String>) {
    println()

    // nested class
    // create & set listener
    val listener = object : YoutubePlayer.EventListener {
        override fun onPlay() {
            if (!this.isPlaying) {
                println("Play!")
                this.isPlaying = true
            } else {
                println("Video is already playing!")
            }
        }

        override fun onPause() {
            if (this.isPlaying) {
                this.isPlaying = false
                println("Pause!")
            } else {
                println("Video is already paused!")
            }
        }

        override var isPlaying: Boolean = false
    }
    val player = YoutubePlayer();
    player.setListener(listener);

    // call trigger
    player.simulateUserClickPlay();
    // re-click play
    player.simulateUserClickPlay()
    player.simulateUserClickPlay()
    // pause
    player.simulateUserClickPause();
    player.simulateUserClickPause(); // re-pause

    println("\n--------------------")
    println("#vduczz")
}