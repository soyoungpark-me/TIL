package `001_Basic`

/**
 * @date 2019.05.21
 *
 * while : 특정 문장을 반복할 때 쓰는 키워드
 * - 조건이 true이면 while 블록 속의 문장을 계속 실행!
 *
 * \[주의] 무한 루프 : while의 조건이 항상 true일 경우 탈출 불가!
 */
fun main() {
    var i = 1

    while (i < 10) {
        println(i)
        i += 1
    }
}