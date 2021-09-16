import org.junit.Test
import kotlin.math.pow
import kotlin.test.assertEquals

class Day4Test {

    // "cid" field is optional, rest is mandatory
    val fields = mutableListOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")
    val requiredFields = mutableListOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val batchExample = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
            "byr:1937 iyr:2017 cid:147 hgt:183cm\n" +
            "\n" +
            "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\n" +
            "hcl:#cfa07d byr:1929\n" +
            "\n" +
            "hcl:#ae17e1 iyr:2013\n" +
            "eyr:2024\n" +
            "ecl:brn pid:760753108 byr:1931\n" +
            "hgt:179cm\n" +
            "\n" +
            "hcl:#cfa07d eyr:2025 pid:166559648\n" +
            "iyr:2011 ecl:brn hgt:59in"

    /**
     * Create a list of persons and their passport infos from the batch
     */
    @Test
    fun `given batch _ separate persons from batch`() {
        val result = listOf(
                "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
                        "byr:1937 iyr:2017 cid:147 hgt:183cm",
                "iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884\n" +
                        "hcl:#cfa07d byr:1929",
                "hcl:#ae17e1 iyr:2013\n" +
                        "eyr:2024\n" +
                        "ecl:brn pid:760753108 byr:1931\n" +
                        "hgt:179cm",
                "hcl:#cfa07d eyr:2025 pid:166559648\n" +
                        "iyr:2011 ecl:brn hgt:59in"
        )

        val separatedPersonsFromBatch = batchExample.split("\n\n").toList()

        assertEquals(result, separatedPersonsFromBatch)
    }

    /**
     * create a HashMap<String,String> of one person's passport info
     * Keys = fields we'll need to check. Values = values of the keys.
     * Can be iterated on for every person of the list in the previous test
     */
    @Test
    fun `given one person _ map key & values`() {
        val resultOnFirstPerson =
                hashMapOf("ecl" to "gry",
                        "pid" to "860033327",
                        "eyr" to "2020",
                        "hcl" to "#fffffd",
                        "byr" to "1937",
                        "iyr" to "2017",
                        "cid" to "147",
                        "hgt" to "183cm"
                )
        val separatedPersonsFromBatch = batchExample.split("\n\n").toList()
        val firstPerson = separatedPersonsFromBatch.first()
        val pairsOfKeyAndValue = firstPerson.split(" ", "\n")
        val mapOfKeysAndValues = pairsOfKeyAndValue.associateBy({ it.split(":").first() }, { it.split(":").last() })
        assertEquals(resultOnFirstPerson, mapOfKeysAndValues)
    }

    @Test
    fun `given list of persons _ validate them`() {
        val result = 2
        var validatedPassport = 0
        val separatedPersonsFromBatch = batchExample.split("\n\n").toList()
        val listOfFieldsPerPerson: MutableList<MutableList<String>> = mutableListOf()
        for (person in separatedPersonsFromBatch) {
            val pairsOfKeyAndValue = person.split(" ", "\n")
            val listOfFields: MutableList<String> = mutableListOf()
            for (pair in pairsOfKeyAndValue) {
                listOfFields.add(pair.split(":").first())
            }
            listOfFieldsPerPerson.add(listOfFields)
        }
        requiredFields.sort()
        println(requiredFields)
        for (fields in listOfFieldsPerPerson) {
            if (fields.contains("cid"))
                fields.remove("cid")

            fields.sort()
            println(fields)

            if (fields == requiredFields)
                validatedPassport++
        }

        assertEquals(result, validatedPassport)
    }


}
