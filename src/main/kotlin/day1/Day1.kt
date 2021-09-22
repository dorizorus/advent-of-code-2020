package day1

import java.io.File

/**
 * Find 2 numbers adding to 2020 and multiply them
 */
fun List<Int>.findPairAndMultiply(): Int {
    for (i in this.indices)
        for (j in i + 1 until this.size)
            if (this[i] + this[j] == 2020)
                return i * j

    // -1 if RIP
    return -1
}

/**
 * Find 3 numbers adding to 2020 and multiply them
 */
fun List<Int>.findTripleAndMultiply(): Int {
    for (i in this.indices)
        for (j in i + 1 until this.size)
            for (k in i + 2 until this.size)
                if (this[i] + this[j] + this[k] == 2020)
                    return i * j * k

    // -1 if RIP.
    return -1
}

fun main(args: Array<String>) {

    // opening input files (strings) and converting them to ints
    val numbers = File("""src\main\kotlin\day1\input.txt""").readLines().map { it.toInt() }
    val pairMult = numbers.findPairAndMultiply()
    println("multiplication of a pair of number adding to 2020 : $pairMult") // 15488

    val tripleMult = numbers.findTripleAndMultiply()
    println("multiplication of three numbers adding to 2020 : $tripleMult") // 2236016
}


