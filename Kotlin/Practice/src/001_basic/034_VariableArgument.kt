package `001_basic`

/**
 * @date 2019.05.28
 *
 * vararg : 개수가 정해지지 않은 매개변수 넘기기
 * - 이 키워드를 붙이면 여러개의 인수를 받을 수 있게 된다!
 * - 가변 매개변수에 빈 값을 주면, numbers.size가 0이 된다 (NPE 발생 X)
 */
fun main() {
    println(getSumOf(1, 2, 3, 4, 5, 6, 7))  // 28
    println(getSumOf(13, 57, 91))           // 161
    println(getSumOf())                              // 0
}

fun getSumOf(vararg numbers: Int): Int {
    val count = numbers.size
    var i = 0; var sum = 0

    while (i < count) {
        sum += numbers[i]
        i++
    }
    return sum
}