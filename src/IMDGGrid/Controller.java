package IMDGGrid;

import com.hazelcast.spi.ManagedService;
import com.hazelcast.spi.NodeEngine;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by administrator on 08.12.17.
 */
public class Controller {

}

class TestService implements ManagedService {
    private NodeEngine nodeEngine;
    @Override
    public void init( NodeEngine nodeEngine, Properties properties ) {
        System.out.println( "TestService.init" );
        this.nodeEngine = nodeEngine;
    }
    @Override
    public void shutdown( boolean terminate ) {
        System.out.println( "TestService.shutdown" );
    }
    @Override
    public void reset() {
    }
}
