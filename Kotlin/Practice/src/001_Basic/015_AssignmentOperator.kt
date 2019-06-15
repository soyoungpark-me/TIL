package `001_Basic`

/**
 * @date 2019.05.12
 *
 * 배정 연산자 : 변수에 값을 저장할 때 사용
 * - 좌측의 피연산자는 변수여야 함!
 *
 * \[참고] 우선 순위가 매우 낮은 편!
 */
fun main() {
    val a: Int
    var b: Int

    a = 10 + 5
    b = 10

    b += a // b에 a의 값을 누적
    println(b)

    b %= 3 // b를 3으로 나눈 나머지를 다시 b에 저장
    println(b)
}