// Calculate sum of all number from 1 to n + 1, then subtract all numbers in the array. => Find missing number
fun findMissingNumber(numbers: Array<Int>): Int {

    // If array is empty, return -1
    if (numbers.isEmpty()) {
        return -1
    }

    // Check for duplicate or invalid number
    val uniqueNumbers = numbers.toSet()
    if (uniqueNumbers.size != numbers.size || uniqueNumbers.any { it <= 0 }) {
        return -1
    }

    val size = numbers.size

    // Sum of all numbers from 1 to n + 1
    val expectedSum = (size + 1) * (size + 2) / 2

    // Sum of array
    val actualSum = numbers.sum()

    // Missing number
    val missingNumber = expectedSum - actualSum

    return missingNumber
}

fun main(){
    val numbers = arrayOf(3,7,1,2,6,4)
    val missingNumber = findMissingNumber(numbers)
    println("$missingNumber")
}