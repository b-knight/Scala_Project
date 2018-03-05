/**
  * Performs bivariate regression.
  *
  * Executes ordinary least squares on 2 lists of type double
  * and outputs the coefficients (i.e. slope and intercept).
  */


def reg(x: List[Double], y: List[Double]) = {
  if (x.size != y.size)
  {println("Input vectors are not of equal length.")}
  else {
    def sqr(x: Double) = x * x

    val sumY = y.sum
    val sumXsqr = x.map(sqr).sum
    val sumYsqr = y.map(sqr).sum
    val sumx = x.sum
    val sumxy = x.zip(y).map { case (x, y) => x * y }.sum
    val n = x.length
    val sqrsumofx = sqr(sumx)
    //    val b = ((sumY * sumXsqr) - (sumx * sumxy)) /
    //      ((n * sumXsqr) - sqrsumofx)
    //    val m = ((n * sumxy) - (sumx * sumY)) /
    //      ((n * sumXsqr) - sqrsumofx)
    val p = (n * sumxy - sumx * sumY) /
      Math.sqrt((n * sumXsqr - sqr(sumx)) * (n * sumYsqr - sqr(sumY)))
    val output: Double = p
    output
  }
}

//val x: List[Double] = List(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0,
//                           8.0, 9.0, 10.0)
//val y: List[Double] = List(-1.0, -2.0, -3.0, -4.0, -5.0, -6.0, -7.0,
//                           -8.0, -9.0, -10.0)
//reg(x,y)



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
  var resultSet = new ListBuffer[Any]()
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
    var pearson = reg(valueOne.toList, valueTwo.toList)
    resultSet += pearson
  }
  val output = lagVector zip resultSet
  output
}

val x: List[Double] = List(1.0, 2.0, 1.0, 2.0, 1.0, 2.0, 1.0)
acf(3, x)

import scala.io.Source
//import breeze.linalg.DenseVector

val filename = "/home/ben/Documents/Scala_Project/src/main/scala/data/rep_height_weights.csv"
val file = Source.fromFile(filename)
val lines = file.getLines.toVector
val splitLines = lines.map { _.split(',') }
val excerpt = lines.drop(1).grouped(103).map(_.head).toList

//def fromList[T:ClassTag](index:Int, converter:(String => T)):DenseVector[T] =
//  DenseVector.tabulate(lines.length) { irow => converter(splitLines(irow)(index)) }








