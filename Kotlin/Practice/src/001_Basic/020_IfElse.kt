package `001_Basic`

/**
 * @date 2019.05.14
 *
 * else : if와 짝이 되는 키워드
 * - if가 나오고 난 다음에만 사용 가능!
 * - if의 조건이 false일 때 else의 내용이 실행된다
 *
 * \[참고] if부터 else까지는 하나의 문장으로 인식됨!
 */
fun main() {
    val a = 10
    val b = 5

    if (a < b)
        println("if")
    else
        println("else")

    if (a > b)
        println("a가 크다")
    else
        println("b는 a 이상이다.")
}