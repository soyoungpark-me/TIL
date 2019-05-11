/**
 * @date 2019.05.11
 *
 * 문자 타입 : 문자를 저장할 수 있는 타입, 하나에 2바이트
 *
 * - 컴퓨터는 숫자만 다룰 수 있어, 문자는 번호를 매겨서 쓴다.
 * - 코틀린에서 문자 코드는 유니코드(Unicode)를 사용한다.
 */
fun main(args: Array<String>): Unit
{
    var ch:Char = 'A'
    println(ch)         // A

    ch = '\uAC00'
    println(ch)         // 가

    ch = '한'
    println(ch)         // 한
    println(ch.toInt()) // 54620
}