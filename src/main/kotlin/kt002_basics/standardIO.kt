package kt002_basics

import java.util.Scanner

fun main(args: Array<String>) {
    println()
    // Standard Output
    // - to print a message:
    //      print(msg)
    // - to print a message and auto new line:
    //      println(msg)
    // - to formatted print:
    //      printf(formattedString, args)
    //   or using embedded string, using ${expression}
    print("Enter your name:");

    // Standard Input
    // 1. to read a line, using:
    //      readLine()
    //      readln()
    //      readlnOrNull()
    val name = readln();
    // 2. using Scanner class
    val sc = Scanner(System.`in`);
    print("Enter your age: ");
    // using:
    //  .next() to get next word
    //  .nextLine() to get a line
    //  .nextT() to get a value of type <T>
    // note: if you was invoked next() or any nextT() method, exclusive nextLine(),
    // then, you have to call nextLine() to remove anything in buffer
    // before invoke nextLine() again to read a line.
    val age = sc.nextInt();
    print("Enter your major: ");
    sc.nextLine(); // ignore the buffer
    val major = sc.nextLine();

    println("\n==============")
    println("student's information: ");
    println("{\n\t\"name\": \"$name\",\n\t\"age\": \"$age\", \n\t\"major\": \"$major\"\n}") // embedded string

    println("\n--------------------")
    println("#vduczz")
}