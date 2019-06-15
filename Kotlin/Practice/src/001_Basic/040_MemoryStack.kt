package `001_Basic`

/**
 * @date 2019.06.02
 *
 * 스택 : 지역 변수가 저장되는 메모리 속 공간
 * - 변수가 생성될 때는 생성된 순서대로 스택에 차곡차곡 쌓인다.
 * - 스코프에서 탈출할 때 해당 매개변수도 스택 영역에서 사라진다.
 */
fun main() {
    val a = -36
    val result = absolute(a)

    println(result) // 36
}

fun absolute(number: Int): Any {
    return if (number >= 0)
        number
    else
        -number
}
