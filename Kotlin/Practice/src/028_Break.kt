/**
 * @date 2019.05.24
 *
 * break : 반복문을 즉시 탈출한다
 */
fun main() {
    var i = 0
    while (true) {
        i += 1

        if (i >= 5) {
            break // 이 문장이 실행되는 순간 while문 탈출!
        }

        print(i) // 1234
    }
}