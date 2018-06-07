
/**The MIT License (MIT)
Copyright (c) 2018 by AleksanderSergeevich
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package uav.SparkIntegration;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SparkFS {
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
    public NetNode findNodeById(JavaRDD<NetNode> netNodesRDD, Integer nodeId) {
        List<NetNode> nodes = netNodesRDD.filter(new Function<NetNode, Boolean>() {
            @Override
            public Boolean call(NetNode netNode) throws Exception {
                return netNode.getNodeId()==nodeId ? true : false;
            }
        }).distinct().collect();
        if(nodes.isEmpty()) return null;
        return nodes.get(0);
    }
    public List<NetEdge> netNodesToEdges(JavaRDD<NetNode> netNodesRDD) {
        return netNodesRDD.flatMap(new NetNodeToEdges()).distinct().collect();
    }
}
