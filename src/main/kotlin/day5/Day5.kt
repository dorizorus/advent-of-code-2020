package day5

import java.io.File

// how to calc row is explained the puzzle wording
fun String.calcRow(): Int {
    var rowUpperbound = 128;
    var rowLowerbound = 0

    for (char in this.substring(0, this.length - 3))
        if (char == 'F')
            rowUpperbound = (rowLowerbound + rowUpperbound) / 2
        else
            rowLowerbound = (rowLowerbound + rowUpperbound) / 2

    return rowLowerbound
}

// // how to calc col is explained the puzzle wording
fun String.calcCol(): Int {
    var colUpperbound = 0;
    var colLowerbound = 7

    for (char in this.substring(this.length - 3))
        if (char == 'R')
            colUpperbound = (colLowerbound + colUpperbound) / 2
        else
            colLowerbound = (colLowerbound + colUpperbound) / 2

    return colLowerbound
}

fun calcSeatId(row: Int, col: Int): Int {
    return row * 8 + col
}

fun keepHighestId(currentHighestId: Int, boardingpass: String): Int {
    val row = boardingpass.calcRow()
    val col = boardingpass.calcCol()
    val id = calcSeatId(row, col)
    return if (id > currentHighestId) id else currentHighestId

}

fun getListOfSeatIds(boardingPasses: List<String>): MutableList<Int> {
    val listOfSeatIds = mutableListOf<Int>()
    for (boardingpass in boardingPasses) {
        val row = boardingpass.calcRow()
        val col = boardingpass.calcCol()
        listOfSeatIds.add(calcSeatId(row, col))
    }
    return listOfSeatIds
}

fun main() {
    val boardingPasses = File("""src\main\kotlin\day5\input.txt""").readLines()

    // part is about calculating seat ids and finding the highest one.
    var highestId = 0
    for (boardingpass in boardingPasses)
        highestId = keepHighestId(highestId, boardingpass)


    println("highest id => $highestId") // 832

    // for part2 we have to find our seatId, our seat isn't in the front or back row, as such we can define a range
    // in which our seat will be. If there is a hole (seat(n+1)Id - seat(n)Id != 1), it is our seat.
    var mySeatId = 0
    val highestForwardSeat = "FFFFFFFRRR"
    val highestForwardSeatRow = highestForwardSeat.calcRow()
    val highestForwardSeatCol = highestForwardSeat.calcCol()
    val lowestBackSeat = "BBBBBBBBLLL"
    val lowestBackSeatRow = lowestBackSeat.calcRow()
    val lowestBackSeatCol = lowestBackSeat.calcCol()

    val highestForwardSeatId = calcSeatId(highestForwardSeatRow, highestForwardSeatCol)
    val lowestBackSeatId = calcSeatId(lowestBackSeatRow, lowestBackSeatCol)

    val listOfSeatId = getListOfSeatIds(boardingPasses)
    listOfSeatId.sort()
    for (i in 0 until listOfSeatId.size)
        if (listOfSeatId[i] in highestForwardSeatId..lowestBackSeatId)
            if (listOfSeatId[i + 1] in highestForwardSeatId..lowestBackSeatId)
                if (listOfSeatId[i + 1] - listOfSeatId[i] != 1) {
                    mySeatId = listOfSeatId[i] + 1
                    break
                }

    println("my seat id => $mySeatId") // 517
}

