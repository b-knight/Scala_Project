import breeze.linalg.DenseVector
import breeze.linalg._
import breeze.optimize._

// For a top optimization function, let's take the sum of squares:
//
// f(X) = SUM(X^2)

def f(xs:DenseVector[Double]) = sum(xs ^:^ 2.0)

// To optimize this function, we typically need the gradient. The gradient
// is a vector of partial derivatives of the function with respect to each
// of the function's inputs. For example, the gradient for the function
// A + B + C will be a vector of length 3 corresponding to the partial
// derivatives with respect to A, b, and C.
//
// The function we are optimizing - the sum of squares - takes only one
// input (x) which is then summed. To get the gradient of the toy loss
// function, we take the partial derivative with respect to X, leaving us
// with:
//
// Delta(f) = 2X

// We can capture this gradient in the following Scala function.

def gradf(xs:DenseVector[Double]) = 2.0 *:* xs

// Let's test the gradient function using a vector of ones.

val ones_vector = DenseVector.ones[Double](3)

gradf(ones_vector)

// To create the optimizer, we can use Breeze's DiffFunction. This function
// will make use the the gradient function we derived above. When executed,
// it will return the gradient at the specified point. It does this by
// invoking the calculate function, which will return a tuple of
// the function to be optimized and its gradient.

val optTrait = new DiffFunction[DenseVector[Double]]
{
  def calculate(xs:DenseVector[Double]) = (f(xs), gradf(xs))
}

// With the optTrait created we can now employ Breeze's minimize
// function. The minimize function takes two inputs: the function
// to be optimized and a starting point for the limited-memory
// BFGS optimization algorithm.

val minimum = minimize(optTrait, DenseVector(1.0, 1.0, 1.0))

// Printing the value of minimize...

minimum

// The sum of squares is minimized by inputs of zeroes, which
// Breeze's minimize function correctly found. 