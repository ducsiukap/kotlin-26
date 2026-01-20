package kt005_oops

import java.util.Objects

// Mặc định, mọi class trong kotlin là final -> không thể inherit
// => để cho phép inherit, sử dụng `open` keyword khi khai báo class
open class Shape {
    // để cho phép override member (property / function) nào,
    // sử dung `open` trước member đó

    // kotlin cho phép override cả property
    // sử dụng `open`
    open val area: Double = 0.0

    // mặc định là không cho override
    val variable: Int = 100

    // cho phep sub-class override function
    open fun info() = ""
}

// kế thừa phải gọi constructor của cha ngay đầu tiên
// `:` thay cho extends
// - nếu con có primary constructor -> phải gọi constructor của cha ngay ở đầu khai báo class
// - nếu con chỉ có secondary constructor -> sử dụng `: super()` để gọi constructor của cha.
class Circle(val radius: Double) : Shape() {

    // override property
    // - vì bản chất property là get/set => override là override get/set
    // - `override` keyword
    // rule "chỉ mở rộng quyền":
    // - var (super) -> var/val (sub) // val (super) -> val (sub)
    // - larger access modifier.
    override val area: Double
        inline get() = radius * radius * Math.PI

    // variable được inherit từ cha nhưng không có quyền override

    // override method
    override fun info() = """
        {
            "radius": "$radius",
            "area": "$area",
            "variable": "$variable"
        }
    """.trimIndent()

    // Mọi class đều ngầm kế thừa từ Any -> được override 3 hàm:
    // .toString()
    override fun toString(): String {
        return this.info();
    }

    // .equals()
    override fun equals(other: Any?): Boolean {
        val o = other as Circle;

        return (this.radius == o.radius &&
                this.variable == o.variable)
    }

    // .hashCode()
    // nguyên tắc: nếu equals == true -> hashCode phải bằng nhau
    // => So sánh trên field nào, hash trên các field đó
    override fun hashCode(): Int {
        return Objects.hash(area, variable)
    }
}

// Vì extends là quan hệ IS-A
// => chỉ extends được tối đa 1 super-class

// Muốn extends nhiều (thực ra là Composition over Inheritance)
// - sử dụng interface
// - sử dụng Class Delegation => `by` keyword
//      các tham số của primary constructor được đảm nhiệm bởi các lớp khác nhau
//
// generated example:
interface ChienDau {
    fun danhNhau()
}

interface DiChuyen {
    fun chay()
}

class NguoiNhen : ChienDau {
    override fun danhNhau() = println("Bắn tơ :)")
}

class NguoiDoi : DiChuyen {
    override fun chay() = println("Lái Batmobile")
}

// --- CLASS DELEGATION ---
// SieuNhan "ủy quyền" việc đánh nhau cho NguoiNhen, việc chạy cho NguoiDoi
class SieuNhan(nhen: ChienDau, doi: DiChuyen) : ChienDau by nhen, DiChuyen by doi

fun main(args: Array<String>) {
    println()

    val shape: Shape = Circle(5.0);

    println(shape)
    println(shape.info())

    println()
    var circle = Circle(5.0);
    println(circle == shape)
    println("circle hashcode: ${circle.hashCode()}, shape hashcode: ${shape.hashCode()}")

    println()
    circle = Circle(13.3);
    println(circle == shape)
    println("circle hashcode: ${circle.hashCode()}, shape hashcode: ${shape.hashCode()}")

    println()
    println()

    // class Delegation
    val nhen = NguoiNhen()
    val doi = NguoiDoi()

    val s = SieuNhan(nhen, doi)
    s.danhNhau() // "Bắn tơ :)"
    s.chay()     // "Lái Batmobile"

    println("\n--------------------")
    println("#vduczz")
}