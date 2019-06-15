package `001_Basic`

/**
 * @date 2019.06.02
 *
 * - 전역 변수 : 함수 밖에 선언한 변수
 * - 지역 변수 : 블록 안에서 선언된 변수
 * - 스코프 : 변수가 인식될 수 있는 범위
 *
 * \[참고] 전역 변수는 접근할 수 있는 범위가 너무 넓다.
 *   => 변수가 사용되는 지점과 값이 바뀌는 지점을 한 눈에 파악하기 어렵다!
 */
var count = 0

fun main() {
    val a = 15
    println(a)      // 15

    count++

    printCount()    // 1
    println(count)  // 2
}

fun printCount() {
    println(count)
    count++
}