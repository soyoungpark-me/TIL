/**
 * @date 2019.05.12
 *
 * 산술 연산자 : 가감승제를 수행하는 연산자
 * - 코틀린은 수학과 마찬가지로 사칙연산의 법칙을 따름!
 *
 * \[주의] 특정 타입의 변수에는 반드시 해당 변수의 값만 지정할 수 있음
 */
fun main() {
    val num: Int = 15 - 5 * 3
    val num2: Int = 65 % 7
    val num3: Double = 7.5 / 5 + 22.25
    val num4: Double = num / num2 + 0.7

    println(num)    // 0
    println(num2)   // 2
    println(num3)   // 23.75
    println(num4)   // 0.7
}