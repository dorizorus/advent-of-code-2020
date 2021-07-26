import org.junit.Test
import kotlin.test.assertEquals

class Day3Test {

// Example data is provided and result is known. At the end we should meet 7 trees when going through the whole map

    var map = listOf(
            "..##.......",
            "#...#...#..",
            ".#....#..#.",
            "..#.#...#.#",
            ".#...##..#.",
            "..#.##.....",
            ".#.#.#....#",
            ".#........#",
            "#.##...#...",
            "#...##....#",
            ".#..#...#.#"
    )
    val oneLineOfMap = map[0]
    val twoLinesOfMap = listOf(map[0], map[1])

    /**
     * Testing looping around one line of map as the lines repeat indefinitely
     * We test 10 repeats of 3 steps
     * Final result must be : index = 8, trees = 2
     */
    @Test
    fun loopingOnOneLine() {
        val step = 3
        var repeats = 0
        var index = 0
        var trees = 0

        while (repeats < 10) {
            index += step
            if (index >= oneLineOfMap.length)
                index -= oneLineOfMap.length

            if (oneLineOfMap[index] == '#')
                trees++

            repeats++
        }

        assertEquals(2, trees)
        assertEquals(8, index)
    }

    /**
     * Looping on whole map. We go forward 3 steps and down 1 step every repetition
     * We count the number of tree we meet (we stop on) everytime. Count should be 7
     */
    @Test
    fun loopingOnWholeMap() {
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

        assertEquals(7, treeCounter)
    }
}
