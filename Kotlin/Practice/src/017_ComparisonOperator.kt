/**
 * @date 2019.05.14
 *
 * 비교 연산자 : 두 피연산자를 비교하는 연산자
 * - ==
 * - !=
 * - >
 * - <
 * - >=
 * - <=
 * 결과 타입은 Boolean (true, false)
 */
fun main() {
    var isRight: Boolean = (10 + 70) > (3 * 25)
    println(isRight) // true

    isRight = false
    println(isRight) // false

    isRight = 30 == (10 + 20)
    println(isRight) // true

    isRight = 0.00001f == 0.005f * 0.002f
    println(isRight) // false, 실수 타입의 한계로 정확히 비교 불가

    isRight = 3.0 * 5 + 2.7 <= 16
    println(isRight) // false
}