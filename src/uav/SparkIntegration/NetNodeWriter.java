package uav.SparkIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by administrator on 05.06.18.
 */

public class NetNodeWriter implements FlatMapFunction<Iterator<NetNode>, String> {
    public Iterable<String> call(Iterator<NetNode> netNodes) throws Exception {
        List<String> netNodesList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        while(netNodes.hasNext()) {
            NetNode netNode = netNodes.next();
            try {
                netNodesList.add(mapper.writeValueAsString(netNode));
            }
            catch(Exception ex) {
            }
        }
        return netNodesList;
    }
}
