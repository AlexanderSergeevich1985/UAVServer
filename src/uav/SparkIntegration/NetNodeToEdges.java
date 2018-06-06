package uav.SparkIntegration;

import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by administrator on 06.06.18.
 */
public class NetNodeToEdges implements FlatMapFunction<NetNode, NetEdge> {
    public Iterable<NetEdge> call(NetNode node) throws Exception {
        Integer nodeId = node.getNodeId();
        List<Integer> adjVertex = node.getAdjVertex();
        List<Double> adjVertexDist = node.getAdjVertexDist();
        if(adjVertex.size() != adjVertexDist.size()) return null;
        Iterator<Double> adjVertexDistIter = adjVertexDist.iterator();
        List<NetEdge> netEdgesList = adjVertex.stream()
                .map(vertexId -> { return new NetEdge(nodeId, vertexId, adjVertexDistIter.next()); })
                .collect(Collectors.toList());
        return netEdgesList;
    }
}