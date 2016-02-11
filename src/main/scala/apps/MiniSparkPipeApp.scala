package apps

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

/**
 * Created on: 2/3/16
 * @author Kaan Yuksel 
 */
object MiniSparkPipeApp extends App {
  println("Running app...")

  // initialise spark context
  val conf = new SparkConf().setAppName("MiniSparkPipe").setMaster("local")
  val sc = new SparkContext(conf)

  // do stuff
  val vcf = sc.parallelize(List(
    (("s1", "v1"), 0),
    (("s1", "v2"), 2),
    (("s2", "v2"), 1),
    (("s2", "v4"), 1),
    (("s2", "v3"), 0),
    (("s3", "v1"), 0),
    (("s3", "v3"), 2)))

  // find singletons

  // terminate spark context
  sc.stop()

  println("App run!")

}
