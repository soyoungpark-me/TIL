package `001_Basic`

/**
 * @date 2019.05.28
 *
 * 매개변수 (Parameter) : 함수를 호출한 곳으로부터 값을 전달 받음
 * 인수 (Argument) : 매개변수에 저장되는 표현식! 매개변수의 타입이 일치해야 함
 *
 * \[주의] 매개변수를 선언할 때는 val, var 키워드를 붙이지 않는다.
 * \[참고] 매개변수는 무조건 val로 선언되어 값을 수정할 수 없다
 */
fun main() {
    println(cToF(30))
    println(getAverage(89, 96))
}

fun cToF(celsius: Int): Any? {
    // celsius++ // Val cannot be reassigned
    return celsius * 1.8 + 32
}

fun getAverage(a: Int, b: Int): Any? {
    return (a + b) / 2.0
}
