package `001_basic`

/**
 * @date 2019.05.14
 *
 * 조건문 if : 상황에 따라 실행될 코드를 다르게 하고 싶을 때 사용!
 *
 * if (Boolean 타입 표현식, 조건)
 * {
 *      if 문에 포함시킬 내용 (블록)
 * }
 *
 * \[참고] if(){} 부분 자체는 한 문장으로 인식된다.
 */
fun main() {
    var a = 15
    var b = 11

    if (a > b) {
        println("if문 안으로 들어왔음!")
        a -= b
    }

    println(a)
}