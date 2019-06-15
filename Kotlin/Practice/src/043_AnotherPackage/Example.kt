package `043_AnotherPackage`

/**
 * @date 2019.06.15
 *
 * - 소스 파일이 서로 다른 패키지에 있다면 패키지이름.함수이름() 으로 호출한다.
 * - 패키지가 서로 다르면, 이름과 시그니처가 같은 함수를 선언해도 상관없다! (패키지로 구분 가능)
 */
fun main() {
    println(`041_SplitSourceFile`.min(30, 10))
}