// Về cơ bản, Kotlin Regex vẫn tương tự Java Regex
// - để dễ dàng viết Regex string hơn, ta dùng raw string """"""

// ---------------
// NẾU DÙNG RAW-STRING, BỎ QUA ESCAPE "\" PHÍA TRƯƠC
// ex: "\\d" => """\d"""

// "." for any one-character
// "\n", "\t" => newline / tab

// [abc], [a-z], [A-Z], [0-9] => 1 ký tự bất kỳ trong abc / a-z / A-Z/ 0-9
// [^abc] => không phải 1 trong các ký tự a, b, c

// "\\" for \
// "\\d" / "\\D" for \d / \D => a number / not a number
// "\\w" => a character [a-zA-Z0-9_] // number, character or _
// "\\W" => not a character [a-zA-Z0-9_]
// "\s" => space / tab/ newline // whitespace
// "\\S" => not a whitespace

// "*" => 0 or more
// "+" => 1 or more
// "?" => 0 or 1
// {n} => exact n time
// {n,} => ít nhất n lần
// {n, m} => từ n -> m lần

// "^" => begin of string
// "$" => end of string
// "\b" => đầu/cuối ký tự // ex: \bcat\b chỉ match cat chứ không match scatter
// "\B" => ko phải đầu/cuối ký tự // ex cat\B chỉ match scatter, không match cat

// escape
// . ^ $ * + ? ( ) [ ] { } | \
// => phải thêm "\" phía trước nếu muốn nó thực sự là các ký tự đó trong string
// ex: "\.", "\("

// group => using ()
// () => capturing group // có thể dùng với group() / groupCount()
// (?:) => non-capturing group // ko thể dùng với group() / groupCount()
// (?<name>) => named group => matcher.group("name")
// \1, \2 => tham chiếu lại nội dung .group(1), .group(2) vừa match
// ex: (\w+)\s\1:
// - "hi hi" => match
// - "hi world" => no-match
// - "java java" => match

// "|" for or
// ex: (?:jpg|png|gif) => jpg or png or gif

// lookaround
// (?=abc) => phía sau là abc
// (?!abc) => phía sau không là abc
// (?<=abc) => phía trước là abc
// (?<!abc) => phía trước không là abc

fun main(args: Array<String>) {
    println()

    // using str.toRegex() to create Pattern object
    val dateRegex = """(\d{2})([/\-])(\d{2})\2(\d{4})""".toRegex() // dd/mm/yyyy
    val test = """
            2025-12-27
            24/10/2004
            13\3\2026
            24-10/2004
            13-03-2029
            16/05/2027
    """.trimIndent()
    println("regex: ${dateRegex.pattern}\ntest string: $test\n")

    // check full-match
    println("full-match: ${dateRegex.matches(test)}")

    // finding
    // .find(input) => find each match
    // .find(start), .find(start, end, input)
    println("\n.find(input): ")
    var match = dateRegex.find(test);
    while (match != null) {
        // .group() -> .value
        // .groupCount() -> groupValues.size
        // .start(), .end() -> .range
        println("match: ${match.value}, range: ${match.range}, group value: ${match.groupValues}")

        // destructuring
        val (d, m, y) = match.destructured // .group(0), group(1), ...
        println("\tDestructuring: date: $d, month: $m, year: $y")

        // refer to next match
        match = match.next()
    }

    // .findAll(input)
    println("\n.findAll(input): ")
    val allMatch = dateRegex.findAll(test);
    for (match in allMatch) {
        println("match: ${match.value}")
    }

    // smart Replacement
    // trong java, để replace phải:
    // 1. tạo StringBuilder / StringBuffer sb
    // 2. tìm từng match
    // 3. matcher.appendReplacement(sb, replacement)
    // trong kotlin
    println("\n.replace(input, {match -> match.value + \"(valid)\":}")
    val result = dateRegex.replace(test) { match ->
        buildString {
            append(match.value);
            append(" (valid)")
        }
    }
    println(result)

    println("\n--------------------")
    println("#vduczz")
}