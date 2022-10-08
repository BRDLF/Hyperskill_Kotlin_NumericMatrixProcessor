package processor

object MatrixProcessor {
    class MatrixProcessorException(message: String?): RuntimeException(message)
    private val determinantMemo: MutableMap<List<List<Double>>, Double> = mutableMapOf<List<List<Double>>, Double>()

    fun start() {
        do {
            try {
                showMenu()
                when (readln().trim()) {
                    "1" -> printMatrix(matrixSum())
                    "2" -> printMatrix(matrixMultiplyByConstant())
                    "3" -> printMatrix(matrixMultiplyByMatrix())
                    "4" -> printMatrix(matrixTranspose())
                    "5" -> println("The result is:\n${matrixDeterminant()}")
                    "6" -> printMatrix(matrixInverse())
                    "0" -> break
                }
                println()
            } catch(e: MatrixProcessorException) {
                println("${e.message}\n")
            }
        } while (true)

    }

    private fun showMenu() = print("""
        1. Add matrices
        2. Multiply matrix by a constant
        3. Multiply matrices
        4. Transpose matrix
        5. Calculate a determinant
        6. Inverse Matrix
        0. Exit
        Your choice: 
    """.trimIndent())
    private fun getMatrix(num: String = ""): List<List<Double>> {
        print("Enter size of$num matrix: ")
        val size = readln().split(" ").map {it.toIntOrNull()?: throw MatrixProcessorException("$it is not an int")}.take(2)
        println("Enter$num matrix:")
        return List(size[0]) { readln().split(" ").map { it.toDoubleOrNull()?: throw MatrixProcessorException("$it is not a float") }.take(size[1]) }
    }
    private fun getDouble(): Double {
        print("Enter constant: ")
        return readln().trim().toDoubleOrNull()?: throw MatrixProcessorException("Not a valid constant")
    }
    private fun getTransposeLine(): Int {
        print("""
            
            1. Main diagonal
            2. Side diagonal
            3. Vertical line
            4. Horizontal line
            Your choice: 
        """.trimIndent())
        when (val choice = readln().trim().toIntOrNull()?: throw MatrixProcessorException("Invalid choice")) {
            1, 2, 3, 4 -> return choice
            else -> throw MatrixProcessorException("Invalid choice")
        }
    }
    private fun printMatrix(matrix: List<List<Double>>) {
        println("The result is:")
        matrix.forEach{ row -> row.forEach{
                element -> print("${element.toString().dropLastWhile { it == '0' }.dropLastWhile { it == '.' }} ") }
            println()
        }
    }

    private fun matrixSum(): List<List<Double>> {
        val firstMatrix = getMatrix(" first")
        val secondMatrix = getMatrix(" second")
        if (firstMatrix.isEmpty() || secondMatrix.isEmpty() || firstMatrix.size != secondMatrix.size || firstMatrix.first().size != secondMatrix.first().size) println("ERROR")
        return List<List<Double>>(firstMatrix.size) { row -> List<Double>(firstMatrix[row].size) { col -> firstMatrix[row][col] + secondMatrix[row][col] } }
    }
    private fun matrixMultiplyByConstant(passedMatrix: List<List<Double>> = getMatrix(), constant: Double = getDouble()): List<List<Double>> {
        return List<List<Double>>(passedMatrix.size) {
                row -> List<Double>(passedMatrix[row].size) {
                col -> passedMatrix[row][col] * constant }
        }
    }
    private fun matrixMultiplyByMatrix(firstMatrix: List<List<Double>> = getMatrix(" first"), secondMatrix: List<List<Double>> = getMatrix(" second")): List<List<Double>> {
        if (firstMatrix.isEmpty() || secondMatrix.isEmpty() || firstMatrix.first().isEmpty() || secondMatrix.first().isEmpty() || firstMatrix.first().size != secondMatrix.size) throw MatrixProcessorException("These matrices cannot be multiplied")
        return List<List<Double>>(firstMatrix.size) {
                row -> List<Double>(secondMatrix[row].size){
                col -> firstMatrix[row].mapIndexed{ index, d -> d*secondMatrix[index][col] }.sum()
        }
        }
    }
    private fun matrixTranspose(transposeLine: Int = getTransposeLine(), passedMatrix: List<List<Double>> = getMatrix()): List<List<Double>> {
        return when(transposeLine) {
            1 -> List<List<Double>>(passedMatrix.first().size){ row -> List<Double>(passedMatrix.size){ col -> passedMatrix[col][row]} }
            2 -> List<List<Double>>(passedMatrix.first().size){ row -> List<Double>(passedMatrix.size){ col -> passedMatrix[passedMatrix[row].lastIndex - col][passedMatrix.lastIndex - row]} }
            3 -> List<List<Double>>(passedMatrix.size){ row -> List<Double>(passedMatrix[row].size){ col -> passedMatrix[row][passedMatrix[row].lastIndex - col]} }
            4 -> List<List<Double>>(passedMatrix.size){ row -> List<Double>(passedMatrix[row].size){ col -> passedMatrix[passedMatrix.lastIndex - row][col]} }
            else -> throw MatrixProcessorException("Invalid choice, but you shouldn't see this!uh-oh!")
        }
    }
    private fun matrixDeterminant(passedMatrix: List<List<Double>> = getMatrix()): Double {
        if (passedMatrix.size != passedMatrix.first().size) throw MatrixProcessorException("Cannot find determinant of non-square matrix")
        if (passedMatrix.size == 2) {
            return passedMatrix[0][0]*passedMatrix[1][1] - passedMatrix[1][0]*passedMatrix[0][1]
        }
        var returnValue = 0.0
        for (index in passedMatrix[0].indices) {
            val element = passedMatrix[0][index]
            val newList = passedMatrix.filterIndexed { row, _ -> row != 0 }.map{ it.filterIndexed{ col, _ -> col != index}}
            if (determinantMemo[newList]==null) {
                determinantMemo[newList] = matrixDeterminant(newList)
            }
            returnValue = if (index%2 == 0) { returnValue + element*determinantMemo[newList]!!
            } else returnValue - element*determinantMemo[newList]!!

        }
        return returnValue
    }
    private fun matrixCofactor(passedMatrix: List<List<Double>> = getMatrix()): List<List<Double>> {
        val cofactorMatrix = MutableList<MutableList<Double>>(passedMatrix.size){MutableList<Double>(passedMatrix.size){0.0}}
        for (rowIndex in cofactorMatrix.indices) {
            for (elementIndex in cofactorMatrix[rowIndex].indices) {
                cofactorMatrix[rowIndex][elementIndex] = matrixDeterminant(passedMatrix.filterIndexed { row, _ -> row != rowIndex }.map{ it.filterIndexed{ col, _ -> col != elementIndex}})
                if ((rowIndex + elementIndex)%2 == 1) cofactorMatrix[rowIndex][elementIndex] = -cofactorMatrix[rowIndex][elementIndex]
            }
        }
        return cofactorMatrix
    }
    private fun matrixInverse(passedMatrix: List<List<Double>> = getMatrix()): List<List<Double>> {
        val det = matrixDeterminant(passedMatrix)
        if (det == 0.0) throw MatrixProcessorException("Determinant of given matrix is Zero")
        return matrixMultiplyByConstant(matrixTranspose(1, matrixCofactor(passedMatrix)), 1/det)
    }
}

fun main() {
    MatrixProcessor.start()
}
