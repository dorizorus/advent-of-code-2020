package day2

import java.io.File

fun validatePasswordProtocol1(inputs: List<List<String>>): Int {
    var validPasswordCounter = 0

    // forEach List<String> in List<List<String>>
    for (input in inputs) {
        val lowerBound = input[0].toInt()
        val higherBound = input[1].toInt()
        val descriptor = input[2]
        val password = input[3]
        var counter = 0

        //forEach Char in String
        for (c in password) {
            if (c.toString() == descriptor)
                counter++
        }

        if (counter in lowerBound..higherBound)
            validPasswordCounter++

    }

    return validPasswordCounter
}

fun validatePasswordProtocol2(inputs: List<List<String>>): Int {
    var validPasswordCounter = 0

    // forEach List<String> in List<List<String>>
    for (input in inputs) {
        val firstPos = input[0].toInt()
        val secondPos = input[1].toInt()
        val descriptor = input[2]
        val password = input[3]
        val firstPosChar = password[firstPos - 1]
        val secondPosChar = password[secondPos - 1]

        if (firstPosChar != secondPosChar)
            if (firstPosChar.toString() == descriptor || secondPosChar.toString() == descriptor)
                validPasswordCounter++
    }

    return validPasswordCounter
}

fun main(args: Array<String>) {

    //first we read the file and create a list of strings, each line of the file contains the range, the letter and the password
    val inputsAsStrings = File("""src\main\kotlin\day2\input.txt""").readLines()
        .map { it }

    //second we map every entry of the first list into a List<List<String>>, every entry will be composed as such :
    // index 0 = lower bound of the range, 1 = upper bound of the range (in version2, these becomes charPositions in password (counting from 1))
    // index 2 = letter (descriptor), 3 = password
    val inputsSplit = inputsAsStrings.map { it.replace(":", "").split('-', ' ') }

    val validPasswordsProtocol1 = validatePasswordProtocol1(inputsSplit)
    println("# of valid passwords with protocol1 : $validPasswordsProtocol1")

    val validPasswordsProtocol2 = validatePasswordProtocol2(inputsSplit)
    println("# of valid passwords with protocol2 : $validPasswordsProtocol2")


}

