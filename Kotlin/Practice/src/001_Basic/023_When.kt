package `001_Basic`

/**
 * @date 2019.05.21
 *
 * when : if와 같이 조건에 따른 문장 실행 여부를 결정!
 *
 * when (타겟 표현식)
 * {
 *      타겟 표현식과 비교할 값 -> { 문장 }
 * }
 */
fun main() {
    val score = 64

    when (score / 10) {
        6 -> { println("D")}
        7 -> { println("C")}
        8 -> { println("B")}
        9, 10 -> { println("A")}
        else -> { println("F")}     // default
    }

    println("test")
}