package `041_SplitSourceFile`

/**
 * @date 2019.06.10
 *
 * 소스 파일 하나를 여러 개로 분리할 수 있다
 * - 함수가 선언된 파일이 서로 달라도 호출이 가능하다.
 * - 소스코드를 재사용하기 쉬워진다.
 */
fun main() {
    val a = 20
    val b = -30

    println(max(a, abs(b))) // 30
}