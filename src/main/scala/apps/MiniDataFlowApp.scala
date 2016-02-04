package apps

import com.google.cloud.dataflow.sdk.Pipeline
import com.google.cloud.dataflow.sdk.io.TextIO
import com.google.cloud.dataflow.sdk.options.{DirectPipelineOptions, DataflowPipelineOptions, PipelineOptionsFactory}
import com.google.cloud.dataflow.sdk.runners.DirectPipelineRunner
import com.google.cloud.dataflow.sdk.transforms.{Count, DoFn, ParDo}
import com.google.cloud.dataflow.sdk.values.KV

/**
 * Created on: 2/3/16
 * Modified from:
 *  - https://cloud.google.com/dataflow/examples/wordcount-example
 *  - https://github.com/emchristiansen/ScalaDataflow.git
 * @author Kaan Yuksel 
 */
object MiniDataFlowApp extends App {
  println("Creating pipeline...")

  // pipeline definition
  val options = PipelineOptionsFactory.create().as(classOf[DirectPipelineOptions])
  //val options = PipelineOptionsFactory.create().as(classOf[DataflowPipelineOptions])
  options.setProject("Scala Dataflow")
  options.setRunner(classOf[DirectPipelineRunner])
  //options.setStagingLocation("/Users/kyuksel/GitHubClones/scala-dataflow/staging")
  options.setTestSerializability(true)
  options.setTestEncodability(true)
  options.setTestUnorderedness(true)

  val p: Pipeline = Pipeline.create(options)
  p.apply(TextIO.Read.from("/Users/kyuksel/GitHubClones/scala-dataflow/kinglear.characters.txt"))
   .apply(ParDo.of(extractWords))
   .apply(Count.perElement[String]())
   .apply(ParDo.of(formatOutput))
   .apply(TextIO.Write.to("/Users/kyuksel/GitHubClones/scala-dataflow/kinglear.counts.txt"))

  p.run()

  println("Pipeline run!")

  val splitIntoWords = new DoFn[String, String]() {
    override def processElement(c: DoFn[String, String]#ProcessContext) {
      val words = c.element().split("[^a-zA-Z']+")
      for (word <- words) {
        if (!word.isEmpty()) {
          c.output(word)
        }
      }
    }
  }

  val extractWords = new DoFn[String, String]() {
    override def processElement(c: DoFn[String, String]#ProcessContext) {
      c.element.split("[^a-zA-Z']+").filter(_.nonEmpty).map(_ => c.output(_))
    }
  }

  val formatOutput = new DoFn[KV[String, java.lang.Long], String]() {
    override def processElement(c: DoFn[KV[String, java.lang.Long], String]#ProcessContext) {
      c.output(c.element.getKey + ": " + c.element.getValue)
    }
  }
}