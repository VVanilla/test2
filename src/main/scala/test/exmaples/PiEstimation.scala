package test.exmaples

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._

import scala.collection.mutable

object PiEstimation {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val sc = new SparkContext(conf)
    val NUM_SAMPLES = 100000

    val count = sc.parallelize(1 to NUM_SAMPLES)
      .map(idx => {
        var res = 0
        for (i <- 0 until 1000000) {
          val x = math.random
          val y = math.random
          if (x * x + y * y < 1) {
            res += 1
          }
        }
        (res / 10000.0, 1)
      })
      .reduce((a, b) =>{
        (a._1 + b._1, a._2 + b._2)
      })


    val res = count._1 / count._2
    sc.parallelize(Array(res)).saveAsTextFile("/user/root/test/pi_res.txt")

  }

}
