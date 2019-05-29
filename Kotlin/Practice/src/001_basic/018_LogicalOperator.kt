package `001_basic`

/**
 * @date 2019.05.14
 *
 * 논리 연산자 : 참과 거짓으로만 계산을 수행함!
 * - &&
 * - ||
 * - !
 */
fun main() {
    val a = 15
    val b = 17

    var bool: Boolean = (a - b < a + b) && (a == 15)
    println(bool) // true

    bool = !((a + b) > (a * 3) || (b - a) > 0)
    println(bool) // false
}