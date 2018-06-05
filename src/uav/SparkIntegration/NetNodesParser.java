package uav.SparkIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by administrator on 05.06.18.
 */

public class NetNodesParser implements FlatMapFunction<Iterator<String>, NetNode> {
    public Iterable<NetNode> call(Iterator<String> lines) throws Exception {
        List<NetNode> netNodesList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        while(lines.hasNext()) {
            String line = lines.next();
            try {
                netNodesList.add(mapper.readValue(line, NetNode.class));
            }
            catch(Exception ex) {
            }
        }
        return netNodesList;
    }
}
