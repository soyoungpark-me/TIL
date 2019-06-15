package `001_Basic`

/**
 * @date 2019.05.21
 *
 * 중첩문 : if나 else 안에는 또 다른 if나 else를 넣을 수 있음!
 */
fun main() {
    val score = 88

    if (score >= 90) {
        println("A")
    } else {
        if (score >= 80) {
            println("B")
        } else {
            if (score >= 70) {
                println("C")
            } else {
                println("F")
            }
        }
    }
}