package `001_basic`

/**
 * @date 2019.05.28
 *
 * 함수 오버로딩 : 함수 시그니처만 다르면 함수 이름의 중복이 허용된다
 *
 * - 함수 시그니처 : 함수의 고유한 특징을 나타내는 것..
 *   - 매개변수의 개수
 *   - 매개변수 타입
 *   - 반환 타입
 */
fun main() {
    println()

    /*
        /** Prints the given [message] and the line separator to the standard output stream. */
        @kotlin.internal.InlineOnly
        public actual inline fun println(message: Any?) {
            System.out.println(message)
        }

        /** Prints the given [message] and the line separator to the standard output stream. */
        @kotlin.internal.InlineOnly
        public inline fun println(message: Int) {
            System.out.println(message)
        }

        /** Prints the given [message] and the line separator to the standard output stream. */
        @kotlin.internal.InlineOnly
        public inline fun println(message: Long) {
            System.out.println(message)
        }
    */
}