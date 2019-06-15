package `045_Intermediate`

/**
 * @date 2019.06.15
 *
 * 힙 :
 * - 힙 영역에서는 임의의 위치에 객체가 생성된다.
 * - 차곡차곡 쌓이는 형태인 스택 영역과는 다르다!!!
 * 
 * - object {} 표현식은
 *   1. 힙 영역에 객체를 생성하고,
 *   2. 갓 생성된 객체의 좌푯값을 가진다.
 *   3. 따라서 person 겍체에는 객체의 좌표를 저장하기 위한 공간만 존대한다.
 * - 참조 변수 : person처럼 실제 값은 가지지 않고 좌표만 저장하는 변수!
 */
fun main() {
    // person 친구는 object의 좌표값을 가진다.
    val person = object
    {
        val name: String = "홍길동"
        val age: Int = 36
    }

    println(person.name)
    println(person.age)
}