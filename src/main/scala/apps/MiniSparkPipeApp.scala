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
  val conf = new SparkConf().setAppName("HelloWorld").setMaster("local")
  val sc = new SparkContext(conf)

  // do stuff
  val data = List("hi","hello","how","are","you")
  val dataRDD = sc.makeRDD(data)
  val echoScriptPath = "/Users/kyuksel/GitHubClones/scala-dataflow/echo.sh"
  val echoCommand = "echo \"hola amigo\""
  val tabixHeaderExtractionCommand = "tabix -H /Users/kyuksel/BurdenFiles/v3.clean.1000.vcf.gz"
  //val pipeRDD = dataRDD.pipe(echoCommand)
  //val pipeRDD = dataRDD.pipe(echoScriptPath)
  val pipeRDD = dataRDD.pipe(tabixHeaderExtractionCommand)
  val output = pipeRDD.collect()

  // terminate spark context
  sc.stop()

  println("App run!")

}
