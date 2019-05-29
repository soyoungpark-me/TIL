package `001_basic`

/**
 * @date 2019.05.24
 *
 * label (레이블) : break의 경우 가장 가까운 반복문 하나만 빠져나온다.
 * - 이런 문제를 해결하기 위해 등장!
 */
fun main() {
    var x = 0
    var y = 0

    outer@ while(x <= 20) {
        y = 0
        while (y <= 20) {
            if (x + y == 15 && x - y == 5) {
                break@outer // 이렇게 나갈 곳을 지정해준다
            }
            y += 1
        }
        x += 1
    }

    println("x : ${x}, y :${y}") // 10, 5
}