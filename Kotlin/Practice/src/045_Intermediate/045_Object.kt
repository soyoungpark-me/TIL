package `045_Intermediate`

/**
 * @date 2019.06.15
 *
 * 객체 : 우리가 인식할 수 있는 물체 또는 물건!
 * - 객체는 각자의 고유한 속성과 동작을 가진다
 * - 소프트웨어 관점의 객체 : 서로 연관있는 변수(속성)들을 묶어놓은 데이터 덩어리
 *
 * - 프로퍼티(property) : object(객체)에 포함된 변수들...
 * - 프로퍼티는 반드시 선언과 동시에 초기화해야 한다!
 * - 프로퍼티와 필드는 서로 다르다!
 */
fun main() {
    // person 객체에 object(객체) 저장
    val person = object
    {
        val name: String = "홍길동"
        val age: Int = 36
    }

    println(person.name)
    println(person.age)
}