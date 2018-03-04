import scala.math._


def sqrt(x: Double) = {

  def sqrtIter(guess: Double, x: Double): Double =
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)

  def square(guess: Double) = guess * guess

  def improve(guess: Double, x: Double) =
    (guess + x / guess) / 2

  def isGoodEnough(guess: Double, x: Double) =
    abs(square(guess) - x) < 0.001

  sqrtIter(1.0, x)
}


def factorial(n: Int): Int = {
  def iter(x: Int, result: Int): Int =
    if (x == 0) result
    else iter(x - 1, result * x)

  iter(n, 1)
}

factorial(4)


//----------------------------------------
//See page 17 of Pascal Bugnion's "Scala for Data Science"
//----------------------------------------

//Import the package for linear algebra.

import breeze.linalg._

//Create a vector named 'v.'

val v1 = DenseVector(1.0, 2.0, 3.0)

//Extract the first element of the vector.

v1(0)