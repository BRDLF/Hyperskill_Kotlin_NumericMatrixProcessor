package processor



fun matrixAdd() {
    val m1 = matrixInput(matrixSizeInput())
    val m2 = matrixInput(matrixSizeInput())
    matrixPrintSum(m1, m2)
}

fun matrixSizeInput(): List<Int> = readln().split(" ").map {it.toIntOrNull()?: throw RuntimeException("Not an int")}.take(2)

fun matrixInput(size: List<Int>): List<List<Int>> = List(size[0]) { readln().split(" ").map { it.toIntOrNull()?: throw RuntimeException("Not an int") }.take(size[1]) }

fun matrixPrintSum(a: List<List<Int>>, b: List<List<Int>>) {
    if (a.isEmpty() || b.isEmpty() || a.size != b.size || a.first().size != b.first().size) println("ERROR")
    else for (row in a.indices) {
        for (col in a[row].indices) {
            print("${a[row][col] + b[row][col]} ")
        }
        println()
    }
}

fun main() {
    matrixAdd()
}
