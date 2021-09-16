package day4

import java.io.File

// **********************************
// **** PART 1 & 2 common things ****
// **********************************

/**
 * Creates a list of Persons & their passport infos.
 * For now our batch is a list of strings, each element being a line of the batch. However, a new person's full details
 * starts after a \n\n entry in the input file. Fortunately, this \n\n input is registered by being
 * an empty string in the batch. As such, everything in between two empty strings is a persons full info.
 * We just have to concatenate it to create one string per person and their infos.
 * Do not forget to trim to erase the trailing space
 */
fun createListOfPersonsFromBatch(batch: List<String>): List<String> {
    val listOfPersons = mutableListOf<String>()
    var person = ""
    for (line in batch) {
        if (!line.isEmpty())
            person += "$line "
        else {
            listOfPersons.add(person.trim())
            person = ""
        }
    }
    //the last person isn't added in the forEach loop as the end of batch isn't an empty line
    listOfPersons.add(person.trim())
    return listOfPersons
}

/**
 * Creates a list of maps . 1 map == 1 passport (1 person and their passport info)
 * The passports are pairs as such : field:value.
 * Thus, we can create a Map<String, String> where Key = field , Value = value
 */
fun createListOfPassportsMap(listOfPersons: List<String>): List<Map<String, String>> {
    val passportsMap = mutableListOf<Map<String, String>>()
    for (person in listOfPersons) {
        val pairsAsString = person.split(" ").toList()
        val personMap = mutableMapOf<String, String>()
        for (pairString in pairsAsString) {
            personMap[pairString.split(':').first()] = pairString.split(':').last()
        }
        passportsMap.add(personMap)
    }
    return passportsMap
}

// **************************
// **** PART 1 EXCLUSIVE ****
// **************************

/**
 * validate passwords by comparing required fields & passport's fields
 * 2 ways to do it :
 * I. sort the lists (after removing the optional field) and compare them (if equals, validated)
 *
 * II. compare the size of each. Required fields has 8 items, if you remove an optional one it's 7.
 *     You then have to find 7 items in the passports, also after having removed the optional one.
 */
fun validatePassportPart1Method1(requiredFields: MutableList<String>, optionalField: String?, passports: List<Map<String, String>>): Int {
    var validatedPassports = 0
    if (requiredFields.contains(optionalField))
        requiredFields.remove(optionalField)
    //requiredFields is now of size 7

    for (passport in passports) {
        val passportFields = passport.keys.toMutableList()
        if (passportFields.contains(optionalField))
            passportFields.remove(optionalField)

        passportFields.sort()

        if (passportFields.size == requiredFields.size)
            validatedPassports++
    }

    return validatedPassports
}


fun validatePassportPart1Method2(requiredFields: MutableList<String>, optionalField: String?, passports: List<Map<String, String>>): Int {
    var validatedPassports = 0
    if (requiredFields.contains(optionalField))
        requiredFields.remove(optionalField)
    requiredFields.sort()

    for (passport in passports) {
        val passportFields = passport.keys.toMutableList()
        if (passportFields.contains(optionalField))
            passportFields.remove(optionalField)

        passportFields.sort()

        if (passportFields == requiredFields)
            validatedPassports++
    }

    return validatedPassports
}

// **************************
// **** PART 2 EXCLUSIVE ****
// **************************

/**
 * Validate a passport based on their fields (like in part1).
 * It's redundant with part1 (but tbf I was lazy to change part1)
 */
fun validatePassportFields(passport: String, requiredFields: String) = passport == requiredFields

/**
 * Validate a passport by both checking if all the required fields are present and if their values are within bounds
 */
