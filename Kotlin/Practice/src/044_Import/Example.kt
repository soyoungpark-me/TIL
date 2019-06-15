package `044_Import`

/**
 * @date 2019.06.15
 *
 * - import 키워드를 사용해 다른 패키지의 선언된 함수를 이름 없이 호출!
 * - import 패키지이름.함수이름 : 함수를 패키지 이름 없이 호출 가능
 * - import 패키지이름.* : 해당 패키지의 모든 함수를 이름 없이 호출 가능
 * - import 패키지이름.함수이름 as 별명 : 별명 지어서 호출 가능
 */
import `041_SplitSourceFile`.*
import `041_SplitSourceFile.min` as ex_min

fun main() {
    println(max(55, 47))
    println(ex_min(3, 10))
}