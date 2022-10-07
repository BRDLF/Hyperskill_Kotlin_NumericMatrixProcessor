package processor

class MatrixProcessorException(message: String?): RuntimeException(message)

object MatrixProcessor {

    fun menu() {
        do {
            try {
                printMenu()
                when (readln().toInt()) {
                    1 -> matrixPrintSum()
                    2 -> matrixMultiplyByConstant()
                    3 -> matrixMultiplyByMatrix()
                    0 -> break
                }
                println()
            } catch(e: MatrixProcessorException) {
                println("The operation cannot be performed.")
                println()
            }
        } while (true)

    }

    private fun printMenu() = println("""
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        0. Exit
        Your choice: 
    """.trimIndent())
}

fun matrixMultiplyByConstant() {
    val m1 = matrixInput()
    val c = readln().toIntOrNull()?: throw MatrixProcessorException("Not an int")
    matrixPrintMultiply(m1, c)
}

fun matrixMultiplyByMatrix() {
    val m1 = matrixInput(" first")
    val m2 = matrixInput(" second")
    if (m1.isEmpty() || m2.isEmpty() || m1.first().isEmpty() || m2.first().isEmpty() || m1.first().size != m2.size) throw MatrixProcessorException("These matrices cannot be multiplied")
    println("The result is:")
    for (row in m1.indices) {
        for (col in m2.first().indices) {
            var product = 0.0
            for (itemIndex in m1[row].indices) {
                product += m1[row][itemIndex] * m2[itemIndex][col]
            }
            print("$product ")
        }
        println()
    }
}

fun matrixInput(num: String = ""): List<List<Double>> {
    print("Enter size of$num matrix: ")
    val size = readln().split(" ").map {it.toIntOrNull()?: throw MatrixProcessorException("$it is not an int")}.take(2)
    println("Enter$num matrix:")
    return List(size[0]) { readln().split(" ").map { it.toDoubleOrNull()?: throw MatrixProcessorException("$it is not a float") }.take(size[1]) }
}

fun matrixPrintSum() {
    val a = matrixInput(" first")
    val b = matrixInput(" second")
    if (a.isEmpty() || b.isEmpty() || a.size != b.size || a.first().size != b.first().size) println("ERROR")
    else{
        println("The result is:")
        for (row in a.indices) {
            for (col in a[row].indices) {
                print("${a[row][col] + b[row][col]} ")
            }
            println()
        }
    }
}

fun matrixPrintMultiply(matrix: List<List<Double>>, someNumber: Int) {
    println("The result is:")
    matrix.forEach{ row ->
        row.forEach{ item -> print("${item * someNumber} ")}
        println()
    }
}

fun main() {
    MatrixProcessor.menu()
}
