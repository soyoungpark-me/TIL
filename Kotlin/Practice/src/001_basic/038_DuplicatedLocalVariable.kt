package `001_basic`

/**
 * @date 2019.06.02
 *
 * 서로 다른 함수 안에 같은 이름의 변수가 존재할 수도 있다.
 * - 지역 변수의 이름은 같은 스코프 내에서만 안 겹치면 된다.
 */

fun main() {
    val a = 52
    println(a)  // 52

    printA()    // 17
    printA2()   // 25
}

fun printA() {
    val a = 17
    println(a)
}

fun printA2() {
    val a = 25
    println(a)
}
