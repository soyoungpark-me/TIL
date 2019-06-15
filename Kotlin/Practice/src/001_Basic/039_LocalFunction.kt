package `001_Basic`

/**
 * @date 2019.06.02
 *
 * 지역 함수 : 블록 안에 선언된 함수
 */
fun main() {
    fun printFomular(a: Int, b: Int) {
        println(a * b + a - b)
    }

    printFomular(73, 1) // 145
    printFomular(4, 6)  // 22
}