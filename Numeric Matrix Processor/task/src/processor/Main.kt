package processor

class MatrixProcessorException(message: String?): RuntimeException(message)


fun matrixMultiplyByConstant() {
    try {
        val m1 = matrixInput(matrixSizeInput())
        val c = readln().toIntOrNull()?: throw MatrixProcessorException("Not an int")
        matrixPrintMultiply(m1, c)
    } catch(e: MatrixProcessorException) {
        println(e.message)
    }

}

fun matrixSizeInput(): List<Int> = readln().split(" ").map {it.toIntOrNull()?: throw MatrixProcessorException("Not an int")}.take(2)

fun matrixInput(size: List<Int>): List<List<Int>> = List(size[0]) { readln().split(" ").map { it.toIntOrNull()?: throw MatrixProcessorException("Not an int") }.take(size[1]) }

fun matrixPrintSum(a: List<List<Int>>, b: List<List<Int>>) {
    if (a.isEmpty() || b.isEmpty() || a.size != b.size || a.first().size != b.first().size) println("ERROR")
    else for (row in a.indices) {
        for (col in a[row].indices) {
            print("${a[row][col] + b[row][col]} ")
        }
        println()
    }
}

fun matrixPrintMultiply(matrix: List<List<Int>>, someNumber: Int) {
    matrix.forEach{ row ->
        row.forEach{ item -> print("${item * someNumber} ")}
        println()
    }
}

fun main() {
    matrixMultiplyByConstant()
}
