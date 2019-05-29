package `001_basic`

/**
 * @date 2019.05.28
 *
 * Unit : 반환 값이 필요하지 않을 경우 사용 (void)
 * - 반환 타입이 Unit이면 함수 끝에 return을 쓰지 않아도 됨!
 * - 굳이 쓰고 싶다면 return만 쓰면 됨!
 *
 * \[참고] 코틀린의 Unit 타입은 자바와 Void 타입과 비슷! (class로 정의된 일반 타입)
 * \[참고] Unit을 반환할 때, return을 생략해도 암묵적으로 Unit 객제를 return 한다.
 *   => Unit 객체는 싱글톤 인스턴스로, 매번 객체를 생성하진 않는다.
 */
fun main() {
    celsiusToFah(27)
}

fun celsiusToFah(celsius: Int): Unit {
    println(celsius + 1.8 + 32) // 60.8
}
