/**
  * Performs bivariate regression.
  *
  * Executes ordinary least squares on 2 lists of type double
  * and outputs the coefficients (i.e. slope and intercept).
  */

//------------------------------------------------------------------------------
def bivar(x: List[Double], y: List[Double]):
  List[Double] = {
  def sqr(x: Double) = x*x
  val sum_y = y.sum
  val sum_x_sqr = x.map(sqr).sum
  val sum_x = x.sum
  val sum_xy = x.zip(y).map { case (x, y) => x * y }.sum
  val n = x.length
  val sqr_sum_of_x = sqr(sum_x)
  val b = ((sum_y*sum_x_sqr) - (sum_x * sum_xy)) /
    ((n * sum_x_sqr) - sqr_sum_of_x)
  val m = ((n*sum_xy) - (sum_x * sum_y)) /
    ((n * sum_x_sqr) - sqr_sum_of_x)
  val output: List[Double] = List(m, b)
  output
}

//val x: List[Double] = List(1.0, 2.0, 3.0, 4.0, 5.0,  6.0, 7.0,
//                           8.0, 9.0, 10.0)
//val y: List[Double] = List(1.0, 2.0, 4.0, 8.0, 16.0, 32.0,
//  64.0, 128.0, 256.0, 512.0)
//
//val results = bivar(x,y).headOption
//results

//------------------------------------------------------------------------------
/**
  * Creates 2 lists of values with a specified lag.
  *
  */

def lag(lag: Int, x: List[Double], y: List[Double]): List[(Double, Double)] = {
  val output = {
    if (lag == 0) {x zip y}
    else {
      val x_ListBuffer = scala.collection.mutable.ListBuffer(x: _*)
      x_ListBuffer.remove(x_ListBuffer.size - lag, lag)
      val x_immutable = x_ListBuffer.toList
      val y_ListBuffer = scala.collection.mutable.ListBuffer(y: _*)
      y_ListBuffer.remove(0, lag)
      val y_immutable = y_ListBuffer.toList
      val k  = x_immutable zip y_immutable
      k
        }
    }
  output
}

//val x: List[Double] = List(1.0, 2.0, 3.0, 4.0, 5.0)
//val y: List[Double] = List(1.0, 2.0, 3.0, 4.0, 5.0)
//lag(2, x, y)
//------------------------------------------------------------------------------



def acf(numberOfLags: Int, dataVector: List[Double]) = {
  import scala.collection.mutable.ListBuffer
  val lagVector = List.range(0, numberOfLags+1)
  for (i <- lagVector)
  {var a = lag(i, dataVector, dataVector)
    var valueOne = new ListBuffer[Double]()
    var valueTwo = new ListBuffer[Double]()
//    println(a)
    for (k <- a)
    {valueOne += k._1
      valueTwo += k._2
    }
//    println(valueOne.toList)
//    println(valueTwo.toList)
    println(bivar(valueOne.toList, valueTwo.toList))
  }

}

val x: List[Double] = List(1.0, 2.0, 1.0, 2.0, 1.0, 2.0, 1.0)
acf(3, x)










