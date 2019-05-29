package `001_basic`

/**
 * @date 2019.05.11
 *
 * 비트 연산자 : 코틀린의 비트 연산자는 기호가 아니라 문자!
 *
 * - and : 비트 단위로 and 연산
 * - or : 비트 단위로 or 연산
 * - xor : 비트 단위로 xor 연산
 * - inv : 비트 단위로 반전
 * - shl : 왼쪽으로 시프트
 * - shr : 오른쪽으로 시프트
 * - 부호를 유지한 채 오른쪽으로 시프트
 */
fun main(args: Array<String>): Unit
{
    println(15 and 7)       // 7
    println(5 or 2)         // 7
    println(15 xor 5)       // 10
    println(32767.inv())    // -32768
    println(1 shl 3)        // 8
    println(8 shr 1)        // 4
    println(-17 ushr 2)     // 1073741819
}