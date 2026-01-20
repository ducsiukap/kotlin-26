package kt005_oops

// > Để đạt được abstraction, có 2 cách:
// - sử dụng interface (ưu tiên cao hơn)
// - sử dụng abstract class

// abstract class
// - class có abstract function -> abstract class
// - sử dụng `abstract` keyword.
// - không thể tạo instance
// - có thể chứa normal member (prop, function), constructor và abstract function
//      + constructor để khởi tạo dữ liệu cho biến
//      + method trong abstract class:
//          i. abstract fun
//          ii. open fun
//          iii. fun (normal fun)
//      + properties
//          i. abstract val/var -> implement get() and set()
//          ii. val/var -> normal properties
//          iii. open val/var
//      // ngoài public (interface chỉ public),
//      // có thể sử dụng protected, internal cho class members
// - sub-classes have to implement super's abstract method
abstract class Animal(val name: String?) {
    init {
        require(!name.isNullOrEmpty()) { "name must not be empty" }
    }

    // abstract prop
    // abstract val prop: Type;

    // abstract method
    abstract fun makeSound();

    // normal / open fun
    open fun eat() {
        println("$name is eating!")
    }
}

class Dog(name: String) : Animal(name) {
    // have to override abstract method/prop
    override fun makeSound() {
        println("gau gau")
    }
}

class Cat(name: String) : Animal(name) {
    override fun makeSound() {
        println("meow meow")
    }
}

fun main(args: Array<String>) {
    println()

    var animal: Animal = Dog("Bong");
    animal.makeSound();
    animal.eat()

    animal = Cat("Tom");
    animal.makeSound();
    animal.eat()

    println("\n--------------------")
    println("#vduczz")
}