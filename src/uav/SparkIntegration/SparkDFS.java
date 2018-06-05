package uav.SparkIntegration;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by administrator on 05.06.18.
 */

@Component
public class SparkDFS {
    @Autowired
    private JavaSparkContext javaSparkContext;

    public List<NetNode> loadFromJson(String pathToFile) {
        JavaRDD<NetNode> netNodesRDD = javaSparkContext.textFile(pathToFile).mapPartitions(new NetNodesParser());
        return netNodesRDD.collect();
    }
    public void writeToFile(List<NetNode> netNodesList, String pathToFile) {
        JavaRDD<NetNode> netNodesRDD = javaSparkContext.parallelize(netNodesList);
        JavaRDD<String> stringRDD = netNodesRDD.mapPartitions(new NetNodeWriter());
        stringRDD.saveAsTextFile(pathToFile);
    }
}
