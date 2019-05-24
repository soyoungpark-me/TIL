/**
 * @date 2019.05.24
 *
 * continue : 반복문의 일부 문장을 무시하고 건너뛸 수 있음!
 */
fun main() {
    var i = 0

    while (i < 10) {
        i += 1

        if (i % 2 == 0){ // 짝수일 경우
            continue    // continue 다음 문장은 다 Skip됨
        }
        println(i)
    }

    // 1 3 5 7 9 만 출력됨!
}