package `001_basic`

/**
 * @date 2019.05.28
 *
 * 매개변수에는 디폴트 값을 지정할 수 있다.
 * - 인수가 지정되지 않은 매개변수는 디폴트 값으로 초기화된다.
 * - 인자를 줄 때 이름을 지정하면 순서와 무관하게 넘길 수 있다.
 *
 * \[주의] 매개변수의 이름을 지정한 인수는 일반 인수들보다 항상 오른쪽에 있어야 한다!
 */
fun main() {
    println(getAverageWithDefault(89, 96))
    getAverageWithDefault(100, 50, true)
    println(getAverageWithDefault(90))
    getAverageWithDefault(print = true)
    getAverageWithDefault(print = true, a = 10, b = 30)

    // getAverageWithDefault(print = true, 10, 30)
    // Mixing named and positioned arguments is not allowed
}

fun getAverageWithDefault(a: Int = 0, b: Int = 0, print: Boolean = false): Double {
    val result = (a + b) / 2.0

    if (print) {
        println(result)
    }
    return result
}