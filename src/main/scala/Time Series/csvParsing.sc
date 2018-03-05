import scala.io.Source
import scala.collection.mutable.ListBuffer
var valueOne = new ListBuffer[Any]()
val fileName = "/home/ben/Documents/Scala_Project/src/main/scala/data/rep_height_weights.csv"
val file = Source.fromFile(fileName)
val lines = file.getLines.drop(0)
//for (i <- lines) {valueOne += List(i.split(","))}
//valueOne(0)
for (i <- lines)
{println(i)}