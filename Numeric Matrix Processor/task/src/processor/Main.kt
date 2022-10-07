package processor

object MatrixProcessor {
    class MatrixProcessorException(message: String?): RuntimeException(message)

    fun start() {
        do {
            try {
                printMenu()
                when (readln().trim()) {
                    "1" -> matrixSum()
                    "2" -> matrixMultiplyByConstant()
                    "3" -> matrixMultiplyByMatrix()
                    "4" -> matrixTranspose()
                    "5" -> matrixDeterminant()
                    "0" -> break
                }
                println()
            } catch(e: MatrixProcessorException) {
                println("${e.message}\n")
            }
        } while (true)

    }

    private fun printMenu() = print("""
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        4. Transpose matrix
        5. Calculate a determinant
        0. Exit
        Your choice: 
    """.trimIndent())
    private fun matrixInput(num: String = ""): List<List<Double>> {
        print("Enter size of$num matrix: ")
        val size = readln().split(" ").map {it.toIntOrNull()?: throw MatrixProcessorException("$it is not an int")}.take(2)
        println("Enter$num matrix:")
        return List(size[0]) { readln().split(" ").map { it.toDoubleOrNull()?: throw MatrixProcessorException("$it is not a float") }.take(size[1]) }
    }
    private fun matrixDeterminant() {
        val m1 = matrixInput()
        val memo: MutableMap<List<List<Double>>, Double> = mutableMapOf<List<List<Double>>, Double>()
        fun recursiveDeterminant(passed: List<List<Double>>, subIndex: Int = 0): Double {
            if (passed.size != passed.first().size) throw MatrixProcessorException("Cannot find determinant of non-square matrix")
            if (passed.size == 2) {
                return passed[0][0]*passed[1][1] - passed[1][0]*passed[0][1]
            }
            var returnValue = 0.0
            for (index in passed[0].indices) {
                val cofactor = passed[0][index]
                if (passed.size < m1.size-1) { //prevent memo-ing first-level submatrix of given matrix
                    val newList = passed.filterIndexed { row, _ -> row != 0 }.map{ it.filterIndexed{ col, _ -> col != index}}
                    if (memo[newList]==null) {
                        memo[newList] = recursiveDeterminant(newList)
                    }
                    returnValue = if (index%2 == 0) {
                        returnValue + cofactor*memo[newList]!!
                    } else returnValue - cofactor*memo[newList]!!
                }
                else {
                    val newList = passed.filterIndexed { row, _ -> row != 0 }.map{ it.filterIndexed{ col, _ -> col != index}}
                    returnValue = if (index%2 == 0) {
                        returnValue + cofactor*recursiveDeterminant(newList)
                    } else returnValue - cofactor*recursiveDeterminant(newList)
                }
            }
            return returnValue
        }

        println("The result is:\n${recursiveDeterminant(m1)}")
    }
    private fun matrixMultiplyByConstant() {
        val m1 = matrixInput()
        print("Enter constant: ")
        val c = readln().trim().toDoubleOrNull()?: throw MatrixProcessorException("Not a valid constant")
        val newMatrix = List<List<Double>>(m1.size) {
                row -> List<Double>(m1[row].size) {
                col -> m1[row][col] * c }
        }
        printMatrix(newMatrix)
    }
    private fun matrixMultiplyByMatrix() {
        val m1 = matrixInput(" first")
        val m2 = matrixInput(" second")
        if (m1.isEmpty() || m2.isEmpty() || m1.first().isEmpty() || m2.first().isEmpty() || m1.first().size != m2.size) throw MatrixProcessorException("These matrices cannot be multiplied")
        val result = List<List<Double>>(m1.size) {
            row -> List<Double>(m2[row].size){
                col -> m1[row].mapIndexed{ index, d -> d*m2[index][col] }.sum()
            }
        }
        printMatrix(result)
    }
    private fun matrixTranspose() {
        print("""
            
            1. Main diagonal
            2. Side diagonal
            3. Vertical line
            4. Horizontal line
            Your choice: 
        """.trimIndent())
        when (val choice = readln().trim().toIntOrNull()?: throw MatrixProcessorException("Invalid choice")) {
            1, 2, 3, 4 -> runTranspose(choice)
            else -> throw MatrixProcessorException("Invalid choice")
        }
    }
    private fun runTranspose(choice: Int) {
        val m1 = matrixInput()
        val result = when(choice) {
            1 -> List<List<Double>>(m1.first().size){ row -> List<Double>(m1.size){ col -> m1[col][row]} }
            2 -> List<List<Double>>(m1.first().size){ row -> List<Double>(m1.size){ col -> m1[m1[row].lastIndex - col][m1.lastIndex - row]} }
            3 -> List<List<Double>>(m1.size){ row -> List<Double>(m1[row].size){ col -> m1[row][m1[row].lastIndex - col]} }
            4 -> List<List<Double>>(m1.size){ row -> List<Double>(m1[row].size){ col -> m1[m1.lastIndex - row][col]} }
            else -> throw MatrixProcessorException("Invalid choice, but you shouldn't see this!uh-oh!")
        }
        printMatrix(result)
    }
    private fun matrixSum() {
        val a = matrixInput(" first")
        val b = matrixInput(" second")
        if (a.isEmpty() || b.isEmpty() || a.size != b.size || a.first().size != b.first().size) println("ERROR")
        else{
            val newMatrix = List<List<Double>>(a.size) { row -> List<Double>(a[row].size) { col -> a[row][col] + b[row][col] } }
            printMatrix(newMatrix)
        }
    }
    private fun printMatrix(matrix: List<List<Double>>) {
        println("The result is:")
        matrix.forEach{ row -> row.forEach{
                element -> print("${element.toString().dropLastWhile { it == '0' }.dropLastWhile { it == '.' }} ") }
                println()
        }
    }
}

fun main() {
    MatrixProcessor.start()
}
