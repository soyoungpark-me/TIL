package `041_SplitSourceFile`

fun max(a: Int, b: Int): Int =
    if (a > b)
        a
    else
        b

fun min(a: Int, b: Int): Int =
    if (a < b)
        a
    else
        b

fun abs(num: Int): Int =
    if (num >= 0)
        num
    else
        -num