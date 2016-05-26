package learn

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by xinmei on 16/5/25.
  */
object wordCount {


  def main(args:Array[String])={


    val conf = new SparkConf()

    val sc  = new SparkContext(conf)




    val hdfspath = "hdfs:///lxw/test"
    val savepath = "hdfs:///lxw/result"

    val hadoopConf = sc.hadoopConfiguration


    val awsAccessKeyId = args(0)
    val awsSecretAccessKey = args(1)

    val anchordate = "20160426"

    hadoopConf.set("fs.s3n.impl", "org.apache.hadoop.fs.s3native.NativeS3FileSystem")

    hadoopConf.set("fs.s3n.awsAccessKeyId", awsAccessKeyId)

    hadoopConf.set("fs.s3n.awsSecretAccessKey", awsSecretAccessKey)


    val text = sc.textFile(hdfspath)
      .flatMap{case line  =>
        val item  = line.replaceAll(" +", " ").split("\\W+").map(y => (y, 1))
        item
      }
      .reduceByKey((a:Int, b:Int) => a + b)
      //.collect()
      //.foreach(x => println("lixuefei log " + x))

    text.saveAsTextFile(savepath)

    sc.stop()

  }




}
