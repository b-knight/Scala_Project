//----------------------------------------
//See page 17 of Pascal Bugnion's "Scala for Data Science"
//----------------------------------------

//Import the package for linear algebra.

import breeze.linalg._

//Create a vector named 'v.'

val v1 = DenseVector(1.0, 2.0, 3.0)

//Extract the first element of the vector.

v1(0)

//Multiply the vector times a scalar. While a '*' will work,
//we want to make clear that this is an element-wise
//vector operation, so we repeat the operator with a ':'
//in the middle.

v1 *:* 2.0

//Use element-wise operations with another vector.

val v2 =  DenseVector(4.0, 5.0, 6.0)
v1 +:+ v2

//Element-wise addition with vectors of different lengths
//will throw an error:

val v3 = DenseVector(7.0, 8.0)
//v1 + v3

//We can calculate the dot product as well.

v1 dot v2

// Note that while we are using a dense vector, Breeze also
// supports 'SparseVector' (Breeze was intended for NLP,
// afterall), as well as 'HashVector'.

val m = DenseMatrix((1.0, 2.0, 3.0), (4.0, 5.0, 6.0))

// We can rapidly create vectors of ones or zeros.

val v4 = DenseVector.ones[Double](5)
val v5 = DenseVector.zeros[Int](3)

// Similiar to Python's range and xrange function, we can use
// 'linspace' where linspace(a, b, c) generates a vector of c
// evenly spaced values beginning at a and ending at b

linspace(0, 1, 5)

//Using tabulate, we can create a vector from a function.

val v6 = DenseVector.tabulate(4) {i => 5.0*1.0}

// We can create a matrix from tabulate as well.
// Specify the variable type, the dimensions of the
// matrix, and a function to populate it.
// Here we take the row number [0,1], multiply by 2, and
// then add the column number [0,1,2]

val v7 = DenseMatrix.tabulate[Int](2,3) {
  (irow, icol) => irow * 2 + icol
}

// We can create a vector of random numbers...

val v8 = DenseVector.rand(2)

// ...or a matrix of random numbers

DenseMatrix.rand(2, 3)

// We can create vectors from Scala arrays...

DenseVector(Array(2,3,4))

// ... or from a Scala collection.
// Note how we need to use the 'splat' operator :_*
// to flatten the list

val l = Seq(1, 2, 3)

val v9 = DenseVector(l :_*)
// ------------------------------------------------------------------
// Indexing & Slicing
// ------------------------------------------------------------------
// Breeze let's us do not just indexing, but negative indexing.
// First, create a vector.

val v = DenseVector.tabulate(5) {_.toDouble}

println("The first element of 'v' is " + v(0))
println("The last element of 'v' is " + v(-1))

//We can also slice the vector using a range.
//Note that 'to' is inclusive...
val segment1: DenseVector[Double] = v(0 to 2)

//...while 'until' is exclusive.
val segment2: DenseVector[Double] = v(0 until 2)

// We can easily get the length of the vector:
v.length

//From that, we can iterate in reverse:
v(v.length-1 to 0 by -1)

// Breeze allows the creation of slice vectors. Slice vectors are like
// DenseVectors, but memory is not allocated until called. In this fashion,
// it is more a 'view' than a proper data object

val vslice1 = {
  v(1, 2)
}
println(vslice1)


//Note that because Scala is 'lazy' in its compilation, an error will not
//occur until an illegal object is actually accessed.

//val vslice2 = {
//  v(3, 6)
//}
//println(vslice2)

//One can also access elements in a vector using boolean logic in instances
//where that element is 'true'. First, create a vector of boolean values...

val boolean_vector : (Boolean, Boolean, Boolean, Boolean, Boolean) = (true, false, false, true, true)

//Only the elements 0, 3, and 4 are true and are thus captured in the
//dense vector below.

boolean_vector





