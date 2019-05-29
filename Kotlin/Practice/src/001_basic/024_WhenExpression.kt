package `001_basic`

/**
 * @date 2019.05.21
 *
 * when 또한 else 블록을 포함하고 있다면 표현식이 된다!
 */
fun main() {
    val score = 95

    val grade: Char = when (score / 10)
    {
        6 -> 'D'
        7 -> 'C'
        8 -> 'B'
        9,10 -> 'A'
        else -> 'F'
    }

    println(grade)
}