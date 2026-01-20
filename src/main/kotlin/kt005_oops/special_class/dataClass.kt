package kt005_oops.special_class

// Data class
// như tên:), data class là class để chứa data, => hỗ trợ class theo kiểu POJO
// tự động được cung cấp các hàm:
// .equals(), .hashCode(), .toString(), .copy(), .componentN()
//      *NOTE: các hàm này, nếu có dùng chỉ dùng các props bên trong primary constructor

// rules of data class:
// - primary constructor có ít nhất 1 params,
//      và tất cả các param phải trở thành thuộc tính => val/var
// - không được abstract, open, sealed or inner
// - chỉ implements interfaces, không extends class khác

// syntax
//      data class className(val/var params: type) [: interfaces ] {
//          // body
//      }
// - hoàn toàn có body như class thường, miễn là không vi phạm các rules.
// - có thể override các hàm có sẵn, ngoại trừ copy() và componentN()

data class CreditCard(internal val cardNumber: String, var owner: String, private val ccv: String) {
    init {
        require(cardNumber.length == 16) { "Invalid Card Number!" };
        require(ccv.length == 3) { "Invalid CCV!" };
    }

    override fun toString(): String {
        return "CreditCard(cardNumber=****${cardNumber.takeLast(4)}, ccv=***, owner=$owner)"
    }
}

fun main(args: Array<String>) {
    // val cc1 = CreditCard("123", "123", "123") // validation failed!
    println()

    val cc1 = CreditCard("1234123412341234", "PHAM VAN DUC", "999");
    val cc2 = CreditCard("1234123412341234", "PHAM VAN DUC", "999");
    val cc3 = CreditCard("9876543212345678", "NGUYEN DUC DAT", "999");

    // .toString()
    println(cc1)
    println()

    // .equals()
    // bình thường, == sẽ so sánh reference
    // data class -> so sách data bên trong
    println(cc1 == cc2)
    println(cc1.equals(cc3)) // == vs .equals() is similar
    println()

    // .hashCode()
    // nếu a.equals(b) trả về true thì a.hashCode() == b.hashCode()
    // => nếu override .equals(), phải override cả .hashCode()
    println("cc1==cc2: ${cc1 == cc2}\n\tcc1.hashCode=${cc1.hashCode()}, cc2.hashCode=${cc2.hashCode()}")
    println("cc1==cc3: ${cc1 == cc3}\n\tcc1.hashCode=${cc1.hashCode()}, cc3.hashCode=${cc3.hashCode()}")
    println()

    // .copy()
    // copy thành object mới, cho phép sửa dữ liệu :)
    val cc4 = cc1.copy(ccv = "771"); // object mới với cardNumber, ower của cc1 và ccv mới (771)
    println(cc4)
    println()
    // val cc5 = cc1; // cùng reference -> chỉnh sửa ảnh hưởng lẫn nhau!

    // .componentN() -> destructoring
    // -> chú ý quyền truy câp (access modifier)
    // -> component1() ứng với tham số đầu tiên trong primary constructor
    // ...
    //      => N, access modifier của componentN() = thứ tự, access modifier của param đó trong primary constructor
    // destructoring toàn bộ
    val (cardNumber, owner) = cc1 // destructor theo thứ tự trong primary constructor
    //                            // nên để các private param nằm cuối
    //                            // sử dụng _ cho thuộc tính nào không muốn lấy
    // hoặc lấy từng component
    val cn2 = cc1.component1()
    val o2 = cc1.component2()
    println("$cardNumber, $owner, $cn2, $o2")
}
