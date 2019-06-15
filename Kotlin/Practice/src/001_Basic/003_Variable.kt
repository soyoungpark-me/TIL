package `001_Basic`

/**
 * @date 2019.05.08
 *
 * - 변수 : 값을 저장해놓는 공간
 * - var : 바로 뒤에 오는 것이 변수임을 나타내는 키워드
 * - val : immutable variable. 수정 불가능한 변수
 *
 *  + Kotlin에는 Java와 다르게 Primitive type이 없다.
 */
fun main(args: Array<String>): Unit
{
    var total: Int
    total = 0

    val a: Int = 10 + 53 - 7
    println(a)

    val b: Int = 43 + 75 + a
    println(b)

    total = a + b
    println(total)
}