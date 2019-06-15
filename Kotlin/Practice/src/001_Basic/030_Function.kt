package `001_Basic`

/**
 * @date 2019.05.24
 *
 * 함수 : 명령어를 담는 상자
 * - main 함수 외 별도의 함수를 선언하고 사용할 수 있다.
 * - myFunction라는 식별자는 새로운 함수로 인식된다
 * - 함수의 return 뒤에 함수 반환 타입에 맞는 표현식을 적어준다.
 *
 * fun 식별자() : 반환 타입
 * {
 *      포함시킬 문장들 ...
 * }
 *
 * - 문장이 하나뿐인 블록은 축약 가능하다
 * fun myFunction(): Double = 3.0 + 7
 *
 * - 함수의 반환 타입 또한 생략 가능하다 (추론 가능하기 때문)
 * fun myFunction() = 3.0 + 7
 *
 */
fun main() {
    println(myFuncion())
    println(myFuncion() + 10)
}

fun myFuncion(): Int {
    val a = 3
    val b = 6
    println("a : ${a}, b: ${b}")

    return a + b
}