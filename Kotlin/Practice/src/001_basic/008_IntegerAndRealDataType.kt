package `001_basic`

/**
 * @date 2019.05.11
 *
 * 부동소수점 방식 : 소수점이 고정되지 않고 떠다니는 것
 * - 컴퓨터는 표현하고자 하는 실수 값을 항상 1.xxxx 형태로 만듦
 *
 */
fun main(args: Array<String>): Unit
{
    val a: Byte = 125
    val b: Short = (100 + 200) * 100
    var c: Int = 12_4354_6538
    c = 0xFF_88_88
    c = 0b01010010_01100011_01110101_01000101
    var d: Long = -543_7847_3984_7238_4723

    c = a + b
    d = c + 10L

    var e: Float = 67.6f
    var f: Double = 658.456
    e = (e + f).toFloat()
    println(e)
}