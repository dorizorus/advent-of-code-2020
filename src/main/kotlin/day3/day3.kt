package day3

import java.io.File

/**
 * Find the number of trees we meet (we stop at) following these steps :
 * forward 3, down 1
 */
fun findNumOfTrees_exe1(map: List<String>): Int {
    val step = 3
    var row = 0 // == i as in map[i] -> map[i] is a string
    var col = 0 // == j as in map[i][j] -> map[i][j] is a char
    var treeCounter = 0

    while (row < map.lastIndex) { // while (i <= map.size - 1)
        col += step // add 3 steps forward (j+=3)
        row++ // add 1 step down (i++)


        if (col >= map[row].length)
            col -= map[row].length

        if (map[row][col] == '#')
            treeCounter++
    }
    return treeCounter
}

/**
 * Find the number of trees we meet (we stop at) following the steps in the parameters
 * @param forward steps forward
 * @param down steps downward
 */
fun findNumOfTrees(forward: Int, down: Int, map: List<String>) : Int {
    var row = 0 // == i as in map[i] -> map[i] is a string
    var col = 0 // == j as in map[i][j] -> map[i][j] is a char
    var treeCounter = 0
    while (row < map.lastIndex) { // while (i <= map.size - 1)
        col += forward // add 3 steps forward (j+=3)
        row += down // add 1 step down (i++)


        if (col >= map[row].length)
            col -= map[row].length

        if (map[row][col] == '#')
            treeCounter++
    }
    return treeCounter
}

fun main(args: Array<String>) {

    val map = File("""src\main\kotlin\day3\input.txt""").readLines()
            .map { it }

    val treeCounter_exe1 = findNumOfTrees_exe1(map)
    println("treeCounter_exe1 : $treeCounter_exe1")

    val treeCounter_exe2 = findNumOfTrees(1, 1, map) * findNumOfTrees_exe1(map) *
            findNumOfTrees(5,1, map) * findNumOfTrees(7, 1, map) * findNumOfTrees(1, 2, map)
    println("treeCounter_exe2 : $treeCounter_exe2")
}
