package `001_Basic`

/**
 * @date 2019.05.11
 *
 * String : 문자열을 저장할 수 있는 타입
 * - 큰따옴표로 문자 여러개를 감싸면 String 타입이 된다
 * - 문자열끼리 +로 연결하면 String이 아니어도 String으로 변환해서 합친다
 */
fun main(args: Array<String>): Unit
{
    var str:String = "Hello"
    println(str)        // Hello

    str = str + "\nKotlin!"
    println(str)        // Hello
                        // Kotlin!

    println(str[8])     // t

    val num = 10 * 5 + 3
    println(str + num)  // Hello
                        // Kotlin!53
}