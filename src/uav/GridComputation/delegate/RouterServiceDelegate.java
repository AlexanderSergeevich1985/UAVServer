package uav.GridComputation.delegate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uav.GridComputation.RouterResponse;

import java.util.Optional;

@Service
public class RouterServiceDelegate {
    static final Logger logger = LoggerFactory.getLogger(RouterServiceDelegate.class);

    private final RestTemplate restTemplate;
    private String targetServiceURL;

    @Autowired
    public RouterServiceDelegate(RestTemplate restTemplate) {
        if(restTemplate != null)
            this.restTemplate = restTemplate;
        else
            this.restTemplate = new RestTemplate();
    }
    public void setTargetServiceURL(String targetServiceURL) {
        this.targetServiceURL = targetServiceURL;
    }
    public String getTargetServiceURL() {
        return this.targetServiceURL;
    }
    @HystrixCommand(fallbackMethod = "getDefaultProductInventoryByCode",
            commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="70"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="10"),
            @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value="200")
    })
    public Optional<RouterResponse> getRouterResponse(String callerIP) {
        ResponseEntity<RouterResponse> response = restTemplate.getForEntity(targetServiceURL, RouterResponse.class, callerIP);
        if(response.getStatusCode() == HttpStatus.OK) {
            return Optional.ofNullable(response.getBody());
        }
        else {
            logger.error("Unable to route for callerIP: "+callerIP+", StatusCode: "+response.getStatusCode());
            return Optional.empty();
        }
    }
    @SuppressWarnings("unused")
    Optional<RouterResponse> getDefaultRouterResponse(String callerIP) {
        logger.info("Router service is down! Returning default response for callerIP: "+callerIP);
        RouterResponse response = new RouterResponse();
        return Optional.ofNullable(response);
    }
}
