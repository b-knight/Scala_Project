import breeze.linalg._
import breeze.plot._
import breeze.numerics._
import org.jfree.chart.axis.NumberTickUnit

// Breeze-Viz uses the same general framework as Python's
// Matplotlib wherein one or more subplots are appended to
// a parent Figure object. It it built upon JFreeChart, a Java
// chart library founded in the year 2000. Breeze-Vis is not as
// well-developed a Python's Matplotlib, and is definitely lacking
// when compared to R's GGPlot. When the desired functionality
// is unavailable, it is necessary to access the underlying
// JFreeChart object.

// The linspace command (a,b,c) creates a vector of c values
// within the range [a, b].
val x = linspace(-4.0, 4.0, 200)


// Now we take those values, and apply a sigmoid function
// i.e. f(x) = 1/(1+e^x).
val fx = sigmoid(x)

// The first step to creating a visualization in Breeze-viz
// is to create an empty figure.
val fig = Figure()

// Now we can append a subplot to the parent plot.
val plt = fig.subplot(0)

// With these lines, we are iteratively adding adding data points
// of fx as a function of x.
plt += plot(x, fx, name="S(x)")


// Now let's add a couple of more curves.
val f2x  = sigmoid(2.0*x)
val f10x = sigmoid(10.0*x)


plt += plot(x, f2x,  name="S(2x)")
plt += plot(x, f10x, name="S(10x)")

// Add a legend.
plt.legend = true

// We can reduce the range of the x-axis by use of the xlim
// command.
plt.xlim(-4.0, 4.0)

// To adjust the tick intervals on the y-axis, it is necessary
// to access the underlying JFreeChart Axis object.
plt.yaxis.setTickUnit(new NumberTickUnit(0.1))

// Similiar to Matplotlib's abline() functionality, it can be
// helpful to add vertical or horizontal lines to our plot.
// For this, we need to import DomainMarker and RangeMarker
// functionality fro JFreeChart
import org.jfree.chart.plot.ValueMarker
plt.plot.addDomainMarker(new ValueMarker(0.0))
plt.plot.addRangeMarker(new ValueMarker(1.0))

// Finally, let's label our axes.
plt.xlabel = "x"
plt.ylabel = "f(x)"
plt.title = "A Demonstration of Plotting with " +
            "Breeze-Viz Using Sigmoid Curves"

fig.refresh()

// We can save out plot using the following statement:
fig.saveas("sigmoid.png")