package kt005_oops

// Cú pháp khai báo class trong Kotlin

// [modifier] class [className] [<generic>] [primaryConstructor] [: superClassList()] {}

// trong đó:
// > modifier:
//  - visibility modifier:
//      + public: //default
//      + private: chỉ file hiện tại thấy
//      + protected: module-scope
//  - behavior modifier:
//      + abstract: abstract class
//      + open: cho phép inherit, (mặc định là final - không cho phép inherit)
//      + data: tự sinh code POJO // các hàm .toString(), equals(), hashCode(), copy(), ...
//      + sealed

// > primary constructor:
// - fully-syntax: constructor(val prop: type , ...)
// - params:
//      + không có val/var => chỉ là tham số truyền vào, không tạo class Property
//          -> chỉ sống trong lúc class khởi tạo => dùng cho init{} hoặc gán cho biến khác
//      + có val/var => tham số trở thành class's field.
//          -> val: tự tạo get // likely `final` => read-only
//          -> var: tự tạo get + set cho biến đó
// - params có hỗ trợ default arguments / vararg
// - bình thường, chỉ cần (param: type), nhưng nếu:
//      + constructor có Annotation // ex: @Inject
//      + visibility modifier //ex: private constructor
// => phải viết đầy đủ keyword `constructor(param: type)

// > : superClassList() : extends/implements trong java -> kế thừa
// - kế thừa class => cần gọi constructor của superclass
//      ex: extends Animal // Java
//          : Animal() // kotlin
// - kế thừa interface tương tự java
//      ex: implements Flyable -> : Flyable

class Student1(
        var firstName: String,
        var lastName: String,
        var age: Int = 18,
        var result: Float = 0f
) {
    // class members:

    // =====================
    // > property // field trong java class
    // ẩn phía sau là:
    // - field: backing field -> `field` keyword để truy cập trực tiếp
    // - get / set method để thao tác với property (cụ thể hơn là với field)
    // *note:
    // + mặc định, get/set được tự sinh, có thể override
    // + mỗi arguments dạng val/var trong primary constructor đều trở thành property của class
    // ---------------------
    // property bao gồm:
    // - val/var arg trong primary constructor
    //    // *note: muốn custom get/set thì phải bỏ val/var của biến đó trong primary constructor
    //             sau đó, tạo biến mới trong class body đảm nhiệm biến đó
    // - khai báo bên trong class body
    // ---------------------
    // stored property: // có backing field ẩn
    // - được gán giá trị khởi tạo
    // - get/set có động tới `field`
    // => không thể inline get/set
    // ---------------------
    // computed property: // không tốn không gian lưu trữ - backing field
    // - không có giá trị khởi tạo
    // - get và set không động tới `field`
    // => có thể inline get/set
    val resultType: String
        inline get() {
            return if (result < 5.0f) "Yeu"
            else if (result < 7f) "Trung binh" else if (result < 8.5f) "Kha" else "Gioi"
        }
    var fullName: String
        inline get() = "$firstName $lastName"
        inline set(value) {
            val firstName = value.substringBeforeLast(" ")
            val lastName = value.substringAfterLast(" ", "")

            this.firstName = firstName
            this.lastName = lastName

            // this.fullName = value // lỗi đệ quy vô hạn
            // nếu set cho field hiện tại
            // field = value
        }
    // ---------------------
    // - val: read-only -> only get
    // - var: read-write -> get + set

    // =====================
    // > init{} block
    // - là khối lệnh auto-running, chạy tự động, ngay sau khi PRIMARY CONSTRUCTOR được gọi
    // - có thể có nhiều khối init{}, thứ tự chạy theo thứ tự được khai báo
    //      // các khối property và init chạy xen kẽ, theo tứ tự khai báo
    //      // khối init{} phía trên không được truy cập biến phía dưới vì chưa được khởi tạo =>
    // NullPointerException
    // - init{} dùng để:
    //      + validate dữ liệu cho primary
    //      + logic phức tạp
    //      + log, connect, ...
    // => xử lý logic cho primary constructor
    init {
        ++count
        println("Student $fullName is created!")
    }

    // =====================
    // > functions
    fun toJSON(): String {
        return """
            {
                "fullName": "$fullName"
                "age": $age,
                "result": $result,
                "resultType": "$resultType",
            }
        """.trimIndent()
    }

    // =====================
    // > secondary constructor
    // rules:
    // - dùng fully-syntax: constructor(arg: type) {} // có body
    // - phải gọi primary constructor
    //      constructor(data) : this(..., ..., ..., ...) {}
    // - tham số không được có keyword val/var
    // => Quy trình: primary -> init -> secondary
    // - thường dùng default argument thay vì secondary, trừ khi cần parse dữ liệu phức tạp

    // =====================
    // > Inner / Nested class

    // =====================
    // > Companion object - static component
    // - giống nơi tập hợp các static member trong java
    // kotlin không hỗ trợ static keyword
    // - companion object là object, vì vậy nó có thể kế thừa interface :)
    // - có thể đặt tên, default là "Companion"
    // cú pháp:
    //      companion object [name] [: superType] {}
    // gọi:
    //      className.companionObjectName.member
    // - để gọi trực tiếp qua classname:
    //      className.member
    //  + bổ sung @JvmStatic cho method
    //  + bổ sung @JvmField cho field
    // *note: className.companionObjectName.member chỉ cần nếu gọi từ file Java,
    // nếu trong file Kotlin, gọi trực tiếp qua className được, khng cần Annotation :)
    companion object {
        // val cho các object phức tạp / giá trị runtime
        val PI1 = 3.1415925f

        // const val cho kiểu nguyên thủy: Int, String, Boolean, .. => hiệu năng cao hơn
        const val PI2 = 3.1415925f

        @JvmField var count = 0

        // method
        @JvmStatic
        fun fromFullName(fullName: String, age: Int = 18, result: Float = 0f): Student1 {
            val firstName = fullName.substringBeforeLast(" ")
            val lastName = fullName.substringAfterLast(" ", "")

            return Student1(firstName, lastName, age, result)
        }
    }
}

fun main(args: Array<String>) {
    println()

    // create object using constructor (primary/secondary)
    val student1 = Student1("Pham Van", "Duc", 22, 3.4f)
    // factory method
    val student2 = Student1.fromFullName("Nguyen Duc Dat", 22, 3.33f)

    // accessing to class members
    println()
    println(student2.toJSON()) // method
    println()
    println("student1.fullName: ${student1.fullName}") // property -> student1.getFullName()
    println()
    // companion object
    println("number of student: ${Student1.count}")
    println("PI: ${Student1.Companion.PI1}") // Student1.PI1 is valid too :)

    println("\n--------------------")
    println("#vduczz")
}
