/**
 * @date 2019.05.21
 *
 * if와 else가 모두 갖춰져 있을 경우, if-else 부분 전체가 표현식이 된다
 * - 따라서 변수에 결과를 대입할 수 있다.
 * - 블록 내의 마지막 표현식이 해당 if-else 표현식의 값이 된다!
 *
 * \[주의] if 블록과 else 블록의 마지막 표현식의 타입은 일치해야 한다!
 * \[주의] 블록이 비어있거나 마지막이 표현식이 아니라면
 *   => if-else 표현식이 Unit 타입이 되고, 쓰레기 값이 저장됨!
 */
fun main() {
    val value: Int = if (10 > 5) {
        println("10은 5보다 크다")
        10
    } else { // 여기서 else문을 써주지 않으면 오류남!
        println("10은 5보다 크지 않다")
        5
    }

    println(value) // 10

    val test = if (10 > 5) {} else {}
    println(test)  // kotlin.Unit
}