// The .csv we will be reading in looks like this:

// "1","M",77,182,77,180
// "2","F",58,161,51,159
// "5","F",59,157,59,155

// These values correspond to (1.) the row number, (2.) the sex of
// the subject, (3.) the height of the subject, (4.) the weight of the
// subject, (5.) the subject's self-reported height, and (6.) the subject's
// self-reported weight.

import scala.io.Source
import scala.reflect.ClassTag
import breeze.linalg.DenseVector

val filename = "/home/ben/Documents/Scala_Project/data/rep_height_weights.csv"
val file = Source.fromFile(filename)
val lines = file.getLines.toVector
val splitLines = lines.map { _.split(',') }

def fromList[T:ClassTag](index:Int, converter:(String => T)):DenseVector[T] =
  DenseVector.tabulate(lines.length) { irow => converter(splitLines(irow)(index)) }

// Interpreting the above function, 'fromLost' takes two arguments, 'index' of
// type integer, and 'converter' which is itself a function. The converter
// function takes an input of type string and outputs an object of whatever type
// is specified when `fromList` is called. Because we are doing type conversion
// from x -> y where the type of y is not explicitly stated, we need to invoke
// a ClassTag. From the Scala documentation:

// "A ClassTag[T] stores the erased class of a given type T, accessible via
// the runtimeClass field. This is particularly useful for instantiating Arrays
// whose element types are unknown at compile time.

// Given these inputs 'fromList' outputs a dense vector. Specifically, Breeze's
// tabluate function creates a dense vector equal to the length of
// 'lines' from the output of converter function.

// Thus, the user specifies an index value which is effectively the column of the
// array. Iterating through every row in the Scala collection `splitLines,`
// the fromList function executes whatever function the user specified in the
// 'converter' argument, but only for the value in that row that corresponds to the
// column of interest. These newly created values are incorporated into the dense
// vector which is the final output.

val genders         = fromList(1, elem => elem.replace("\"", "").head)
val weights         = fromList(2, elem => elem.toDouble)
val heights         = fromList(3, elem => elem.toDouble)
val reportedWeights = fromList(4, elem => elem.toDouble)
val reportedHeights = fromList(5, elem => elem.toDouble)

// Above, you can see the 'fromList' function working its magic, where it is
// creating a dense vector of 'Gender' from index=1, and removing quotation marks
// in the process.

// Now we want to derive some statistics from the data, but to do that we need to
// be able to filter these dense vectors. To do this we employ 'masks' with 'in'
// 'not in' logic. First, create a vector of equal length to the vector of interest,
// but one that consists entirely of the category of interest - 'M'

val male_vector = DenseVector.fill(genders.length)('M')

// Now create a vector of boolean values based on whether the value of gender in
// the Gender vector matches the corresponding value in the masking vector.

val is_male = {genders :== male_vector}

// Now, to get only male heights, we subset the heights vector for those values
// for which 'is_male' is true.

val male_heights = heights(is_male)

// When we check, we see that there are 82 male heights in the height vector of
// total length = 181

male_heights.length

// Similarly, we can use Breeze's numerics package to invoke the indicator function
// (the capital 'I' in the code below. This function treats each 'true' in the
// boolean vector as a 1, which we then sum to  get the number of males that way.
// Note that to invoke the sum operator, we are making use of Breeze's linear
// algebra package.

import breeze.linalg._
import breeze.numerics._

sum(I(is_male))

// Now time for some summary statistics. First, let's call breeze's stats
// package and use it to calculate the mean male height....

import breeze.stats._

mean(heights(is_male))

// and mean female height.

mean(heights(!is_male))

// Let's take the differences between columns in order to understand differences
// in self-reported versus actual values.

val weight_discrepancy = weights - reportedWeights

mean(weight_discrepancy(is_male))

mean(weight_discrepancy(!is_male))

// How many men-under reported their weight?

val weight_mask = (weights >:> reportedWeights).toDenseVector

sum(I(weight_mask &:& is_male))

// Parsing the above expression, an element is TRUE if it is in the list
// of weight under-reporters AND it is in the list of male subjects. True
// elements are converted to 1's and then summed. 