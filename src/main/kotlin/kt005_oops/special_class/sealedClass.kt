package kt005_oops.special_class

// Sealed thường gọi là class quản lý trạng thái
// cơ chế giống Enum nhưng mạnh hơn -> Enum 2.0 :)

// - là abstract nên không thể tạo instance của class này
// - keyword: `sealed class`
// - all subclasses of it must be in the same file where the sealed class is defined
// *From kotlin 1.5+, all subclasses of it do not need to be declared inside its block
//      nhưng vẫn nên viết bên trong
// ex:
sealed class UIState {
    object Idle : UIState() // do nothing yet
    data class Success(val results: List<Int>) : UIState() // fetch ok
    data class Error(val msg: String) : UIState() // fetch error
}

// declare outside sealed class block
object Loading : UIState() // loading // ok

// sealed class được dùng với when là chủ yếu
fun log(state: UIState) = when (state) {
    // object bên ngoài
    // sử dụng className
    is Loading -> println("Loading")
    // class/object bên trong sealed class
    // sử dụng: sealedClassName.className
    is UIState.Success ->
        println("success!\n\tdata: ${state.results}");
    is UIState.Error -> println("fetch-error: ${state.msg}");
    is UIState.Idle -> println("Idle");
}

fun main(args: Array<String>) {

    // fetching data example
    log(UIState.Idle)
    log(Loading)
    log(UIState.Success(Array(5) { it + 1 }.asList()));
    log(UIState.Error("Fetch error"))

    println("\n--------------------")
    println("#vduczz")
}