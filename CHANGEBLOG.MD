# Hyperskill_Kotlin_NumericMatrixProcessor

### Stage 5/6: Determined!

Time and time again I learn the valuable lesson of KISS.
I wanted to solve with memoization. For some reason decided to use a List instead of a Map. Spent an hour breaking my brain over how to math out finding the spot in the list based on what sub matrix of what sub matrix I was using, passing unneeded variables etc.
Then I thought maybe I should use Gauss transformation since that's more efficient. But I'm not familiar with that.
Eventually I just used a dang Map. Problem solved.

It's been a while since I did anything with matrices... Gosh, I'm rusty. This has been a nice refresher, if a frustrating one.

### Stage 4/6: Transpose

I initially sat down to just change the organization of the code, didn't mean to finish this stage today. That said, this wasn't terribly hard to implement after the fact.
I restructured the matrix by matrix multiplication function so that it uses lambdas instead of nested for loops. And I made a function to print a passed matrix instead of putting a print loop in every. single. function.
Overall, thins are cleaner and nicer. Though the lambdas were hard for me to parse at first. I think I've got my head around it.

### Stage 3/6: Matrix by matrix multiplication

For the most part I think I understand matrices, but I had another simple mistake prop up.
I was comparing the row count of the first matrix to the column count of the second, instead of the column count of the first to the row count of the second.
So I was getting a very unhelpful error from the unit tests "Could not evaluate answer"
I don't think it's cheating to read the supplied unit tests. Doing so let me see it was expecting an answer, and showed me my error.

### Stage 2/6: Multiplication by number

This was simple. Multiply each element by a constant.

### Stage 1/6: Addition

Pretty simple stuff. For the last few projects I'd been putting everything in a singleton and building from there. I don't know that I'll continue to do this.
For now, it's just a mess of functions, since I expect the goals of the project to change soon. \
Some functions are rather dense single-lines. I'm unsure how wise it is to do this. It might make things harder to diagnose in the future.
Though, I like how it looks at the moment.