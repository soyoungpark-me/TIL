package `001_basic`

/**용
 * @date 2019.05.08
 *
 * 리터럴에도 타입이 존재한다.
 *
 * val something: Int = 결과 타입이 Int인 표현식
 *
 * - 타입 추론 : 저장하려는 표현식으로부터 타입을 추론할 수 있을 땐 적지 않아도 됨!
 */
fun main(args: Array<String>):Unit
{
    val variable = 10 + 12 - 5
    println(variable)
}