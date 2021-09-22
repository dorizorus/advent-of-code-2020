package day6

import java.io.File

fun main() {

    val nl = System.lineSeparator()
    // - read the text file
    // - separate each groups (delimiter = nl as defined above
    val groups = File("""src\main\kotlin\day6\input.txt""")
            .readText()
            .split(nl + nl)

    // ***************
    // **** Part1 ****
    // ***************

    // we cas use Set<Char> as it doesn't allow duplicates
    var sumOfQuestionsAnsweredYesByAnyone = 0
    for (group in groups)
        sumOfQuestionsAnsweredYesByAnyone += group.replace(nl, "").toSet().count()

    println("sum of question answered by yes by anyone in a group : $sumOfQuestionsAnsweredYesByAnyone") // 6763



    // ***************
    // **** Part2 ****
    // ***************
    
    // creating a List<List<String>>, yikes but oh well
    // each List<String> is a group
    val listOfGroups = groups.map { it.split(nl) }
    var sumOfQuestionsAnsweredYesByEveryoneInAGroup = 0
    for (group in listOfGroups) {
        var intersection = setOf<Char>()

        if (group.size == 1) {
            // if group size is one person, we just add the number of questions they answered yes to.
            intersection = group[0].toSet()
        } else {
            // first we intersect the first 2 strings, if they
            intersection = group[0].toSet().intersect(group[1].toSet())

            // if intersection is empty, there's no point comparing it with the rest of the group
            // so we skip to the next iteration
            if (intersection.isEmpty()) {
                continue
            } else
                for (i in 1..group.size - 2) {
                    // then we intersect the rest of the group with the intersection
                    intersection = intersection.intersect(group[i + 1].toSet())
                    // if intersection is empty at a later moment, we break out of this loop to get to the next group
                    if (intersection.isEmpty())
                        break
                }
        }

        sumOfQuestionsAnsweredYesByEveryoneInAGroup += intersection.size
    }

    println("sum of questions answered by yes by everyone in a group : $sumOfQuestionsAnsweredYesByEveryoneInAGroup")
    // 3512
}