fun validatePassportPart2(
        requiredFields: MutableList<String>, optionalField: String?, passports: List<Map<String, String>>,
        byrLowerBound: Int, byrUpperBound: Int,
        iyrLowerBound: Int, iyrUpperBound: Int, eyrLowerBound: Int, eyrUpperBound: Int,
        hgtCmLowerBound: Int, hgtCmUpperBound: Int, hgtInLowerBound: Int, hgtInUpperBound: Int,
        hclSize: Int, eclValues: List<String>, pidSize: Int): Int {

    var passportsValidated = 0

    if (requiredFields.contains(optionalField))
        requiredFields.remove(optionalField)

    requiredFields.sort()
    val finalRequiredFields = requiredFields.joinToString(" ")

    for (passport in passports) {
        val passportFields = passport.keys.toMutableList()
        if (passportFields.contains(optionalField))
            passportFields.remove(optionalField)

        passportFields.sort()
        val finalPassportFields = passportFields.joinToString(" ")

        if (validatePassportFields(finalPassportFields, finalRequiredFields)) {

            var validPassport = true
            for (field in passportFields) {
                if (!validPassport)
                    break
                val value = passport[field]

                when (field) {
                    "byr" -> if (value?.toInt() !in byrLowerBound..byrUpperBound) validPassport = false
                    "ecl" -> if (!eclValues.contains(value)) validPassport = false
                    "eyr" -> if (value?.toInt() !in eyrLowerBound..eyrUpperBound) validPassport = false
                    "hcl" -> if (value?.length != hclSize) validPassport = false
                    "hgt" -> if (!isHgtWithinRange(value, hgtCmLowerBound, hgtCmUpperBound, hgtInLowerBound, hgtInUpperBound)) validPassport = false
                    "iyr" -> if (value?.toInt() !in iyrLowerBound..iyrUpperBound) validPassport = false
                    "pid" -> if (value?.length != pidSize) validPassport = false
                }
            }

            if (validPassport)
                passportsValidated++
        }

    }
    return passportsValidated
}

fun isHgtWithinRange(hgt: String?, hgtCmLowerBound: Int, hgtCmUpperBound: Int,
                     hgtInLowerBound: Int, hgtInUpperBound: Int): Boolean {

    val type = hgt?.substring(hgt.length - 2)
    val value = hgt!!.substring(0, hgt.length - 2)

    if (type == "in") {
        if (value in hgtInLowerBound.toString()..hgtInUpperBound.toString())
            return true
    } else if (type == "cm") {
        if (value in hgtCmLowerBound.toString()..hgtCmUpperBound.toString())
            return true
    }

    return false
}


fun main() {

    val requiredFields = mutableListOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
    val batch = File("""src\main\kotlin\day4\input.txt""").readLines()
    val listOfPersons = createListOfPersonsFromBatch(batch)
    val passports = createListOfPassportsMap(listOfPersons)


    // In these puzzles, "cid" is optional, rest is required
    // Part1
    val validatedPassportsPart1Method1 = validatePassportPart1Method1(requiredFields, "cid", passports)
    val validatedPassportsPart1Method2 = validatePassportPart1Method2(requiredFields, "cid", passports)
    // answer in both cases will be 260 with the given batch
    println("validated passports part1 method 1 => $validatedPassportsPart1Method1")
    println("validated passports part1 method 2 => $validatedPassportsPart1Method2")

    // Part2
    val byrLowerBound = 1920;
    val byrUpperBound = 2002
    val iyrLowerBound = 2010;
    val iyrUpperBound = 2020
    val eyrLowerBound = 2020;
    val eyrUpperBound = 2030
    val hgtCmLowerBound = 150;
    val hgtCmUpperBound = 193;
    val hgtInLowerBound = 59;
    val hgtInUpperBound = 76
    val hclSize = 6 + 1
    val eclValues = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
    val pidSize = 9

    val validatedPassportsPart2 = validatePassportPart2(requiredFields, "cid", passports,
            byrLowerBound, byrUpperBound, iyrLowerBound, iyrUpperBound, eyrLowerBound, eyrUpperBound,
            hgtCmLowerBound, hgtCmUpperBound, hgtInLowerBound, hgtInUpperBound, hclSize, eclValues, pidSize)
    println("validated passports part 2 => $validatedPassportsPart2")
}
